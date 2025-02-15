package com.iamkaf.torchtoss;

import com.iamkaf.amber.api.core.AmberMod;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class TorchToss extends AmberMod {
    public static final String MOD_ID = "torchtoss";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TorchToss() {
        super(MOD_ID);
    }

    public static void init() {
        LOGGER.info("TorchToss initializing...");

        // Registries
        ModItems.init();
    }

    /**
     * Creates resource location in the mod namespace with the given path.
     */
    public static ResourceLocation resource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
