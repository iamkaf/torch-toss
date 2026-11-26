import { Capability, Readiness, describe, expect, pos, test } from "@teakit/test";
import type { ItemId, TeaKitTestContext } from "@teakit/test";

const TORCHES = [
  ["torchtoss:throwable_torch", "minecraft:torch", "torchtoss:throw_torch"],
  ["torchtoss:throwable_soul_torch", "minecraft:soul_torch", "torchtoss:throw_soul_torch"],
  ["torchtoss:throwable_redstone_torch", "minecraft:redstone_torch", "torchtoss:throw_redstone_torch"],
] as const;

// The arena sits away from the area the throwables suite builds in.
const Z = 30;
const thrower = pos(0.5, 70, Z + 0.5);

describe.configure({
  timeout: "8m",
  readiness: [Readiness.World, Readiness.Player],
  capabilities: [
    Capability.ClientInput,
    Capability.PlayerUseItem,
    Capability.PlayerInventory,
    Capability.PlayerInteractions,
    Capability.PlayerSelf,
    Capability.WorldRecipes,
    Capability.RuntimeTiming,
    Capability.ServerCommands,
    Capability.WorldBlock,
    Capability.WorldEntities,
    Capability.WorldLoot,
  ],
});

describe("Torch Toss", () => {
  test("crafts every throwable torch from, and back into, two vanilla torches", async (ctx) => {
    for (const [throwable, placed] of TORCHES) {
      const name = placed.slice("minecraft:".length);
      const forward = await ctx.recipes.assertCrafting(2, 1, [placed, placed], throwable, { resultCount: 2 });
      expect(forward.recipeId).toBe(`torchtoss:vanilla_to_throwable_${name}`);
      const back = await ctx.recipes.assertCrafting(2, 1, [throwable, throwable], placed, { resultCount: 2 });
      expect(back.recipeId).toBe(`torchtoss:throwable_to_vanilla_${name}`);
    }
  });

  test("grants the throw advancements when each torch is thrown", async (ctx) => {
    await prepare(ctx);
    await ctx.commands.assert("/advancement revoke @s everything");

    for (const [throwable, , advancement] of TORCHES) {
      await ctx.commands.assert(`/execute unless entity @s[advancements={${advancement}=true}]`);
      await throwAt(ctx, throwable, pos(0.5, 70.5, Z + 5.5));
      await expect(async () => (await ctx.commands.run(`/execute if entity @s[advancements={${advancement}=true}]`)).result)
        .toEventuallyEqual(1, { timeout: "5s", interval: 100 });
    }
  });

  test("every throwable torch hurts the mob it hits", async (ctx) => {
    await prepare(ctx);

    for (const [throwable] of TORCHES) {
      await ctx.commands.run(`/kill @e[type=minecraft:cow,distance=..40]`);
      await ctx.commands.assert(`/summon minecraft:cow 0.5 70 ${Z + 2.5} {NoAI:1b}`);
      await ctx.commands.assert("/execute if entity @e[type=minecraft:cow,nbt={Health:10.0f}]");
      await throwAt(ctx, throwable, pos(0.5, 70.8, Z + 2.5));
      // One point of damage by default.
      await expect(async () => (await ctx.commands.run("/execute if entity @e[type=minecraft:cow,nbt={Health:9.0f}]")).result)
        .toEventuallyEqual(1, { timeout: "5s", interval: 100 });
    }
  });

  test("sticks a torch to a wall, drops it when nothing can hold it, and drops it for a thrower that is not a player", async (ctx) => {
    await prepare(ctx);

    // A stone wall holds a wall torch.
    await ctx.commands.assert(`/fill -1 70 ${Z + 6} 1 70 ${Z + 6} minecraft:stone`);
    await throwAt(ctx, "torchtoss:throwable_torch", pos(0.5, 70.5, Z + 6.5));
    await expect(() => ctx.world.block(pos(0, 70, Z + 5))).toEventuallyEqual(
      expect.objectContaining({ id: "minecraft:wall_torch" }),
      { timeout: "5s", interval: 100 },
    );

    // Thrown at the underside of a ledge with nothing below it, there is nowhere to put the torch.
    await ctx.commands.assert("/fill -1 82 40 1 82 42 minecraft:stone");
    await ctx.player.teleport(pos(0.5, 70, Z + 0.5));
    await ctx.commands.run("/kill @e[type=minecraft:item,distance=..64]");
    await ctx.commands.assert("/summon minecraft:snowball 0.5 80.2 41.5 {Item:{id:\"torchtoss:throwable_soul_torch\",count:1},Motion:[0.0,1.0,0.0]}");
    await expect(async () => (await ctx.commands.run('/execute if entity @e[type=minecraft:item,nbt={Item:{id:"torchtoss:throwable_soul_torch"}}]')).result)
      .toEventuallyEqual(1, { timeout: "5s", interval: 100 });
    await ctx.commands.assert("/execute unless block 0 81 41 minecraft:soul_torch");

    // An ownerless torch hitting a solid wall lands as an item and leaves no block behind.
    await ctx.commands.run("/kill @e[type=minecraft:item,distance=..64]");
    await ctx.commands.assert(`/summon minecraft:snowball 0.5 70.5 ${Z + 2.5} {Item:{id:"torchtoss:throwable_redstone_torch",count:1},Motion:[0.0,0.0,1.0]}`);
    await expect(async () => (await ctx.commands.run('/execute if entity @e[type=minecraft:item,nbt={Item:{id:"torchtoss:throwable_redstone_torch"}}]')).result)
      .toEventuallyEqual(1, { timeout: "5s", interval: 100 });
    await ctx.commands.assert(`/execute unless block 0 70 ${Z + 5} minecraft:redstone_wall_torch`);
  });
});

async function prepare(ctx: TeaKitTestContext) {
  await ctx.commands.batch([
    "/gamemode creative @s",
    "/difficulty peaceful",
    "/clear @s",
    `/fill -4 69 ${Z - 2} 4 69 ${Z + 12} minecraft:stone`,
    `/fill -4 70 ${Z - 2} 4 78 ${Z + 12} minecraft:air`,
    "/fill -1 82 40 1 82 42 minecraft:air",
    "/kill @e[type=minecraft:item,distance=..64]",
    "/kill @e[type=minecraft:snowball,distance=..64]",
    "/kill @e[type=minecraft:cow,distance=..64]",
  ]);
  await ctx.player.teleport(thrower);
}

/** Holds a throwable, aims, and throws it. */
async function throwAt(ctx: TeaKitTestContext, throwable: ItemId, target: ReturnType<typeof pos>) {
  await ctx.player.teleport(thrower);
  await ctx.commands.run(`/item replace entity @s weapon.mainhand with ${throwable}`);
  await ctx.player.inventory().waitForItem(throwable, { selected: true, timeout: "5s" });
  await ctx.player.lookAt(target);
  await ctx.player.useItem();
  await ctx.runtime.wait(400);
}
