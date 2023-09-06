package com.phantomskeep.phantomeq.item;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PEQSaddleItem extends Item {
    public PEQSaddleItem(Item.Properties properties) {
        super(properties);
    }

    public InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if (livingEntity instanceof Saddleable && livingEntity.isAlive()) {
            Saddleable saddleable = (Saddleable) livingEntity;
            if (!saddleable.isSaddled() && saddleable.isSaddleable()) {
                if (!player.level.isClientSide) {
                    saddleable.equipSaddle(SoundSource.NEUTRAL);
                    itemStack.shrink(1);
                }

                return InteractionResult.sidedSuccess(player.level.isClientSide);
            }
        }

        return InteractionResult.PASS;
    }
}
