package dev.sterner.registry;

import dev.sterner.CoatsAndValour;
import dev.sterner.common.item.*;
import dev.sterner.common.util.GunProperties;
import dev.sterner.common.util.ProjectileProperties;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface CAVObjects {
    RegistryKey<ItemGroup> CAV_ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, CoatsAndValour.id("main"));

    Map<Block, Identifier> BLOCKS = new LinkedHashMap<>();
    Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

    Item DEBUG = register("debug", new DebugWand(new Item.Settings()));

    Item PISTOL = register("pistol", new PistolItem(new Item.Settings(), new GunProperties.Builder().range(32).maxAmmo(1).reloadTicks(20 * 4).reloadSound(SoundEvents.BLOCK_LEVER_CLICK).build()));
    Item BLUNDERBUSS = register("blunderbuss", new BlunderbussItem(new Item.Settings(), new GunProperties.Builder().range(32).maxAmmo(1).reloadTicks(20 * 4).reloadSound(SoundEvents.BLOCK_LEVER_CLICK).build()));
    Item MUSKET = register("musket", new MusketItem(new Item.Settings(), new GunProperties.Builder().range(32).maxAmmo(1).reloadTicks(20 * 4).reloadSound(SoundEvents.BLOCK_LEVER_CLICK).build()));
    Item DUCKFOT = register("duckfoot", new DuckfootItem(new Item.Settings(), new GunProperties.Builder().range(24).maxAmmo(4).barrels(4).reloadSound(SoundEvents.BLOCK_LEVER_CLICK).build()));
    Item NOCK_GUN = register("nock_gun", new NockItem(new Item.Settings(), new GunProperties.Builder().range(24).maxAmmo(3).barrels(3).reloadSound(SoundEvents.BLOCK_LEVER_CLICK).build()));
    Item SANGUINE_HUNTER = register("sanguine_hunter", new SanguineHunterItem(new Item.Settings(), new GunProperties.Builder().range(24).maxAmmo(3).barrels(3).reloadSound(SoundEvents.BLOCK_LEVER_CLICK).build()));
    Item PISTOL_SWORD = register("pistol_sword", new PistolSwordItem(new Item.Settings(), new GunProperties.Builder().range(24).maxAmmo(3).barrels(3).reloadSound(SoundEvents.BLOCK_LEVER_CLICK).build()));
    Item SABRE = register("sabre", new SabreItem(new Item.Settings()));

    Item MUSKET_SHOT = register("musket_shot", new AmmoItem(new Item.Settings(), new ProjectileProperties.Builder().recoilPower(10).damageSource(DamageTypes.GENERIC).build()));
    Item PELLET_SHOT = register("pellet_shot", new AmmoItem(new Item.Settings(), new ProjectileProperties.Builder().recoilPower(10).build()));
    Item PISTOL_SHOT = register("pistol_shot", new AmmoItem(new Item.Settings(), new ProjectileProperties.Builder().recoilPower(10).build()));
    Item SILVER_SHOT = register("silver_shot", new AmmoItem(new Item.Settings(), new ProjectileProperties.Builder().recoilPower(10).build()));
    Item ROUNDSHOT = register("roundshot", new Item(new Item.Settings()));
    Item CASESHOT = register("caseshot", new Item(new Item.Settings()));
    Item SHELLSHOT = register("shellshot", new Item(new Item.Settings()));
    Item POWDER_CHARGE = register("powder_charge", new Item(new Item.Settings()));
    Item BANDAGE = register("bandage", new BandageItem(new Item.Settings()));
    Item BATTLE_MAP = register("battle_map", new Item(new Item.Settings()));
    Item BONE_SAW = register("bone_saw", new Item(new Item.Settings()));
    Item NATION_FLAG = register("nation_flag", new Item(new Item.Settings()));
    Item RAMROD = register("ramrod", new Item(new Item.Settings()));
    Item BLOCK_RAMMER = register("block_rammer", new Item(new Item.Settings()));
    Item SPONGE = register("sponge", new Item(new Item.Settings()));

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
        Registry.register(Registries.ITEM_GROUP, CAV_ITEM_GROUP, FabricItemGroup.builder()
                .icon(() -> new ItemStack(PISTOL))
                .displayName(Text.translatable(CoatsAndValour.MODID + ".group.main"))
                .build());

        BLOCKS.keySet().forEach(block -> Registry.register(Registries.BLOCK, BLOCKS.get(block), block));
        ITEMS.keySet().forEach(item -> Registry.register(Registries.ITEM, ITEMS.get(item), item));

        ITEMS.keySet().remove(DEBUG);
        ItemGroupEvents.modifyEntriesEvent(CAV_ITEM_GROUP).register(entries -> ITEMS.keySet().forEach(entries::add));
    }
}
