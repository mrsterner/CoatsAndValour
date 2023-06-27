package dev.sterner.common.item;

import dev.sterner.registry.CoatsAndValourMobEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public final class BandageItem extends Item {

    public BandageItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var itemStack = user.getStackInHand(hand);

        if (user.hasStatusEffect(CoatsAndValourMobEffects.BLEED)) {
            if (!world.isClient) {
                user.removeStatusEffect(CoatsAndValourMobEffects.BLEED);

                itemStack.decrement(1);

                return TypedActionResult.consume(itemStack);
            }

            return TypedActionResult.success(itemStack);
        } else {
            return TypedActionResult.pass(itemStack);
        }
    }
}
