package dev.sterner.common.item;

import dev.sterner.common.util.GunProperties;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import net.minecraft.entity.player.PlayerEntity;

public abstract class GeoCockableGunItem extends CockableGunItem implements GeoItem {
    public GeoCockableGunItem(Settings settings, GunProperties gunProperties) {
        super(settings, gunProperties);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>(this, "gun_controller", state -> PlayState.CONTINUE)
                        .triggerableAnim(getUnloadedAnimation(), RawAnimation.begin().thenPlay(getUnloadedAnimation()))
                        .triggerableAnim(getHalfCockedAnimation(), RawAnimation.begin().thenPlay(getHalfCockedAnimation()))
                        .triggerableAnim(getShootAnimation(), RawAnimation.begin().thenPlay(getShootAnimation()))
                        .triggerableAnim(getPrimeAndReloadAnimation(), RawAnimation.begin().thenPlay(getPrimeAnimation()).thenPlay(getPrimeAndReloadAnimation()))
                        .triggerableAnim(getPrimeAnimation(), RawAnimation.begin().thenPlay(getPrimeAnimation()))
                        .triggerableAnim(getCockedAnimation(), RawAnimation.begin().thenPlay(getCockedAnimation()))
        );
    }

    @Override
    public void triggerAnimation(PlayerEntity player, long orAssignId, String gunController, String primeAndReloadAnimation) {
        triggerAnim(player, orAssignId, gunController, primeAndReloadAnimation);
    }
}
