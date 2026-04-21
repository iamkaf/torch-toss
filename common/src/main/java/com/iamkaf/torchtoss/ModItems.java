package com.iamkaf.torchtoss;

import java.util.function.Supplier;

import com.iamkaf.amber.api.event.v1.events.common.LootEvents;
import com.iamkaf.amber.api.registry.v1.DeferredRegister;
//? if >=1.21.10 {
import com.iamkaf.amber.api.registry.v1.creativetabs.CreativeTabHelper;
//?}
//? if >=1.19.3 {
import net.minecraft.core.registries.Registries;
//?} else {
import net.minecraft.core.Registry;
//?}
//? if >=1.21.2 {
import net.minecraft.resources.ResourceKey;
//?}
//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//?} else {
import net.minecraft.resources.ResourceLocation;
//?}
//? if <1.19.3 {
import net.minecraft.world.item.CreativeModeTab;
//?}
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
//? if >=1.17 {
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
//?} else {
import net.minecraft.world.level.storage.loot.RandomValueBounds;
//?}

public class ModItems {
    //? if >=1.19.3 {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(TorchTossConstants.MOD_ID, Registries.ITEM);
    //?} else if >=1.16 {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(TorchTossConstants.MOD_ID, Registry.ITEM_REGISTRY);
    //?} else {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(TorchTossConstants.MOD_ID, Registry.ITEM);
    //?}

    public static final Supplier<Item> THROWABLE_TORCH = ITEMS.register(
            "throwable_torch",
            () -> new ThrowableTorchItem(
                    itemProperties("throwable_torch")
            )
    );

    //? if >=1.16 {
    public static final Supplier<Item> THROWABLE_SOUL_TORCH = ITEMS.register(
            "throwable_soul_torch",
            () -> new ThrowableTorchItem(
                    itemProperties("throwable_soul_torch")
            )
    );
    //?}

    //? if >=1.21.10 {
    public static final Supplier<Item> THROWABLE_COPPER_TORCH = ITEMS.register(
            "throwable_copper_torch",
            () -> new ThrowableTorchItem(
                    itemProperties("throwable_copper_torch")
            )
    );
    //?}

    public static final Supplier<Item> THROWABLE_REDSTONE_TORCH = ITEMS.register(
            "throwable_redstone_torch",
            () -> new ThrowableTorchItem(
                    itemProperties("throwable_redstone_torch")
            )
    );

    private static Item.Properties itemProperties(String id) {
        //? if >=1.21.2 {
        return new Item.Properties().setId(ResourceKey.create(
                Registries.ITEM,
                TorchTossConstants.resource(id)
        ));
        //?} else if >=1.19.3 {
        return new Item.Properties();
        //?} else {
        return new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS);
        //?}
    }

    @SuppressWarnings("unchecked") // FIXME: remove this when porting to Amber 9
    public static void init() {
        ITEMS.register();

        //? if >=1.21.10 {
        // Add items to the Functional Blocks creative tab
        ResourceKey<net.minecraft.world.item.CreativeModeTab> functionalBlocksTab = ResourceKey.create(
                Registries.CREATIVE_MODE_TAB,
                //? if >=1.21.11 {
                Identifier.fromNamespaceAndPath("minecraft", "functional_blocks")
                //?} else {
                ResourceLocation.fromNamespaceAndPath("minecraft", "functional_blocks")
                //?}
        );

        // FIXME: remove these casts when porting to Amber 9
        //? if >=1.21.10 {
        CreativeTabHelper.addItems(functionalBlocksTab,
                (Supplier<ItemLike>) (Supplier<?>) THROWABLE_TORCH,
                (Supplier<ItemLike>) (Supplier<?>) THROWABLE_SOUL_TORCH,
                (Supplier<ItemLike>) (Supplier<?>) THROWABLE_COPPER_TORCH,
                (Supplier<ItemLike>) (Supplier<?>) THROWABLE_REDSTONE_TORCH
        );
        //?} else if >=1.16 {
        CreativeTabHelper.addItems(functionalBlocksTab,
                (Supplier<ItemLike>) (Supplier<?>) THROWABLE_TORCH,
                (Supplier<ItemLike>) (Supplier<?>) THROWABLE_SOUL_TORCH,
                (Supplier<ItemLike>) (Supplier<?>) THROWABLE_REDSTONE_TORCH
        );
        //?} else {
        CreativeTabHelper.addItems(functionalBlocksTab,
                (Supplier<ItemLike>) (Supplier<?>) THROWABLE_TORCH,
                (Supplier<ItemLike>) (Supplier<?>) THROWABLE_REDSTONE_TORCH
        );
        //?}
        //?}

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
                .setRolls(randomCount(MIN_ROLLS, MAX_ROLLS))
                .add(LootItem.lootTableItem(THROWABLE_TORCH.get())
                        .setWeight(10)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_REDSTONE_TORCH.get())
                        .setWeight(4)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))));

        //? if >=1.16 {
        // Nether pool: regular + soul + redstone torches
        LootPool.Builder netherPool = LootPool.lootPool()
                .setRolls(randomCount(MIN_ROLLS, MAX_ROLLS))
                .add(LootItem.lootTableItem(THROWABLE_TORCH.get())
                        .setWeight(10)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_SOUL_TORCH.get())
                        .setWeight(8)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_REDSTONE_TORCH.get())
                        .setWeight(4)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))));
        //?}

        //? if >=1.21.10 {
        // Trial chambers pool: regular + copper + redstone torches
        LootPool.Builder trialChambersPool = LootPool.lootPool()
                .setRolls(randomCount(MIN_ROLLS, MAX_ROLLS))
                .add(LootItem.lootTableItem(THROWABLE_TORCH.get())
                        .setWeight(10)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_COPPER_TORCH.get())
                        .setWeight(8)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_REDSTONE_TORCH.get())
                        .setWeight(4)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))));
        //?}

        //? if >=1.21.10 {
        // Ancient city pool: all torch types with emphasis on soul
        LootPool.Builder ancientCityPool = LootPool.lootPool()
                .setRolls(randomCount(MIN_ROLLS, MAX_ROLLS))
                .add(LootItem.lootTableItem(THROWABLE_TORCH.get())
                        .setWeight(8)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_SOUL_TORCH.get())
                        .setWeight(10)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_COPPER_TORCH.get())
                        .setWeight(6)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_REDSTONE_TORCH.get())
                        .setWeight(4)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))));
        //?} else if >=1.16 {
        // Ancient city pool: torch, soul torch, and redstone torch
        LootPool.Builder ancientCityPool = LootPool.lootPool()
                .setRolls(randomCount(MIN_ROLLS, MAX_ROLLS))
                .add(LootItem.lootTableItem(THROWABLE_TORCH.get())
                        .setWeight(8)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_SOUL_TORCH.get())
                        .setWeight(10)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))))
                .add(LootItem.lootTableItem(THROWABLE_REDSTONE_TORCH.get())
                        .setWeight(4)
                        .apply(SetItemCountFunction.setCount(randomCount(MIN_COUNT, MAX_COUNT))));
        //?}

            String path = lootTable.getPath();

            // Mining & Underground structures (basic pool)
            if (path.equals("chests/simple_dungeon") ||
                path.equals("chests/abandoned_mineshaft") ||
                path.equals("chests/stronghold_corridor") ||
                path.equals("chests/woodland_mansion")) {
                addPool.accept(basicPool);
            }

            // Ancient city - all torch types with emphasis on soul
            //? if >=1.16 {
            if (path.equals("chests/ancient_city")) {
                addPool.accept(ancientCityPool);
            }
            //?}

            //? if >=1.21.10 {
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
            //?}

            //? if >=1.16 {
            // Nether structures (soul torch pool)
            if (path.equals("chests/nether_bridge") ||
                path.equals("chests/bastion_treasure") ||
                path.equals("chests/bastion_other") ||
                path.equals("chests/bastion_bridge") ||
                path.equals("chests/bastion_hoglin_stable")) {
                addPool.accept(netherPool);
            }
            //?}

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

    //? if >=1.17 {
    private static UniformGenerator randomCount(int min, int max) {
        return UniformGenerator.between(min, max);
    }
    //?} else {
    private static RandomValueBounds randomCount(int min, int max) {
        return RandomValueBounds.between(min, max);
    }
    //?}
}
