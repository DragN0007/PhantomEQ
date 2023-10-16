package com.phantomskeep.phantomeq.entity;

import com.phantomskeep.phantomeq.entity.ai.HorseEatGrassGoal;
import com.phantomskeep.phantomeq.entity.ai.HorseEatHayGoal;
import com.phantomskeep.phantomeq.entity.genetics.Species;
import com.phantomskeep.phantomeq.entity.util.Util;
import com.phantomskeep.phantomeq.item.ModItems;
import com.phantomskeep.phantomeq.model.WarmbloodHorseModel;
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
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;


public class WarmbloodHorseEntity extends AbstractPhantHorse implements IAnimatable {

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private static final ResourceLocation LOOT_TABLE = new ResourceLocation("minecraft", "entities/horse");

    public WarmbloodHorseEntity(EntityType<? extends WarmbloodHorseEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull ResourceLocation getDefaultLootTable() {
        return LOOT_TABLE;
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

    protected SoundEvent getHurtSound(@NotNull DamageSource p_30720_) {
        super.getHurtSound(p_30720_);
        return SoundEvents.HORSE_HURT;
    }

    protected SoundEvent getAngrySound() {
        super.getAngrySound();
        return SoundEvents.HORSE_ANGRY;
    }
    public Species getSpecies() {
        return Species.WARMBLOOD;
    }

    @Override
    public boolean canMate(Animal otherAnimal)
    {
        if (otherAnimal == this)
        {
            return false;
        }
        if (otherAnimal instanceof AbstractPhantHorse) {
            if (!this.isOppositeGender((AbstractPhantHorse) otherAnimal)) {
                return false;
            }
        }
        if (otherAnimal instanceof WarmbloodHorseEntity)

        {
            return this.canParent() && Util.horseCanMate((AbstractHorse)otherAnimal);
        }
        else
        {
            return false;
        }
    }

    // Helper function for createChild that creates and spawns an entity of the
    // correct species
    @Override
    public AbstractHorse getChild(ServerLevel world, AgeableMob ageable) {
        if (ageable instanceof AbstractPhantHorse) {
            AbstractPhantHorse foal;
            foal = ModEntities.WARMBLOOD_HORSE.get().create(level);
            return foal;
        } else {
            return null;
        }
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        WarmbloodHorseEntity warmbloodHorse = (WarmbloodHorseEntity) event.getAnimatable();
        if (warmbloodHorse.isBaby()) {
            return this.foalPredicate(event);
        } else {
            if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("slow_walk", ILoopType.EDefaultLoopTypes.LOOP));
            } else
                event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP));

            return PlayState.CONTINUE;
        }
    }

    public <E extends IAnimatable> PlayState foalPredicate(AnimationEvent<E> event) {
        WarmbloodHorseEntity warmbloodHorse = (WarmbloodHorseEntity) event.getAnimatable();
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("slow_walk", ILoopType.EDefaultLoopTypes.LOOP));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle"));
        }
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
}