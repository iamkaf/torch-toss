package com.iamkaf.torchtoss.fabric.datagen;

import com.iamkaf.torchtoss.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(net.minecraft.data.models.BlockModelGenerators blockModelGenerators) {

    }

    @Override
    public void generateItemModels(net.minecraft.data.models.ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(
                ModItems.THROWABLE_TORCH.get(),
                net.minecraft.data.models.model.ModelTemplates.FLAT_ITEM
        );
        itemModelGenerators.generateFlatItem(
                ModItems.THROWABLE_SOUL_TORCH.get(),
                net.minecraft.data.models.model.ModelTemplates.FLAT_ITEM
        );
        itemModelGenerators.generateFlatItem(
                ModItems.THROWABLE_REDSTONE_TORCH.get(),
                net.minecraft.data.models.model.ModelTemplates.FLAT_ITEM
        );
    }
}
