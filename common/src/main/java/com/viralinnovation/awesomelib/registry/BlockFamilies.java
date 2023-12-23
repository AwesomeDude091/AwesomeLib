package com.viralinnovation.awesomelib.registry;

import com.viralinnovation.awesomelib.AwesomeLib;
import com.viralinnovation.awesomelib.context.RegistrationContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class BlockFamilies {

    private final RegistrationContext context;

    private final Map<String, BlockFamily> organicMap = new HashMap<>();
    private final Map<String, BlockFamily> inorganicMap = new HashMap<>();

    public BlockFamilies(RegistrationContext context) {
        this.context = context;
    }

    public Map<String, BlockFamily> getOrganicMap() {
        return organicMap;
    }

    public Map<String, BlockFamily> getInorganicMap() {
        return inorganicMap;
    }

    public BlockFamily.OrganicBuilder getOrganicBuilder(WoodType woodType,
                                                        MapColor color, String baseName,
                                                        ResourceKey<DimensionType> dimension,
                                                        boolean hasPlanks) {
        return new BlockFamily.OrganicBuilder(context, woodType, color, baseName, dimension, hasPlanks);
    }

    public BlockFamily.InorganicBuilder getInorganicBuilder(BlockSetType blockSetType,
                                                            MapColor color, String baseName,
                                                            ResourceKey<DimensionType> dimension,
                                                            Supplier<? extends Block> blockSupplier) {
        return new BlockFamily.InorganicBuilder(context, blockSetType, color, baseName, dimension, blockSupplier);
    }

    public BlockFamily register(BlockFamily.OrganicBuilder builder) {
        BlockFamily blockFamily = builder.build();
        organicMap.put(blockFamily.getBaseName(), blockFamily);
        return blockFamily;
    }

    public BlockFamily register(BlockFamily.InorganicBuilder builder) {
        BlockFamily blockFamily = builder.build();
        inorganicMap.put(blockFamily.getBaseName(), blockFamily);
        return blockFamily;
    }

    public static @NotNull WoodType getOverworldWoodType(RegistrationContext context, String name){
        String id = context.id(name).toString().replace(':', '/');
        WoodType woodType = WoodType.register(new WoodType(id, new BlockSetType(id)));
        BlockSetType.register(woodType.setType());
        return woodType;
    }

    public static @NotNull WoodType getNotOverworldWoodType(RegistrationContext context, String name) {
        String id = context.id(name).toString().replace(':', '/');
        WoodType woodType = new WoodType(id, new BlockSetType(id, true, SoundType.NETHER_WOOD,
                SoundEvents.NETHER_WOOD_DOOR_CLOSE, SoundEvents.NETHER_WOOD_DOOR_OPEN,
                SoundEvents.NETHER_WOOD_TRAPDOOR_CLOSE, SoundEvents.NETHER_WOOD_TRAPDOOR_OPEN,
                SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_OFF,
                SoundEvents.NETHER_WOOD_PRESSURE_PLATE_CLICK_ON, SoundEvents.NETHER_WOOD_BUTTON_CLICK_OFF,
                SoundEvents.NETHER_WOOD_BUTTON_CLICK_ON), SoundType.NETHER_WOOD,
                SoundType.NETHER_WOOD_HANGING_SIGN, SoundEvents.NETHER_WOOD_FENCE_GATE_CLOSE,
                SoundEvents.NETHER_WOOD_FENCE_GATE_OPEN);
        BlockSetType.register(woodType.setType());
        return woodType;
    }

    public static BlockSetType getBlockSetType(@NotNull Supplier<? extends BlockSetType> supplier) {
        BlockSetType blockSetType = supplier.get();
        BlockSetType.register(blockSetType);
        return blockSetType;
    }

    public interface BlockFamiliesFactory {
        BlockFamilies create(RegistrationContext registrationContext);
    }
}
