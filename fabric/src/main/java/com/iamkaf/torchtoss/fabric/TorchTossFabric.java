package com.iamkaf.torchtoss.fabric;

import com.iamkaf.torchtoss.ModItems;
import com.iamkaf.torchtoss.TorchToss;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

public final class TorchTossFabric implements ModInitializer {
    public static void registerItemsToCreativeTab(FabricItemGroupEntries event) {
        event.addAfter(
                Items.REDSTONE_TORCH.getDefaultInstance(),
                ModItems.THROWABLE_REDSTONE_TORCH.get().getDefaultInstance()
        );
        event.addAfter(Items.REDSTONE_TORCH.getDefaultInstance(), ModItems.THROWABLE_SOUL_TORCH.get().getDefaultInstance());
        event.addAfter(Items.REDSTONE_TORCH.getDefaultInstance(), ModItems.THROWABLE_TORCH.get().getDefaultInstance());
    }

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        TorchToss.init();

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS)
                .register(TorchTossFabric::registerItemsToCreativeTab);
    }
}
