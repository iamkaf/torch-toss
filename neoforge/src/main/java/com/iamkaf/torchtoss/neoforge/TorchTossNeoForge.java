package com.iamkaf.torchtoss.neoforge;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import com.iamkaf.torchtoss.TorchToss;

import java.util.Objects;

@Mod(TorchToss.MOD_ID)
public final class TorchTossNeoForge {
    public TorchTossNeoForge(ModContainer container) {
        // Run our common setup.
        TorchToss.init();
        ModItemsImpl.ITEMS.register(Objects.requireNonNull(container.getEventBus()));
    }
}
