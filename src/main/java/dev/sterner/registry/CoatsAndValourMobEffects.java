package dev.sterner.registry;

import dev.sterner.CoatsAndValour;
import dev.sterner.common.effect.BleedEffect;
import dev.sterner.common.effect.GangreneEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class CoatsAndValourMobEffects {
    public static final StatusEffect BLEED = new BleedEffect();
    public static final StatusEffect GANGRENE = new GangreneEffect();

    private CoatsAndValourMobEffects() {}

    public static void registerAll() {
        register("bleed", BLEED);
        register("gangrene", GANGRENE);
    }

    private static void register(String name, StatusEffect effect) {
        Registry.register(Registries.STATUS_EFFECT, CoatsAndValour.id(name), effect);
    }
}
