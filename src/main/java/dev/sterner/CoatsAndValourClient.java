package dev.sterner;

import dev.sterner.registry.CAVItemRenderers;
import dev.sterner.registry.CAVParticleTypes;
import net.fabricmc.api.ClientModInitializer;

public class CoatsAndValourClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CAVParticleTypes.clientInit();
        CAVItemRenderers.clientInit();
    }
}