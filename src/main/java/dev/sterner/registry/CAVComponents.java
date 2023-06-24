package dev.sterner.registry;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import dev.sterner.CoatsAndValour;
import dev.sterner.common.component.PlayerDataComponent;
import net.minecraft.entity.player.PlayerEntity;

public class CAVComponents implements EntityComponentInitializer {
    public static final ComponentKey<PlayerDataComponent> PLAYER_COMPONENT = ComponentRegistry.getOrCreate(CoatsAndValour.id("player"), PlayerDataComponent.class);


    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, PLAYER_COMPONENT).respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY).end(PlayerDataComponent::new);
    }
}
