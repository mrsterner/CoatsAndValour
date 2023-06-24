package dev.sterner.client.render.item;

import dev.sterner.common.util.CAVUtils;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.block.StainedGlassPaneBlock;
import net.minecraft.block.TransparentBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.MatrixUtil;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class TwoDItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer, IdentifiableResourceReloadListener {
    private final Identifier id;
    private final Identifier itemId;
    private ItemRenderer itemRenderer;
    private BakedModel inventoryModel;

    public TwoDItemRenderer(Identifier itemId) {
        this.id = new Identifier(itemId.getNamespace(), itemId.getPath() + "_renderer");
        this.itemId = itemId;
    }
    
    @Override
    public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        return synchronizer.whenPrepared(Unit.INSTANCE).thenRunAsync(() -> {
            applyProfiler.startTick();
            applyProfiler.push("listener");
            final MinecraftClient client = MinecraftClient.getInstance();
            this.itemRenderer = client.getItemRenderer();
            this.inventoryModel = client.getBakedModelManager().getModel(new ModelIdentifier(itemId.withPath(itemId.getPath() + "_gui"), "inventory"));
            applyProfiler.pop();
            applyProfiler.endTick();
        }, applyExecutor);
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.pop();
        matrices.push();
        if (mode != ModelTransformationMode.FIRST_PERSON_LEFT_HAND && mode != ModelTransformationMode.FIRST_PERSON_RIGHT_HAND && mode != ModelTransformationMode.THIRD_PERSON_LEFT_HAND && mode != ModelTransformationMode.THIRD_PERSON_RIGHT_HAND) {
            if (this.itemRenderer == null) {
                this.itemRenderer = MinecraftClient.getInstance().getItemRenderer();
            }
            if (this.inventoryModel == null) {
                this.inventoryModel = MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(itemId.withPath(itemId.getPath() + "_gui"), "inventory"));
            }
            DiffuseLighting.enableGuiDepthLighting();
            renderItem(itemRenderer, stack, mode, false, matrices, vertexConsumers, CAVUtils.MAX_LIGHT, overlay, this.inventoryModel);
            DiffuseLighting.disableGuiDepthLighting();
        }
    }

    public void renderItem(ItemRenderer itemRenderer, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model) {
        if (!stack.isEmpty()) {
            matrices.push();

            model.getTransformation().getTransformation(renderMode).apply(leftHanded, matrices);
            matrices.translate(-0.5F, -0.5F, -0.5F);
            if (!model.isBuiltin()) {
                boolean bl2 = true;
                RenderLayer renderLayer = RenderLayers.getItemLayer(stack, bl2);
                VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, renderLayer, true, stack.hasGlint());

                itemRenderer.renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumer);
            }

            matrices.pop();
        }
    }

    @Override
    public Identifier getFabricId() {
        return id;
    }
}