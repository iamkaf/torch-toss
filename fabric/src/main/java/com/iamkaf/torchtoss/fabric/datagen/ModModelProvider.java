package com.iamkaf.torchtoss.fabric.datagen;

import com.iamkaf.torchtoss.ModItems;
//? if >=1.21.4 {
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
//?} else {
//? if >=1.19 {
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
//?} else {
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockStateDefinitionProvider;
//?}
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
//?}
//? if >=26.1 {
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
//?} else if >=1.19 {
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
//?} else {
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
//?}

/**
 * Generates models for blocks and items.
 */
//? if >=1.19 {
public class ModModelProvider extends FabricModelProvider {
//?} else {
public class ModModelProvider extends FabricBlockStateDefinitionProvider {
//?}
    //? if >=26.1 {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }
    //?} else if >=1.19 {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }
    //?} else {
    public ModModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }
    //?}

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        // Generate block models here
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        // Generate item models for throwable torches
        itemModelGenerators.generateFlatItem(ModItems.THROWABLE_TORCH.get(), ModelTemplates.FLAT_ITEM);
        //? if >=1.16 {
        itemModelGenerators.generateFlatItem(ModItems.THROWABLE_SOUL_TORCH.get(), ModelTemplates.FLAT_ITEM);
        //?}
        //? if >=1.21.10 {
        itemModelGenerators.generateFlatItem(ModItems.THROWABLE_COPPER_TORCH.get(), ModelTemplates.FLAT_ITEM);
        //?}
        itemModelGenerators.generateFlatItem(ModItems.THROWABLE_REDSTONE_TORCH.get(), ModelTemplates.FLAT_ITEM);
    }
}
