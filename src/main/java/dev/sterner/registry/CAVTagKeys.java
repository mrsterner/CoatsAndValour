package dev.sterner.registry;

import dev.sterner.CoatsAndValour;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface CAVTagKeys {
    TagKey<Item> MUSKET_AMMO = TagKey.of(RegistryKeys.ITEM, CoatsAndValour.id("musket_ammo"));
    TagKey<Item> BLUNDERBUSS_AMMO = TagKey.of(RegistryKeys.ITEM, CoatsAndValour.id("blunderbuss_ammo"));
    TagKey<Item> PISTOL_AMMO = TagKey.of(RegistryKeys.ITEM, CoatsAndValour.id("pistol_ammo"));
    TagKey<Item> PISTOL_SWORD_AMMO = TagKey.of(RegistryKeys.ITEM, CoatsAndValour.id("pistol_sword_ammo"));
    TagKey<Item> SANGUINE_HUNTER_AMMO = TagKey.of(RegistryKeys.ITEM, CoatsAndValour.id("sanguine_hunter_ammo"));
    TagKey<Item> DUCKFOOT_AMMO = TagKey.of(RegistryKeys.ITEM, CoatsAndValour.id("duckfoot_ammo"));
    TagKey<Item> NOCK_GUN_AMMO = TagKey.of(RegistryKeys.ITEM, CoatsAndValour.id("nock_gun_ammo"));

    static void init() {

    }
}
