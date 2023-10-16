package com.phantomskeep.phantomeq.entity;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.config.PhantomEQCommonConfig;
import com.phantomskeep.phantomeq.entity.genetics.Species;
import com.phantomskeep.phantomeq.entity.util.EntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.levelgen.RandomSource;
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
import com.phantomskeep.phantomeq.entity.util.Util;

import javax.annotation.Nullable;

public class QuarterHorseEntity extends AbstractPhantHorse implements IAnimatable{
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    private static final ResourceLocation LOOT_TABLE = new ResourceLocation("minecraft", "entities/horse");

    public QuarterHorseEntity(EntityType<? extends QuarterHorseEntity> entityType, Level level) {
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
        return Species.QUARTER_HORSE;
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
        if (otherAnimal instanceof QuarterHorseEntity)

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
            foal = ModEntities.QUARTER_HORSE.get().create(level);
            return foal;
        } else {
            return null;
        }
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        QuarterHorseEntity quarterHorse = (QuarterHorseEntity) event.getAnimatable();
        if (quarterHorse.isBaby()) {
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
        QuarterHorseEntity quarterHorse = (QuarterHorseEntity) event.getAnimatable();
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