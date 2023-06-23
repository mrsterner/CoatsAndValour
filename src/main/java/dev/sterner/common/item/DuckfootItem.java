package dev.sterner.common.item;

import dev.sterner.api.interfaces.IGeoDualModel;
import dev.sterner.client.render.item.GeoDualModelItemRenderer;
import dev.sterner.common.util.GunProperties;
import dev.sterner.registry.CAVObjects;
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
import net.minecraft.registry.Registries;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class DuckfootItem extends GeoCockableGunItem implements IGeoDualModel {
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public DuckfootItem(Settings settings, GunProperties gunProperties) {
        super(settings, gunProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private GeoDualModelItemRenderer<DuckfootItem> renderer = null;

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new GeoDualModelItemRenderer<>("duckfoot");

                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return this.renderProvider;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
