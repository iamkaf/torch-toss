import { Capability, Readiness, describe, expect, pos, test } from "@teakit/test";
import type { TeaKitTestContext } from "@teakit/test";

const launch = pos(0.5, 70, 0.5);
const landing = pos(0, 70, 2);

describe.configure({
  timeout: "6m",
  readiness: [Readiness.World, Readiness.Player],
  capabilities: [
    Capability.ClientInput,
    Capability.ClientScreenshot,
    Capability.PlayerUseItem,
    Capability.PlayerInventory,
    Capability.RuntimeSummary,
    Capability.RuntimeTiming,
    Capability.ServerCommands,
    Capability.WorldBlock,
    Capability.WorldEntities,
    Capability.WorldLoot,
  ],
});

test("throws every supported torch and damages a nearby mob", async (ctx) => {
  const version = (await ctx.runtime.summary()).minecraftVersion ?? "0";
  await prepare(ctx);

  try {
    const torches = [
      ["torchtoss:throwable_torch", "minecraft:torch"],
      ...(atLeast(version, "1.16") ? [["torchtoss:throwable_soul_torch", "minecraft:soul_torch"]] : []),
      ["torchtoss:throwable_redstone_torch", "minecraft:redstone_torch"],
      ...(atLeast(version, "26.1") ? [["torchtoss:throwable_copper_torch", "minecraft:copper_torch"]] : []),
    ] as const;

    for (const [throwable, placed] of torches) {
      await ctx.commands.run(`/setblock ${landing.x} ${landing.y} ${landing.z} minecraft:air`);
      await ctx.commands.run(replaceMainHand(version, throwable));
      await ctx.player.inventory().waitForItem(throwable, { selected: true, timeout: "5s" });
      await ctx.player.teleport(launch);
      await ctx.player.lookAt(pos(0.5, 70, 2.5));
      if (atMost(version, "1.16.5")) await ctx.runtime.wait(350);
      await ctx.player.useItem();
      await expect(async () => (await ctx.world.block(landing)).id)
        .toEventuallyEqual(placed, { timeout: "5s", interval: 100 });
    }

    await assertMobHit(ctx, version);
    await ctx.client.screenshot(`torchtoss-throwables-${version}`);
  } finally {
    await cleanup(ctx);
  }
});

async function prepare(ctx: TeaKitTestContext) {
  await cleanup(ctx);
  await ctx.commands.batch([
    "/gamemode creative @s",
    "/clear @s",
    "/tp @s 0.5 70 0.5",
    "/fill -2 69 0 4 69 12 minecraft:stone replace",
    "/fill -2 70 0 4 75 12 minecraft:air replace",
  ]);
}

async function assertMobHit(ctx: TeaKitTestContext, version: string) {
  const legacy = atMost(version, "1.15.2");
  const type = legacy ? "minecraft:chicken" : "minecraft:cow";
  const arenaY = legacy ? 70 : 200;
  const arena = pos(0.5, arenaY, 8.5);

  await ctx.commands.batch([
    `/gamemode ${legacy ? "survival" : "creative"} @s`,
    `/tp @s 0.5 ${arenaY} 6.5`,
    `/fill -2 ${arenaY - 1} 6 2 ${arenaY - 1} 10 minecraft:stone replace`,
    `/fill -2 ${arenaY} 6 2 ${arenaY + 5} 10 minecraft:air replace`,
    `/summon ${type} ${arena.x} ${arena.y} ${arena.z} {NoAI:1b${legacy ? "" : ",Health:1.0f"}}`,
    replaceMainHand(version, "torchtoss:throwable_torch"),
  ]);

  const mobs = ctx.entities.query({ type, origin: arena, radius: 16 });
  await mobs.waitForCount(1, { timeout: "3s" });
  await ctx.player.teleport(pos(0.5, arenaY, 7));
  await ctx.player.lookAt(pos(0.5, arenaY + 0.5, 8.5));

  const attempts = legacy ? 4 : 1;
  for (let attempt = 0; attempt < attempts; attempt += 1) {
    if (attempt > 0) await ctx.commands.run(replaceMainHand(version, "torchtoss:throwable_torch"));
    if (atMost(version, "1.16.5")) await ctx.runtime.wait(350);
    await ctx.player.useItem();
    await ctx.runtime.wait(legacy ? 750 : 650);
  }

  if (legacy) {
    await ctx.loot.near(arena, { item: "minecraft:chicken", radius: 16 })
      .waitForCountAtLeast(1, { timeout: "30s" });
  } else {
    await mobs.waitForCount(0, { timeout: "7s" });
  }
}

async function cleanup(ctx: TeaKitTestContext) {
  for (const type of ["minecraft:item", "minecraft:snowball", "minecraft:cow", "minecraft:chicken"] as const) {
    await ctx.entities.query({ type, origin: pos(0, 100, 0), radius: 160 }).removeAll();
  }
  await ctx.commands.batch([
    "/clear @s",
    "/fill -2 69 0 4 75 12 minecraft:air replace",
    "/fill -2 199 6 2 205 10 minecraft:air replace",
  ]);
}

function replaceMainHand(version: string, item: string): string {
  return atMost(version, "1.16.5")
    ? `/replaceitem entity @s weapon.mainhand ${item}`
    : `/item replace entity @s weapon.mainhand with ${item}`;
}

function atLeast(actual: string, expected: string): boolean {
  return compareVersions(actual, expected) >= 0;
}

function atMost(actual: string, expected: string): boolean {
  return compareVersions(actual, expected) <= 0;
}

function compareVersions(left: string, right: string): number {
  const a = left.split(/[.-]/).map((part) => Number.parseInt(part, 10) || 0);
  const b = right.split(/[.-]/).map((part) => Number.parseInt(part, 10) || 0);
  for (let index = 0; index < Math.max(a.length, b.length); index += 1) {
    const difference = (a[index] ?? 0) - (b[index] ?? 0);
    if (difference !== 0) return difference;
  }
  return 0;
}
