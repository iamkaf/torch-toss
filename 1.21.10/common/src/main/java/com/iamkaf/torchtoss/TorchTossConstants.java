package com.iamkaf.torchtoss;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TorchTossConstants {
    /**
     * Mod identifier and configuration fields.
     * Update these fields when reusing this code for other projects.
     */
    public static final String MOD_ID = "torchtoss";
    public static final String MOD_NAME = "Torch Toss";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    /**
     * Creates a resource location in the mod namespace with the given path.
     */
    public static ResourceLocation resource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
