package com.iamkaf.torchtoss;

import com.iamkaf.torchtoss.advancement.ModTriggers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
//? if <1.21.2 {
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
//?}
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.level.Level;

public class ThrowableTorchItem extends SnowballItem {
    public ThrowableTorchItem(Properties properties) {
        super(properties);
    }

    //? if >=1.21.2 {
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        InteractionResult result = super.use(level, player, usedHand);

        // Trigger advancement when torch is thrown (server side only)
        if (!level.isClientSide() && result.consumesAction()) {
            if (player instanceof net.minecraft.server.level.ServerPlayer) {
                ModTriggers.THROW_TORCH.get().trigger((net.minecraft.server.level.ServerPlayer) player, this);
            }
        }

        return result;
    }
    //?} else {
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        InteractionResultHolder<ItemStack> result = super.use(level, player, usedHand);

        //? if <1.15 {
        if (!level.isClientSide() && result.getResult() == InteractionResult.SUCCESS) {
        //?} else {
        if (!level.isClientSide() && result.getResult().consumesAction()) {
        //?}
            if (player instanceof net.minecraft.server.level.ServerPlayer) {
                ModTriggers.THROW_TORCH.get().trigger((net.minecraft.server.level.ServerPlayer) player, this);
            }
        }

        return result;
    }
    //?}
}
