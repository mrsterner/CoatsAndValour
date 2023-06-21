package dev.sterner.registry;

import dev.sterner.CoatsAndValour;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface CAVEntityTypes {
    Map<EntityType<?>, Identifier> ENTITY_TYPES = new LinkedHashMap<>();

    static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
        ENTITY_TYPES.put(type, CoatsAndValour.id(name));
        return type;
    }

    static void init() {
        ENTITY_TYPES.keySet().forEach(entityType -> Registry.register(Registries.ENTITY_TYPE, ENTITY_TYPES.get(entityType), entityType));
    }
}
