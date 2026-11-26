import { Capability, Readiness, describe, expect, test } from "@teakit/test";
import type { ClientScreen, TeaKitTestContext } from "@teakit/test";

const CONFIG_SCREEN = "com.iamkaf.konfig.impl.v1.client.screen.KonfigConfigScreen";

// TeaKit's Forge control plane stops answering after leaving a world, so Forge starts at the title screen here
// instead of joining the world the main suite uses. Run with: teakitw run --node <version>-forge --config teakit-title.toml --test-file <this file>
describe.configure({
  timeout: "4m",
  readiness: [Readiness.Title],
  capabilities: [Capability.ClientInput, Capability.ClientScreen, Capability.ClientScreens, Capability.ClientScreenshot, Capability.RuntimeTiming],
});

describe("Torch Toss config screen on Forge", () => {
  test("opens from the mod list and lists the damage of every throwable torch", { target: { loader: "forge" } }, async (ctx) => {
    await ctx.client.waitForScreen("Title", { timeoutMs: 30_000 });
    await ctx.runtime.wait(3500);

    let screen = await ctx.client.screen();
    await screen.widgets().activate({ label: "Mods", contains: true });
    screen = await ctx.client.waitForScreen("Mods", { timeoutMs: 5_000 });
    screen = await selectMod(ctx, "Torch Toss");
    await screen.widgets().activate({ label: "Config", nth: 0 });

    screen = await ctx.client.waitForScreen(CONFIG_SCREEN, { timeoutMs: 10_000 });
    await ctx.client.screenshot("torchtoss-config-screen-forge");
    const labels = screen.lists().entries().map((entry) => entry.label);
    for (const label of ["Torch Damage", "Soul Torch Damage", "Redstone Torch Damage"]) {
      expect(labels.filter((candidate) => candidate.includes(label)).length).toBeGreaterThanOrEqual(1);
    }
  });
});

// Mod lists do not reliably select an entry through activate(), so scroll to it and click until it reports selected.
async function selectMod(ctx: TeaKitTestContext, label: string): Promise<ClientScreen> {
  const startedAt = Date.now();
  while (Date.now() - startedAt < 10_000) {
    const screen = await ctx.client.screen();
    const entry = screen.lists().entries().find((candidate) => candidate.label.includes(label));
    if (entry?.selected) return screen;
    if (entry != null) {
      await ctx.client.click({ x: entry.x + entry.width / 2, y: entry.y + entry.height / 2, button: 0 });
    } else {
      await screen.scroll({ vertical: -2 });
    }
    await ctx.runtime.wait(200);
  }
  const labels = (await ctx.client.screen()).lists().entries().map((entry) => entry.label);
  throw new Error(`Timed out selecting mod list entry ${label}; visible entries: ${labels.join(", ")}`);
}
