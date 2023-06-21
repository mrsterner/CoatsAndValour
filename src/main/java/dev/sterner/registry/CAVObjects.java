package dev.sterner.registry;

import dev.sterner.CoatsAndValour;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface CAVObjects {
    Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    Map<Item, Identifier> ITEMS = new LinkedHashMap<>();


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
    }
}
