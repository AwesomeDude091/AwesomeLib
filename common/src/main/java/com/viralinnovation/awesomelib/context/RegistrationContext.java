package com.viralinnovation.awesomelib.context;

import com.viralinnovation.awesomelib.config.Variants;
import com.viralinnovation.awesomelib.entities.BlockFamilyBoat;
import com.viralinnovation.awesomelib.entities.BlockFamilyChestBoat;
import com.viralinnovation.awesomelib.entities.ContextEntityType;
import com.viralinnovation.awesomelib.registry.BlockFamilies;
import com.viralinnovation.awesomelib.registry.BlockFamily;
import dev.architectury.registry.registries.RegistrySupplier;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;

import java.util.Set;
import java.util.function.Supplier;

public interface RegistrationContext {

    <E extends Entity> RegistrySupplier<EntityType<E>> createEntity(String id, EntityType.Builder<E> entityType);

    <E extends Entity> RegistrySupplier<EntityType<E>> createEntity(String id, ContextEntityType.Builder<E> entityType);

    RegistrySupplier<? extends Block> createBlock(Supplier<? extends Block> block, String id);

    <T extends Item> RegistrySupplier<T> createItem(Supplier<? extends T> item,
                                                    RegistrySupplier<? extends Block> block);

    <T extends Item> RegistrySupplier<T> createItem(Supplier<? extends T> item, String id);

    RegistrySupplier<? extends ParticleType<?>> createParticleType(Supplier<? extends ParticleType<?>> particle, String id);

    String getNamespace();

    RegistrySupplier<CreativeModeTab> getDefaultTab();

    BlockFamilies getFamilies();

    RegistrySupplier<EntityType<BlockFamilyBoat>> getBoatEntityType();

    RegistrySupplier<EntityType<BlockFamilyChestBoat>> getChestBoatEntityType();

    ResourceLocation id(String id);

    static void fixSigns(BlockFamilies families) {
        // Fix Signs and Hanging Signs
        BlockEntityType<SignBlockEntity> signBuilder = BlockEntityType.SIGN;
        BlockEntityType<HangingSignBlockEntity> hangingSignBuilder = BlockEntityType.HANGING_SIGN;
        Set<Block> signValidBlocks = new ObjectOpenHashSet<>(signBuilder.validBlocks);
        Set<Block> hangingSignValidBlocks = new ObjectOpenHashSet<>(hangingSignBuilder.validBlocks);
        for (BlockFamily family : families.getOrganicMap().values()) {
            RegistrySupplier<? extends Block> signBlock = family.getBlock(Variants.Sign.class);
            RegistrySupplier<? extends Block> hangingSignBlock = family.getBlock(Variants.HangingSign.class);
            if (signBlock != null) {
                signValidBlocks.add(signBlock.get());
                signValidBlocks.add(family.getBlock(Variants.WallSign.class).get());
            }

            if (hangingSignBlock != null) {
                hangingSignValidBlocks.add(hangingSignBlock.get());
                hangingSignValidBlocks.add(family.getBlock(Variants.WallHangingSign.class).get());
            }

        }
        signBuilder.validBlocks = signValidBlocks;
        hangingSignBuilder.validBlocks = hangingSignValidBlocks;
    }
}
