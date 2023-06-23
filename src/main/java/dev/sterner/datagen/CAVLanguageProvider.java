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
        builder.add(CAVObjects.NOCK_GUN, "Nock Gun");
        builder.add(CAVObjects.DUCKFOT, "Duckfoot");
        builder.add(CAVObjects.SANGUINE_HUNTER, "Sanguine Hunter");
        builder.add(CAVObjects.PISTOL_SWORD, "Pistol Sword");

        builder.add(CAVObjects.BANDAGE, "Bandage");
        builder.add(CAVObjects.BATTLE_MAP, "Battle Map");
        builder.add(CAVObjects.BLOCK_RAMMER, "Block Rammer");
        builder.add(CAVObjects.BONE_SAW, "Bone Saw");
        builder.add(CAVObjects.CASESHOT, "Caseshot");
        builder.add(CAVObjects.MUSKET_SHOT, "Musket Shot");
        builder.add(CAVObjects.NATION_FLAG, "Nation Flag");
        builder.add(CAVObjects.PELLET_SHOT, "Pellet Shot");
        builder.add(CAVObjects.PISTOL_SHOT, "Pistol Shot");
        builder.add(CAVObjects.POWDER_CHARGE, "Powder Charge");
        builder.add(CAVObjects.RAMROD, "Ramrod");
        builder.add(CAVObjects.ROUNDSHOT, "Roundshot");
        builder.add(CAVObjects.SABRE, "Sabre");
        builder.add(CAVObjects.SHELLSHOT, "Shellshot");
        builder.add(CAVObjects.SILVER_SHOT, "Silver Shot");
        builder.add(CAVObjects.SPONGE, "Sponge");
    }
}
