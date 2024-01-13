package com.phantomskeep.phantomeq.entity;

import com.phantomskeep.phantomeq.config.PhantomEQCommonConfig;
import com.phantomskeep.phantomeq.entity.genetics.Species;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public abstract class AbstractPhantHorse extends AbstractHorse {

    protected static final EntityDataAccessor<Integer> AGE = SynchedEntityData.<Integer>defineId(AbstractPhantHorse.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> GENDER = SynchedEntityData.<Boolean>defineId(AbstractPhantHorse.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> FERTILE = SynchedEntityData.<Boolean>defineId(AbstractPhantHorse.class, EntityDataSerializers.BOOLEAN);


    public AbstractPhantHorse(EntityType<? extends AbstractHorse> entityType, Level level) {
        super(entityType, level);
    }

    public static final Ingredient BREEDING_ITEMS = Ingredient.of(Items.WHEAT);
    public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.WHEAT);

    public abstract Species getSpecies();

    public boolean canEquipChest() {
        return true;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2D));
        this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D, QuarterHorseEntity.class));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.addBehaviourGoals();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AGE, 0);
        this.entityData.define(GENDER, false);
        this.entityData.define(FERTILE, true);
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();

        if (isFood(itemstack)) {
            return super.mobInteract(player, hand);
        }
        if (this.isBreedingFood(itemstack)) {
            this.fedBreedingFood(player, itemstack);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean canMate(Animal pOtherAnimal) {
        if (pOtherAnimal == this) {
            return false;
        } else if (pOtherAnimal.getClass() != this.getClass()) {
            return false;
        } else {
            return this.isInLove() && pOtherAnimal.isInLove();
        }
    }
    public void setBaby(boolean isBaby) {
        this.setAge(isBaby ? this.getBirthAge() : 0);
    }


    public boolean isMale() {
        return (Boolean) this.entityData.get(GENDER);
    }


    public void setMale(boolean gender) {
        this.entityData.set(GENDER, gender);
    }
    public boolean isFertile() {
        return (Boolean) this.entityData.get(FERTILE);
    }
    public int getBirthAge() {
        return PhantomEQCommonConfig.getHorseBirthAge();
    }

    public int getRebreedTicks() {
        return PhantomEQCommonConfig.getHorseRebreedTicks(this.isMale());
    }

    public boolean isBreedingFood(ItemStack pStack) {
        return BREEDING_ITEMS.test(pStack);
    }
    public InteractionResult fedBreedingFood(Player pPlayer, ItemStack pStack) {
        boolean flag = this.handleEatingBreedingFood(pPlayer, pStack);
        if (!pPlayer.getAbilities().instabuild) {
            if (pStack.getItem() == Items.WHEAT)
                pStack.shrink(1);
        }

        if (this.level.isClientSide) {
            return InteractionResult.CONSUME;
        } else {
            return flag ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }
    }

    public boolean handleEatingBreedingFood(Player pPlayer, ItemStack pStack) {
        boolean flag = false;
        float f = 0.0F;
        int i = 0;
        int j = 0;
        Item item = pStack.getItem();
        if (item == Items.WHEAT) {
            if (!this.level.isClientSide  && this.getAge() == 0 && !this.isInLove()) {
                flag = true;
                this.setInLove(pPlayer);
            }
        }

        if (this.isBaby()) {
            this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), 0.0D, 0.0D, 0.0D);
            if (!this.level.isClientSide) {
                this.ageUp(i);
            }
            flag = true;
        }
        return flag;
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelAccessor, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        return super.finalizeSpawn(levelAccessor, instance, spawnType, groupData, tag);
    }

   /* @Override
    public void spawnChildFromBreeding(ServerLevel world, Animal mate) {
        // If vanilla mate, handle the vanilla way
        if (!(mate instanceof IGeneticEntity)) {
            super.spawnChildFromBreeding(world, mate);
            return;
        }
        // If two animals go for the same mate at the same time this function
        // can be called twice. This check makes sure it only works the first time.
        if (!(this.isInLove() && mate.isInLove())) {
            return;
        }

        IGeneticEntity geneticMate = (IGeneticEntity) mate;

        // Call this on the female's side, if possible
        if (this.isMale() && !geneticMate.isMale()) {
            mate.spawnChildFromBreeding(world, this);
            return;
        }

        int numFoals = this.getTwinChance();
        List foals = new List();
        AgeableMob ageableentity = null;
        for (int i = 0; i < numFoals; ++i) {

            ageableentity = this.getBreedOffspring(world, mate);
            // If ageableentity is null, leave this and the mate in love mode to try again
            // Note posting an event with a null child could cause crashes with other
            // mods that do not check their input carefully, so we don't do that
            if (ageableentity == null) {
                continue;
            }
            final BabyEntitySpawnEvent event = new BabyEntitySpawnEvent(this, mate, ageableentity);
            final boolean cancelled = MinecraftForge.EVENT_BUS.post(event);
            ageableentity = event.getChild();
            // Don't spawn if cancelled or a null entity
            if (cancelled || ageableentity == null) {
                continue;
            }
            spawnChild(ageableentity, world);
        }
        foals.add(String.valueOf(ageableentity));

        // Reset love state
        this.setAge(this.getRebreedTicks());
        mate.setAge(geneticMate.getRebreedTicks());
        this.resetLove();
        mate.resetLove();

        // Spawn XP orbs
        if (world.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            int xp = this.getRandom().nextInt(7) + 1;
            world.addFreshEntity(new ExperienceOrb(world, this.getX(), this.getY(), this.getZ(), xp));
        }
    }

    private void spawnChild(AgeableMob child, ServerLevel world) {
        child.setBaby(true);
        child.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
        world.addFreshEntity(child);
        // Spawn heart particles
        world.broadcastEntityEvent(this, (byte) 18);
    }

    abstract AbstractHorse getChild(ServerLevel world, AgeableMob otherparent);

    protected int getTwinChance() {
        double chance = 0.0001;
        int twinChance = 1;
        if (getRandom().nextDouble() < chance) {
            twinChance += 1;
        }
        return twinChance;
    }

    public boolean isOppositeGender(AbstractPhantHorse other) {
        return this.isMale() != other.isMale();
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob ageable) {
        if (!(ageable instanceof Animal)) {
            return null;
        }
        // Have the female create the child if possible
        if (this.isMale()
                && ageable instanceof AbstractPhantHorse
                && !((AbstractPhantHorse) ageable).isMale()) {
            return ageable.getBreedOffspring(world, this);
        }
        AbstractHorse child = this.getChild(world, ageable);
        if (child != null) {
            this.setOffspringAttributes(ageable, child);
        }
        if (child instanceof AbstractPhantHorse) {
            AbstractPhantHorse foal = (AbstractPhantHorse) child;
            foal.setMale(this.random.nextBoolean());
            foal.setAge(PhantomEQCommonConfig.GROWTH.getMinAge());
        }
        return child;
    }*/
}