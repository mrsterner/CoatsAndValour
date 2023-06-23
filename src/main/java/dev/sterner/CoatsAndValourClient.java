package dev.sterner;

import dev.sterner.registry.CAVObjects;
import dev.sterner.registry.CAVParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class CoatsAndValourClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CAVParticleTypes.clientInit();

        ModelPredicateProviderRegistry.register(CAVObjects.MUSKET_SHOT, new Identifier("exposed"), ((stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            }
            return stack.getOrCreateNbt().getInt("Exposed");
        }));
    }
}