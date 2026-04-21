package com.iamkaf.torchtoss;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TorchTossConstants.MOD_ID)
public class TorchTossForge {
    public TorchTossForge() {
        TorchTossMod.init(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.register(TorchTossLegacyForgeProjectileHandler.class);
    }
}
