package dev.sterner.registry;

import dev.sterner.CoatsAndValour;
import dev.sterner.common.item.CAVGunItem;
import dev.sterner.common.item.PistolItem;
import dev.sterner.common.util.GunProperties;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface CAVObjects {
    RegistryKey<ItemGroup> CAV_ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, CoatsAndValour.id("main"));

    Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    Item PISTOL = register("pistol", new PistolItem(new Item.Settings(), new GunProperties.Builder().range(32).maxAmmo(1).reloadTicks(20 * 4).reloadSound(SoundEvents.BLOCK_LEVER_CLICK).build()));
    Item BLUNDERBUSS = register("blunderbuss", new PistolItem(new Item.Settings(), new GunProperties.Builder().range(32).maxAmmo(1).reloadTicks(20 * 4).reloadSound(SoundEvents.BLOCK_LEVER_CLICK).build()));
    Item MUSKET = register("musket", new PistolItem(new Item.Settings(), new GunProperties.Builder().range(32).maxAmmo(1).reloadTicks(20 * 4).reloadSound(SoundEvents.BLOCK_LEVER_CLICK).build()));

    static <T extends Item> T register(String name, T item) {
        ITEMS.put(item, CoatsAndValour.id(name));
        return item;
    }

    static <T extends Block> T register(String name, T block, boolean createItem) {
        BLOCKS.put(block, CoatsAndValour.id(name));
        if (createItem) {
            ITEMS.put(new BlockItem(block, new Item.Settings()), BLOCKS.get(block));
        }
        return block;
    }

    static void init() {
        BLOCKS.keySet().forEach(block -> Registry.register(Registries.BLOCK, BLOCKS.get(block), block));
        ITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, ITEMS.get(item), item));
        ItemGroupEvents.modifyEntriesEvent(CAV_ITEM_GROUP).register(entries -> ITEMS.keySet().forEach(entries::add));
    }
}
