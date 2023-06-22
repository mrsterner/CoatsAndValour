package dev.sterner.client.render.item;

import dev.sterner.CoatsAndValour;
import dev.sterner.common.item.PistolItem;
import mod.azure.azurelib.model.DefaultedItemGeoModel;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class PistolRenderer extends GeoItemRenderer<PistolItem> {
    public PistolRenderer() {
        super(new DefaultedItemGeoModel<>(CoatsAndValour.id("pistol")));
    }
}
