package com.iamkaf.torchtoss.advancement;

import java.util.function.Supplier;

import com.iamkaf.torchtoss.TorchTossConstants;
//? if >=1.20.3 {
import com.iamkaf.amber.api.registry.v1.DeferredRegister;
//? if >=26.2 {
import net.minecraft.advancements.triggers.CriterionTrigger;
//?} else {
import net.minecraft.advancements.CriterionTrigger;
//?}
import net.minecraft.core.registries.Registries;
//?} else {
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
//?}

//? if <1.20.3 {
import java.lang.reflect.Method;
//?}

/**
 * Custom advancement triggers for Torch Toss.
 */
public class ModTriggers {
    //? if >=1.20.3 {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(
            TorchTossConstants.MOD_ID,
            Registries.TRIGGER_TYPE
    );

    public static final Supplier<ThrowableTorchTrigger> THROW_TORCH = TRIGGERS.register(
            "throw_torch",
            ThrowableTorchTrigger::new
    );
    //?} else {
    private static final ThrowableTorchTrigger THROW_TORCH_INSTANCE = registerLegacy(new ThrowableTorchTrigger());

    public static final Supplier<ThrowableTorchTrigger> THROW_TORCH = () -> THROW_TORCH_INSTANCE;
    //?}

    /**
     * Initializes the triggers - must be called to ensure class loading.
     */
    public static void init() {
        //? if >=1.20.3 {
        TRIGGERS.register();
        //?}
    }

    //? if <1.20.3 {
    @SuppressWarnings("unchecked")
    private static <T extends CriterionTrigger<?>> T registerLegacy(T trigger) {
        try {
            //? if >=1.20.2 {
            Method method = CriteriaTriggers.class.getDeclaredMethod("register", String.class, CriterionTrigger.class);
            method.setAccessible(true);
            return (T) method.invoke(null, "throw_torch", trigger);
            //?} else {
            Method method = CriteriaTriggers.class.getDeclaredMethod("register", CriterionTrigger.class);
            method.setAccessible(true);
            return (T) method.invoke(null, trigger);
            //?}
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to register Torch Toss criterion trigger", exception);
        }
    }
    //?}
}
