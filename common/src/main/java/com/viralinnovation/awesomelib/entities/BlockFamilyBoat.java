package com.viralinnovation.awesomelib.entities;

import com.viralinnovation.awesomelib.config.Variants;
import com.viralinnovation.awesomelib.context.RegistrationContext;
import com.viralinnovation.awesomelib.loot.AwesomeLootContextParams;
import com.viralinnovation.awesomelib.registry.BlockFamily;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlockFamilyBoat extends Boat {

    private final RegistrationContext context;

    private static final EntityDataAccessor<String> AWESOME_BOAT_TYPE = SynchedEntityData.defineId(BlockFamilyBoat.class, EntityDataSerializers.STRING);
    private static final LootContextParamSet LOOT_CONTEXT_PARAM_SETS = LootContextParamSet.builder()
            .required(AwesomeLootContextParams.BOAT_TYPE)
            .required(LootContextParams.THIS_ENTITY)
            .required(LootContextParams.ORIGIN)
            .required(LootContextParams.DAMAGE_SOURCE)
            .build();

    public BlockFamilyBoat(RegistrationContext context, Level worldIn, double x, double y, double z) {
        this(context, context.getBoatEntityType().get(), worldIn);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    public BlockFamilyBoat(RegistrationContext context, EntityType<? extends Boat> boatEntityType, Level worldType) {
        super(boatEntityType, worldType);
        this.context = context;
    }

    public ResourceLocation getLootLocation(BlockFamily type, boolean isChest, boolean isFall) {
        final ResourceLocation regName = context.getBoatEntityType().getId();
        String typeName = type.getBaseName() + (isChest ? "_chest" : "");
        return isFall ? new ResourceLocation(regName.getNamespace(), "boats/" + typeName + "_fall") : new ResourceLocation(regName.getNamespace(), "boats/" + typeName);
    }

    public List<ItemStack> getDrops(DamageSource damageSource, boolean isFall) {
        if (level() instanceof ServerLevel serverLevel) {
            LootParams lootContext = new LootParams.Builder(serverLevel)
                    .withParameter(AwesomeLootContextParams.BOAT_TYPE, getAwesomeBoatType())
                    .withParameter(LootContextParams.THIS_ENTITY, this)
                    .withParameter(LootContextParams.ORIGIN, position())
                    .withParameter(LootContextParams.DAMAGE_SOURCE, damageSource)
                    .create(LOOT_CONTEXT_PARAM_SETS);
            final ResourceLocation defaultLocation = this.getType().getDefaultLootTable();
            final LootTable any = serverLevel.getServer().getLootData().getLootTable(isFall ? new ResourceLocation(defaultLocation.getNamespace(), defaultLocation.getPath() + "_fall") : defaultLocation);
            if (any != LootTable.EMPTY)
                return any.getRandomItems(lootContext);

            final var lootTable = serverLevel.getServer().getLootData()
                    .getLootTable(getLootLocation(getAwesomeBoatType(), this instanceof BlockFamilyChestBoat, isFall));
            return lootTable.getRandomItems(lootContext);
        }
        return List.of();
    }

    @Override
    public @NotNull Item getDropItem() {
        final var type = getAwesomeBoatType().getItem(Variants.BOAT.getClazz()).get();
        if (type != null)
            return type;
        throw new RuntimeException("There is no boat variant saved for this block family");
    }

    public BlockFamily getAwesomeBoatType() {
        return context.getFamilies().getOrganicMap().get(this.entityData.get(AWESOME_BOAT_TYPE));
    }

    public void setAwesomeBoatType(BlockFamily boatAwesomeBoatType) {
        this.entityData.set(AWESOME_BOAT_TYPE, boatAwesomeBoatType.getBaseName());
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AWESOME_BOAT_TYPE, null);
    }


    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putString("AwesomeBoatType", this.getAwesomeBoatType().getBaseName());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("AwesomeBoatType", 8)) {
            this.setAwesomeBoatType(context.getFamilies().getOrganicMap().get(compound.getString("AwesomeBoatType")));
        }
    }

    @Override
    public void animateHurt(float f) {
        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() * 11.0F);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
        this.lastYd = this.getDeltaMovement().y;
        if (!this.isPassenger()) {
            if (onGroundIn) {
                if (this.fallDistance > 3.0F) {
                    if (this.status != Status.ON_LAND) {
                        this.fallDistance = 0.0F;
                        return;
                    }

                    this.causeFallDamage(this.fallDistance, 1.0F, this.damageSources().fall());
                    if (!this.level().isClientSide && !this.isRemoved()) {
                        this.kill();
                        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            getDrops(this.damageSources().fall(), true).forEach(this::spawnAtLocation);
                        }
                    }
                }

                this.fallDistance = 0.0F;
            } else if (!this.level().getFluidState((BlockPos.containing(this.getX(), this.getY(), this.getZ()).below())).is(FluidTags.WATER) && y < 0.0D) {
                this.fallDistance = (float) (this.fallDistance - y);
            }

        }
    }
    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.level().isClientSide && !this.isRemoved()) {
            if (source.isIndirect() && source.getEntity() != null && this.hasPassenger(source.getEntity())) {
                return false;
            } else {
                this.setHurtDir(-this.getHurtDir());
                this.setHurtTime(10);
                this.setDamage(this.getDamage() + amount * 10.0F);
                this.markHurt();
                boolean flag = source.getEntity() instanceof Player player && player.getAbilities().instabuild;
                if (flag || this.getDamage() > 40.0F) {
                    if (!flag && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                        getDrops(source, false).forEach(this::spawnAtLocation);
                    }
                    this.discard();
                }
                return true;
            }
        } else {
            return true;
        }
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        //TODO: Is this right?
        return new ClientboundAddEntityPacket(this);
    }
}
