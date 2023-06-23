package dev.sterner.common.item;

import dev.sterner.common.util.ProjectileProperties;
import net.minecraft.item.Item;

public class AmmoItem extends Item {
    private final ProjectileProperties projectileProperties;

    public AmmoItem(Settings settings, ProjectileProperties projectileProperties) {
        super(settings);
        this.projectileProperties = projectileProperties;
    }

    public ProjectileProperties getProjectileProperties() {
        return projectileProperties;
    }
}
