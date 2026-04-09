package com.iamkaf.torchtoss.mixin;

import com.iamkaf.amber.api.functions.v1.WorldFunctions;
import com.iamkaf.torchtoss.ModItems;
import com.iamkaf.torchtoss.config.TorchTossConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrowableItemProjectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Snowball.class)
public abstract class SnowballMixin extends ThrowableItemProjectile {
    private static final Logger LOGGER = LoggerFactory.getLogger("TorchToss");

    public SnowballMixin(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "onHit", at = @At("HEAD"))
    private void onHit(HitResult result, CallbackInfo info) {
        try {
            if ((getTorchBlock(getItem()) != null) && result.getType() == HitResult.Type.BLOCK) {
                BlockItem elTorcherino = getTorchBlock(getItem());

                assert elTorcherino != null;

                InteractionResult placed = elTorcherino.place(new BlockPlaceContext(
                        (Player) this.getOwner(),
                        InteractionHand.MAIN_HAND,
                        getItem(),
                        (BlockHitResult) result
                ));
                if (placed.equals(InteractionResult.FAIL)) {
                    WorldFunctions.dropItem(level(), getItem(), result.getLocation());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error placing torch from throwable item", e);
        }
    }

    @Inject(method = "onHitEntity", at = @At("RETURN"))
    private void onHitEntity(EntityHitResult result, CallbackInfo info) {
        int damage = getConfiguredDamage(getItem());
        if (damage <= 0) {
            return;
        }

        Entity target = result.getEntity();
        target.hurt(damageSources().thrown(this, getOwner()), damage);
    }

    private BlockItem getTorchBlock(ItemStack item) {
        if (item.is(ModItems.THROWABLE_TORCH.get())) {
            return (BlockItem) Items.TORCH;
        } else if (item.is(ModItems.THROWABLE_SOUL_TORCH.get())) {
            return (BlockItem) Items.SOUL_TORCH;
        } else if (item.is(ModItems.THROWABLE_COPPER_TORCH.get())) {
            return (BlockItem) Items.COPPER_TORCH;
        } else if (item.is(ModItems.THROWABLE_REDSTONE_TORCH.get())) {
            return (BlockItem) Items.REDSTONE_TORCH;
        }

        return null;
    }

    private int getConfiguredDamage(ItemStack item) {
        if (item.is(ModItems.THROWABLE_TORCH.get())) {
            return TorchTossConfig.TORCH_DAMAGE.get();
        } else if (item.is(ModItems.THROWABLE_SOUL_TORCH.get())) {
            return TorchTossConfig.SOUL_TORCH_DAMAGE.get();
        } else if (item.is(ModItems.THROWABLE_COPPER_TORCH.get())) {
            return TorchTossConfig.COPPER_TORCH_DAMAGE.get();
        } else if (item.is(ModItems.THROWABLE_REDSTONE_TORCH.get())) {
            return TorchTossConfig.REDSTONE_TORCH_DAMAGE.get();
        }

        return 0;
    }
}
