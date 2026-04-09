package com.iamkaf.torchtoss;

import com.iamkaf.amber.api.core.v2.AmberInitializer;
import com.iamkaf.torchtoss.config.TorchTossConfig;
import com.iamkaf.torchtoss.advancement.ModTriggers;
import com.iamkaf.torchtoss.platform.Services;

/**
 * Common entry point for the mod.
 * Replace the contents with your own implementation.
 */
public class TorchTossMod {

    /**
     * Called during mod initialization for all loaders.
     */
    public static void init() {
        TorchTossConstants.LOG.info("Initializing {} on {}...", TorchTossConstants.MOD_NAME, Services.PLATFORM.getPlatformName());

        AmberInitializer.initialize(TorchTossConstants.MOD_ID);
        TorchTossConfig.init();

        // Initialize registries
        ModItems.init();
        ModTriggers.init();
    }
}
