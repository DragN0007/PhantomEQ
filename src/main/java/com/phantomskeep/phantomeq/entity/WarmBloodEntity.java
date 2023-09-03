package com.phantomskeep.phantomeq.entity;

import com.phantomskeep.phantomeq.entity.util.EntityTypes;
import com.phantomskeep.phantomeq.item.ModItems;
import com.phantomskeep.phantomeq.model.WarmbloodModel;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Variant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
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
import java.util.Random;
import java.util.function.Predicate;


public class WarmBloodEntity extends AbstractHorse implements IAnimatable {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public WarmBloodEntity(EntityType<? extends AbstractHorse> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
    }

    protected void randomizeAttributes() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.generateRandomMaxHealth());
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateRandomSpeed());
        this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateRandomJumpStrength());
    }


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


    private int isEatingGrass;
    private EatBlockGoal eatBlockGoal;
    protected void customServerAiStep() {
        this.isEatingGrass = this.eatBlockGoal.getEatAnimationTick();
        super.customServerAiStep();
    }
    public void aiStep() {
        if (this.level.isClientSide) {
            this.isEatingGrass = Math.max(1, this.isEatingGrass - 1);
        }

        super.aiStep();
    }

    public void registerGoals() {
        this.eatBlockGoal = new EatBlockGoal(this);
        this.goalSelector.addGoal(5, this.eatBlockGoal);

        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D, AbstractHorse.class));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addBehaviourGoals();
    }


    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event) {

        if (event.isMoving()) {
            if (isEatingGrass > 0) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("sprint", ILoopType.EDefaultLoopTypes.LOOP));
            } else
                event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", ILoopType.EDefaultLoopTypes.LOOP));

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

    //Rider & Lead Offsets
//    public void positionRider(Entity positionRider) {
//        super.positionRider(positionRider);
//        if (positionRider instanceof Mob) {
//            Mob mob = (Mob)positionRider;
//            this.yBodyRot = mob.yBodyRot;
//        }
//
//            positionRider.setPos(this.getX() + (double)(0.3F), this.getY() + this.getPassengersRidingOffset() + positionRider.getMyRidingOffset() + (double)0F, this.getZ() - (double)(0F));
//            if (positionRider instanceof LivingEntity) {
//                ((LivingEntity)positionRider).yBodyRot = this.yBodyRot;
//            }
//        }

    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, (double)(0.9F * this.getEyeHeight()), (double)(this.getBbWidth() * 0.9F));
    }


    //Generates variant textures

    public ResourceLocation getTextureLocation() {
        return WarmbloodModel.Variant.variantFromOrdinal(getVariant()).resourceLocation;
    }

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(WarmBloodEntity.class, EntityDataSerializers.INT);

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

        if (compoundNBT.contains("peq_saddle", 10)) {
            ItemStack itemstack = ItemStack.of(compoundNBT.getCompound("peq_saddle"));
            if (itemstack.is(ModItems.PEQ_SADDLE.get())) {
                this.inventory.setItem(0, itemstack);
            }
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {

        setVariant(new Random().nextInt(WarmbloodModel.Variant.values().length));

        return super.finalizeSpawn(levelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
    }

    public boolean canBeParent() {
        return !this.isVehicle() && !this.isPassenger() && this.isTamed() && !this.isBaby() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
    }
    public boolean canMate(Animal animal) {
        if (animal == this) {
            return false;
        } else if (!(animal instanceof Donkey) && !(animal instanceof Horse)) {
            return false;
        } else {
            return this.canBeParent() && ((WarmBloodEntity)animal).canBeParent();
        }
    }
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        WarmBloodFoalEntity foal;
        if (ageableMob instanceof Donkey) {
            foal = EntityTypes.WARMBLOOD_FOAL.get().create(serverLevel);
        } else {
            WarmBloodEntity warmBloodEntity = (WarmBloodEntity) ageableMob;
            foal = EntityTypes.WARMBLOOD_FOAL.get().create(serverLevel);
            int i = this.random.nextInt(9);

            if (i < 4) {
                this.getVariant();
            } else if (i < 8) {
                warmBloodEntity.getVariant();
            } else {
                Util.getRandom(Variant.values(), this.random);
            }
        }
        this.setOffspringAttributes(ageableMob, foal);
        return foal;
    }

    @Override
    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }
    public SlotAccess createEquipmentSlotAccess(final int p_149503_, final Predicate<ItemStack> p_149504_) {
        return new SlotAccess() {
            public ItemStack get() {
                return WarmBloodEntity.this.inventory.getItem(p_149503_);
            }

            public boolean set(ItemStack p_149528_) {
                if (!p_149504_.test(p_149528_)) {
                    return false;
                } else {
                    WarmBloodEntity.this.inventory.setItem(p_149503_, p_149528_);
                    WarmBloodEntity.this.updateContainerEquipment();
                    return true;
                }
            }
        };
    }

    //Saddleable
    @Override
    public SlotAccess getSlot(int p_149514_) {
        int i = p_149514_ - 400;
        if (i >= 0 && i < 2 && i < this.inventory.getContainerSize()) {
            if (i == 0) {
                return this.createEquipmentSlotAccess(i, (p_149518_) -> {
                    return p_149518_.isEmpty() || p_149518_.is(Items.SADDLE);
                });
            }

            if (i == 1) {
                if (!this.canWearArmor()) {
                    return SlotAccess.NULL;
                }

                return this.createEquipmentSlotAccess(i, (p_149516_) -> {
                    return p_149516_.isEmpty() || this.isArmor(p_149516_);
                });
            }
        }

        int j = p_149514_ - 500 + 2;
        return j >= 2 && j < this.inventory.getContainerSize() ? SlotAccess.forContainer(this.inventory, j) : super.getSlot(p_149514_);
    }
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (!this.isBaby()) {
            if (this.isTamed() && player.isSecondaryUseActive()) {
                this.openInventory(player);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }

            if (this.isVehicle()) {
                return super.mobInteract(player, interactionHand);
            }
        }

        if (!itemstack.isEmpty()) {
            if (this.isFood(itemstack)) {
                return this.fedFood(player, itemstack);
            }

            InteractionResult interactionresult = itemstack.interactLivingEntity(player, this, interactionHand);
            if (interactionresult.consumesAction()) {
                return interactionresult;
            }

            if (!this.isTamed()) {
                this.makeMad();
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }

            boolean flag = !this.isBaby() && !this.isSaddled() && itemstack.is(ModItems.PEQ_SADDLE.get());
            if (this.isArmor(itemstack) || flag) {
                this.openInventory(player);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }

        if (this.isBaby()) {
            return super.mobInteract(player, interactionHand);
        } else {
            this.doPlayerRide(player);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
    }
}