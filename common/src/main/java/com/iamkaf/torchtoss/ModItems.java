package com.iamkaf.torchtoss;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ModItems {
    public static final Supplier<Item> THROWABLE_TORCH =
            register("throwable_torch", () -> new ThrowableTorchItem(new Item.Properties()));

    public static final Supplier<Item> THROWABLE_SOUL_TORCH =
            register("throwable_soul_torch", () -> new ThrowableTorchItem(new Item.Properties()));

    public static final Supplier<Item> THROWABLE_REDSTONE_TORCH =
            register("throwable_redstone_torch", () -> new ThrowableTorchItem(new Item.Properties()));

    public static void init() {
    }

    @ExpectPlatform
    public static Supplier<Item> register(String name, Supplier<Item> item) {
        throw new AssertionError();
    }
}
