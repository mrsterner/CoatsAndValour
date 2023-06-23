package dev.sterner.registry;

import dev.sterner.CoatsAndValour;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface CAVTagKeys {
    TagKey<Item> MUSKET_AMMO = TagKey.of(RegistryKeys.ITEM, CoatsAndValour.id("musket_ammo"));
    TagKey<Item> BLUNDERBUSS_AMMO = TagKey.of(RegistryKeys.ITEM, CoatsAndValour.id("blunderbuss_ammo"));
    TagKey<Item> PISTOL_AMMO = TagKey.of(RegistryKeys.ITEM, CoatsAndValour.id("pistol_ammo"));
}
