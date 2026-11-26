package com.iamkaf.torchtoss;

import com.iamkaf.torchtoss.TorchTossConstants;
import com.iamkaf.torchtoss.TorchTossMod;
import com.iamkaf.konfig.forge.api.v1.KonfigForgeClientScreens;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TorchTossConstants.MOD_ID)
public class TorchTossForge {

    //? if >=1.21.1 {
    public TorchTossForge(FMLJavaModLoadingContext ctx) {
        //? if >=1.21.7 {
        TorchTossMod.init();
        //?} elif >=1.21.6 {
        TorchTossMod.init(ctx.getModBusGroup());
        //?} else {
        TorchTossMod.init(ctx.getModEventBus());
        //?}
        registerConfigScreen();
    }
    //?} else {
    public TorchTossForge() {
        TorchTossMod.init(FMLJavaModLoadingContext.get().getModEventBus());
        registerConfigScreen();
    }
    //?}

    private static void registerConfigScreen() {
        if (FMLLoader.getDist().isClient()) {
            KonfigForgeClientScreens.register(TorchTossConstants.MOD_ID);
        }
    }
}
