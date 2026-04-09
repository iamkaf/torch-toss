package com.iamkaf.torchtoss;

import net.fabricmc.api.ModInitializer;

/**
 * Fabric entry point.
 */
public class TorchTossFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        TorchTossMod.init();
    }
}
