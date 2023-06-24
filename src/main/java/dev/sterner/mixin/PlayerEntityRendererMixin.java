package dev.sterner.mixin;

import dev.sterner.registry.CAVComponents;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "setModelPose", at = @At("TAIL"))
    private void cav$updateBoneVisibility(AbstractClientPlayerEntity player, CallbackInfo ci) {
        PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel = this.getModel();
        if (!player.isSpectator()) {
            CAVComponents.PLAYER_COMPONENT.maybeGet(player).ifPresent(playerDataComponent -> {
                if (playerDataComponent.playerModel == null) {
                    playerDataComponent.setPlayerComponent(playerEntityModel);
                }
                playerDataComponent.updatePlayerModel();
                playerEntityModel.rightLeg.visible = playerDataComponent.playerModel.rightLeg.visible;
                playerEntityModel.rightPants.visible = playerDataComponent.playerModel.rightLeg.visible;

                playerEntityModel.leftLeg.visible = playerDataComponent.playerModel.leftLeg.visible;
                playerEntityModel.leftPants.visible = playerDataComponent.playerModel.leftLeg.visible;

                playerEntityModel.rightArm.visible = playerDataComponent.playerModel.rightArm.visible;
                playerEntityModel.rightSleeve.visible = playerDataComponent.playerModel.rightArm.visible;

                playerEntityModel.leftArm.visible = playerDataComponent.playerModel.leftArm.visible;
                playerEntityModel.leftSleeve.visible = playerDataComponent.playerModel.leftArm.visible;
            });
        }
    }
}
