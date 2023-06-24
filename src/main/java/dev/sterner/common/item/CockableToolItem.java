package dev.sterner.common.item;

import dev.sterner.common.util.GunProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class CockableToolItem extends CockableGunItem {
    private final ToolMaterial material;

    public CockableToolItem(Settings settings, ToolMaterial material, GunProperties gunProperties) {
        super(settings.maxDamageIfAbsent(material.getDurability()), gunProperties);
        this.material = material;
    }

    public ToolMaterial getMaterial() {
        return this.material;
    }

    public int getEnchantability() {
        return this.material.getEnchantability();
    }

    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }
}
