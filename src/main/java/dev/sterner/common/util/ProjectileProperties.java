package dev.sterner.common.util;

import dev.sterner.CoatsAndValour;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.function.ValueLists;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.IntFunction;

public class ProjectileProperties {
    private Identifier muzzleFlashTexture = CoatsAndValour.id("textures/particles/muzzle_flash");
    private float recoilPower = 1;
    private float projectileDamage = 1;
    private SoundEvent sound = SoundEvents.ENTITY_GENERIC_EXPLODE;
    private AmmoType ammoType = AmmoType.PISTOL;
    private RegistryKey<DamageType> damageSource = DamageTypes.GENERIC;

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

    public AmmoType getAmmoType() {
        return ammoType;
    }

    public RegistryKey<DamageType> getDamageSource() {
        return damageSource;
    }

    public NbtCompound writeNbt(ProjectileProperties properties) {
        NbtCompound gun = new NbtCompound();
        NbtCompound nbtProperties = new NbtCompound();
        nbtProperties.putInt("AmmoType", properties.ammoType.id);
        nbtProperties.putFloat("Damage", properties.projectileDamage);
        nbtProperties.putString("SoundEvent", properties.sound.getId().toString());
        nbtProperties.putFloat("Recoil", properties.recoilPower);
        nbtProperties.putString("MuzzleTexture", properties.muzzleFlashTexture.toString());
        //TODO nbtProperties.putString("DamageType", properties.damageSource.toString());
        gun.put("Properties", nbtProperties);

        return gun;
    }

    public static ProjectileProperties readNbt(NbtCompound gun) {
        NbtCompound properties = gun.getCompound("Properties");
        ProjectileProperties projectileProperties = new ProjectileProperties();
        projectileProperties.ammoType = AmmoType.byId(properties.getInt("AmmoType"));
        projectileProperties.projectileDamage = properties.getFloat("Damage");
        projectileProperties.sound = SoundEvent.of(new Identifier(properties.getString("SoundEvent")));
        projectileProperties.recoilPower = properties.getFloat("Recoil");
        projectileProperties.muzzleFlashTexture = new Identifier(properties.getString("MuzzleTexture"));
        /*TODO
        if (world != null) {
            int rawId = world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getRawId(world.getDamageSources().registry.get(new Identifier(properties.getString("DamageType"))));
            Optional<RegistryEntry.Reference<DamageType>> registryEntry = world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getEntry(rawId);
            if (registryEntry.isPresent()) {
                Optional<RegistryKey<DamageType>> optionalDamageTypeRegistryKey = registryEntry.get().getKey();
                optionalDamageTypeRegistryKey.ifPresent(damageTypeRegistryKey -> projectileProperties.damageSource = damageTypeRegistryKey);
            }
        }

         */
        return projectileProperties;

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

        public Builder ammoType(AmmoType ammoType) {
            this.properties.ammoType = ammoType;
            return this;
        }

        public Builder damageSource(RegistryKey<DamageType> damageSource) {
            this.properties.damageSource = damageSource;
            return this;
        }
    }

    public enum AmmoType {
        PISTOL(0),
        MUSKET(1),
        PELLET(2),
        SILVER(3);

        private static final IntFunction<AmmoType> BY_ID = ValueLists.createIdToValueFunction(AmmoType::getId, values(), ValueLists.OutOfBoundsHandling.ZERO);
        private final int id;

        AmmoType(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }

        public static AmmoType byId(int id) {
            return BY_ID.apply(id);
        }

    }
}
