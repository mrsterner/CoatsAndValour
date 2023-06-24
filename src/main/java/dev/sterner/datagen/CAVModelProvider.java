package dev.sterner.datagen;

import dev.sterner.registry.CAVObjects;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Items;

public class CAVModelProvider extends FabricModelProvider {
    public CAVModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(CAVObjects.BANDAGE, Models.GENERATED);
        generator.register(CAVObjects.BATTLE_MAP, Models.GENERATED);
        generator.register(CAVObjects.BLOCK_RAMMER, Models.HANDHELD);
        generator.register(CAVObjects.BONE_SAW, Models.HANDHELD);
        generator.register(CAVObjects.CASESHOT, Models.GENERATED);
        generator.register(CAVObjects.NATION_FLAG, Models.HANDHELD);
        generator.register(CAVObjects.POWDER_CHARGE, Models.GENERATED);
        generator.register(CAVObjects.RAMROD, Models.HANDHELD);
        generator.register(CAVObjects.ROUNDSHOT, Models.GENERATED);
        generator.register(CAVObjects.SHELLSHOT, Models.GENERATED);
        generator.register(CAVObjects.SPONGE, Models.GENERATED);

        generator.register(CAVObjects.DEBUG, Items.STICK, Models.GENERATED);
    }
}
