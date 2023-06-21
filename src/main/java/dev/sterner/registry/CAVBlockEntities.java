package dev.sterner.registry;

import dev.sterner.CoatsAndValour;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface CAVBlockEntities {
    Map<BlockEntityType<?>, Identifier> BLOCK_ENTITY_TYPES = new LinkedHashMap<>();

    static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        BLOCK_ENTITY_TYPES.put(type, CoatsAndValour.id(name));
        return type;
    }

    static void init() {
        BLOCK_ENTITY_TYPES.keySet().forEach(blockEntityType -> Registry.register(Registries.BLOCK_ENTITY_TYPE, BLOCK_ENTITY_TYPES.get(blockEntityType), blockEntityType));
    }
}
