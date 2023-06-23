package dev.sterner.registry;

import dev.sterner.api.interfaces.IDualModel;
import dev.sterner.api.interfaces.IGeoDualModel;
import dev.sterner.client.render.item.BigItemRenderer;
import dev.sterner.client.render.item.TwoDItemRenderer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

public interface CAVItemRenderers {

    static void makeModelPredicate(Item i){
        ModelPredicateProviderRegistry.register(i, new Identifier("exposed"), ((stack, world, entity, seed) -> entity == null ? 0 : stack.getOrCreateNbt().getInt("Exposed")));
    }

    static void registerRenderer(Item item, Object renderer){
        Identifier itemId = Registries.ITEM.getId(item);
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener((IdentifiableResourceReloadListener) renderer);
        BuiltinItemRendererRegistry.INSTANCE.register(item, (BuiltinItemRendererRegistry.DynamicItemRenderer) renderer);
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelIdentifier(itemId.withPath(itemId.getPath() + "_handheld"), "inventory"));
            out.accept(new ModelIdentifier(itemId.withPath(itemId.getPath() + "_gui"), "inventory"));
        });
    }

    static void clientInit(){
        makeModelPredicate(CAVObjects.MUSKET_SHOT);
        makeModelPredicate(CAVObjects.PISTOL_SHOT);
        makeModelPredicate(CAVObjects.SILVER_SHOT);
        makeModelPredicate(CAVObjects.PELLET_SHOT);

        for (Item item : CAVObjects.ITEMS.keySet()) {
            if (item instanceof IGeoDualModel){
                Identifier itemId = Registries.ITEM.getId(item);
                registerRenderer(item, new TwoDItemRenderer(itemId));
            } else if (item instanceof IDualModel) {
                Identifier itemId = Registries.ITEM.getId(item);
                registerRenderer(item, new BigItemRenderer(itemId));
            }
        }
    }
}
