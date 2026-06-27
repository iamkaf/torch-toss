package com.iamkaf.torchtoss.fabric.datagen;

import com.iamkaf.torchtoss.ModItems;
import com.iamkaf.torchtoss.TorchTossConstants;
//? if >=26.1 {
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
//?} else if >=1.19 {
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
//?} else {
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
//?}
//? if >=1.19 {
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
//?} else {
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipesProvider;
//?}
//? if >=1.20.5 {
import net.minecraft.core.HolderLookup;
//?}
//? if >=1.19.3 {
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
//?}
//? if >=1.20.2 {
import net.minecraft.data.recipes.RecipeOutput;
//?} else {
import net.minecraft.data.recipes.FinishedRecipe;
//?}
//? if >=1.21.2 {
import net.minecraft.data.recipes.RecipeProvider;
//?}
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
//? if <1.19.3 {
import net.minecraft.resources.ResourceLocation;
//?}
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

//? if >=1.20.5 {
import java.util.concurrent.CompletableFuture;
//?}
import java.util.function.Consumer;

/**
 * Generates crafting recipes.
 */
//? if >=1.21.2 {
public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        super(provider, recipeOutput);
    }
//?} else {
//? if >=1.19 {
public class ModRecipeProvider extends FabricRecipeProvider {
//?} else {
public class ModRecipeProvider extends FabricRecipesProvider {
//?}
    //? if >=1.20.5 {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }
    //?} else if >=1.19 {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }
    //?} else {
    public ModRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }
    //?}
//?}

    //? if >=1.21.2 {
    @Override
    public void buildRecipes() {
        writeRecipes(output);
    }
    //?} else if >=1.20.2 {
    @Override
    public void buildRecipes(RecipeOutput output) {
        writeRecipes(output);
    }
    //?} else if >=1.19.3 {
    @Override
    public void buildRecipes(Consumer<FinishedRecipe> output) {
        writeRecipes(output);
    }
    //?} else {
    @Override
    public void generateRecipes(Consumer<FinishedRecipe> output) {
        writeRecipes(output);
    }
    //?}

    //? if >=1.20.2 {
    private void writeRecipes(RecipeOutput output) {
    //?} else {
    private void writeRecipes(Consumer<FinishedRecipe> output) {
    //?}
        // turn 2 torches into throwable torches
        shapeless(ModItems.THROWABLE_TORCH.get(), 2)
                .requires(Items.TORCH, 2)
                .unlockedBy("has_torch", has(Items.TORCH))
                .save(output, recipeId("vanilla_to_throwable_torch"));

        // turn 2 throwable torches into torches
        shapeless(Items.TORCH, 2)
                .requires(ModItems.THROWABLE_TORCH.get(), 2)
                .unlockedBy("has_throwable_torch", has(ModItems.THROWABLE_TORCH.get()))
                .save(output, recipeId("throwable_to_vanilla_torch"));

        //? if >=1.16 {
        // turn 2 soul torches into throwable soul torches
        shapeless(ModItems.THROWABLE_SOUL_TORCH.get(), 2)
                .requires(Items.SOUL_TORCH, 2)
                .unlockedBy("has_soul_torch", has(Items.SOUL_TORCH))
                .save(output, recipeId("vanilla_to_throwable_soul_torch"));

        // turn 2 throwable soul torches into soul torches
        shapeless(Items.SOUL_TORCH, 2)
                .requires(ModItems.THROWABLE_SOUL_TORCH.get(), 2)
                .unlockedBy("has_throwable_soul_torch", has(ModItems.THROWABLE_SOUL_TORCH.get()))
                .save(output, recipeId("throwable_to_vanilla_soul_torch"));
        //?}

        //? if >=1.21.10 {
        // turn 2 copper torches into throwable copper torches
        shapeless(ModItems.THROWABLE_COPPER_TORCH.get(), 2)
                .requires(Items.COPPER_TORCH, 2)
                .unlockedBy("has_copper_torch", has(Items.COPPER_TORCH))
                .save(output, recipeId("vanilla_to_throwable_copper_torch"));

        // turn 2 throwable copper torches into copper torches
        shapeless(Items.COPPER_TORCH, 2)
                .requires(ModItems.THROWABLE_COPPER_TORCH.get(), 2)
                .unlockedBy("has_throwable_copper_torch", has(ModItems.THROWABLE_COPPER_TORCH.get()))
                .save(output, recipeId("throwable_to_vanilla_copper_torch"));
        //?}

        // turn 2 redstone torches into throwable redstone torches
        shapeless(ModItems.THROWABLE_REDSTONE_TORCH.get(), 2)
                .requires(Items.REDSTONE_TORCH, 2)
                .unlockedBy("has_redstone_torch", has(Items.REDSTONE_TORCH))
                .save(output, recipeId("vanilla_to_throwable_redstone_torch"));

        // turn 2 throwable redstone torches into redstone torches
        shapeless(Items.REDSTONE_TORCH, 2)
                .requires(ModItems.THROWABLE_REDSTONE_TORCH.get(), 2)
                .unlockedBy("has_throwable_redstone_torch", has(ModItems.THROWABLE_REDSTONE_TORCH.get()))
                .save(output, recipeId("throwable_to_vanilla_redstone_torch"));
    }

    //? if <1.19.3 {
    private ResourceLocation recipeId(String path) {
        return new ResourceLocation(TorchTossConstants.MOD_ID, path);
    }
    //?} else {
    private String recipeId(String path) {
        return path;
    }
    //?}

    private ShapelessRecipeBuilder shapeless(ItemLike item, int count) {
        //? if >=26.1 {
        return ShapelessRecipeBuilder.shapeless(
                registries.lookupOrThrow(BuiltInRegistries.ITEM.key()),
                RecipeCategory.DECORATIONS,
                item,
                count
        );
        //?} else if >=1.21.2 {
        return ShapelessRecipeBuilder.shapeless(
                registries.lookupOrThrow(BuiltInRegistries.ITEM.key()),
                RecipeCategory.DECORATIONS,
                new ItemStack(item, count)
        );
        //?} else if >=1.19 {
        return ShapelessRecipeBuilder.shapeless(
                RecipeCategory.DECORATIONS,
                item,
                count
        );
        //?} else {
        return ShapelessRecipeBuilder.shapeless(item, count);
        //?}
    }

    //? if >=1.21.2 {
    public static class Runner extends FabricRecipeProvider {
        //? if >=26.1 {
        public Runner(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }
        //?} else {
        public Runner(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }
        //?}

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider registries, @NotNull RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public @NotNull String getName() {
            return TorchTossConstants.MOD_ID + " Recipes";
        }
    }
    //?}
}
