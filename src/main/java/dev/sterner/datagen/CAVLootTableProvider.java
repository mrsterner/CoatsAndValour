package dev.sterner.datagen;

import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.function.BiConsumer;

public class CAVLootTableProvider {
    public static class BlockProvider extends FabricBlockLootTableProvider {

        public BlockProvider(FabricDataOutput dataOutput) {
            super(dataOutput);
        }

        @Override
        public void generate() {

        }
    }

    public static class EntityTypeProvider extends SimpleFabricLootTableProvider {
        private final Map<Identifier, LootTable.Builder> loot = Maps.newHashMap();

        public EntityTypeProvider(FabricDataOutput output) {
            super(output, LootContextTypes.ENTITY);
        }

        private void generateLoot() {
        }

        @Override
        public void accept(BiConsumer<Identifier, LootTable.Builder> exporter) {
            this.generateLoot();
            for (Map.Entry<Identifier, LootTable.Builder> entry : loot.entrySet()) {
                exporter.accept(entry.getKey(), entry.getValue());
            }
        }


        @Override
        public BiConsumer<Identifier, LootTable.Builder> withConditions(BiConsumer<Identifier, LootTable.Builder> exporter, ConditionJsonProvider... conditions) {
            return super.withConditions(exporter, conditions);
        }
    }
}
