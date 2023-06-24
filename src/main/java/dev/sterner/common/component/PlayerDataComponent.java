package dev.sterner.common.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.sterner.registry.CAVComponents;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerDataComponent implements AutoSyncedComponent {

    public PlayerEntity player;
    public BipedEntityModel<AbstractClientPlayerEntity> playerModel;
    private boolean rightLegPegged = false;
    private boolean leftLegPegged = false;
    private boolean rightArmHooker = false;
    private boolean leftArmHooker = false;

    public PlayerDataComponent(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void readFromNbt(NbtCompound nbt) {
        setRightLeg(nbt.getBoolean("RightLeg"));
        setLeftLeg(nbt.getBoolean("LeftLeg"));
        setRightArm(nbt.getBoolean("RightArm"));
        setLeftArm(nbt.getBoolean("LeftArm"));
    }

    @Override
    public void writeToNbt(NbtCompound nbt) {
        nbt.putBoolean("RightLeg", getRightLegPegged());
        nbt.putBoolean("LeftLeg", getLeftLegPegged());
        nbt.putBoolean("RightArm", getRightArmHooker());
        nbt.putBoolean("LeftArm", getLeftArmHooker());
    }

    public void updatePlayerModel() {
        if (playerModel != null) {
            playerModel.leftArm.visible = !getLeftArmHooker();
            playerModel.rightArm.visible = !getRightArmHooker();
            playerModel.leftLeg.visible = !getLeftLegPegged();
            playerModel.rightLeg.visible = !getRightLegPegged();
        }
    }

    public void setPlayerModel(PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel) {
        this.playerModel = playerEntityModel;
        CAVComponents.PLAYER_COMPONENT.sync(player);
    }

    public void setRightLeg(boolean pegged) {
        this.rightLegPegged = pegged;
        CAVComponents.PLAYER_COMPONENT.sync(player);
    }

    public void setLeftLeg(boolean pegged) {
        this.leftLegPegged = pegged;
        CAVComponents.PLAYER_COMPONENT.sync(player);
    }

    public void setRightArm(boolean hooker) {
        this.rightArmHooker = hooker;
        CAVComponents.PLAYER_COMPONENT.sync(player);
    }

    public void setLeftArm(boolean hooker) {
        this.leftArmHooker = hooker;
        CAVComponents.PLAYER_COMPONENT.sync(player);
    }

    public boolean getRightLegPegged() {
        return rightLegPegged;
    }

    public boolean getLeftLegPegged() {
        return leftLegPegged;
    }

    public boolean getRightArmHooker() {
        return rightArmHooker;
    }

    public boolean getLeftArmHooker() {
        return leftArmHooker;
    }
}
