package com.iamkaf.torchtoss;

import com.iamkaf.torchtoss.TorchTossConstants;
import com.iamkaf.torchtoss.TorchTossMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(TorchTossConstants.MOD_ID)
public class TorchTossNeoForge {
    public TorchTossNeoForge(IEventBus eventBus) {
        TorchTossMod.init();
    }
}