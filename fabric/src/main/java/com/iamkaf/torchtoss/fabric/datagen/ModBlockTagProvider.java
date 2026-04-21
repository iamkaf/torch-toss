package com.iamkaf.torchtoss.fabric.datagen;

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
 * Generates block tags.
 */
//? if >=26.1 {
public class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }
//?} else if >=1.19.3 {
public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }
//?} else {
public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }
//?}

    //? if >=1.19.3 {
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // Add block tags here
    }
    //?} else {
    @Override
    protected void generateTags() {
        // Add block tags here
    }
    //?}
}
