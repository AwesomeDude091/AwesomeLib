package com.viralinnovation.awesomelib.config.attributes;

import com.viralinnovation.awesomelib.context.RegistrationContext;
import com.viralinnovation.awesomelib.registry.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.function.Supplier;

public interface StrippableVariant {
    Supplier<? extends Block> getStrippedBlock(ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context);
}
