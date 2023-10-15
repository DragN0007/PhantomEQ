package com.phantomskeep.phantomeq.entity;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.genetics.Breed;
import com.phantomskeep.phantomeq.entity.genetics.IGeneticEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import software.bernie.geckolib3.core.IAnimatable;

import java.awt.*;
import java.util.ArrayList;

public abstract class AbstractPhantHorse extends AbstractHorse {

    protected static final EntityDataAccessor<Integer> AGE = SynchedEntityData.<Integer>defineId(AbstractPhantHorse.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> GENDER = SynchedEntityData.<Boolean>defineId(AbstractPhantHorse.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> FERTILE = SynchedEntityData.<Boolean>defineId(AbstractPhantHorse.class, EntityDataSerializers.BOOLEAN);


    public AbstractPhantHorse(EntityType<? extends AbstractHorse> entityType, Level level) {
        super(entityType, level);
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

    @Override
    void setBaby(boolean isBaby) {
        this.setAge(isBaby ? this.getBirthAge() : 0);
    }

    @Override
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
        List<AgeableMob> foals = new ArrayList<>();
        for (int i = 0; i < numFoals; ++i) {

            AgeableMob ageableentity = this.getBreedOffspring(world, mate);
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
        foals.add(ageableentity);

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

        private void spawnChild (AgeableMob child, ServerLevel world){
            child.setBaby(true);
            child.moveTo(this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
            world.addFreshEntity(child);
            // Spawn heart particles
            world.broadcastEntityEvent(this, (byte) 18);
        }
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
        }
    @Override
    public void tick() {
        super.tick();
        // Keep track of age
        if (!this.level().isClientSide) {
            // For children, align with growing age in case they have been fed
            if (this.age < 0) {
                this.trueAge = this.age;
            } else {
                this.trueAge = Math.max(0, Math.max(trueAge, trueAge + 1));
            }
            // Allow imprecision

            @Override
            protected void randomizeAttributes (RandomSource rand){
                // Set stats for vanilla-like breeding
                if (!PhantomEQCommonConfig.GENETICS.useGeneticStats.get()) {
                    float maxHealth = this.generateMaxHealth(rand::nextInt);
                    this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double) maxHealth);
                }
            }
        }
    }