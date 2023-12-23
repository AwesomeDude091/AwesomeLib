package com.viralinnovation.awesomelib.config;

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


public abstract class BlockVariant<SetType> {
    public abstract String getPrefix(ResourceKey<DimensionType> dimension);
    public abstract String getSuffix(ResourceKey<DimensionType> dimension);
    public abstract Supplier<? extends Block> getBlockSupplier(SetType blockSetType,
                                                               Supplier<? extends Block> baseBlock,
                                                               ResourceKey<DimensionType> dimension,
                                                               Supplier<? extends SimpleParticleType> particleOption,
                                                               MapColor color, BlockFamily family,
                                                               RegistrationContext context);
    public abstract Supplier<? extends Item> getItemSupplier(SetType blockSetType,
                                                             Supplier<? extends Block> associatedBlock,
                                                             ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context);

    public abstract Class<? extends BlockVariant<?>> getClazz();

    public static BlockBehaviour.Properties createWoodProperties() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN)
                .sound(SoundType.WOOD).strength(2.0f, 3.0f);
    }

    public static BlockBehaviour.Properties createNetherProperties() {
        return BlockBehaviour.Properties.of()
                .sound(SoundType.STEM)
                .strength(2.0f);
    }

    public static BlockBehaviour.Properties createStoneProperties() {
        return BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).sound(SoundType.STONE)
                .strength(2.0f, 6.0f).requiresCorrectToolForDrops();
    }
}
