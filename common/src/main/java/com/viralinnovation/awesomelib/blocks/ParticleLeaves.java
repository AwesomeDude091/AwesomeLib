package com.viralinnovation.awesomelib.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ParticleLeaves extends LeavesBlock {
    protected final Supplier<? extends SimpleParticleType> particleType;

    public ParticleLeaves(Properties properties, @Nullable Supplier<? extends SimpleParticleType> particleType) {
        super(properties);
        this.particleType = particleType;
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        super.animateTick(blockState, level, blockPos, randomSource);
        if (particleType != null) {
            if (randomSource.nextDouble() < 0.01F) {
                BlockPos below = blockPos.below();
                BlockState state = level.getBlockState(below);
                if (!state.canOcclude() || !state.isFaceSturdy(level, below, Direction.UP)) {
                    double x = (double) blockPos.getX() + randomSource.nextDouble();
                    double y = (double) blockPos.getY() - 0.05;
                    double z = (double) blockPos.getZ() + randomSource.nextDouble();
                    level.addParticle(particleType.get(), x, y, z, 0.0, 0, 0.0);
                }
            }
        }

    }
}
