package dev.sterner.common.block;

import dev.sterner.common.block.blockentity.CAVBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public abstract class CAVBlockWithEntity<T extends CAVBlockEntity> extends BlockWithEntity {
    public CAVBlockWithEntity(Settings settings) {
        super(settings);
    }

    @Override
    @Nullable
    public <B extends BlockEntity> BlockEntityTicker<B> getTicker(@NotNull World w, @NotNull BlockState s, @NotNull BlockEntityType<B> t) {
        return ((world, pos, state, blockEntity) -> {
            if (world.getBlockEntity(pos) instanceof CAVBlockEntity cavBlockEntity) {
                cavBlockEntity.tick();
                if (world.isClient()) {
                    cavBlockEntity.clientTick();
                } else {
                    cavBlockEntity.serverTick();
                }
            }
        });
    }

    @NotNull
    @Override
    public ActionResult onUse(@NotNull BlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull PlayerEntity player, @NotNull Hand hand, @NotNull BlockHitResult hitResult) {
        if (world.getBlockEntity(pos) instanceof CAVBlockEntity blockEntity) {
            return blockEntity.onUse(player, hand);
        }
        return super.onUse(state, world, pos, player, hand, hitResult);
    }

    @Override
    public void onPlaced(@NotNull World world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity livingEntity, @NotNull ItemStack itemStack) {
        if (world.getBlockEntity(pos) instanceof CAVBlockEntity blockEntity) {
            blockEntity.onPlaced(world, pos, state, livingEntity, itemStack);
        }
        super.onPlaced(world, pos, state, livingEntity, itemStack);
    }

    @Override
    public void onBreak(@NotNull World world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull PlayerEntity player) {
        if (world.getBlockEntity(pos) instanceof CAVBlockEntity blockEntity) {
            blockEntity.onBreak(player);
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
