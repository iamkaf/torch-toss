package com.iamkaf.torchtoss.mixin;

import com.iamkaf.amber.api.level.LevelHelper;
import com.iamkaf.torchtoss.ModItems;
import com.iamkaf.torchtoss.TorchToss;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
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
        if (getItem().is(ModItems.THROWABLE_TORCH.get()) && result.getType() == HitResult.Type.BLOCK) {
            BlockItem elTorcherino = (BlockItem) Items.TORCH;
            InteractionResult placed = elTorcherino.place(new BlockPlaceContext(
                    (Player) this.getOwner(),
                    InteractionHand.MAIN_HAND,
                    getItem(),
                    (BlockHitResult) result
            ));
            TorchToss.LOGGER.info("Placed torch: {}", placed);
            if (placed.equals(InteractionResult.FAIL)) {
                LevelHelper.dropItem(level(), getItem(), result.getLocation());
            }
        }
    }
}
