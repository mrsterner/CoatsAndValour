package dev.sterner.client.render.item;

import dev.sterner.CoatsAndValour;
import dev.sterner.common.item.MusketItem;
import mod.azure.azurelib.model.DefaultedItemGeoModel;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class MusketRenderer extends GeoItemRenderer<MusketItem> {
    public MusketRenderer() {
        super(new DefaultedItemGeoModel<>(CoatsAndValour.id("musket")));
    }
}
