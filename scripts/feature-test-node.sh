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

./teakitw run \
  --node "$node" \
  --test-file test/teakit/throwables.test.ts \
  --timeout "$timeout_seconds"
