package dev.sterner.datagen;

import dev.sterner.registry.CAVObjects;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class CAVLanguageProvider extends FabricLanguageProvider {
    protected CAVLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        builder.add("coats-and-valour.group.main", "Coats and Valour");
        builder.add(CAVObjects.BLUNDERBUSS, "Blunderbuss");
        builder.add(CAVObjects.PISTOL, "Pistol");
        builder.add(CAVObjects.MUSKET, "Musket");
    }
}
