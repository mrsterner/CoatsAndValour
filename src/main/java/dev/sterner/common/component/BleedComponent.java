package dev.sterner.common.component;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;

public final class BleedComponent implements ComponentV3, AutoSyncedComponent {
    private int bleedTime;

    @Override
    public void readFromNbt(NbtCompound tag) {
        bleedTime = tag.getInt("BleedTime");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("BleedTime", bleedTime);
    }

    public int getBleedTime() {
        return bleedTime;
    }

    public void setBleedTime(int bleedTime) {
        this.bleedTime = bleedTime;
    }
}
