package dev.sterner.client.model;

import dev.sterner.CoatsAndValour;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class PegLegModel<T extends LivingEntity> extends BipedEntityModel<T> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(CoatsAndValour.id("peg_leg"), "main");
    public final ModelPart rightPeg;
    public final ModelPart leftPeg;

    public PegLegModel(ModelPart root) {
        super(root);
        this.rightPeg = root.getChild("rightPeg");
        this.leftPeg = root.getChild("leftPeg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("rightPeg", ModelPartBuilder.create().uv(0, 8).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)).uv(12, 0).cuboid(-1.0F, 5.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)).uv(0, 16).cuboid(-1.0F, 10.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.2F)), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));
        modelPartData.addChild("leftPeg", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)).uv(12, 8).cuboid(-1.0F, 5.0F, -1.0F, 2.0F, 6.0F, 2.0F, new Dilation(0.0F)).uv(8, 16).cuboid(-1.0F, 10.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.2F)), ModelTransform.pivot(1.9F, 12.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        this.leftPeg.copyTransform(this.leftLeg);
        this.rightPeg.copyTransform(this.rightLeg);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        rightPeg.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        leftPeg.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
