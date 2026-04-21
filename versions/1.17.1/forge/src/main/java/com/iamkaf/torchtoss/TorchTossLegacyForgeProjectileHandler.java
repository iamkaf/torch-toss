package com.iamkaf.torchtoss;

import net.minecraft.world.entity.projectile.Snowball;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class TorchTossLegacyForgeProjectileHandler {
    private TorchTossLegacyForgeProjectileHandler() {
    }

    @SubscribeEvent
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof Snowball snowball)) {
            return;
        }
        if (!ThrowableTorchImpactHandler.handlesItem(snowball.getItem())) {
            return;
        }
        if (ThrowableTorchImpactHandler.handleImpact(snowball, event.getRayTraceResult())) {
            event.setCanceled(true);
            snowball.discard();
        }
    }
}
