package dev.sterner.common.item;

import dev.sterner.api.interfaces.IDualModel;
import dev.sterner.common.util.GunProperties;
import net.minecraft.item.ToolMaterials;

public class PistolSwordItem extends CockableSwordItem implements IDualModel {
    public PistolSwordItem(Settings settings, GunProperties gunProperties) {
        super(ToolMaterials.IRON, 3, -2.4F, gunProperties, settings);
    }
}
