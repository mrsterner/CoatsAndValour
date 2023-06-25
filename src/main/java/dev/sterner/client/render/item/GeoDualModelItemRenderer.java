package dev.sterner.client.render.item;

import dev.sterner.CoatsAndValour;
import dev.sterner.common.item.GeoCockableGunItem;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.model.DefaultedItemGeoModel;
import mod.azure.azurelib.renderer.GeoItemRenderer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

import java.util.Objects;

public class GeoDualModelItemRenderer<T extends GeoCockableGunItem> extends GeoItemRenderer<T> {
    private final String name;

    public GeoDualModelItemRenderer(String name) {
        super(new DefaultedItemGeoModel<>(CoatsAndValour.id(name)));
        this.name = name;
    }

    @Override
    public void renderFinal(MatrixStack poseStack, T animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        if (animatable.isMuzzling()) {

        }
        this.model.getBone("muzzleflash").ifPresent(muzzle -> {

        });

        super.renderFinal(poseStack, animatable, model, bufferSource, buffer, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode transformType, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int packedLight, int packedOverlay) {
        if (Objects.requireNonNull(transformType) == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND) {
        }

        super.render(stack, transformType, matrixStack, vertexConsumerProvider, packedLight, packedOverlay);
    }

    @Override
    protected void renderInGui(ModelTransformationMode transformType, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, int packedOverlay) {
        //super.renderInGui(transformType, poseStack, bufferSource, packedLight, packedOverlay);
    }
}