package com.viralinnovation.awesomelib.config.types;

import com.viralinnovation.awesomelib.context.RegistrationContext;
import com.viralinnovation.awesomelib.registry.BlockFamily;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;


public interface BlockVariant<SetType> {
    String getPrefix(ResourceKey<DimensionType> dimension);
    String getSuffix(ResourceKey<DimensionType> dimension);
    Supplier<? extends Block> getBlockSupplier(SetType blockSetType,
                                               Supplier<? extends Block> baseBlock,
                                               ResourceKey<DimensionType> dimension,
                                               Supplier<? extends SimpleParticleType> particleOption,
                                               MapColor color, BlockFamily family,
                                               RegistrationContext context);
    Supplier<? extends Item> getItemSupplier(SetType blockSetType,
                                             Supplier<? extends Block> associatedBlock,
                                             ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context);

    Class<? extends BlockVariant<?>> getClazz();

    static BlockBehaviour.Properties createWoodProperties() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN)
                .sound(SoundType.WOOD).strength(2.0f, 3.0f);
    }

    static BlockBehaviour.Properties createNetherProperties() {
        return BlockBehaviour.Properties.of()
                .sound(SoundType.STEM)
                .strength(2.0f);
    }

    static BlockBehaviour.Properties createStoneProperties() {
        return BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).sound(SoundType.STONE)
                .strength(2.0f, 6.0f).requiresCorrectToolForDrops();
    }
}
