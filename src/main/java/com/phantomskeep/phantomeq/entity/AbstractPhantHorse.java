package com.phantomskeep.phantomeq.entity;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.genetics.Breed;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;

import java.awt.*;

public class AbstractPhantHorse extends AbstractHorse {

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
}