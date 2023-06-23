package dev.sterner.common.item;

import dev.sterner.common.util.GunProperties;
import mod.azure.azurelib.animatable.GeoItem;

public abstract class GeoCockableGunItem extends CockableGunItem implements GeoItem {
    public GeoCockableGunItem(Settings settings, GunProperties gunProperties) {
        super(settings, gunProperties);
    }
}
