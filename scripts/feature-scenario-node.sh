#!/usr/bin/env bash
set -euo pipefail

if [ "$#" -lt 1 ] || [ "$#" -gt 2 ]; then
  echo "Usage: $0 <node> [timeout-seconds]" >&2
  exit 1
fi

node="$1"
timeout_seconds="${2:-240}"

cd "$(dirname "$0")/.."

if ! just list-nodes | grep -Fxq "$node"; then
  echo "Unknown node: $node" >&2
  exit 1
fi

version="${node%-*}"
loader="${node##*-}"
gradle_task=":$loader:$version:runClient"
props="versions/$version/gradle.properties"

resolve_java_for_version() {
  local props_file="$1"
  local java_version sdkman_dir best_match java_home
  java_version="$(sed -nE 's/^project\.build-java=([0-9]+).*/\1/p' "$props_file" | head -n1)"
  if [ -z "$java_version" ]; then
    java_version="$(sed -nE 's/^project\.java=([0-9]+).*/\1/p' "$props_file" | head -n1)"
  fi
  sdkman_dir="$HOME/.sdkman/candidates/java"
  if [ -n "$java_version" ] && [ -d "$sdkman_dir" ]; then
    best_match="$(find "$sdkman_dir" -mindepth 1 -maxdepth 1 -type d -printf '%f\n' | grep -E "^${java_version}(\\.|-)" | sort -V | tail -n1 || true)"
    if [ -n "$best_match" ]; then
      java_home="$sdkman_dir/$best_match"
      export JAVA_HOME="$java_home"
      export PATH="$JAVA_HOME/bin:$PATH"
    fi
  fi
}

resolve_java_for_version "$props"

case "$loader" in
  fabric)
    instance_path="fabric/versions/$version/runs/client/teakit/instance.json"
    save_root="fabric/versions/$version/runs/client/saves"
    ;;
  forge|neoforge)
    instance_path="$loader/versions/$version/run/teakit/instance.json"
    save_root="$loader/versions/$version/run/saves"
    ;;
  *)
    echo "Unsupported loader: $loader" >&2
    exit 1
    ;;
esac

scenario_file=""
scenario_name=""
scenario_kind=""
case "$version" in
  1.21.10|1.21.11|26.1|26.1.1|26.1.2|26.2-rc-2)
    scenario_file="test/scenarios/torchtoss/throwables-26.1.json"
    scenario_name="torchtoss-throwables-26.1"
    scenario_kind="copper"
    ;;
  1.16|1.16.1|1.16.2|1.16.3|1.16.4|1.16.5|1.17|1.17.1|1.18|1.18.1|1.18.2|1.19|1.19.1|1.19.2|1.19.3|1.19.4|1.20|1.20.1|1.20.2|1.20.3|1.20.4|1.20.5|1.20.6|1.21|1.21.1|1.21.2|1.21.3|1.21.4|1.21.5|1.21.6|1.21.7|1.21.8|1.21.9)
    scenario_file="test/scenarios/torchtoss/throwables-1.16plus.json"
    scenario_name="torchtoss-throwables-1.16plus"
    scenario_kind="soul"
    ;;
  1.14.4|1.15|1.15.1|1.15.2)
    scenario_file="test/scenarios/torchtoss/throwables-pre116.json"
    scenario_name="torchtoss-throwables-pre116"
    scenario_kind="basic"
    ;;
  *)
    echo "No Torch Toss feature scenario is defined for $node yet" >&2
    exit 1
    ;;
esac

workspace_root="$(git rev-parse --show-superproject-working-tree 2>/dev/null || true)"
catalog_root="${TORCHTOSS_VERSION_CATALOG_ROOT:-}"
if [ -z "$catalog_root" ] && [ -n "$workspace_root" ]; then
  catalog_root="$workspace_root/tooling/version-catalog"
fi
catalog="$catalog_root/mc-$version/gradle/libs.versions.toml"
if [ ! -f "$catalog" ] || ! rg -q '^teakit = ' "$catalog"; then
  echo "TeaKit is not configured in the shared catalog for $version" >&2
  exit 1
fi

log="/tmp/torchtoss-$node.feature.run.log"
result="/tmp/torchtoss-$node.feature.result.json"
health="/tmp/torchtoss-$node.feature.health.json"
rm -f "$instance_path" "$log" "$result" "$health"

if [ "$loader" = "fabric" ] && { [ "$version" = "1.16" ] || [ "$version" = "1.16.1" ]; }; then
  target_save="$save_root/teakit_dev"
  template_save="fabric/versions/1.16.2/runs/client/saves/teakit_dev"
  if [ ! -d "$target_save" ] && [ -d "$template_save" ]; then
    mkdir -p "$save_root"
    cp -a "$template_save" "$target_save"
    rm -f "$target_save/session.lock"
  fi
fi

gradle_args=(--configure-on-demand "$gradle_task" --console=plain)
if [ "${TORCHTOSS_REFRESH_DEPS:-0}" = "1" ]; then
  gradle_args+=(--refresh-dependencies)
fi

./gradlew "${gradle_args[@]}" \
  -Dtorchtoss.withTeaKit=true \
  -Dteakit.autoWorld=true \
  -Dteakit.repoRoot="$PWD" \
  -Dteakit.scenarioRoot="$PWD" \
  >"$log" 2>&1 &
gradle_pid=$!

port=""
token=""
base_url=""

cleanup() {
  set +e
  if [ -f "$instance_path" ]; then
    port="$(jq -r '.port' "$instance_path" 2>/dev/null || true)"
    token="$(jq -r '.token' "$instance_path" 2>/dev/null || true)"
    if [ -n "$port" ] && [ -n "$token" ] && [ "$port" != "null" ] && [ "$token" != "null" ]; then
      base_url="http://localhost:$port"
      curl -fsS -H "X-TeaKit-Token: $token" \
        -H 'Content-Type: application/json' \
        --data '{"delayMs":500}' \
        "$base_url/action/client/quit" >/dev/null 2>&1 || true
    fi
  fi
  if kill -0 "$gradle_pid" >/dev/null 2>&1; then
    wait "$gradle_pid" >/dev/null 2>&1 || true
  fi
}
trap cleanup EXIT

for _ in $(seq 1 "$timeout_seconds"); do
  if [ -f "$instance_path" ]; then
    port="$(jq -r '.port' "$instance_path" 2>/dev/null || true)"
    token="$(jq -r '.token' "$instance_path" 2>/dev/null || true)"
    base_url="http://localhost:$port"
    if [ -n "$port" ] && [ -n "$token" ] && [ "$port" != "null" ] && [ "$token" != "null" ] \
      && curl -fsS -H "X-TeaKit-Token: $token" "$base_url/health" >"$health" 2>/dev/null; then
      break
    fi
  fi
  if ! kill -0 "$gradle_pid" >/dev/null 2>&1; then
    wait "$gradle_pid"
    tail -n 160 "$log" >&2
    exit 1
  fi
  sleep 1
done

if [ ! -f "$health" ]; then
  tail -n 160 "$log" >&2
  exit 1
fi

world_ready=0
for _ in $(seq 1 "$timeout_seconds"); do
  if curl -fsS -H "X-TeaKit-Token: $token" "$base_url/health" >"$health" 2>/dev/null \
    && jq -e '.status.worldLoaded == true and .status.singleplayer == true and .status.playerCount > 0' "$health" >/dev/null 2>&1; then
    world_ready=1
    break
  fi
  if ! kill -0 "$gradle_pid" >/dev/null 2>&1; then
    wait "$gradle_pid"
    tail -n 160 "$log" >&2
    exit 1
  fi
  sleep 1
done

if [ "$world_ready" -ne 1 ]; then
  tail -n 160 "$log" >&2
  exit 1
fi

curl -fsS -H "X-TeaKit-Token: $token" \
  -H 'Content-Type: application/json' \
  --data '{}' \
  "$base_url/action/menu/close" >/dev/null || true

sleep 1

http_code="$(
  curl -sS -o "$result" -w '%{http_code}' \
    --max-time "$((timeout_seconds + 30))" \
    -H "X-TeaKit-Token: $token" \
    -H 'Content-Type: application/json' \
    --data-binary @"$scenario_file" \
    "$base_url/scenario/run"
)"

scenario_ok=0
if jq -e --arg scenario_name "$scenario_name" '
  def payload: if has("name") then . else (.error | fromjson) end;
  payload
  | .name == $scenario_name
  and (.steps | length) >= 12
  and any(.steps[]?; (.action == "wait_for_block" or .action == "assert_block") and .result.blockId == "minecraft:torch")
  and any(.steps[]?; (.action == "wait_for_block" or .action == "assert_block") and .result.blockId == "minecraft:redstone_torch")
  and (
    any(.steps[]?; .action == "wait_for_entity_count" and .result.entityType == "minecraft:cow" and .result.count == 0)
    or any(.steps[]?; .action == "wait_for_entity_count" and .result.entityType == "minecraft:chicken" and .result.count == 0)
    or any(.steps[]?; (.action == "wait_for_inventory_item" or .action == "assert_inventory_item") and .result.itemId == "minecraft:beef" and .result.count >= 1)
    or any(.steps[]?; (.action == "wait_for_inventory_item" or .action == "assert_inventory_item") and .result.itemId == "minecraft:chicken" and .result.count >= 1)
    or any(.steps[]?; (.action == "wait_for_inventory_item" or .action == "assert_inventory_item") and .result.itemId == "minecraft:feather" and .result.count >= 1)
    or any(.steps[]?; .action == "wait_for_entity_count" and .result.entityType == "minecraft:item" and .result.itemId == "minecraft:beef" and ((.result.count // 0) >= 1 or (.result.minCount // 0) >= 1))
    or any(.steps[]?; .action == "wait_for_entity_count" and .result.entityType == "minecraft:item" and .result.itemId == "minecraft:chicken" and ((.result.count // 0) >= 1 or (.result.minCount // 0) >= 1))
    or any(.steps[]?; .action == "wait_for_entity_count" and .result.entityType == "minecraft:item" and .result.itemId == "minecraft:feather" and ((.result.count // 0) >= 1 or (.result.minCount // 0) >= 1))
    or any(.cleanup[]?; .action == "clear_nearby_entities" and any(.result.probe.inventory[]?; .itemId == "minecraft:beef" and .count >= 1))
    or any(.cleanup[]?; .action == "clear_nearby_entities" and any(.result.probe.inventory[]?; .itemId == "minecraft:chicken" and .count >= 1))
    or any(.cleanup[]?; .action == "clear_nearby_entities" and any(.result.probe.inventory[]?; .itemId == "minecraft:feather" and .count >= 1))
    or any(.cleanup[]?; .action == "clear_nearby_entities" and ((.result.count // 0) == 0) and .result.entityType == "minecraft:chicken")
  )
' "$result" >/dev/null; then
  scenario_ok=1
fi

if [ "$scenario_kind" != "basic" ]; then
  jq -e '
    def payload: if has("name") then . else (.error | fromjson) end;
    payload
    | any(.steps[]?; (.action == "wait_for_block" or .action == "assert_block") and .result.blockId == "minecraft:soul_torch")
  ' "$result" >/dev/null
fi

if [ "$scenario_kind" = "copper" ]; then
  jq -e '
    def payload: if has("name") then . else (.error | fromjson) end;
    payload
    | any(.steps[]?; (.action == "wait_for_block" or .action == "assert_block") and .result.blockId == "minecraft:copper_torch")
  ' "$result" >/dev/null
fi

if [ "$scenario_ok" -ne 1 ]; then
  cat "$result" >&2 || true
  tail -n 160 "$log" >&2
  exit 1
fi

curl -fsS -H "X-TeaKit-Token: $token" \
  -H 'Content-Type: application/json' \
  --data '{"delayMs":500}' \
  "$base_url/action/client/quit" >/dev/null

wait "$gradle_pid"
echo "Torch Toss feature scenario OK: $node"
