package com.viralinnovation.awesomelib.config;

import com.viralinnovation.awesomelib.blocks.ParticleLeaves;
import com.viralinnovation.awesomelib.context.RegistrationContext;
import com.viralinnovation.awesomelib.items.BlockFamilyBoatItem;
import com.viralinnovation.awesomelib.registry.BlockFamily;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public final class Variants {

    // Item Variants

    public static Boat BOAT = new Boat();

    public static class Boat extends ItemVariant {

        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_boat";
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockFamilyBoatItem(context, false, family,
                    new Item.Properties().stacksTo(1).arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends ItemVariant> getClazz() {
            return this.getClass();
        }
    }

    public static ChestBoat CHEST_BOAT = new ChestBoat();

    public static class ChestBoat extends ItemVariant {

        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_chest_boat";
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockFamilyBoatItem(context, true, family,
                    new Item.Properties().stacksTo(1).arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends ItemVariant> getClazz() {
            return this.getClass();
        }
    }

    // Block Variants

    public static BaseBlock baseBlock = new BaseBlock();
    // This is merely a Storage Index, it will never generate a block
    public static class BaseBlock extends BlockVariant<BlockSetType> {

        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(BlockSetType blockSetType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return null;
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(BlockSetType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static Bookshelf BOOKSHELF = new Bookshelf();

    public static class Bookshelf extends BlockVariant<WoodType> {
        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_bookshelf";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(WoodType woodType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return () -> new Block(createWoodProperties());
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(WoodType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static Button BUTTON = new Button();

    public static class Button extends BlockVariant<BlockSetType> {
        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_button";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(BlockSetType blockSetType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            BlockBehaviour.Properties finalProperties = BlockBehaviour.Properties.of()
                    .noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
            return () -> new ButtonBlock(finalProperties, blockSetType, 30, true);
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(BlockSetType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static Door DOOR = new Door();

    public static class Door extends BlockVariant<BlockSetType> {
        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_door";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(BlockSetType blockSetType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return () -> new DoorBlock(createWoodProperties().noOcclusion(), blockSetType);
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(BlockSetType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static Fence FENCE = new Fence();

    public static class Fence extends BlockVariant<BlockSetType> {
        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_fence";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(BlockSetType blockSetType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return () -> new FenceBlock(createWoodProperties());
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(BlockSetType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static FenceGate fenceGate = new FenceGate();

    public static class FenceGate extends BlockVariant<WoodType> {
        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_fence_gate";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(WoodType woodType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return () -> new FenceGateBlock(createWoodProperties(), woodType);
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(WoodType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static HangingSign hangingSign = new HangingSign();

    public static class HangingSign extends BlockVariant<WoodType> {
        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_hanging_sign";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(WoodType woodType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return () -> new CeilingHangingSignBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).mapColor(color),
                    woodType);
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(WoodType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new HangingSignItem(associatedBlock.get(), family.getBlock(WallHangingSign.class).get(),
                    new Item.Properties().stacksTo(16).arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static WallHangingSign wallHangingSign = new WallHangingSign();

    public static class WallHangingSign extends BlockVariant<WoodType> {
        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_wall_hanging_sign";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(WoodType woodType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return () -> new WallHangingSignBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).mapColor(color),
                    woodType);
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(WoodType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return null;
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static Leaves LEAVES = new Leaves();

    public static class Leaves extends BlockVariant<BlockSetType> {
        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return !dimension.equals(BuiltinDimensionTypes.NETHER) ? "_leaves" : "_wart_block";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(BlockSetType blockSetType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return !dimension.equals(BuiltinDimensionTypes.NETHER) ?
                    () -> new ParticleLeaves(BlockBehaviour.Properties.of().mapColor(color)
                            .strength(0.2F).randomTicks().sound(SoundType.GRASS)
                            .noOcclusion().isViewBlocking((state, world, pos) -> false)
                            .isSuffocating((state, world, pos) -> false), particleOption) :
                    () -> new Block(BlockBehaviour.Properties.of()
                            .sound(SoundType.WART_BLOCK)
                            .strength(1.0F));
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(BlockSetType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static Log LOG = new Log();

    public static class Log extends BlockVariant<WoodType> {
        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return !dimension.equals(BuiltinDimensionTypes.NETHER) ? "_log" : "_stem";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(WoodType blockSetType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return !dimension.equals(BuiltinDimensionTypes.NETHER) ?
                    () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(2.0f)) :
                    () -> new RotatedPillarBlock(createNetherProperties());
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(WoodType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static Planks PLANKS = new Planks();

    public static class Planks extends BlockVariant<WoodType> {
        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_planks";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(WoodType blockSetType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return () -> new Block(createWoodProperties());
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(WoodType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static PressurePlate pressurePlate = new PressurePlate();

    public static class PressurePlate extends BlockVariant<BlockSetType> {

        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_pressure_plate";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(BlockSetType blockSetType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                    BlockBehaviour.Properties.of().sound(SoundType.WOOD).noCollission().strength(0.5F), blockSetType);
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(BlockSetType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static Sign SIGN = new Sign();

    public static class Sign extends BlockVariant<WoodType> {

        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_sign";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(WoodType woodType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return () -> new StandingSignBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).mapColor(color),
                    woodType);
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(WoodType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new SignItem(new Item.Properties().stacksTo(16).arch$tab(context.getDefaultTab()),
                    associatedBlock.get(), family.getBlock(WallSign.class).get());
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static WallSign wallSign = new WallSign();

    public static class WallSign extends BlockVariant<WoodType> {

        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_wall_sign";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(WoodType woodType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return () -> new WallSignBlock(
                    BlockBehaviour.Properties.copy(Blocks.OAK_SIGN).mapColor(color),
                    woodType);
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(WoodType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return null;
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static Slab SLAB = new Slab();

    public static class Slab extends BlockVariant<BlockSetType> {

        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_slab";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(BlockSetType blockSetType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return () -> new SlabBlock(createWoodProperties());
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(BlockSetType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static Stairs STAIRS = new Stairs();

    public static class Stairs extends BlockVariant<BlockSetType> {

        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_stairs";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(BlockSetType blockSetType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return () -> new StairBlock(baseBlock.get().defaultBlockState(),
                    BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD).strength(2.0f, 3.0f));
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(BlockSetType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static StrippedLog strippedLog = new StrippedLog();

    public static class StrippedLog extends BlockVariant<WoodType> {

        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "stripped_";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return !dimension.equals(BuiltinDimensionTypes.NETHER) ? "_log" : "_stem";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(WoodType blockSetType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return !dimension.equals(BuiltinDimensionTypes.NETHER) ?
                    () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(2.0f)) :
                    () -> new RotatedPillarBlock(createNetherProperties());
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(WoodType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static StrippedWood strippedWood = new StrippedWood();

    public static class StrippedWood extends BlockVariant<WoodType> {

        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "stripped_";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return !dimension.equals(BuiltinDimensionTypes.NETHER) ? "_wood" : "_hyphae";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(WoodType blockSetType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return !dimension.equals(BuiltinDimensionTypes.NETHER) ?
                    () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(2.0f)) :
                    () -> new RotatedPillarBlock(createNetherProperties());
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(WoodType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static Trapdoor TRAPDOOR = new Trapdoor();

    public static class Trapdoor extends BlockVariant<BlockSetType> {

        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return "_trapdoor";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(BlockSetType blockSetType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return () -> new TrapDoorBlock(createWoodProperties().noOcclusion(), blockSetType);
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(BlockSetType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }

    public static Wood WOOD = new Wood();

    public static class Wood extends BlockVariant<WoodType> {

        @Override
        public String getPrefix(ResourceKey<DimensionType> dimension) {
            return "";
        }

        @Override
        public String getSuffix(ResourceKey<DimensionType> dimension) {
            return !dimension.equals(BuiltinDimensionTypes.NETHER) ? "_wood" : "_hyphae";
        }

        @Override
        public Supplier<? extends Block> getBlockSupplier(WoodType blockSetType, Supplier<? extends Block> baseBlock, ResourceKey<DimensionType> dimension, Supplier<? extends SimpleParticleType> particleOption, MapColor color, BlockFamily family, RegistrationContext context) {
            return !dimension.equals(BuiltinDimensionTypes.NETHER) ?
                    () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(2.0f)) :
                    () -> new RotatedPillarBlock(createNetherProperties());
        }

        @Override
        public Supplier<? extends Item> getItemSupplier(WoodType blockSetType, Supplier<? extends Block> associatedBlock, ResourceKey<DimensionType> dimension, BlockFamily family, RegistrationContext context) {
            return () -> new BlockItem(associatedBlock.get(), new Item.Properties().arch$tab(context.getDefaultTab()));
        }

        @Override
        public Class<? extends BlockVariant<?>> getClazz() {
            return this.getClass();
        }
    }
}
