package com.viralinnovation.awesomelib.config;

import com.viralinnovation.awesomelib.context.RegistrationContext;
import com.viralinnovation.awesomelib.registry.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.function.Supplier;

public abstract class ItemVariant {

    public abstract String getPrefix(ResourceKey<DimensionType> dimension);
    public abstract String getSuffix(ResourceKey<DimensionType> dimension);
    public abstract Supplier<? extends Item> getItemSupplier(ResourceKey<DimensionType> dimension,
                                                             BlockFamily family, RegistrationContext context);

    public abstract Class<? extends ItemVariant> getClazz();
}
