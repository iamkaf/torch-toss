import { Capability, Readiness, describe, expect, test } from "@teakit/test";
import type { ClientScreen, TeaKitTestContext } from "@teakit/test";

const CONFIG_SCREEN = "com.iamkaf.konfig.impl.v1.client.screen.KonfigConfigScreen";

// This suite leaves the world for the title screen, so its file sorts after the others in the run.
describe.configure({
  timeout: "5m",
  readiness: [Readiness.World, Readiness.Player],
  capabilities: [
    Capability.ClientInput,
    Capability.ClientScreen,
    Capability.ClientScreens,
    Capability.ClientScreenshot,
    Capability.RuntimeTiming,
    Capability.ServerCommands,
  ],
});

describe("Torch Toss config screen", () => {
  // TeaKit's Forge control plane stops answering after leaving the world, so Forge is not covered here.
  test("lists the damage of every throwable torch in the mod's configuration", { target: { loader: ["fabric", "neoforge"] } }, async (ctx) => {
    await ctx.commands.run("/gamemode creative");
    await ctx.client.leaveWorld();
    await ctx.client.waitForScreen("Title", { timeoutMs: 30_000 });

    let screen = await ctx.client.screen();
    await screen.widgets().activate({ label: "Mods", contains: true });
    screen = await ctx.client.waitForScreen("Mods", { timeoutMs: 5_000 });
    // Older NeoForge mod lists start unsorted, which can push Torch Toss below the fold.
    if (screen.widgets().all().some((widget) => widget.label === "A-Z")) {
      await screen.widgets().activate({ label: "A-Z" });
    }
    screen = await selectMod(ctx, "Torch Toss");

    const loader = (await ctx.runtime.health()).loader;
    if (loader === "fabric") {
      await activateModMenuConfigure(screen);
    } else {
      await screen.widgets().activate({ label: "Config", nth: 0 });
    }

    screen = await ctx.client.waitForScreen(CONFIG_SCREEN, { timeoutMs: 10_000 });
    await ctx.client.screenshot("torchtoss-config-screen");
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

// Mod Menu labels its configure button on newer versions; older versions only expose the icon widget.
async function activateModMenuConfigure(screen: ClientScreen): Promise<void> {
  if (screen.widgets().all().some((widget) => widget.label === "Configure...")) {
    await screen.widgets().activate({ label: "Configure..." });
    return;
  }
  await screen.widgets().activate({ widgetClass: "com.terraformersmc.modmenu.gui.widget.LegacyTexturedButtonWidget", nth: 1 });
}
