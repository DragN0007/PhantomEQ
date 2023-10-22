package com.phantomskeep.phantomeq.entity;

import com.google.common.collect.Maps;
import com.phantomskeep.phantomeq.item.ModItems;
import com.phantomskeep.phantomeq.model.TruckDyeableBodyModel;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


public class TruckEntity extends Mob implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public TruckEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1)
                .add(Attributes.MOVEMENT_SPEED, 0.30)
                ;

    }


    private static final Map<DyeColor, ItemLike> ITEM_BY_DYE = Util.make(Maps.newEnumMap(DyeColor.class), (p_29841_) -> {
        p_29841_.put(DyeColor.WHITE, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.ORANGE, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.MAGENTA, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.LIGHT_BLUE, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.YELLOW, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.LIME, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.PINK, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.GRAY, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.LIGHT_GRAY, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.CYAN, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.PURPLE, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.BLUE, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.BROWN, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.GREEN, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.RED, ModItems.TRUCK_SPAWNER.get());
        p_29841_.put(DyeColor.BLACK, ModItems.TRUCK_SPAWNER.get());
    });
    private static final Map<DyeColor, float[]> COLORARRAY_BY_COLOR = Maps.<DyeColor, float[]>newEnumMap(Arrays.stream(DyeColor.values()).collect(Collectors.toMap((p_29868_) -> {
        return p_29868_;
    }, TruckEntity::createTruckColor)));
    private static float[] createTruckColor(DyeColor p_29866_) {
        if (p_29866_ == DyeColor.WHITE) {
            return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
        } else {
            float[] afloat = p_29866_.getTextureDiffuseColors();
            float f = 0.75F;
            return new float[]{afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
        }
    }
    public static float[] getColorArray(DyeColor p_29830_) {
        return COLORARRAY_BY_COLOR.get(p_29830_);
    }


    @Nullable
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }
    @Nullable
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.IRON_GOLEM_HURT;
    }
    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event) {
        if (isVehicle()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("drive", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this,"controller",10,this::predicate));
    }
    @Override
    public AnimationFactory getFactory() {
        return factory;
    }



//    //Generates variant textures
//
    public ResourceLocation getTextureLocation() {
        return TruckDyeableBodyModel.DefaultTruck.variantFromOrdinal(getVariant()).resourceLocation;
    }


    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(TruckEntity.class, EntityDataSerializers.INT);

    public int getVariant(){
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        if(compoundNBT.contains("Variant")) {
            setVariant(compoundNBT.getInt("Variant"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("Variant", getVariant());
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {

        setVariant(new Random().nextInt(TruckDyeableBodyModel.DefaultTruck.values().length));

        return super.finalizeSpawn(levelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Override
    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

}