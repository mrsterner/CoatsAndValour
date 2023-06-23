package dev.sterner.common.util;

import net.minecraft.sound.SoundEvent;

public class GunProperties {

    private int maxAmmo = 1;
    private int reloadTicks = 20;
    private SoundEvent reloadSound;
    private int range = 32;
    private int shots = 1;

    private GunProperties() {

    }

    public int getMaxAmmo() {
        return maxAmmo;
    }

    public int getReloadTicks() {
        return reloadTicks;
    }

    public SoundEvent getReloadSound() {
        return reloadSound;
    }

    public int getRange() {
        return range;
    }

    public int getShots() {
        return shots;
    }

    public static class Builder {
        private final GunProperties properties = new GunProperties();

        public GunProperties build() {
            return this.properties;
        }

        public Builder maxAmmo(int ammo) {
            this.properties.maxAmmo = ammo;
            return this;
        }

        public Builder reloadTicks(int ticks) {
            this.properties.reloadTicks = ticks;
            return this;
        }

        public Builder reloadSound(SoundEvent soundEvent) {
            this.properties.reloadSound = soundEvent;
            return this;
        }

        public Builder range(int range) {
            this.properties.range = range;
            return this;
        }

        public Builder shots(int shots) {
            this.properties.shots = shots;
            return this;
        }
    }
}
