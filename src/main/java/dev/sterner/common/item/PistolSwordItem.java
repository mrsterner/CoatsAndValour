package dev.sterner.common.item;

import dev.sterner.api.interfaces.IDualModel;
import dev.sterner.common.util.GunProperties;

public class PistolSwordItem extends CockableGunItem implements IDualModel {
    public PistolSwordItem(Settings settings, GunProperties gunProperties) {
        super(settings, gunProperties);
    }
}