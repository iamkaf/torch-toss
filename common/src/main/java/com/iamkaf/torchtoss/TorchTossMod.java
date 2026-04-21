package com.iamkaf.torchtoss;

import com.iamkaf.amber.api.core.v2.AmberInitializer;
//? if <1.21.7 {
import com.iamkaf.amber.api.core.v2.AmberModInfo;
import com.iamkaf.amber.api.platform.v1.ModInfo;
import com.iamkaf.amber.api.platform.v1.Platform;
//?}
import com.iamkaf.torchtoss.config.TorchTossConfig;
import com.iamkaf.torchtoss.advancement.ModTriggers;
import com.iamkaf.torchtoss.platform.Services;
import org.jetbrains.annotations.Nullable;

/**
 * Common entry point for the mod.
 * Replace the contents with your own implementation.
 */
public class TorchTossMod {

    /**
     * Called during mod initialization for all loaders.
     */
    public static void init() {
        init(null);
    }

    public static void init(@Nullable Object eventBus) {
        TorchTossConstants.LOG.info("Initializing {} on {}...", TorchTossConstants.MOD_NAME, Services.PLATFORM.getPlatformName());

        //? if >=1.21.7 || <1.18 {
        AmberInitializer.initialize(TorchTossConstants.MOD_ID);
        //?} else {
        ModInfo info = Platform.getModInfo(TorchTossConstants.MOD_ID);
        assert info != null;
        AmberInitializer.initialize(info.id(), info.name(), info.version(), AmberModInfo.AmberModSide.COMMON, eventBus);
        //?}
        TorchTossConfig.init();

        // Initialize registries
        ModItems.init();
        ModTriggers.init();
    }
}
