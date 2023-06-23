package dev.sterner.common.item;

import dev.sterner.api.interfaces.IDualModel;
import dev.sterner.common.util.GunProperties;
import dev.sterner.registry.CAVTagKeys;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

public class PistolSwordItem extends CockableGunItem implements IDualModel {
    public PistolSwordItem(Settings settings, GunProperties gunProperties) {
        super(settings, gunProperties);
    }

    @Override
    public TagKey<Item> getAmmoTag() {
        return CAVTagKeys.PISTOL_SWORD_AMMO;
    }
}