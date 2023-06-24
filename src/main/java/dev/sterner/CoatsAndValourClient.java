package dev.sterner;

import dev.sterner.client.model.PegLegModel;
import dev.sterner.registry.CAVItemRenderers;
import dev.sterner.registry.CAVParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;

public class CoatsAndValourClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CAVParticleTypes.clientInit();
        CAVItemRenderers.clientInit();

        EntityModelLayerRegistry.registerModelLayer(PegLegModel.LAYER, PegLegModel::getTexturedModelData);
    }
}