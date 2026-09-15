package com.iamkaf.torchtoss.fabric.datagen;

//? if >=1.17 {
import com.iamkaf.torchtoss.ModItems;
import com.iamkaf.torchtoss.TorchTossConstants;
import com.iamkaf.torchtoss.advancement.ModTriggers;
import com.iamkaf.torchtoss.advancement.ThrowableTorchTrigger;
//? if >=1.20.2 {
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
//?} else {
import net.minecraft.advancements.Advancement;
//?}
//? if >=1.20.3 {
import net.minecraft.advancements.AdvancementType;
//?} else {
import net.minecraft.advancements.FrameType;
//?}
//? if >=26.2 {
//? if <26.3
import net.minecraft.advancements.predicates.ContextAwarePredicate;
//?} else if >=1.21.11 {
import net.minecraft.advancements.criterion.ContextAwarePredicate;
//?} else {
//? if <1.20 {
import net.minecraft.advancements.critereon.EntityPredicate;
//?} else {
import net.minecraft.advancements.critereon.ContextAwarePredicate;
//?}
//?}
//? if >=1.20.5 {
import net.minecraft.core.HolderLookup;
//?}
//? if >=1.19.3 {
import net.minecraft.core.registries.BuiltInRegistries;
//?}
import net.minecraft.network.chat.Component;
//? if <1.19 {
import net.minecraft.network.chat.TranslatableComponent;
//?}
//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//?} else {
import net.minecraft.resources.ResourceLocation;
//?}
//? if >=26.1 {
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
//?} else if >=1.19.3 {
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
//?}
import net.minecraft.world.item.Items;
//? if <1.19.3 {
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
//?}
//? if <1.19 {
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementsProvider;
//?}
//? if >=1.19 {
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
//?}

import java.util.Optional;
//? if >=1.19 {
import java.util.concurrent.CompletableFuture;
//?}
import java.util.function.Consumer;

/**
 * Generates advancements.
 */
//? if >=1.19 {
public class ModAdvancementProvider extends FabricAdvancementProvider {
//?} else {
public class ModAdvancementProvider extends FabricAdvancementsProvider {
//?}

    //? if >=26.1 {
    public ModAdvancementProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }
    //?} else if >=1.20.5 {
    public ModAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }
    //?} else if >=1.19.3 {
    public ModAdvancementProvider(FabricDataOutput output) {
        super(output);
    }
    //?} else {
    public ModAdvancementProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }
    //?}

    //? if >=1.20.5 {
    @Override
    public void generateAdvancement(HolderLookup.Provider registries, Consumer<AdvancementHolder> writer) {
    //?} else if >=1.20.2 {
    @Override
    public void generateAdvancement(Consumer<AdvancementHolder> writer) {
    //?} else {
    @Override
    public void generateAdvancement(Consumer<Advancement> writer) {
    //?}
        // 1. Throw a normal torch
        //? if >=1.21.11 {
        Identifier normalTorchItem = BuiltInRegistries.ITEM.getKey(ModItems.THROWABLE_TORCH.get());
        //?} else {
        //? if >=1.19.3 {
        ResourceLocation normalTorchItem = BuiltInRegistries.ITEM.getKey(ModItems.THROWABLE_TORCH.get());
        //?} else {
        ResourceLocation normalTorchItem = net.minecraft.core.Registry.ITEM.getKey(ModItems.THROWABLE_TORCH.get());
        //?}
        //?}
        //? if >=1.20.2 {
        AdvancementHolder normalTorch = Advancement.Builder.advancement()
        //?} else {
        Advancement normalTorch = Advancement.Builder.advancement()
        //?}
                //? if >=1.20.2 && >=1.21.11 {
                .parent(Identifier.fromNamespaceAndPath("minecraft", "adventure/root"))
                //?} else if >=1.20.2 && >=1.21 {
                .parent(ResourceLocation.fromNamespaceAndPath("minecraft", "adventure/root"))
                //?} else if >=1.20.2 {
                .parent(new ResourceLocation("minecraft", "adventure/root"))
                //?}
                .display(
                        ModItems.THROWABLE_TORCH.get(),
                        translatable("advancements.torchtoss.throw_torch.title"),
                        translatable("advancements.torchtoss.throw_torch.description"),
                        //? if <26.3
                        null,
                        //? if >=1.20.3 {
                        AdvancementType.TASK,
                        //?} else {
                        FrameType.TASK,
                        //?}
                        true,  // showToast
                        true,  // announceToChat
                        false  // hidden
                )
                .addCriterion("throw_torch",
                        throwableTorchCriterion(normalTorchItem))
                //? if >=26.3
                /*.save(writer, TorchTossConstants.resource("throw_torch"));*/
                //? if <26.3
                .save(writer, TorchTossConstants.resource("throw_torch").toString());

        //? if >=1.21.10 {
        // 2. Throw a copper torch
        //? if >=1.21.11 {
        Identifier copperTorchItem = BuiltInRegistries.ITEM.getKey(ModItems.THROWABLE_COPPER_TORCH.get());
        //?} else {
        ResourceLocation copperTorchItem = BuiltInRegistries.ITEM.getKey(ModItems.THROWABLE_COPPER_TORCH.get());
        //?}
        //? if >=1.20.2 {
        AdvancementHolder copperTorch = Advancement.Builder.advancement()
        //?} else {
        Advancement copperTorch = Advancement.Builder.advancement()
        //?}
                .parent(normalTorch)
                .display(
                        ModItems.THROWABLE_COPPER_TORCH.get(),
                        translatable("advancements.torchtoss.throw_copper_torch.title"),
                        translatable("advancements.torchtoss.throw_copper_torch.description"),
                        //? if <26.3
                        null,
                        //? if >=1.20.3 {
                        AdvancementType.TASK,
                        //?} else {
                        FrameType.TASK,
                        //?}
                        true, true, false
                )
                .addCriterion("throw_copper_torch",
                        throwableTorchCriterion(copperTorchItem))
                //? if >=26.3
                /*.save(writer, TorchTossConstants.resource("throw_copper_torch"));*/
                //? if <26.3
                .save(writer, TorchTossConstants.resource("throw_copper_torch").toString());
        //?}

        // 3. Throw a redstone torch
        //? if >=1.21.11 {
        Identifier redstoneTorchItem = BuiltInRegistries.ITEM.getKey(ModItems.THROWABLE_REDSTONE_TORCH.get());
        //?} else {
        //? if >=1.19.3 {
        ResourceLocation redstoneTorchItem = BuiltInRegistries.ITEM.getKey(ModItems.THROWABLE_REDSTONE_TORCH.get());
        //?} else {
        ResourceLocation redstoneTorchItem = net.minecraft.core.Registry.ITEM.getKey(ModItems.THROWABLE_REDSTONE_TORCH.get());
        //?}
        //?}
        //? if >=1.20.2 {
        AdvancementHolder redstoneTorch = Advancement.Builder.advancement()
        //?} else {
        Advancement redstoneTorch = Advancement.Builder.advancement()
        //?}
                //? if >=1.21.10 {
                .parent(copperTorch)
                //?} else {
                .parent(normalTorch)
                //?}
                .display(
                        ModItems.THROWABLE_REDSTONE_TORCH.get(),
                        translatable("advancements.torchtoss.throw_redstone_torch.title"),
                        translatable("advancements.torchtoss.throw_redstone_torch.description"),
                        //? if <26.3
                        null,
                        //? if >=1.20.3 {
                        AdvancementType.TASK,
                        //?} else {
                        FrameType.TASK,
                        //?}
                        true, true, false
                )
                .addCriterion("throw_redstone_torch",
                        throwableTorchCriterion(redstoneTorchItem))
                //? if >=26.3
                /*.save(writer, TorchTossConstants.resource("throw_redstone_torch"));*/
                //? if <26.3
                .save(writer, TorchTossConstants.resource("throw_redstone_torch").toString());

        //? if >=1.16 {
        // 4. Throw a soul torch
        //? if >=1.21.11 {
        Identifier soulTorchItem = BuiltInRegistries.ITEM.getKey(ModItems.THROWABLE_SOUL_TORCH.get());
        //?} else {
        //? if >=1.19.3 {
        ResourceLocation soulTorchItem = BuiltInRegistries.ITEM.getKey(ModItems.THROWABLE_SOUL_TORCH.get());
        //?} else {
        ResourceLocation soulTorchItem = net.minecraft.core.Registry.ITEM.getKey(ModItems.THROWABLE_SOUL_TORCH.get());
        //?}
        //?}
        //? if >=1.20.2 {
        AdvancementHolder soulTorch = Advancement.Builder.advancement()
        //?} else {
        Advancement soulTorch = Advancement.Builder.advancement()
        //?}
                .parent(redstoneTorch)
                .display(
                        ModItems.THROWABLE_SOUL_TORCH.get(),
                        translatable("advancements.torchtoss.throw_soul_torch.title"),
                        translatable("advancements.torchtoss.throw_soul_torch.description"),
                        //? if <26.3
                        null,
                        //? if >=1.20.3 {
                        AdvancementType.TASK,
                        //?} else {
                        FrameType.TASK,
                        //?}
                        true, true, false
                )
                .addCriterion("throw_soul_torch",
                        throwableTorchCriterion(soulTorchItem))
                //? if >=26.3
                /*.save(writer, TorchTossConstants.resource("throw_soul_torch"));*/
                //? if <26.3
                .save(writer, TorchTossConstants.resource("throw_soul_torch").toString());
        //?}
    }

    //? if >=1.20.2 {
    //? if >=26.2 {
    private static net.minecraft.advancements.triggers.Criterion<ThrowableTorchTrigger.TriggerInstance> throwableTorchCriterion(Identifier item) {
        return ModTriggers.THROW_TORCH.get().createCriterion(new ThrowableTorchTrigger.TriggerInstance(Optional.empty(), item));
    }
    //?} else if >=1.21.11 {
    private static net.minecraft.advancements.Criterion<ThrowableTorchTrigger.TriggerInstance> throwableTorchCriterion(Identifier item) {
        return ModTriggers.THROW_TORCH.get().createCriterion(new ThrowableTorchTrigger.TriggerInstance(Optional.empty(), item));
    }
    //?} else {
    private static net.minecraft.advancements.Criterion<ThrowableTorchTrigger.TriggerInstance> throwableTorchCriterion(ResourceLocation item) {
        return ModTriggers.THROW_TORCH.get().createCriterion(new ThrowableTorchTrigger.TriggerInstance(Optional.empty(), item));
    }
    //?}
    //?} else {
    private static ThrowableTorchTrigger.TriggerInstance throwableTorchCriterion(ResourceLocation item) {
        //? if >=1.20 {
        return new ThrowableTorchTrigger.TriggerInstance(ContextAwarePredicate.ANY, item);
        //?} else {
        return new ThrowableTorchTrigger.TriggerInstance(EntityPredicate.Composite.ANY, item);
        //?}
    }
    //?}

    //? if <1.19 {
    private static Component translatable(String key) {
        return new TranslatableComponent(key);
    }
    //?} else {
    private static Component translatable(String key) {
        return Component.translatable(key);
    }
    //?}
}
//?} else {
public final class ModAdvancementProvider {
    private ModAdvancementProvider() {
    }
}
//?}
