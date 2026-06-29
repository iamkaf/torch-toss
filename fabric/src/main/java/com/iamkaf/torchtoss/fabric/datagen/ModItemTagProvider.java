package com.iamkaf.torchtoss.fabric.datagen;

//? if >=1.17 {
//? if >=26.1 {
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
//?} else if >=1.19.3 {
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
//?} else {
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
//?}
//? if >=1.19 {
import net.minecraft.core.HolderLookup;
//?}

//? if >=1.19 {
import java.util.concurrent.CompletableFuture;
//?}

/**
 * Generates item tags.
 */
//? if >=26.1 {
public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }
//?} else if >=1.19.3 {
public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }
//?} else {
public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }
//?}

    //? if >=1.19.3 {
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Add item tags here
    }
    //?} else {
    @Override
    protected void generateTags() {
        // Add item tags here
    }
    //?}
}
//?} else {
public final class ModItemTagProvider {
    private ModItemTagProvider() {
    }
}
//?}
