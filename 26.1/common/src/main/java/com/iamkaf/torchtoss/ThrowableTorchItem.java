package com.iamkaf.torchtoss;

import com.iamkaf.torchtoss.advancement.ModTriggers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.SnowballItem;
import net.minecraft.world.level.Level;

public class ThrowableTorchItem extends SnowballItem {
    public ThrowableTorchItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, net.minecraft.world.InteractionHand usedHand) {
        InteractionResult result = super.use(level, player, usedHand);

        // Trigger advancement when torch is thrown (server side only)
        if (!level.isClientSide() && result.consumesAction()) {
            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                ModTriggers.THROW_TORCH.get().trigger(serverPlayer, this);
            }
        }

        return result;
    }
}
