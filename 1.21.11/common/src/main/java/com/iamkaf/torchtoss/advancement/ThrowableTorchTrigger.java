package com.iamkaf.torchtoss.advancement;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.resources.Identifier;

/**
 * Trigger that fires when a player throws a throwable torch.
 */
public class ThrowableTorchTrigger extends SimpleCriterionTrigger<ThrowableTorchTrigger.TriggerInstance> {

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    /**
     * Call this method when a player throws a throwable torch.
     * @param player The player who threw the torch
     * @param item The item that was thrown
     */
    public void trigger(ServerPlayer player, Item item) {
        this.trigger(player, instance -> instance.matches(item));
    }

    /**
     * The trigger instance - stores condition data for JSON serialization.
     */
    public record TriggerInstance(Optional<ContextAwarePredicate> player, Identifier item)
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                Identifier.CODEC.fieldOf("item").forGetter(TriggerInstance::item)
            ).apply(instance, TriggerInstance::new)
        );

        public boolean matches(Item thrownItem) {
            Identifier thrownKey = BuiltInRegistries.ITEM.getKey(thrownItem);
            return thrownKey.equals(this.item);
        }

        @Override
        public Optional<ContextAwarePredicate> player() {
            return this.player;
        }
    }
}
