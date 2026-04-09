package com.iamkaf.torchtoss;

import com.iamkaf.torchtoss.TorchTossConstants;
import com.iamkaf.torchtoss.TorchTossMod;
import net.minecraftforge.fml.common.Mod;

@Mod(TorchTossConstants.MOD_ID)
public class TorchTossForge {

    public TorchTossForge() {
        TorchTossMod.init();
    }
}