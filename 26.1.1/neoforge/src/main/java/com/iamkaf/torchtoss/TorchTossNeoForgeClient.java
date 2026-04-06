package com.iamkaf.torchtoss;

import com.iamkaf.konfig.neoforge.api.v1.KonfigNeoForgeClientScreens;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = TorchTossConstants.MOD_ID, dist = Dist.CLIENT)
public final class TorchTossNeoForgeClient {
    public TorchTossNeoForgeClient(ModContainer container) {
        KonfigNeoForgeClientScreens.register(container, TorchTossConstants.MOD_ID);
    }
}
