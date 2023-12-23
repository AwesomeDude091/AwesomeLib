package com.viralinnovation.awesomelib.registry;

import com.viralinnovation.awesomelib.config.BlockVariant;
import com.viralinnovation.awesomelib.config.ItemVariant;
import com.viralinnovation.awesomelib.config.ParticleVariant;
import com.viralinnovation.awesomelib.config.Variants;
import com.viralinnovation.awesomelib.context.RegistrationContext;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * Provides registration logic and categorization for Sets of Blocks
 *
 * @author AwesomeDude091
 */
@SuppressWarnings("unused")
public class BlockFamily {
    private final String baseName;

    private final ResourceKey<DimensionType> dimension;
    private final Map<Class<? extends BlockVariant<?>>, RegistrySupplier<? extends Block>> variants = new HashMap<>();
    private final Map<Class<? extends ItemVariant>, RegistrySupplier<? extends Item>> itemVariants = new HashMap<>();
    private final Map<Class<? extends ParticleVariant>, RegistrySupplier<? extends ParticleType<?>>> particleVariants = new ConcurrentHashMap<>();
    private final Map<Class<? extends BlockVariant<?>>, TagKey<Block>> blockTagKeyMap = new HashMap<>();
    private final Map<Class<? extends BlockVariant<?>>, ModelTypes> modelTypesMap = new HashMap<>();
    private BiConsumer<BiConsumer<RegistrySupplier<? extends Block>, RegistrySupplier<? extends Block>>,
            BlockFamily> strippables = null;
    @Nullable
    String recipeGroupPrefix;

    private BlockFamily(String baseName, ResourceKey<DimensionType> dimension) {
        this.baseName = baseName;
        this.dimension = dimension;
    }

    public Map<Class<? extends BlockVariant<?>>, RegistrySupplier<? extends Block>> getVariants() {
        return this.variants;
    }

    public Map<Class<? extends ItemVariant>, RegistrySupplier<? extends Item>> getItemVariants() {
        return itemVariants;
    }

    public Map<Class<? extends BlockVariant<?>>, TagKey<Block>> getBlockTagKeyMap() {
        return blockTagKeyMap;
    }

    public RegistrySupplier<? extends Block> getBlock(Class<? extends BlockVariant<?>> blockVariant) {
        return this.variants.get(blockVariant);
    }

    public RegistrySupplier<? extends Item> getItem(Class<? extends ItemVariant> itemVariant) {
        return this.itemVariants.get(itemVariant);
    }

    public RegistrySupplier<? extends ParticleType<?>> getParticle(Class<? extends ParticleVariant> variant) {
        return this.particleVariants.get(variant);
    }

    public TagKey<Block> getTag(Class<BlockVariant<?>> blockVariant) {
        return this.blockTagKeyMap.get(blockVariant);
    }

    public ModelTypes getModelType(Class<? extends BlockVariant<?>> blockVariant) {
        ModelTypes modelTypes = modelTypesMap.get(blockVariant);
        if(modelTypes != null) {
            return modelTypes;
        }
        return ModelTypes.NORMAL;
    }

    public BiConsumer<BiConsumer<RegistrySupplier<? extends Block>, RegistrySupplier<? extends Block>>,
            BlockFamily> getStrippables() {
        return strippables;
    }

    public String getBaseName() {
        return baseName;
    }

    public Optional<String> getRecipeGroupPrefix() {
        return Util.isBlank(this.recipeGroupPrefix) ? Optional.empty() : Optional.of(this.recipeGroupPrefix);
    }

    public ResourceKey<DimensionType> getDimension() {
        return dimension;
    }

    public static final class OrganicBuilder {

        private final RegistrationContext context;

        private final WoodType woodType;
        private final BlockFamily family;
        private final MapColor color;
        private Supplier<? extends Block> baseBlock;

        public OrganicBuilder(RegistrationContext context, WoodType woodType, MapColor color,
                              String baseName, ResourceKey<DimensionType> dimension, boolean hasPlanks) {
            this.context = context;
            this.woodType = woodType;
            this.color = color;
            family = new BlockFamily(baseName, dimension);
            if(hasPlanks) {
                RegistrySupplier<? extends Block> planks = context.createBlock(Variants.PLANKS
                        .getBlockSupplier(woodType, null, dimension, null, color, family, context),
                        Variants.PLANKS.getPrefix(dimension) + baseName + Variants.PLANKS.getSuffix(dimension));
                this.family.variants.put(Variants.Planks.class, planks);
                this.family.variants.put(Variants.BaseBlock.class, planks);
                baseBlock = planks;
                context.createItem(Variants.PLANKS.getItemSupplier(woodType, planks, dimension, family, context),
                        planks.getId().getPath());
            }
        }

        public OrganicBuilder addWoodBlock(BlockVariant<WoodType> variant,
                                       Supplier<? extends SimpleParticleType> particleOption) {
            RegistrySupplier<? extends Block> supplier = context.createBlock(variant.getBlockSupplier(woodType, baseBlock, family.getDimension(), particleOption, color, family, context),
                    variant.getPrefix(family.getDimension()) + family.getBaseName() + variant.getSuffix(family.getDimension()));
            family.variants.put(variant.getClazz(), supplier);
            context.createItem(variant.getItemSupplier(woodType, supplier, family.getDimension(), family, context),
                    supplier.getId().getPath());
            return this;
        }

        public OrganicBuilder addBlock(BlockVariant<BlockSetType> variant,
                                       Supplier<? extends SimpleParticleType> particleOption) {
            RegistrySupplier<? extends Block> supplier = context.createBlock(variant.getBlockSupplier(woodType.setType(), baseBlock, family.getDimension(), particleOption, color, family, context),
                    variant.getPrefix(family.getDimension()) + family.getBaseName() + variant.getSuffix(family.getDimension()));
            family.variants.put(variant.getClazz(), supplier);
            context.createItem(variant.getItemSupplier(woodType.setType(), supplier, family.getDimension(), family, context),
                    supplier.getId().getPath());
            return this;
        }

        public OrganicBuilder addParticleType(ParticleVariant variant) {
            RegistrySupplier<? extends ParticleType<?>> supplier = context.createParticleType(variant.getParticleSupplier(baseBlock, family.getDimension(), family),
                    variant.getPrefix(family.getDimension()) + family.getBaseName() + variant.getSuffix(family.getDimension()));
            family.particleVariants.put(variant.getClazz(), supplier);
            return this;
        }

        public OrganicBuilder addItem(ItemVariant variant) {
            RegistrySupplier<? extends Item> supplier = context.createItem(variant.getItemSupplier(family.getDimension(), family, context),
                    variant.getPrefix(family.getDimension()) + family.getBaseName() + variant.getSuffix(family.getDimension()));
            family.itemVariants.put(variant.getClazz(), supplier);
            return this;
        }

        public BlockFamily build() {
            return family;
        }
    }

    public static final class InorganicBuilder {

        private final RegistrationContext context;

        private final BlockSetType blockSetType;
        private final BlockFamily family;
        private final MapColor color;
        private final Supplier<? extends Block> baseBlock;

        public InorganicBuilder(RegistrationContext context, BlockSetType blockSetType, MapColor color,
                              String baseName, ResourceKey<DimensionType> dimension,
                              Supplier<? extends Block> baseBlock) {
            this.context = context;
            this.blockSetType = blockSetType;
            this.color = color;
            family = new BlockFamily(baseName, dimension);
            this.baseBlock = baseBlock;
            this.family.variants.put(Variants.BaseBlock.class,
                    context.createBlock(baseBlock, baseName));
        }

        public InorganicBuilder addBlock(BlockVariant<BlockSetType> variant,
                                       Supplier<? extends SimpleParticleType> particleOption) {
            RegistrySupplier<? extends Block> supplier = context.createBlock(variant.getBlockSupplier(blockSetType, baseBlock, family.getDimension(), particleOption, color, family, context),
                    variant.getPrefix(family.getDimension()) + family.getBaseName() + variant.getSuffix(family.getDimension()));
            family.variants.put(variant.getClazz(), supplier);
            context.createItem(variant.getItemSupplier(blockSetType, supplier, family.getDimension(), family, context),
                    supplier.getId().getPath());
            return this;
        }

        public InorganicBuilder addParticleType(ParticleVariant variant) {
            RegistrySupplier<? extends ParticleType<?>> supplier = context.createParticleType(variant.getParticleSupplier(baseBlock, family.getDimension(), family),
                    variant.getPrefix(family.getDimension()) + family.getBaseName() + variant.getSuffix(family.getDimension()));
            family.particleVariants.put(variant.getClazz(), supplier);
            return this;
        }

        public InorganicBuilder addItem(ItemVariant variant) {
            RegistrySupplier<? extends Item> supplier = context.createItem(variant.getItemSupplier(family.getDimension(), family, context),
                    variant.getPrefix(family.getDimension()) + family.getBaseName() + variant.getSuffix(family.getDimension()));
            family.itemVariants.put(variant.getClazz(), supplier);
            return this;
        }

        public BlockFamily build() {
            return family;
        }
    }

    public enum GrowerTypes {
        PLANT,
        BUSH,
        MUSHROOM,
        NETHER_PLANT,
        NETHER_BUSH,
        END_PLANT,
        END_BUSH,
        DESERT_PLANT
    }

    public enum ModelTypes {
        OVERLAY,
        NORMAL
    }
}
