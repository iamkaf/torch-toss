package com.iamkaf.torchtoss.fabric;

import com.iamkaf.torchtoss.fabric.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

/**
 * Datagen entry point for Fabric.
 */
public final class ModDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        //? if >=1.19.3 {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModBlockLootTableProvider::new);
        pack.addProvider(ModModelProvider::new);
        //? if >=1.21.2 {
        pack.addProvider(ModRecipeProvider.Runner::new);
        //?} else {
        pack.addProvider(ModRecipeProvider::new);
        //?}
        pack.addProvider(ModAdvancementProvider::new);
        //?} else {
        fabricDataGenerator.addProvider(ModBlockTagProvider::new);
        fabricDataGenerator.addProvider(ModItemTagProvider::new);
        fabricDataGenerator.addProvider(ModBlockLootTableProvider::new);
        fabricDataGenerator.addProvider(ModModelProvider::new);
        fabricDataGenerator.addProvider(ModRecipeProvider::new);
        fabricDataGenerator.addProvider(ModAdvancementProvider::new);
        //?}
    }
}
