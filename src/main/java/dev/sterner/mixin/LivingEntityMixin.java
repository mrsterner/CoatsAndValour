package dev.sterner.mixin;

import dev.sterner.CoatsAndValourEntity;
import dev.sterner.registry.CAVComponents;
import dev.sterner.registry.CoatsAndValourMobEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public final class LivingEntityMixin implements CoatsAndValourEntity {

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (!((LivingEntity) (Object) this).getWorld().isClient) {
            var bleed = ((LivingEntity) (Object) this).getComponent(CAVComponents.BLEED);

            if (isBleeding()) {
                var bleedTime = bleed.getBleedTime();
                if (bleedTime < 1200) {
                    bleed.setBleedTime(bleedTime + 1);
                } else if (!((LivingEntity) (Object) this).hasStatusEffect(CoatsAndValourMobEffects.GANGRENE)) {
                    ((LivingEntity) (Object) this).addStatusEffect(new StatusEffectInstance(CoatsAndValourMobEffects.GANGRENE, 600, 1));
                }
            } else {
                bleed.setBleedTime(0);
            }

            ((LivingEntity) (Object) this).syncComponent(CAVComponents.BLEED);
        }
    }

    @Override
    public boolean isBleeding() {
        return ((LivingEntity) (Object) this).hasStatusEffect(CoatsAndValourMobEffects.BLEED);
    }
}
