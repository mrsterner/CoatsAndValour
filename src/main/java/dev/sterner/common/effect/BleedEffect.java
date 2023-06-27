package dev.sterner.common.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public final class BleedEffect extends StatusEffect {
    public BleedEffect() {
        super(StatusEffectCategory.HARMFUL, 0xD40017);
    }
}
