package dev.sterner.common.item;

import dev.sterner.common.util.RecoilHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CAVGunItem extends Item  {
    private RecoilHandler handler;

    public CAVGunItem(Settings settings) {
        super(settings);
        this.handler = new RecoilHandler();
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (world.isClient()){

        }

        super.usageTick(world, user, stack, remainingUseTicks);
    }

}
