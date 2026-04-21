package com.iamkaf.torchtoss.mixin;

import com.iamkaf.torchtoss.ThrowableTorchImpactHandler;
import net.minecraft.world.entity.EntityType;
//? if >=1.21.11 {
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
//?} else {
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
//?}
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Snowball.class)
public abstract class SnowballMixin extends ThrowableItemProjectile {
    public SnowballMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "onHit", at = @At("HEAD"))
    private void onHit(HitResult result, CallbackInfo info) {
        ThrowableTorchImpactHandler.handleImpact((Snowball) (Object) this, result);
    }

    //? if >=1.16 {
    @Inject(method = "onHitEntity", at = @At("HEAD"))
    private void onHitEntity(EntityHitResult result, CallbackInfo info) {
        ThrowableTorchImpactHandler.handleImpact((Snowball) (Object) this, result);
    }
    //?}
}
