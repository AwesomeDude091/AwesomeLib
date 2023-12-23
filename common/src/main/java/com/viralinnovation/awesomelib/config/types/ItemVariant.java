package com.viralinnovation.awesomelib.config.types;

import com.viralinnovation.awesomelib.context.RegistrationContext;
import com.viralinnovation.awesomelib.registry.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.function.Supplier;

public interface ItemVariant {

    String getPrefix(ResourceKey<DimensionType> dimension);
    String getSuffix(ResourceKey<DimensionType> dimension);
    Supplier<? extends Item> getItemSupplier(ResourceKey<DimensionType> dimension,
                                             BlockFamily family, RegistrationContext context);

    Class<? extends ItemVariant> getClazz();
}
