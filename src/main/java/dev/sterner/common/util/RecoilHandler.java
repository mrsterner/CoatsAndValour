package dev.sterner.common.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class RecoilHandler {

    public void recoil(PlayerEntity player, float power) {
        float powerPitch = power;
        float yaw = powerPitch * 0.5F;
        if (player.isSneaking()) {
            powerPitch *= 0.7F;
            yaw *= 0.7F;
        }

        float modifier = 0.75F;
        powerPitch *= 1.0F - modifier;
        yaw *= 1.0F - modifier;
        player.setPitch(MathHelper.clamp(player.getPitch() - powerPitch, -90.0F, 90.0F));
        if (Math.random() < 0.3) {
            player.setYaw(player.getYaw() + (Math.random() < 0.5 ? yaw : -yaw));
        }
    }
}
