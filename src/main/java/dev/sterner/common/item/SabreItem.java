package dev.sterner.common.item;

import dev.sterner.api.interfaces.IDualModel;
import net.minecraft.item.Item;

public class SabreItem extends Item implements IDualModel {
    public SabreItem(Settings settings) {
        super(settings.maxCount(1));
    }
}
