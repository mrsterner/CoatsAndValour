package dev.sterner.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import dev.sterner.common.item.CAVGunItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @WrapWithCondition(method = "doItemUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;swingHand(Lnet/minecraft/util/Hand;)V"))
    private boolean cav$dontSwingHand(ClientPlayerEntity player, Hand hand) {
        return !(player.getStackInHand(hand).getItem() instanceof CAVGunItem);
    }
}