package dev.sterner.common.util;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ProjectileProperties {
    private Identifier muzzleFlashTexture;
    private float recoilPower;
    private float projectileDamage;
    private SoundEvent sound;
    private Material material;
    private DamageSource damageSource;

    private ProjectileProperties() {

    }

    public Identifier getMuzzleFlashTexture() {
        return muzzleFlashTexture;
    }

    public float getRecoilPower() {
        return recoilPower;
    }

    public float getProjectileDamage() {
        return projectileDamage;
    }

    public SoundEvent getSound() {
        return sound;
    }

    public Material getMaterial() {
        return material;
    }

    public DamageSource getDamageSource() {
        return damageSource;
    }

    public static class Builder {
        private final ProjectileProperties properties = new ProjectileProperties();

        public ProjectileProperties build() {
            return this.properties;
        }

        public Builder muzzleFlashTexture(Identifier texture) {
            this.properties.muzzleFlashTexture = texture;
            return this;
        }

        public Builder sound(SoundEvent soundEvent) {
            this.properties.sound = soundEvent;
            return this;
        }

        public Builder recoilPower(float power) {
            this.properties.recoilPower = power;
            return this;
        }

        public Builder damage(float damage) {
            this.properties.projectileDamage = damage;
            return this;
        }

        public Builder material(Material material) {
            this.properties.material = material;
            return this;
        }

        public Builder damageSource(DamageSource damageSource) {
            this.properties.damageSource = damageSource;
            return this;
        }
    }

    public enum Material {
        IRON,
        SILVER
    }
}
