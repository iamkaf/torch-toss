package com.iamkaf.torchtoss.neoforge;

import com.iamkaf.torchtoss.TorchToss;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModItemsImpl {
    static DeferredRegister<Item> ITEMS = DeferredRegister.createItems(TorchToss.MOD_ID);

    public static Supplier<Item> register(String name, Supplier<Item> item) {
        return ITEMS.register(name, item);
    }
}
