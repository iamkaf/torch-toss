package com.iamkaf.torchtoss.advancement;

import java.util.function.Supplier;

import com.iamkaf.amber.api.registry.v1.DeferredRegister;
import com.iamkaf.torchtoss.TorchTossConstants;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;

/**
 * Custom advancement triggers for Torch Toss.
 */
public class ModTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(
            TorchTossConstants.MOD_ID,
            Registries.TRIGGER_TYPE
    );

    public static final Supplier<ThrowableTorchTrigger> THROW_TORCH = TRIGGERS.register(
            "throw_torch",
            ThrowableTorchTrigger::new
    );

    /**
     * Initializes the triggers - must be called to ensure class loading.
     */
    public static void init() {
        TRIGGERS.register();
    }
}
