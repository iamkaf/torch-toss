package com.iamkaf.torchtoss.neoforge;

import com.iamkaf.torchtoss.ModItems;
import com.iamkaf.torchtoss.TorchToss;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.Objects;

@Mod(TorchToss.MOD_ID)
public final class TorchTossNeoForge {
    public TorchTossNeoForge(ModContainer container) {
        // Run our common setup.
        TorchToss.init();
        ModItemsImpl.ITEMS.register(Objects.requireNonNull(container.getEventBus()));

        container.getEventBus().addListener(TorchTossNeoForge::registerItemsToCreativeTab);
    }

    public static void registerItemsToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (!event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            return;
        }

        event.insertAfter(
                Items.REDSTONE_TORCH.getDefaultInstance(),
                ModItems.THROWABLE_REDSTONE_TORCH.get().getDefaultInstance(),
                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
        );
        event.insertAfter(
                Items.REDSTONE_TORCH.getDefaultInstance(),
                ModItems.THROWABLE_SOUL_TORCH.get().getDefaultInstance(),
                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
        );
        event.insertAfter(
                Items.REDSTONE_TORCH.getDefaultInstance(),
                ModItems.THROWABLE_TORCH.get().getDefaultInstance(),
                CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
        );
    }
}
