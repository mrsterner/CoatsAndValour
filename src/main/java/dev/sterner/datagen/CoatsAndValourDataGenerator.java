package dev.sterner.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CoatsAndValourDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        FabricDataGenerator.Pack pack = dataGenerator.createPack();
        pack.addProvider(CAVLanguageProvider::new);
        pack.addProvider(CAVLootTableProvider.EntityTypeProvider::new);
        pack.addProvider(CAVLootTableProvider.BlockProvider::new);
        pack.addProvider(CAVModelProvider::new);
        pack.addProvider(CAVTagProvider.BlockProvider::new);
        pack.addProvider(CAVTagProvider.ItemProvider::new);
        pack.addProvider(CAVTagProvider.EntityTypeProvider::new);
    }
}
