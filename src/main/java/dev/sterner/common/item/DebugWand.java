package dev.sterner.common.item;

import dev.sterner.common.component.PlayerDataComponent;
import dev.sterner.registry.CAVComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DebugWand extends Item {
    public DebugWand(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {

        CAVComponents.PLAYER_COMPONENT.maybeGet(player).ifPresent(playerDataComponent -> {
            switch (playerDataComponent.getRightLeg()) {
                case NONE -> playerDataComponent.setRightLeg(PlayerDataComponent.BodyPartType.NORMAL);
                case NORMAL -> playerDataComponent.setRightLeg(PlayerDataComponent.BodyPartType.PEG_LEG);
                case PEG_LEG -> playerDataComponent.setRightLeg(PlayerDataComponent.BodyPartType.NONE);
            }
        });

        return super.use(world, player, hand);
    }
}
