package dev.sterner.mixin;

import dev.sterner.common.Nation;
import dev.sterner.common.NationChunk;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Optional;

@Mixin(Chunk.class)
public final class ChunkMixin implements NationChunk {
    @Override
    public Optional<Nation> getNation() {
        return Optional.empty();
    }

    @Override
    public void setNation(Nation nation) {

    }
}
