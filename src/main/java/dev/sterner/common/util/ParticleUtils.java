package dev.sterner.common.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleUtils {

    public static void generateParticleVector(World world, Vec3d startPos, Vec3d direction, ParticleEffect effect, double steps, int count, boolean cone) {
        for (int i = 0; i < count; i++) {
            Vec3d pos = startPos.add(direction.x * steps * i, direction.y * steps * i, direction.z * steps * i);
            world.addImportantParticle(effect, true, pos.getX(), pos.getY(), pos.getZ(), 0.0, 0.02, 0.0);
            if (cone) {
                double radius = 0.25 * i;
                int numPoints = 10;
                for (int j = 0; j < numPoints; j++) {
                    double angle = (2 * Math.PI * j) / numPoints;
                    double offsetX = Math.cos(angle) * radius;
                    double offsetZ = Math.sin(angle) * radius;
                    double offsetY = Math.cos(angle) * Math.sin(angle) * radius;

                    Vec3d conePos = pos.add(offsetX, offsetY, offsetZ);
                    world.addImportantParticle(effect, true, conePos.getX(), conePos.getY(), conePos.getZ(), 0.0, 0.02, 0.0);
                }
            }
        }
    }

    public static void generateParticleVector(World world, PlayerEntity player, ParticleEffect effect, double steps, int count, boolean cone) {
        generateParticleVector(world, player.getCameraPosVec(1), player.getRotationVec(1), effect, steps, count, cone);
    }
}
