package com.viralinnovation.awesomelib.context;

import com.viralinnovation.awesomelib.entities.BlockFamilyBoat;
import com.viralinnovation.awesomelib.entities.BlockFamilyChestBoat;
import com.viralinnovation.awesomelib.entities.ContextEntityType;
import com.viralinnovation.awesomelib.registry.BlockFamilies;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public final class ArchRegistrationContext implements RegistrationContext {

    // ID
    private final String namespace;

    // Registries
    public final DeferredRegister<Item> ITEMS;
    public final DeferredRegister<Block> BLOCKS;
    public final DeferredRegister<ParticleType<?>> PARTICLES;
    public final DeferredRegister<EntityType<?>> ENTITIES;
    public final DeferredRegister<CreativeModeTab> TABS;

    // Preconfigured Items
    private final RegistrySupplier<CreativeModeTab> CREATIVE_TAB;
    public final RegistrySupplier<EntityType<BlockFamilyBoat>> BOAT;
    public final RegistrySupplier<EntityType<BlockFamilyChestBoat>> CHEST_BOAT;

    private final BlockFamilies families;

    public ArchRegistrationContext(String namespace, BlockFamilies.BlockFamiliesFactory familiesFactory) {

        // Register Basics

        this.namespace = namespace;
        ITEMS = DeferredRegister.create(namespace, Registries.ITEM);
        BLOCKS = DeferredRegister.create(namespace, Registries.BLOCK);
        PARTICLES = DeferredRegister.create(namespace, Registries.PARTICLE_TYPE);
        TABS = DeferredRegister.create(namespace, Registries.CREATIVE_MODE_TAB);
        ENTITIES = DeferredRegister.create(namespace, Registries.ENTITY_TYPE);
        CREATIVE_TAB = TABS.register(
                namespace, // Tab ID
                () -> CreativeTabRegistry.create(
                        Component.translatable("category." + namespace), // Tab Name
                        () -> new ItemStack(Items.CHERRY_LEAVES) // Icon
                )
        );
        BLOCKS.register();
        ITEMS.register();
        PARTICLES.register();
        TABS.register();

        // Register Block Families

        families = familiesFactory.create(this);

        // Register Entities

        BOAT = createEntity("boat", ContextEntityType.Builder.<BlockFamilyBoat>of(BlockFamilyBoat::new, MobCategory.MISC).sized(1.375F, 0.5625F));
        CHEST_BOAT = createEntity("chest_boat", ContextEntityType.Builder.<BlockFamilyChestBoat>of(BlockFamilyChestBoat::new, MobCategory.MISC)
                .sized(1.375F, 0.5625F).clientTrackingRange(10));

        RegistrationContext.fixSigns(families);
    }

    @Override
    public <E extends Entity> RegistrySupplier<EntityType<E>> createEntity(String id, EntityType.Builder<E> entityType) {
        return ENTITIES.register(id, () -> entityType.build(namespace + ":" + id));
    }

    @Override
    public <E extends Entity> RegistrySupplier<EntityType<E>> createEntity(String id, ContextEntityType.Builder<E> entityType) {
        return ENTITIES.register(id, () -> entityType.build(this, namespace + ":" + id));
    }

    @Override
    public RegistrySupplier<? extends Block> createBlock(Supplier<? extends Block> block, String id) {
        return BLOCKS.register(id, block);
    }

    @Override
    public <T extends Item> RegistrySupplier<T> createItem(Supplier<? extends T> item,
                                                           RegistrySupplier<? extends Block> block) {
        return createItem(item, block.getId().getPath());
    }

    @Override
    public <T extends Item> RegistrySupplier<T> createItem(Supplier<? extends T> item, String id) {
        return ITEMS.register(id, item);
    }

    @Override
    public RegistrySupplier<? extends ParticleType<?>> createParticleType(Supplier<? extends ParticleType<?>> particle, String id) {
        return PARTICLES.register(id, particle);
    }

    @Override
    public String getNamespace() {
        return namespace;
    }

    @Override
    public RegistrySupplier<CreativeModeTab> getDefaultTab() {
        return CREATIVE_TAB;
    }

    @Override
    public BlockFamilies getFamilies() {
        return families;
    }

    @Override
    public RegistrySupplier<EntityType<BlockFamilyBoat>> getBoatEntityType() {
        return BOAT;
    }

    @Override
    public RegistrySupplier<EntityType<BlockFamilyChestBoat>> getChestBoatEntityType() {
        return CHEST_BOAT;
    }

    @Override
    public ResourceLocation id(String id) {
        return new ResourceLocation(namespace, id);
    }
}
