package com.iamkaf.torchtoss;

import java.util.function.Supplier;

import com.iamkaf.amber.api.event.v1.events.common.LootEvents;
import com.iamkaf.amber.api.registry.v1.DeferredRegister;
import com.iamkaf.amber.api.registry.v1.creativetabs.CreativeTabHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(TorchTossConstants.MOD_ID, Registries.ITEM);

    public static final Supplier<Item> THROWABLE_TORCH = ITEMS.register(
            "throwable_torch",
            () -> new ThrowableTorchItem(new Item.Properties().setId(ResourceKey.create(
                    Registries.ITEM,
                    TorchTossConstants.resource("throwable_torch")
            )))
    );

    public static final Supplier<Item> THROWABLE_SOUL_TORCH = ITEMS.register(
            "throwable_soul_torch",
            () -> new ThrowableTorchItem(new Item.Properties().setId(ResourceKey.create(
                    Registries.ITEM,
                    TorchTossConstants.resource("throwable_soul_torch")
            )))
    );

    public static final Supplier<Item> THROWABLE_COPPER_TORCH = ITEMS.register(
            "throwable_copper_torch",
            () -> new ThrowableTorchItem(new Item.Properties().setId(ResourceKey.create(
                    Registries.ITEM,
                    TorchTossConstants.resource("throwable_copper_torch")
            )))
    );

    public static final Supplier<Item> THROWABLE_REDSTONE_TORCH = ITEMS.register(
            "throwable_redstone_torch",
            () -> new ThrowableTorchItem(new Item.Properties().setId(ResourceKey.create(
                    Registries.ITEM,
                    TorchTossConstants.resource("throwable_redstone_torch")
            )))
    );

    @SuppressWarnings("unchecked") // FIXME: remove this when porting to Amber 9
    public static void init() {
        ITEMS.register();

        // Add items to the Functional Blocks creative tab
        ResourceKey<net.minecraft.world.item.CreativeModeTab> functionalBlocksTab = ResourceKey.create(
                Registries.CREATIVE_MODE_TAB,
                Identifier.fromNamespaceAndPath("minecraft", "functional_blocks")
        );

        // FIXME: remove these casts when porting to Amber 9
        CreativeTabHelper.addItems(functionalBlocksTab,
                (Supplier<ItemLike>) (Supplier<?>) THROWABLE_TORCH,
                (Supplier<ItemLike>) (Supplier<?>) THROWABLE_SOUL_TORCH,
                (Supplier<ItemLike>) (Supplier<?>) THROWABLE_COPPER_TORCH,
                (Supplier<ItemLike>) (Supplier<?>) THROWABLE_REDSTONE_TORCH
        );

        // Register loot table modifications
        registerLoot();
    }

    private static void registerLoot() {
        // Loot configuration
        final int MIN_ROLLS = 0;
        final int MAX_ROLLS = 1;
        final int MIN_COUNT = 1;
        final int MAX_COUNT = 4;
        
        LootEvents.MODIFY.register((lootTable, addPool) -> {
        // Basic pool: regular + redstone torches only
        LootPool.Builder basicPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(MIN_ROLLS, MAX_ROLLS))
                .add(LootItem.lootTableItem(THROWABLE_TORCH.get())
                        .setWeight(10)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_REDSTONE_TORCH.get())
                        .setWeight(4)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MIN_COUNT, MAX_COUNT))));

        // Nether pool: regular + soul + redstone torches
        LootPool.Builder netherPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(MIN_ROLLS, MAX_ROLLS))
                .add(LootItem.lootTableItem(THROWABLE_TORCH.get())
                        .setWeight(10)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_SOUL_TORCH.get())
                        .setWeight(8)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_REDSTONE_TORCH.get())
                        .setWeight(4)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MIN_COUNT, MAX_COUNT))));

        // Trial chambers pool: regular + copper + redstone torches
        LootPool.Builder trialChambersPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(MIN_ROLLS, MAX_ROLLS))
                .add(LootItem.lootTableItem(THROWABLE_TORCH.get())
                        .setWeight(10)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_COPPER_TORCH.get())
                        .setWeight(8)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_REDSTONE_TORCH.get())
                        .setWeight(4)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MIN_COUNT, MAX_COUNT))));

        // Ancient city pool: all torch types with emphasis on soul
        LootPool.Builder ancientCityPool = LootPool.lootPool()
                .setRolls(UniformGenerator.between(MIN_ROLLS, MAX_ROLLS))
                .add(LootItem.lootTableItem(THROWABLE_TORCH.get())
                        .setWeight(8)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_SOUL_TORCH.get())
                        .setWeight(10)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_COPPER_TORCH.get())
                        .setWeight(6)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_REDSTONE_TORCH.get())
                        .setWeight(4)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(MIN_COUNT, MAX_COUNT))));

            String path = lootTable.getPath();

            // Mining & Underground structures (basic pool)
            if (path.equals("chests/simple_dungeon") ||
                path.equals("chests/abandoned_mineshaft") ||
                path.equals("chests/stronghold_corridor") ||
                path.equals("chests/woodland_mansion")) {
                addPool.accept(basicPool);
            }

            // Ancient city - all torch types with emphasis on soul
            if (path.equals("chests/ancient_city")) {
                addPool.accept(ancientCityPool);
            }

            // Trial chambers rewards (copper torch pool)
            if (path.equals("chests/trial_chambers/reward") ||
                path.equals("chests/trial_chambers/reward_common") ||
                path.equals("chests/trial_chambers/reward_rare") ||
                path.equals("chests/trial_chambers/reward_unique") ||
                path.equals("chests/trial_chambers/reward_ominous") ||
                path.equals("chests/trial_chambers/reward_ominous_common") ||
                path.equals("chests/trial_chambers/reward_ominous_rare") ||
                path.equals("chests/trial_chambers/reward_ominous_unique")) {
                addPool.accept(trialChambersPool);
            }

            // Nether structures (soul torch pool)
            if (path.equals("chests/nether_bridge") ||
                path.equals("chests/bastion_treasure") ||
                path.equals("chests/bastion_other") ||
                path.equals("chests/bastion_bridge") ||
                path.equals("chests/bastion_hoglin_stable")) {
                addPool.accept(netherPool);
            }

            // Treasure & exploration (basic pool)
            if (path.equals("chests/buried_treasure") ||
                path.equals("chests/pillager_outpost")) {
                addPool.accept(basicPool);
            }

            // Village chests (basic pool)
            if (path.equals("chests/village/village_temple") ||
                path.equals("chests/village/village_cartographer")) {
                addPool.accept(basicPool);
            }

            // Temples (basic pool)
            if (path.equals("chests/desert_pyramid") ||
                path.equals("chests/jungle_temple") ||
                path.equals("chests/igloo_chest")) {
                addPool.accept(basicPool);
            }
        });
    }
}
