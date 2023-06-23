package dev.sterner.registry;

import dev.sterner.CoatsAndValour;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface CAVTagKeys {
    TagKey<Item> MUSKET_AMMO = itemTag("musket_ammo");
    TagKey<Item> BLUNDERBUSS_AMMO = itemTag("blunderbuss_ammo");
    TagKey<Item> PISTOL_AMMO = itemTag("pistol_ammo");
    TagKey<Item> PISTOL_SWORD_AMMO = itemTag("pistol_sword_ammo");
    TagKey<Item> SANGUINE_HUNTER_AMMO = itemTag("sanguine_hunter_ammo");
    TagKey<Item> DUCKFOOT_AMMO = itemTag("duckfoot_ammo");
    TagKey<Item> NOCK_GUN_AMMO = itemTag("nock_gun_ammo");

    static TagKey<Item> itemTag(String path) {
        return TagKey.of(RegistryKeys.ITEM, CoatsAndValour.id(path));
    }

    static void init() {

    }
}