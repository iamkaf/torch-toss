package com.iamkaf.torchtoss;

import com.iamkaf.torchtoss.TorchTossConstants;
import com.iamkaf.torchtoss.TorchTossMod;
import net.minecraftforge.fml.common.Mod;
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
    }
    //?} else {
    public TorchTossForge() {
        TorchTossMod.init(FMLJavaModLoadingContext.get().getModEventBus());
    }
    //?}
}
