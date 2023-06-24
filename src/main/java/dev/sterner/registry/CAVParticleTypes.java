package dev.sterner.registry;

import dev.sterner.CoatsAndValour;
import dev.sterner.client.particle.SmokeParticle;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface CAVParticleTypes {
    Map<ParticleType<?>, Identifier> PARTICLE_TYPES = new LinkedHashMap<>();

    ParticleType<DefaultParticleType> SMOKE = register("smoke", FabricParticleTypes.simple());
    ParticleType<DefaultParticleType> BLOOD = register("blood", FabricParticleTypes.simple());


    static <T extends ParticleEffect> ParticleType<T> register(String name, ParticleType<T> type) {
        PARTICLE_TYPES.put(type, CoatsAndValour.id(name));
        return type;
    }

    static void init() {
        PARTICLE_TYPES.keySet().forEach(particleType -> Registry.register(Registries.PARTICLE_TYPE, PARTICLE_TYPES.get(particleType), particleType));

    }

    static void clientInit() {
        ParticleFactoryRegistry.getInstance().register(SMOKE, SmokeParticle.Factory::new);
    }
}
