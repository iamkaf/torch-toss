package com.iamkaf.torchtoss.fabric.datagen;

import com.iamkaf.torchtoss.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        super(provider, recipeOutput);
    }

    @Override
    public void buildRecipes() {
        // turn 2 torches into a throwable torch
        ShapelessRecipeBuilder.shapeless(
                registries.lookupOrThrow(BuiltInRegistries.ITEM.key()),
                RecipeCategory.DECORATIONS,
                new ItemStack(ModItems.THROWABLE_TORCH.get(), 2)
        ).requires(Items.TORCH, 2).unlockedBy("has_torch", has(Items.TORCH)).save(output);

        // turn 2 throwable torches into a torch
        ShapelessRecipeBuilder.shapeless(
                        registries.lookupOrThrow(BuiltInRegistries.ITEM.key()),
                        RecipeCategory.DECORATIONS,
                        new ItemStack(Items.TORCH, 2)
                )
                .requires(ModItems.THROWABLE_TORCH.get(), 2)
                .unlockedBy("has_throwable_torch", has(ModItems.THROWABLE_TORCH.get()))
                .save(output);
    }

    public static class Runner extends FabricRecipeProvider {
        public Runner(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider registries,
                @NotNull RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public @NotNull String getName() {
            return "TorchToss Recipes";
        }
    }
}
