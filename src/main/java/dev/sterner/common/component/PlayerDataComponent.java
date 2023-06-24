package dev.sterner.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.sterner.registry.CAVComponents;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public class PlayerDataComponent implements AutoSyncedComponent {
    public PlayerEntity player;
    public BipedEntityModel<AbstractClientPlayerEntity> playerModel;
    private BodyPartType rightLeg = BodyPartType.NORMAL;
    private BodyPartType leftLeg = BodyPartType.NORMAL;
    private BodyPartType rightArm = BodyPartType.NORMAL;
    private BodyPartType leftArm = BodyPartType.NORMAL;

    public PlayerDataComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        setRightLeg(BodyPartType.byId(nbt.getInt("RightLeg")));
        setLeftLeg(BodyPartType.byId(nbt.getInt("LeftLeg")));
        setRightArm(BodyPartType.byId(nbt.getInt("RightArm")));
        setLeftArm(BodyPartType.byId(nbt.getInt("LeftArm")));
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putInt("RightLeg", rightLeg.getId());
        nbt.putInt("LeftLeg", leftLeg.getId());
        nbt.putInt("RightArm", rightArm.getId());
        nbt.putInt("LeftArm", leftArm.getId());
    }

    public void updatePlayerModel(){
        this.playerModel.rightLeg.visible = rightLeg == BodyPartType.NORMAL;
        this.playerModel.leftLeg.visible = leftLeg == BodyPartType.NORMAL;
        this.playerModel.rightArm.visible = rightArm == BodyPartType.NORMAL;
        this.playerModel.leftArm.visible = leftArm == BodyPartType.NORMAL;
    }

    public void setPlayerComponent(PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel) {
        this.playerModel = playerEntityModel;
        CAVComponents.PLAYER_COMPONENT.sync(player);
    }

    public void setRightLeg(BodyPartType type){
        this.rightLeg = type;
        CAVComponents.PLAYER_COMPONENT.sync(player);
    }

    public void setLeftLeg(BodyPartType type){
        this.leftLeg = type;
        CAVComponents.PLAYER_COMPONENT.sync(player);
    }

    public void setRightArm(BodyPartType type){
        this.rightArm = type;
        CAVComponents.PLAYER_COMPONENT.sync(player);
    }

    public void setLeftArm(BodyPartType type){
        this.leftArm = type;
        CAVComponents.PLAYER_COMPONENT.sync(player);
    }

    public BodyPartType getRightLeg(){
        return rightLeg;
    }

    public BodyPartType getLeftLeg(){
        return leftLeg;
    }

    public BodyPartType getRightArm(){
        return rightArm;
    }

    public BodyPartType getLeftArm(){
        return leftArm;
    }

    public enum BodyPartType implements StringIdentifiable {
        NORMAL(0, "normal"),
        NONE(1, "none"),
        PEG_LEG(2, "peg_leg");

        private static final IntFunction<BodyPartType> BY_ID = ValueLists.createIdToValueFunction(BodyPartType::getId, values(), ValueLists.OutOfBoundsHandling.ZERO);
        private String name;
        private int id;

        BodyPartType(int id, String name) {
            this.name = name;
            this.id = id;
        }

        public static BodyPartType byId(int id) {
            return BY_ID.apply(id);
        }

        public int getId() {
            return this.id;
        }

        @Override
        public String asString() {
            return this == NORMAL ? NONE.name : this == NONE ? NONE.name : PEG_LEG.name;
        }
    }
}
