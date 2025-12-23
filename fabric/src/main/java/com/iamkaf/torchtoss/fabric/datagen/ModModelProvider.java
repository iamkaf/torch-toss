package com.iamkaf.torchtoss.fabric.datagen;

import com.iamkaf.torchtoss.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

/**
 * Generates models for blocks and items.
 */
public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        // Generate block models here
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        // Generate item models for throwable torches
        itemModelGenerators.generateFlatItem(ModItems.THROWABLE_TORCH.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.THROWABLE_SOUL_TORCH.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.THROWABLE_COPPER_TORCH.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.THROWABLE_REDSTONE_TORCH.get(), ModelTemplates.FLAT_ITEM);
    }
}
