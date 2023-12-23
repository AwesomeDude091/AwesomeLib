package com.viralinnovation.awesomelib.config.types;

import com.viralinnovation.awesomelib.registry.BlockFamily;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.function.Supplier;

public interface ParticleVariant<T extends ParticleOptions> {
    String getPrefix(ResourceKey<DimensionType> dimension);
    String getSuffix(ResourceKey<DimensionType> dimension);
    Supplier<? extends ParticleType<T>> getParticleSupplier(Supplier<? extends Block> baseBlock,
                                                                         ResourceKey<DimensionType> dimension,
                                                                         BlockFamily family);

    ParticleEngine.SpriteParticleRegistration<T> getSpriteProvider();

    Class<? extends ParticleVariant<?>> getClazz();
}
