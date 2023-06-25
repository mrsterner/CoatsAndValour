package dev.sterner.client.render.feature;

import dev.sterner.CoatsAndValour;
import dev.sterner.client.model.PegLegModel;
import dev.sterner.registry.CAVComponents;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class PegLegFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    private final PegLegModel<T> MODEL;
    private final Identifier TEXTURE;

    public PegLegFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
        this.MODEL = new PegLegModel<>(loader.getModelPart(PegLegModel.LAYER));
        this.TEXTURE = CoatsAndValour.id("textures/entity/peg_leg.png");
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        CAVComponents.PLAYER_COMPONENT.maybeGet(entity).ifPresent(component -> {
            MODEL.leftPeg.visible = false;
            MODEL.rightPeg.visible = false;
            if (component.getRightLegPegged()) {
                MODEL.rightPeg.visible = true;
                matrices.push();
                getContextModel().rightLeg.rotate(matrices);
                matrices.translate(0.1, -0.75, 0);
                MODEL.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
                matrices.pop();
            }
            if (component.getLeftLegPegged()) {
                MODEL.leftPeg.visible = true;
                matrices.push();
                getContextModel().leftLeg.rotate(matrices);
                matrices.translate(-0.1, -0.75, 0);
                MODEL.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(TEXTURE)), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
                matrices.pop();
            }
        });
    }
}
