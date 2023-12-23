package com.viralinnovation.awesomelib.loot;

import com.viralinnovation.awesomelib.registry.BlockFamily;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public class BlockFamiliesLootContextParams {

    public static final LootContextParam<BlockFamily> BOAT_TYPE = create("boat_type");

    private static <T> LootContextParam<T> create(String name) {
        return new LootContextParam<>(new ResourceLocation(name));
    }
}
