package dev.sterner.common.item;

import dev.sterner.api.interfaces.IDualModel;
import net.minecraft.item.*;

public class SabreItem extends SwordItem implements IDualModel {
    public SabreItem(Settings settings) {
        super(ToolMaterials.IRON, 3, -2.4F, settings);
    }
}
