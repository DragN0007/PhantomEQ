package com.phantomskeep.phantomeq.entity.horse.warmblood;

import com.phantomskeep.phantomeq.entity.AbstractPhantHorse;
import com.phantomskeep.phantomeq.entity.PEQEntities;
import com.phantomskeep.phantomeq.entity.ai.HorseEatGrassGoal;
import com.phantomskeep.phantomeq.entity.ai.HorseEatHayGoal;
import com.phantomskeep.phantomeq.entity.ai.HorseFollowHerdLeaderGoal;
import com.phantomskeep.phantomeq.entity.genetics.Species;
import com.phantomskeep.phantomeq.entity.horse.warmblood.layer.WarmbloodDetailLayer;
import com.phantomskeep.phantomeq.entity.horse.warmblood.layer.WarmbloodPatternLayer;
import com.phantomskeep.phantomeq.entity.other.vehicle.EntityTypes;
import com.phantomskeep.phantomeq.item.PEQItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
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
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;


public class Warmblood extends AbstractPhantHorse implements IAnimatable {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private static final ResourceLocation LOOT_TABLE = new ResourceLocation("minecraft", "entities/horse");

    @Override
    public @NotNull ResourceLocation getDefaultLootTable() {
        return LOOT_TABLE;
    }

    private int animTimer = 0;
    private int idleAnimCooldown = 0;
    public Warmblood(EntityType<? extends AbstractHorse> entityType, Level level) {
        super(entityType, level);
        this.noCulling = true;
    }

    public Species getSpecies() {
        return Species.WARMBLOOD;
    }

    @Override
    public void tick() {
        if (this.level.isClientSide) {
            animTimer = Math.max(animTimer - 1, 0);
            idleAnimCooldown = Math.max(idleAnimCooldown - 1, 0);
        }
        super.tick();

        if (this.isSaddled() && !this.isVehicle() || this.isLeashed()) {
            this.getNavigation().stop();
        }

        if (this.hasFollowers() && this.level.random.nextInt(200) == 1) {
            List<? extends AbstractPhantHorse> list = this.level.getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
            if (list.size() <= 1) {
                this.herdSize = 1;
            }
        }
    }

    public static AttributeSupplier.Builder createBaseHorseAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.JUMP_STRENGTH)
                .add(Attributes.MAX_HEALTH, 53.0D)
                .add(Attributes.MOVEMENT_SPEED, (double)0.235F);
    }

    protected void randomizeAttributes() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double)this.generateRandomMaxHealth());
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(this.generateRandomSpeed());
        this.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(this.generateRandomJumpStrength());
    }

    public float generateRandomMaxHealth() {
        return 15.0F + (float)this.random.nextInt(8) + (float)this.random.nextInt(9);
    }

    public double generateRandomJumpStrength() {
        return (double)0.4F + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D + this.random.nextDouble() * 0.2D;
    }

    public double generateRandomSpeed() {
        return ((double)0.45F + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D + this.random.nextDouble() * 0.3D) * 0.25D;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.4, true));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 0.0F));

        this.goalSelector.addGoal(3, new HorseFollowHerdLeaderGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D, AbstractPhantHorse.class));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.horseEatGrassGoal = new HorseEatGrassGoal(this);
        this.goalSelector.addGoal(5, this.horseEatGrassGoal);

        this.horseEatHayGoal = new HorseEatHayGoal(this);
        this.goalSelector.addGoal(5, this.horseEatHayGoal);
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
        this.addBehaviourGoals();
    }

    @Override
    public boolean canBeRiddenInWater(Entity rider) {
        return false;
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

    private int iAmEating;
    private HorseEatGrassGoal horseEatGrassGoal;
    private HorseEatHayGoal horseEatHayGoal;
    protected void customServerAiStep() {
        this.iAmEating = this.horseEatGrassGoal.getEatAnimationTick();
        this.iAmEating = this.horseEatHayGoal.getEatAnimationTick();
        super.customServerAiStep();
    }

    public void aiStep() {
        if (this.level.isClientSide) {
            this.iAmEating = Math.max(1, this.iAmEating - 1);
        }

        super.aiStep();
    }

    private <E extends IAnimatable>PlayState predicate(AnimationEvent<E> event) {

        if (event.isMoving()) {
            if (isVehicle()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.horse.run", ILoopType.EDefaultLoopTypes.LOOP));
            } else
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.horse.slow_walk", ILoopType.EDefaultLoopTypes.LOOP));

        } else
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.horse.new", ILoopType.EDefaultLoopTypes.LOOP));


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

    @Override
    public void positionRider(Entity entity) {
        if (this.hasPassenger(entity)) {
            double offsetX;
            double offsetY;
            double offsetZ;

            if (this.isSaddled()) {
                offsetX = 0;
                offsetY = 1.3;
                offsetZ = -0.1;
            } else {
                offsetX = 0;
                offsetY = 1.1;
                offsetZ = -0.2;
            }

            double radYaw = Math.toRadians(this.getYRot());

            double offsetXRotated = offsetX * Math.cos(radYaw) - offsetZ * Math.sin(radYaw);
            double offsetYRotated = offsetY;
            double offsetZRotated = offsetX * Math.sin(radYaw) + offsetZ * Math.cos(radYaw);

            double x = this.getX() + offsetXRotated;
            double y = this.getY() + offsetYRotated;
            double z = this.getZ() + offsetZRotated;

            entity.setPos(x, y, z);
        }
    }

    public Vec3 getLeashOffset() {
        return new Vec3(0.0D, (double)(1F * this.getEyeHeight()), (double)(this.getBbWidth() * 1F));
    }

    //Generates variant textures
    public ResourceLocation getTextureLocation() {
        return WarmbloodModel.Variant.variantFromOrdinal(getVariant()).resourceLocation;
    }

    public ResourceLocation getPatternLocation() {
        return WarmbloodPatternLayer.PatternOverlay.patternOverlayFromOrdinal(getPattern()).resourceLocation;
    }

    public ResourceLocation getDetailLocation() {
        return WarmbloodDetailLayer.DetailOverlay.detailOverlayFromOrdinal(getDetail()).resourceLocation;
    }

    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Warmblood.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PATTERN = SynchedEntityData.defineId(Warmblood.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DETAIL = SynchedEntityData.defineId(Warmblood.class, EntityDataSerializers.INT);

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }
    public int getPattern() {
        return this.entityData.get(PATTERN);
    }
    public int getDetail() {
        return this.entityData.get(DETAIL);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }
    public void setPattern(int patternVariant) {
        this.entityData.set(PATTERN, patternVariant);
    }
    public void setDetail(int detailVariant) {
        this.entityData.set(DETAIL, detailVariant);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        if(compoundNBT.contains("Variant")) {
            setVariant(compoundNBT.getInt("Variant"));
        }
    }

    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_GENDER =
            SynchedEntityData.defineId(Warmblood.class, EntityDataSerializers.INT);

    @Override
    public void addAdditionalSaveData(CompoundTag compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("Variant", getVariant());
        this.setEating(compoundNBT.getBoolean("EatingHaystack"));
        this.setBred(compoundNBT.getBoolean("Breed"));
        this.setTemper(compoundNBT.getInt("Temper"));
        this.setTamed(compoundNBT.getBoolean("Tame"));
        UUID uuid;
        if (compoundNBT.hasUUID("Owner")) {
            uuid = compoundNBT.getUUID("Owner");
        } else {
            String s = compoundNBT.getString("Owner");
            uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
        }

        if (uuid != null) {
            this.setOwnerUUID(uuid);
        }

        if (compoundNBT.contains("PEQSaddleItem", 10)) {
            ItemStack itemstack = ItemStack.of(compoundNBT.getCompound("PEQSaddleItem"));
            if (itemstack.is(PEQItems.PEQ_SADDLE.get())) {
                this.inventory.setItem(0, itemstack);
            }
        }

        this.updateContainerEquipment();
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
        } else if (!(animal instanceof Donkey) && !(animal instanceof Warmblood)) {
            return false;
        } else {
            return this.canBeParent() && ((Warmblood)animal).canBeParent();
        }
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        AbstractHorse abstracthorse;
        if (ageableMob instanceof Donkey) {
            abstracthorse = EntityType.MULE.create(serverLevel);
        } else {
            Warmblood horse = (Warmblood) ageableMob;
            abstracthorse = PEQEntities.WARMBLOOD_HORSE.get().create(serverLevel);

            int i = this.random.nextInt(9);
            int variant;
            if (i < 4) {
                variant = this.getVariant();
            } else if (i < 8) {
                variant = horse.getVariant();
            } else {
                variant = this.random.nextInt(WarmbloodModel.Variant.values().length);
            }

            int j = this.random.nextInt(5);
            int pattern;
            if (j < 2) {
                pattern = this.getPattern();
            } else if (j < 4) {
                pattern = horse.getPattern();
            } else {
                pattern = this.random.nextInt(WarmbloodPatternLayer.PatternOverlay.values().length);
            }

            int k = this.random.nextInt(5);
            int detail;
            if (k < 2) {
                detail = this.getDetail();
            } else if (k < 4) {
                detail = horse.getDetail();
            } else {
                detail = this.random.nextInt(WarmbloodDetailLayer.DetailOverlay.values().length);
            }

            ((Warmblood) abstracthorse).setVariant(variant);
            ((Warmblood) abstracthorse).setPattern(pattern);
            ((Warmblood) abstracthorse).setPattern(detail);
        }

        this.setOffspringAttributes(ageableMob, abstracthorse);
        return abstracthorse;
    }

   /* @Nullable
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        WarmbloodHorseEntity foal;
        if (ageableMob instanceof Donkey) {
            foal = EntityTypes.WARMBLOOD_FOAL.get().create(serverLevel);
        } else {
            WarmbloodHorseEntity warmbloodHorseEntity = (WarmbloodHorseEntity) ageableMob;
            foal = EntityTypes.WARMBLOOD_FOAL.get().create(serverLevel);
            int i = this.random.nextInt(9);

            if (i < 4) {
                this.getVariant();
            } else if (i < 8) {
                warmbloodHorseEntity.getVariant();
            } else {
                Util.getRandom(Variant.values(), this.random);
            }
        }
        this.setOffspringAttributes(ageableMob, foal);
        return foal;
    }*/

    @Override
    protected void defineSynchedData(){
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }
    public SlotAccess createEquipmentSlotAccess(final int p_149503_, final Predicate<ItemStack> p_149504_) {
        return new SlotAccess() {
            public ItemStack get() {
                return Warmblood.this.inventory.getItem(p_149503_);
            }

            public boolean set(ItemStack p_149528_) {
                if (!p_149504_.test(p_149528_)) {
                    return false;
                } else {
                    Warmblood.this.inventory.setItem(p_149503_, p_149528_);
                    Warmblood.this.updateContainerEquipment();
                    return true;
                }
            }
        };
    }

    //Saddleable
    @Override
    public void equipSaddle(@Nullable SoundSource p_30546_) {
        this.inventory.setItem(0, new ItemStack(PEQItems.PEQ_SADDLE.get()));
        if (p_30546_ != null) {
            this.level.playSound((Player)null, this, SoundEvents.HORSE_SADDLE, p_30546_, 0.5F, 1.0F);
        }

    }

    @Override
    public SlotAccess getSlot(int p_149514_) {
        int i = p_149514_ - 400;
        if (i >= 0 && i < 2 && i < this.inventory.getContainerSize()) {
            if (i == 0) {
                return this.createEquipmentSlotAccess(i, (p_149518_) -> p_149518_.isEmpty() || p_149518_.is(PEQItems.PEQ_SADDLE.get()));
            }

            if (i == 1) {
                if (!this.canWearArmor()) {
                    return SlotAccess.NULL;
                }

                return this.createEquipmentSlotAccess(i, (p_149516_) -> p_149516_.isEmpty() || this.isArmor(p_149516_));
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

            boolean flag = !this.isBaby() && !this.isSaddled() && itemstack.is(PEQItems.PEQ_SADDLE.get());
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