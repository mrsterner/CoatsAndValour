package dev.sterner.registry;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import dev.sterner.CoatsAndValour;
import dev.sterner.common.component.BleedComponent;
import dev.sterner.common.component.NationChunkComponent;
import dev.sterner.common.component.PlayerDataComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class CAVComponents implements EntityComponentInitializer, ChunkComponentInitializer {
    public static final ComponentKey<PlayerDataComponent> PLAYER_COMPONENT = ComponentRegistry.getOrCreate(CoatsAndValour.id("player"), PlayerDataComponent.class);
    public static final ComponentKey<NationChunkComponent> NATION_CHUNK = ComponentRegistry.getOrCreate(CoatsAndValour.id("nation_chunk"), NationChunkComponent.class);
    public static final ComponentKey<BleedComponent> BLEED = ComponentRegistry.getOrCreate(CoatsAndValour.id("bleed"), BleedComponent.class);


    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, BLEED, livingEntity -> new BleedComponent());
        registry.beginRegistration(PlayerEntity.class, PLAYER_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(PlayerDataComponent::new);
    }

    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
        registry.register(NATION_CHUNK, NationChunkComponent.class, chunk -> new NationChunkComponent());
    }
}
