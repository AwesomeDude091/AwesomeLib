package com.viralinnovation.awesomelib.config;

import com.viralinnovation.awesomelib.registry.BlockFamily;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.function.Supplier;

public abstract class ParticleVariant {
    public abstract String getPrefix(ResourceKey<DimensionType> dimension);
    public abstract String getSuffix(ResourceKey<DimensionType> dimension);
    public abstract Supplier<? extends ParticleType<?>> getParticleSupplier(Supplier<? extends Block> baseBlock,
                                                                         ResourceKey<DimensionType> dimension,
                                                                         BlockFamily family);

    public abstract Class<? extends ParticleVariant> getClazz();
}
