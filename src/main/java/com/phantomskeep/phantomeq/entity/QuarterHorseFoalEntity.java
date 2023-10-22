package com.phantomskeep.phantomeq.entity;

import com.phantomskeep.phantomeq.model.WarmbloodFoalModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
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
import java.util.Random;


public class QuarterHorseFoalEntity extends AbstractHorse implements IAnimatable {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public QuarterHorseFoalEntity(EntityType<? extends AbstractHorse> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
    }

    /* private static final Predicate<LivingEntity> PARENT_WARMBLOOD_SELECTOR = (livingEntity) -> livingEntity instanceof QuarterHorseEntity && ((QuarterHorseEntity)livingEntity).isBred();
    private static final TargetingConditions TARGET_MAMA = TargetingConditions.forNonCombat()
            .range(16.0D).ignoreLineOfSight().selector(PARENT_WARMBLOOD_SELECTOR);

     protected void followMommy() {
        if (this.isBred() && this.isBaby() && !this.isEating()) {
            LivingEntity livingentity = this.level.getNearestEntity(QuarterHorseEntity.class, TARGET_MAMA, this, this.getX(), this.getY(), this.getZ(), this.getBoundingBox().inflate(16.0D));
            if (livingentity != null && this.distanceToSqr(livingentity) > 4.0D) {
                this.navigation.createPath(livingentity, 0);
            }
        }

    } */

    //Sound code.
    protected void playGallopSound(SoundType p_30709_) {
        super.playGallopSound(p_30709_);
        if (this.random.nextInt(10) == 0) {
            this.playSound(SoundEvents.HORSE_BREATHE, p_30709_.getVolume() * 0.6F, p_30709_.getPitch());
        }

        ItemStack stack = this.inventory.getItem(1);
        if (isArmor(stack)) stack.onHorseArmorTick(level, this);
    }
    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        super.getAmbientSound();
        return SoundEvents.HORSE_AMBIENT;
    }
    protected SoundEvent getDeathSound() {
        super.getDeathSound();
        return SoundEvents.HORSE_DEATH;
    }
    @Nullable
    protected SoundEvent getEatingSound() {
        return SoundEvents.HORSE_EAT;
    }
    protected SoundEvent getHurtSound(DamageSource p_30720_) {
        super.getHurtSound(p_30720_);
        return SoundEvents.HORSE_HURT;
    }
    protected SoundEvent getAngrySound() {
        super.getAngrySound();
        return SoundEvents.HORSE_ANGRY;
    }


    public void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2D));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addBehaviourGoals();
    }


    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event) {

            if (event.isMoving()) {
                if (isSprinting()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("sprint", ILoopType.EDefaultLoopTypes.LOOP));

            } else event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", ILoopType.EDefaultLoopTypes.LOOP));

            } else
                event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP));


        return PlayState.CONTINUE;
    }


    //Controls animations
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this,"controller",1,this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }




    //Generates variant textures

    public ResourceLocation getTextureLocation() {
        return WarmbloodFoalModel.Variant.variantFromOrdinal(getVariant()).resourceLocation;
    }

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(QuarterHorseFoalEntity.class, EntityDataSerializers.INT);

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

        setVariant(new Random().nextInt(WarmbloodFoalModel.Variant.values().length));

        return super.finalizeSpawn(levelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
        return null;
    }

    @Override
    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

}