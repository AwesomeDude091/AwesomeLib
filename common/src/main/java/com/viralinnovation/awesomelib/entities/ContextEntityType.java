package com.viralinnovation.awesomelib.entities;

import com.google.common.collect.ImmutableSet;
import com.viralinnovation.awesomelib.context.RegistrationContext;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class ContextEntityType<T extends Entity> extends EntityType<T> {

    private final RegistrationContext context;
    private final EntityFactory<T> entityFactory;
    public ContextEntityType(RegistrationContext context, EntityFactory<T> entityFactory,
                             MobCategory mobCategory,
                             boolean bl, boolean bl2,
                             boolean bl3, boolean bl4,
                             ImmutableSet<Block> immutableSet,
                             EntityDimensions entityDimensions,
                             int i, int j, FeatureFlagSet featureFlagSet) {
        super(null, mobCategory, bl, bl2, bl3, bl4, immutableSet, entityDimensions, i, j, featureFlagSet);
        this.context = context;
        this.entityFactory = entityFactory;
    }

    @Nullable
    public T create(Level level) {
        if (!this.isEnabled(level.enabledFeatures())) {
            return null;
        }
        return this.entityFactory.create(context, this, level);
    }

    public static class Builder<T extends Entity> {
        private final EntityFactory<T> factory;
        private final MobCategory category;
        private ImmutableSet<Block> immuneTo = ImmutableSet.of();
        private boolean serialize = true;
        private boolean summon = true;
        private boolean fireImmune;
        private boolean canSpawnFarFromPlayer;
        private int clientTrackingRange = 5;
        private int updateInterval = 3;
        private EntityDimensions dimensions = EntityDimensions.scalable(0.6f, 1.8f);
        private FeatureFlagSet requiredFeatures = FeatureFlags.VANILLA_SET;

        private Builder(EntityFactory<T> entityFactory, MobCategory mobCategory) {
            this.factory = entityFactory;
            this.category = mobCategory;
            this.canSpawnFarFromPlayer = mobCategory == MobCategory.CREATURE || mobCategory == MobCategory.MISC;
        }

        public static <T extends Entity> Builder<T> of(EntityFactory<T> entityFactory, MobCategory mobCategory) {
            return new Builder<T>(entityFactory, mobCategory);
        }

        public static <T extends Entity> Builder<T> createNothing(MobCategory mobCategory) {
            return new Builder<T>((context, entityType, level) -> null, mobCategory);
        }

        public Builder<T> sized(float f, float g) {
            this.dimensions = EntityDimensions.scalable(f, g);
            return this;
        }

        public Builder<T> noSummon() {
            this.summon = false;
            return this;
        }

        public Builder<T> noSave() {
            this.serialize = false;
            return this;
        }

        public Builder<T> fireImmune() {
            this.fireImmune = true;
            return this;
        }

        public Builder<T> immuneTo(Block ... blocks) {
            this.immuneTo = ImmutableSet.copyOf(blocks);
            return this;
        }

        public Builder<T> canSpawnFarFromPlayer() {
            this.canSpawnFarFromPlayer = true;
            return this;
        }

        public Builder<T> clientTrackingRange(int i) {
            this.clientTrackingRange = i;
            return this;
        }

        public Builder<T> updateInterval(int i) {
            this.updateInterval = i;
            return this;
        }

        public Builder<T> requiredFeatures(FeatureFlag... featureFlags) {
            this.requiredFeatures = FeatureFlags.REGISTRY.subset(featureFlags);
            return this;
        }

        public EntityType<T> build(RegistrationContext context, String string) {
            if (this.serialize) {
                Util.fetchChoiceType(References.ENTITY_TREE, string);
            }
            return new ContextEntityType<>(context, this.factory, this.category, this.serialize, this.summon, this.fireImmune, this.canSpawnFarFromPlayer, this.immuneTo, this.dimensions, this.clientTrackingRange, this.updateInterval, this.requiredFeatures);
        }
    }

    public interface EntityFactory<T extends Entity> {
        T create(RegistrationContext context, EntityType<T> var1, Level var2);
    }
}
