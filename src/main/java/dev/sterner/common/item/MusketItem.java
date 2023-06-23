package dev.sterner.common.item;

import dev.sterner.client.render.item.MusketRenderer;
import dev.sterner.common.util.GunProperties;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class MusketItem extends CockableGunItem implements GeoItem {
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public MusketItem(Settings settings, GunProperties gunProperties) {
        super(settings, gunProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public String getShootAnimation() {
        return "animation.musket.fire";
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private MusketRenderer renderer = null;

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new MusketRenderer();

                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "shoot_controller", state -> PlayState.CONTINUE)
                .triggerableAnim(getShootAnimation(), RawAnimation.begin().thenPlay(getShootAnimation())));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
