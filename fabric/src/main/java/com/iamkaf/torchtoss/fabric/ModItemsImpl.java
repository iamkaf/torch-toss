package com.iamkaf.torchtoss.fabric;

import com.iamkaf.torchtoss.TorchToss;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ModItemsImpl {
    public static Supplier<Item> register(String name, Supplier<Item> item) {
        var obj = Registry.register(BuiltInRegistries.ITEM, TorchToss.resource(name), item.get());
        return () -> obj;
    }
}
