package com.iamkaf.torchtoss.advancement;

import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
//? if <1.16 {
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
//?}
import java.util.Optional;
//? if >=26.3 {
/*import net.minecraft.core.Holder;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
*///?}
//? if <1.16 {
import java.util.List;
import java.util.Map;
import java.util.Set;
//?}

//? if >=1.20.3 {
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
//?}
//? if >=26.2 {
//? if <26.3
import net.minecraft.advancements.predicates.ContextAwarePredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
//?} else if >=1.21.11 {
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
//?} else {
//? if <1.16 {
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.server.PlayerAdvancements;
//?} else if <1.16 {
import net.minecraft.advancements.CriterionTriggerInstance;
//?}
//? if >=1.16 && <1.20.3 {
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
//?}
//? if >=1.16 && <1.20.2 {
import net.minecraft.advancements.critereon.SerializationContext;
//?}
import net.minecraft.advancements.critereon.EntityPredicate;
//? if >=1.15 {
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
//?}
//? if >=1.20 {
import net.minecraft.advancements.critereon.ContextAwarePredicate;
//?}
//?}
//? if >=1.19.3 {
import net.minecraft.core.registries.BuiltInRegistries;
//?} else {
import net.minecraft.core.Registry;
//?}
//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//?} else {
import net.minecraft.resources.ResourceLocation;
//?}
import net.minecraft.server.level.ServerPlayer;
//? if <1.20.3 {
import net.minecraft.util.GsonHelper;
//?}
import net.minecraft.world.item.Item;
import com.iamkaf.torchtoss.TorchTossConstants;

/**
 * Trigger that fires when a player throws a throwable torch.
 */
//? if <1.15 {
public class ThrowableTorchTrigger implements CriterionTrigger<ThrowableTorchTrigger.TriggerInstance> {
    private final Map<PlayerAdvancements, PlayerListeners> players = Maps.newHashMap();
//?} else {
public class ThrowableTorchTrigger extends SimpleCriterionTrigger<ThrowableTorchTrigger.TriggerInstance> {
//?}

    //? if >=1.20.3 {
    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }
    //?} else if >=1.20.2 {
    @Override
    protected TriggerInstance createInstance(JsonObject json, Optional<ContextAwarePredicate> player, DeserializationContext context) {
        return new TriggerInstance(
                player,
                new ResourceLocation(GsonHelper.getAsString(json, "item"))
        );
    }
    //?} else if >=1.20 {
    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
        return new TriggerInstance(
                player,
                new ResourceLocation(GsonHelper.getAsString(json, "item"))
        );
    }

    @Override
    public ResourceLocation getId() {
        return TorchTossConstants.resource("throw_torch");
    }
    //?} else if >=1.16 {
    @Override
    protected TriggerInstance createInstance(JsonObject json, EntityPredicate.Composite player, DeserializationContext context) {
        return new TriggerInstance(
                player,
                new ResourceLocation(GsonHelper.getAsString(json, "item"))
        );
    }

    @Override
    public ResourceLocation getId() {
        return TorchTossConstants.resource("throw_torch");
    }
    //?} else if >=1.15 {
    @Override
    public TriggerInstance createInstance(JsonObject json, JsonDeserializationContext context) {
        return new TriggerInstance(
            EntityPredicate.fromJson(json.get("player")),
            new ResourceLocation(GsonHelper.getAsString(json, "item"))
        );
    }

    @Override
    public ResourceLocation getId() {
        return TorchTossConstants.resource("throw_torch");
    }
    //?} else {
    @Override
    public TriggerInstance createInstance(JsonObject json, JsonDeserializationContext context) {
        return new TriggerInstance(
                EntityPredicate.fromJson(json.get("player")),
                new ResourceLocation(GsonHelper.getAsString(json, "item"))
        );
    }

    @Override
    public ResourceLocation getId() {
        return TorchTossConstants.resource("throw_torch");
    }

    @Override
    public void addPlayerListener(PlayerAdvancements advancements, CriterionTrigger.Listener<TriggerInstance> listener) {
        PlayerListeners listeners = this.players.get(advancements);
        if (listeners == null) {
            listeners = new PlayerListeners(advancements);
            this.players.put(advancements, listeners);
        }
        listeners.addListener(listener);
    }

    @Override
    public void removePlayerListener(PlayerAdvancements advancements, CriterionTrigger.Listener<TriggerInstance> listener) {
        PlayerListeners listeners = this.players.get(advancements);
        if (listeners != null) {
            listeners.removeListener(listener);
            if (listeners.isEmpty()) {
                this.players.remove(advancements);
            }
        }
    }

    @Override
    public void removePlayerListeners(PlayerAdvancements advancements) {
        this.players.remove(advancements);
    }
    //?}

    /**
     * Call this method when a player throws a throwable torch.
     * @param player The player who threw the torch
     * @param item The item that was thrown
     */
    public void trigger(ServerPlayer player, Item item) {
        //? if >=1.16 {
        this.trigger(player, instance -> instance.matches(item));
        //?} else if >=1.15 {
        this.trigger(player.getAdvancements(), instance -> instance.matches(player, item));
        //?} else {
        PlayerListeners listeners = this.players.get(player.getAdvancements());
        if (listeners != null) {
            listeners.trigger(player, item);
        }
        //?}
    }

    /**
     * The trigger instance - stores condition data for JSON serialization.
     */
    //? if >=1.20.3 {
    //? if >=26.3 {
    /*public record TriggerInstance(Optional<Holder<LootItemCondition>> player, Identifier item)
    *///?} else if >=1.21.11 {
    public record TriggerInstance(Optional<ContextAwarePredicate> player, Identifier item)
    //?} else {
    public record TriggerInstance(Optional<ContextAwarePredicate> player, ResourceLocation item)
    //?}
            implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                //? if >=26.3
                /*LootItemCondition.CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),*/
                //? if <26.3
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player),
                //? if >=1.21.11 {
                Identifier.CODEC.fieldOf("item").forGetter(TriggerInstance::item)
                //?} else {
                ResourceLocation.CODEC.fieldOf("item").forGetter(TriggerInstance::item)
                //?}
            ).apply(instance, TriggerInstance::new)
        );

        public boolean matches(Item thrownItem) {
            //? if >=1.21.11 {
            Identifier thrownKey = BuiltInRegistries.ITEM.getKey(thrownItem);
            //?} else if >=1.19.3 {
            ResourceLocation thrownKey = BuiltInRegistries.ITEM.getKey(thrownItem);
            //?} else {
            ResourceLocation thrownKey = Registry.ITEM.getKey(thrownItem);
            //?}
            return thrownKey.equals(this.item);
        }

    }
    //?} else if >=1.20.2 {
    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ResourceLocation item;

        public TriggerInstance(Optional<ContextAwarePredicate> player, ResourceLocation item) {
            super(player);
            this.item = item;
        }

        @Override
        public JsonObject serializeToJson() {
            JsonObject json = super.serializeToJson();
            json.addProperty("item", this.item.toString());
            return json;
        }

        public boolean matches(Item thrownItem) {
            //? if >=1.19.3 {
            ResourceLocation thrownKey = BuiltInRegistries.ITEM.getKey(thrownItem);
            //?} else {
            ResourceLocation thrownKey = Registry.ITEM.getKey(thrownItem);
            //?}
            return thrownKey.equals(this.item);
        }
    }
    //?} else if >=1.20 {
    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ResourceLocation item;

        public TriggerInstance(ContextAwarePredicate player, ResourceLocation item) {
            super(TorchTossConstants.resource("throw_torch"), player);
            this.item = item;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject json = super.serializeToJson(context);
            json.addProperty("item", this.item.toString());
            return json;
        }

        public boolean matches(Item thrownItem) {
            //? if >=1.19.3 {
            ResourceLocation thrownKey = BuiltInRegistries.ITEM.getKey(thrownItem);
            //?} else {
            ResourceLocation thrownKey = Registry.ITEM.getKey(thrownItem);
            //?}
            return thrownKey.equals(this.item);
        }
    }
    //?} else if >=1.16 {
    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ResourceLocation item;

        public TriggerInstance(EntityPredicate.Composite player, ResourceLocation item) {
            super(TorchTossConstants.resource("throw_torch"), player);
            this.item = item;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject json = super.serializeToJson(context);
            json.addProperty("item", this.item.toString());
            return json;
        }

        public boolean matches(Item thrownItem) {
            //? if >=1.19.3 {
            ResourceLocation thrownKey = BuiltInRegistries.ITEM.getKey(thrownItem);
            //?} else {
            ResourceLocation thrownKey = Registry.ITEM.getKey(thrownItem);
            //?}
            return thrownKey.equals(this.item);
        }
    }
    //?} else {
    public static class TriggerInstance implements CriterionTriggerInstance {
        private final EntityPredicate player;
        private final ResourceLocation item;

        public TriggerInstance(EntityPredicate player, ResourceLocation item) {
            this.player = player;
            this.item = item;
        }

        @Override
        public ResourceLocation getCriterion() {
            return TorchTossConstants.resource("throw_torch");
        }

        @Override
        public JsonObject serializeToJson() {
            JsonObject json = new JsonObject();
            json.add("player", this.player.serializeToJson());
            json.addProperty("item", this.item.toString());
            return json;
        }

        public boolean matches(ServerPlayer player, Item thrownItem) {
            ResourceLocation thrownKey = Registry.ITEM.getKey(thrownItem);
            return thrownKey.equals(this.item) && this.player.matches(player, player);
        }
    }

    private static final class PlayerListeners {
        private final PlayerAdvancements player;
        private final Set<CriterionTrigger.Listener<TriggerInstance>> listeners = Sets.newHashSet();

        private PlayerListeners(PlayerAdvancements player) {
            this.player = player;
        }

        private boolean isEmpty() {
            return this.listeners.isEmpty();
        }

        private void addListener(CriterionTrigger.Listener<TriggerInstance> listener) {
            this.listeners.add(listener);
        }

        private void removeListener(CriterionTrigger.Listener<TriggerInstance> listener) {
            this.listeners.remove(listener);
        }

        private void trigger(ServerPlayer player, Item item) {
            List<CriterionTrigger.Listener<TriggerInstance>> matches = null;
            for (CriterionTrigger.Listener<TriggerInstance> listener : this.listeners) {
                if (listener.getTriggerInstance().matches(player, item)) {
                    if (matches == null) {
                        matches = Lists.newArrayList();
                    }
                    matches.add(listener);
                }
            }

            if (matches != null) {
                for (CriterionTrigger.Listener<TriggerInstance> listener : matches) {
                    listener.run(this.player);
                }
            }
        }
    }
    //?}
}
