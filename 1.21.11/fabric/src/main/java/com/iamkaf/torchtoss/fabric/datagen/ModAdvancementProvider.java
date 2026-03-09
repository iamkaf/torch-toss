package com.iamkaf.torchtoss.fabric.datagen;

import com.iamkaf.torchtoss.ModItems;
import com.iamkaf.torchtoss.TorchTossConstants;
import com.iamkaf.torchtoss.advancement.ModTriggers;
import com.iamkaf.torchtoss.advancement.ThrowableTorchTrigger;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * Generates advancements.
 */
public class ModAdvancementProvider extends FabricAdvancementProvider {

    public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
        // 1. Throw a normal torch
        Identifier normalTorchItem = BuiltInRegistries.ITEM.getKey(ModItems.THROWABLE_TORCH.get());
        AdvancementHolder normalTorch = Advancement.Builder.advancement()
                .parent(Identifier.fromNamespaceAndPath("minecraft", "adventure/root"))
                .display(
                        ModItems.THROWABLE_TORCH.get(),
                        Component.translatable("advancements.torchtoss.throw_torch.title"),
                        Component.translatable("advancements.torchtoss.throw_torch.description"),
                        null,
                        AdvancementType.TASK,
                        true,  // showToast
                        true,  // announceToChat
                        false  // hidden
                )
                .addCriterion("throw_torch",
                        ModTriggers.THROW_TORCH.get().createCriterion(new ThrowableTorchTrigger.TriggerInstance(Optional.empty(), normalTorchItem)))
                .save(writer, TorchTossConstants.resource("throw_torch").toString());

        // 2. Throw a copper torch
        Identifier copperTorchItem = BuiltInRegistries.ITEM.getKey(ModItems.THROWABLE_COPPER_TORCH.get());
        AdvancementHolder copperTorch = Advancement.Builder.advancement()
                .parent(normalTorch)
                .display(
                        ModItems.THROWABLE_COPPER_TORCH.get(),
                        Component.translatable("advancements.torchtoss.throw_copper_torch.title"),
                        Component.translatable("advancements.torchtoss.throw_copper_torch.description"),
                        null,
                        AdvancementType.TASK,
                        true, true, false
                )
                .addCriterion("throw_copper_torch",
                        ModTriggers.THROW_TORCH.get().createCriterion(new ThrowableTorchTrigger.TriggerInstance(Optional.empty(), copperTorchItem)))
                .save(writer, TorchTossConstants.resource("throw_copper_torch").toString());

        // 3. Throw a redstone torch
        Identifier redstoneTorchItem = BuiltInRegistries.ITEM.getKey(ModItems.THROWABLE_REDSTONE_TORCH.get());
        AdvancementHolder redstoneTorch = Advancement.Builder.advancement()
                .parent(copperTorch)
                .display(
                        ModItems.THROWABLE_REDSTONE_TORCH.get(),
                        Component.translatable("advancements.torchtoss.throw_redstone_torch.title"),
                        Component.translatable("advancements.torchtoss.throw_redstone_torch.description"),
                        null,
                        AdvancementType.TASK,
                        true, true, false
                )
                .addCriterion("throw_redstone_torch",
                        ModTriggers.THROW_TORCH.get().createCriterion(new ThrowableTorchTrigger.TriggerInstance(Optional.empty(), redstoneTorchItem)))
                .save(writer, TorchTossConstants.resource("throw_redstone_torch").toString());

        // 4. Throw a soul torch
        Identifier soulTorchItem = BuiltInRegistries.ITEM.getKey(ModItems.THROWABLE_SOUL_TORCH.get());
        AdvancementHolder soulTorch = Advancement.Builder.advancement()
                .parent(redstoneTorch)
                .display(
                        ModItems.THROWABLE_SOUL_TORCH.get(),
                        Component.translatable("advancements.torchtoss.throw_soul_torch.title"),
                        Component.translatable("advancements.torchtoss.throw_soul_torch.description"),
                        null,
                        AdvancementType.TASK,
                        true, true, false
                )
                .addCriterion("throw_soul_torch",
                        ModTriggers.THROW_TORCH.get().createCriterion(new ThrowableTorchTrigger.TriggerInstance(Optional.empty(), soulTorchItem)))
                .save(writer, TorchTossConstants.resource("throw_soul_torch").toString());
    }
}
