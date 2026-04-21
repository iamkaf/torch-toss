package com.iamkaf.torchtoss;

import com.iamkaf.amber.api.functions.v1.WorldFunctions;
import com.iamkaf.torchtoss.config.TorchTossConfig;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
//? if >=1.21.11 {
import net.minecraft.world.entity.projectile.throwableitemprojectile.Snowball;
//?} else {
import net.minecraft.world.entity.projectile.Snowball;
//?}
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
//? if >=1.16.2 {
import net.minecraft.world.item.context.BlockPlaceContext;
//?} else {
import net.minecraft.world.item.BlockPlaceContext;
//?}
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public final class ThrowableTorchImpactHandler {
    private ThrowableTorchImpactHandler() {
    }

    public static boolean handlesItem(ItemStack item) {
        return getTorchBlock(item) != null || getConfiguredDamage(item) > 0;
    }

    public static boolean handleImpact(Snowball projectile, HitResult result) {
        ItemStack item = projectile.getItem();
        if (!handlesItem(item)) {
            return false;
        }

        if (result.getType() == HitResult.Type.BLOCK) {
            return handleBlockHit(projectile, item, (BlockHitResult) result);
        }

        if (result.getType() == HitResult.Type.ENTITY) {
            return handleEntityHit(projectile, item, (EntityHitResult) result);
        }

        return false;
    }

    private static boolean handleBlockHit(Snowball projectile, ItemStack item, BlockHitResult result) {
        BlockItem torchBlock = getTorchBlock(item);
        if (torchBlock == null) {
            return false;
        }

        Entity owner = projectile.getOwner();
        Player player = owner instanceof Player ? (Player) owner : null;
        if (player == null) {
            dropTorchItem(projectile, item, result);
            return true;
        }

        //? if >=1.16.2 {
        InteractionResult placed = torchBlock.place(new BlockPlaceContext(
                player,
                InteractionHand.MAIN_HAND,
                item,
                result
        ));
        //?} else {
        InteractionResult placed = torchBlock.place(new LegacyBlockPlaceContext(
                player.level,
                player,
                InteractionHand.MAIN_HAND,
                item,
                result
        ));
        //?}
        if (placed == InteractionResult.FAIL) {
            dropTorchItem(projectile, item, result);
        }
        return true;
    }

    private static boolean handleEntityHit(Snowball projectile, ItemStack item, EntityHitResult result) {
        int damage = getConfiguredDamage(item);
        if (damage <= 0) {
            return false;
        }

        Entity target = result.getEntity();
        //? if >=1.19.4 {
        target.hurt(projectile.damageSources().thrown(projectile, projectile.getOwner()), damage);
        //?} else {
        target.hurt(net.minecraft.world.damagesource.DamageSource.thrown(projectile, projectile.getOwner()), damage);
        //?}
        return true;
    }

    private static BlockItem getTorchBlock(ItemStack item) {
        if (itemMatches(item, ModItems.THROWABLE_TORCH.get())) {
            return (BlockItem) Items.TORCH;
        //? if >=1.16 {
        } else if (itemMatches(item, ModItems.THROWABLE_SOUL_TORCH.get())) {
            return (BlockItem) Items.SOUL_TORCH;
        //?}
        //? if >=1.21.10 {
        } else if (itemMatches(item, ModItems.THROWABLE_COPPER_TORCH.get())) {
            return (BlockItem) Items.COPPER_TORCH;
        //?}
        } else if (itemMatches(item, ModItems.THROWABLE_REDSTONE_TORCH.get())) {
            return (BlockItem) Items.REDSTONE_TORCH;
        }

        return null;
    }

    private static void dropTorchItem(Snowball projectile, ItemStack item, HitResult result) {
        //? if >=1.20 {
        WorldFunctions.dropItem(projectile.level(), item, result.getLocation());
        //?} else {
        WorldFunctions.dropItem(projectile.level, item, result.getLocation());
        //?}
    }

    private static int getConfiguredDamage(ItemStack item) {
        if (itemMatches(item, ModItems.THROWABLE_TORCH.get())) {
            return TorchTossConfig.TORCH_DAMAGE.get();
        //? if >=1.16 {
        } else if (itemMatches(item, ModItems.THROWABLE_SOUL_TORCH.get())) {
            return TorchTossConfig.SOUL_TORCH_DAMAGE.get();
        //?}
        //? if >=1.21.10 {
        } else if (itemMatches(item, ModItems.THROWABLE_COPPER_TORCH.get())) {
            return TorchTossConfig.COPPER_TORCH_DAMAGE.get();
        //?}
        } else if (itemMatches(item, ModItems.THROWABLE_REDSTONE_TORCH.get())) {
            return TorchTossConfig.REDSTONE_TORCH_DAMAGE.get();
        }

        return 0;
    }

    private static boolean itemMatches(ItemStack stack, net.minecraft.world.level.ItemLike item) {
        return stack.getItem() == item.asItem();
    }

    //? if <1.16.2 {
    private static final class LegacyBlockPlaceContext extends BlockPlaceContext {
        private LegacyBlockPlaceContext(
                net.minecraft.world.level.Level level,
                Player player,
                InteractionHand hand,
                ItemStack item,
                BlockHitResult result
        ) {
            super(level, player, hand, item, result);
        }
    }
    //?}
}
