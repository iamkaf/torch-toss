import { Capability, Readiness, describe, expect, pos, test } from "@teakit/test";

const TORCHES = ["torchtoss:throwable_torch", "torchtoss:throwable_soul_torch", "torchtoss:throwable_redstone_torch"] as const;

// A sealed room, so neither sun nor moon reaches it.
const ROOM = { x: 40, y: 100, z: 40 };

describe.configure({
  timeout: "6m",
  readiness: [Readiness.World, Readiness.Player],
  capabilities: [
    Capability.ClientInput,
    Capability.ClientScreenshot,
    Capability.PlayerInventory,
    Capability.RuntimeLogs,
    Capability.RuntimeTiming,
    Capability.ServerCommands,
  ],
});

describe("Torch Toss dynamic lights", () => {
  test(
    "the dynamic light files load cleanly and held torches light a dark room",
    { target: { mods: "lambdynlights" } },
    async (ctx) => {
      const { x, y, z } = ROOM;
      await ctx.commands.batch([
        "/gamemode creative @s",
        "/time set midnight",
        "/clear @s",
        `/fill ${x} ${y} ${z} ${x + 6} ${y + 4} ${z + 6} minecraft:stone hollow`,
      ]);
      await ctx.player.teleport(pos(x + 3, y + 1, z + 3));
      await ctx.runtime.wait(1000);

      for (const throwable of TORCHES) {
        await ctx.commands.run(`/item replace entity @s weapon.mainhand with ${throwable}`);
        await ctx.player.inventory().waitForItem(throwable, { selected: true, timeout: "5s" });
        await ctx.client.lookAt(pos(x + 3, y + 1, z + 6));
        await ctx.runtime.wait(1500);
        await ctx.client.waitForFrames(10);
        await ctx.client.screenshot(`torchtoss-dynamic-light-${throwable.slice("torchtoss:throwable_".length)}`);
      }
      await ctx.commands.run("/clear @s");

      // LambDynamicLights warns about every light source file it cannot read.
      const log = await ctx.logs.text({ limit: 40000 });
      const problems = log.split(/\r?\n/).filter((line) => /Failed to load .* light source/i.test(line));
      expect(problems).toEqual([]);
    },
  );
});
