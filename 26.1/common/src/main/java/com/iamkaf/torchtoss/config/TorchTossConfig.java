package com.iamkaf.torchtoss.config;

import com.iamkaf.konfig.api.v1.ConfigBuilder;
import com.iamkaf.konfig.api.v1.ConfigHandle;
import com.iamkaf.konfig.api.v1.ConfigScope;
import com.iamkaf.konfig.api.v1.ConfigValue;
import com.iamkaf.konfig.api.v1.Konfig;
import com.iamkaf.konfig.api.v1.SyncMode;
import com.iamkaf.torchtoss.TorchTossConstants;

public final class TorchTossConfig {
    public static final ConfigHandle HANDLE;
    public static final ConfigValue<Integer> TORCH_DAMAGE;
    public static final ConfigValue<Integer> SOUL_TORCH_DAMAGE;
    public static final ConfigValue<Integer> COPPER_TORCH_DAMAGE;
    public static final ConfigValue<Integer> REDSTONE_TORCH_DAMAGE;

    static {
        ConfigBuilder builder = Konfig.builder(TorchTossConstants.MOD_ID, "common")
                .scope(ConfigScope.COMMON)
                .syncMode(SyncMode.LOGIN)
                .comment("Gameplay settings for Torch Toss.");

        builder.push("general");
        TORCH_DAMAGE = builder.intRange("torchDamage", 1, 0, 4)
                .comment("Damage dealt when a throwable torch hits an entity.")
                .sync(true)
                .build();
        SOUL_TORCH_DAMAGE = builder.intRange("soulTorchDamage", 1, 0, 4)
                .comment("Damage dealt when a throwable soul torch hits an entity.")
                .sync(true)
                .build();
        COPPER_TORCH_DAMAGE = builder.intRange("copperTorchDamage", 1, 0, 4)
                .comment("Damage dealt when a throwable copper torch hits an entity.")
                .sync(true)
                .build();
        REDSTONE_TORCH_DAMAGE = builder.intRange("redstoneTorchDamage", 1, 0, 4)
                .comment("Damage dealt when a throwable redstone torch hits an entity.")
                .sync(true)
                .build();
        builder.pop();

        HANDLE = builder.build();
    }

    private TorchTossConfig() {
    }

    public static void init() {
    }
}
