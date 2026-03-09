package com.iamkaf.torchtoss.fabric.datagen;

import com.iamkaf.torchtoss.ModItems;
import com.iamkaf.torchtoss.TorchTossConstants;
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

/**
 * Generates crafting recipes.
 */
public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider provider, RecipeOutput recipeOutput) {
        super(provider, recipeOutput);
    }

    @Override
    public void buildRecipes() {
        // turn 2 torches into throwable torches
        ShapelessRecipeBuilder.shapeless(
                registries.lookupOrThrow(BuiltInRegistries.ITEM.key()),
                RecipeCategory.DECORATIONS,
                new ItemStack(ModItems.THROWABLE_TORCH.get(), 2)
        ).requires(Items.TORCH, 2).unlockedBy("has_torch", has(Items.TORCH)).save(output, "vanilla_to_throwable_torch");

        // turn 2 throwable torches into torches
        ShapelessRecipeBuilder.shapeless(
                        registries.lookupOrThrow(BuiltInRegistries.ITEM.key()),
                        RecipeCategory.DECORATIONS,
                        new ItemStack(Items.TORCH, 2)
                )
                .requires(ModItems.THROWABLE_TORCH.get(), 2)
                .unlockedBy("has_throwable_torch", has(ModItems.THROWABLE_TORCH.get()))
                .save(output, "throwable_to_vanilla_torch");

        // turn 2 soul torches into throwable soul torches
        ShapelessRecipeBuilder.shapeless(
                        registries.lookupOrThrow(BuiltInRegistries.ITEM.key()),
                        RecipeCategory.DECORATIONS,
                        new ItemStack(ModItems.THROWABLE_SOUL_TORCH.get(), 2)
                )
                .requires(Items.SOUL_TORCH, 2)
                .unlockedBy("has_soul_torch", has(Items.SOUL_TORCH))
                .save(output, "vanilla_to_throwable_soul_torch");

        // turn 2 throwable soul torches into soul torches
        ShapelessRecipeBuilder.shapeless(
                        registries.lookupOrThrow(BuiltInRegistries.ITEM.key()),
                        RecipeCategory.DECORATIONS,
                        new ItemStack(Items.SOUL_TORCH, 2)
                )
                .requires(ModItems.THROWABLE_SOUL_TORCH.get(), 2)
                .unlockedBy("has_throwable_soul_torch", has(ModItems.THROWABLE_SOUL_TORCH.get()))
                .save(output, "throwable_to_vanilla_soul_torch");

        // turn 2 copper torches into throwable copper torches
        ShapelessRecipeBuilder.shapeless(
                        registries.lookupOrThrow(BuiltInRegistries.ITEM.key()),
                        RecipeCategory.DECORATIONS,
                        new ItemStack(ModItems.THROWABLE_COPPER_TORCH.get(), 2)
                )
                .requires(Items.COPPER_TORCH, 2)
                .unlockedBy("has_copper_torch", has(Items.COPPER_TORCH))
                .save(output, "vanilla_to_throwable_copper_torch");

        // turn 2 throwable copper torches into copper torches
        ShapelessRecipeBuilder.shapeless(
                        registries.lookupOrThrow(BuiltInRegistries.ITEM.key()),
                        RecipeCategory.DECORATIONS,
                        new ItemStack(Items.COPPER_TORCH, 2)
                )
                .requires(ModItems.THROWABLE_COPPER_TORCH.get(), 2)
                .unlockedBy("has_throwable_copper_torch", has(ModItems.THROWABLE_COPPER_TORCH.get()))
                .save(output, "throwable_to_vanilla_copper_torch");

        // turn 2 redstone torches into throwable redstone torches
        ShapelessRecipeBuilder.shapeless(
                        registries.lookupOrThrow(BuiltInRegistries.ITEM.key()),
                        RecipeCategory.DECORATIONS,
                        new ItemStack(ModItems.THROWABLE_REDSTONE_TORCH.get(), 2)
                )
                .requires(Items.REDSTONE_TORCH, 2)
                .unlockedBy("has_redstone_torch", has(Items.REDSTONE_TORCH))
                .save(output, "vanilla_to_throwable_redstone_torch");

        // turn 2 throwable redstone torches into redstone torches
        ShapelessRecipeBuilder.shapeless(
                        registries.lookupOrThrow(BuiltInRegistries.ITEM.key()),
                        RecipeCategory.DECORATIONS,
                        new ItemStack(Items.REDSTONE_TORCH, 2)
                )
                .requires(ModItems.THROWABLE_REDSTONE_TORCH.get(), 2)
                .unlockedBy("has_throwable_redstone_torch", has(ModItems.THROWABLE_REDSTONE_TORCH.get()))
                .save(output, "throwable_to_vanilla_redstone_torch");
    }

    public static class Runner extends FabricRecipeProvider {
        public Runner(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider registries, @NotNull RecipeOutput output) {
            return new ModRecipeProvider(registries, output);
        }

        @Override
        public @NotNull String getName() {
            return TorchTossConstants.MOD_ID + " Recipes";
        }
    }
}
