package dev.sterner.common.item;

import dev.sterner.api.interfaces.IGeoDualModel;
import dev.sterner.client.render.item.GeoDualModelItemRenderer;
import dev.sterner.common.util.GunProperties;
import dev.sterner.registry.CAVTagKeys;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class NockItem extends GeoCockableGunItem implements IGeoDualModel {
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

    public NockItem(Settings settings, GunProperties gunProperties) {
        super(settings, gunProperties);
        SingletonGeoAnimatable.registerSyncedAnimatable(this);
    }

    @Override
    public TagKey<Item> getAmmoTag() {
        return CAVTagKeys.NOCK_GUN_AMMO;
    }

    @Override
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private GeoDualModelItemRenderer<NockItem> renderer = null;

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new GeoDualModelItemRenderer<>("nock_gun");

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
