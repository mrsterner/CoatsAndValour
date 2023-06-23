package dev.sterner.client.render.item;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class BigItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer, IdentifiableResourceReloadListener {
    private final Identifier id;
    private final Identifier itemId;
    private ItemRenderer itemRenderer;
    private BakedModel inventoryItemModel;
    private BakedModel worldItemModel;

    public BigItemRenderer(Identifier itemId) {
        this.id = new Identifier(itemId.getNamespace(), itemId.getPath() + "_renderer");
        this.itemId = itemId;
    }

    @Override
    public Identifier getFabricId() {
        return this.id;
    }

    @Override
    public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
        return synchronizer.whenPrepared(Unit.INSTANCE).thenRunAsync(() -> {
            applyProfiler.startTick();
            applyProfiler.push("listener");
            final MinecraftClient client = MinecraftClient.getInstance();
            this.itemRenderer = client.getItemRenderer();
            this.inventoryItemModel = client.getBakedModelManager().getModel(new ModelIdentifier(itemId.withPath(itemId.getPath() + "_gui"), "inventory"));
            this.worldItemModel = client.getBakedModelManager().getModel(new ModelIdentifier(itemId.withPath(itemId.getPath() + "_handheld"), "inventory"));
            applyProfiler.pop();
            applyProfiler.endTick();
        }, applyExecutor);
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.pop();
        matrices.push();
        if (mode != ModelTransformationMode.FIRST_PERSON_LEFT_HAND && mode != ModelTransformationMode.FIRST_PERSON_RIGHT_HAND && mode != ModelTransformationMode.THIRD_PERSON_LEFT_HAND && mode != ModelTransformationMode.THIRD_PERSON_RIGHT_HAND) {
            itemRenderer.renderItem(stack, mode, false, matrices, vertexConsumers, light, overlay, this.inventoryItemModel);
        } else {
            boolean leftHanded;
            switch (mode) {
                case FIRST_PERSON_LEFT_HAND, THIRD_PERSON_LEFT_HAND -> leftHanded = true;
                default -> leftHanded = false;
            }
            itemRenderer.renderItem(stack, mode, leftHanded, matrices, vertexConsumers, light, overlay, this.worldItemModel);
        }
    }
}