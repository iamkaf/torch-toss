package com.iamkaf.torchtoss.fabric.datagen;

//? if >=26.1 {
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
//?} else if >=1.19.3 {
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
//?} else if >=1.18.2 {
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
//?}
//? if >=1.18.2 && <1.19.4 {
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;
import java.util.function.BiConsumer;
//?}
//? if >=1.19 {
import net.minecraft.core.HolderLookup;
//?}

//? if >=1.19 {
import java.util.concurrent.CompletableFuture;
//?}

/**
 * Generates block loot tables.
 */
//? if >=26.1 {
public class ModBlockLootTableProvider extends FabricBlockLootSubProvider {
    public ModBlockLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }
//?} else if >=1.20.5 {
public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }
//?} else if >=1.19.3 {
public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }
//?} else if >=1.18.2 {
public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }
//?} else {
public final class ModBlockLootTableProvider {
    private ModBlockLootTableProvider() {
    }
//?}

//? if >=1.18.2 {
    //? if >=1.19.3 {
    @Override
    public void generate() {
    //?} else {
    @Override
    protected void generateBlockLootTables() {
    //?}
        // Add loot table generation here
    }

//? if <1.19.4 {
    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> exporter) {
        // No block loot tables are generated for this mod on older Fabric datagen.
    }
//?}
//?}
}
