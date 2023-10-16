package com.phantomskeep.phantomeq.entity;

import com.phantomskeep.phantomeq.config.PhantomEQCommonConfig;
import com.phantomskeep.phantomeq.entity.genetics.IGeneticEntity;
import com.phantomskeep.phantomeq.entity.genetics.Species;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPhantHorse extends AbstractHorse {

    protected static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(AbstractPhantHorse.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> GENDER = SynchedEntityData.defineId(AbstractPhantHorse.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> FERTILE = SynchedEntityData.defineId(AbstractPhantHorse.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> PREGNANT_SINCE = SynchedEntityData.defineId(AbstractPhantHorse.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> OWNED_BY = SynchedEntityData.defineId(AbstractPhantHorse.class, EntityDataSerializers.STRING);
    protected int trueAge;

    protected List<AbstractPhantHorse> unbornChildren = new ArrayList<>();


    public AbstractPhantHorse(EntityType<? extends AbstractHorse> entityType, Level level) {
        super(entityType, level);
    }

    public static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.WHEAT);

    public boolean canEquipChest() {
        return true;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, TEMPTATION_ITEMS, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 0.8D));
        this.goalSelector.addGoal(5, new EatBlockGoal(this));
        this.goalSelector.addGoal(6, new FloatGoal(this));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.addBehaviourGoals();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AGE, 0);
        this.entityData.define(GENDER, false);
        this.entityData.define(FERTILE, true);
        this.entityData.define(PREGNANT_SINCE, -1);
        this.entityData.define(OWNED_BY, "");
    }

    //Ownership Stuff Here
    public String getOwnedBy() {
        return (String)this.entityData.get(OWNED_BY);
    }
    public void setOwnedBy(String ownedBy) {
        this.entityData.set(OWNED_BY, ownedBy);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        writeSubclassData(compound);
        this.setTemper(compound.getInt("Temper"));
        this.setTamed(compound.getBoolean("Tame"));

        if (!this.inventory.getItem(1).isEmpty()) {
            compound.put("ArmorItem", this.inventory.getItem(1).save(new CompoundTag()));
        }
    }

    private void writeSubclassData(CompoundTag compound) {
        compound.putInt("true_age", this.trueAge);
        compound.putBoolean("gender", this.isMale());
        compound.putBoolean("fertile", this.isFertile());
        compound.putInt("pregnant_since", this.getPregnancyStart());
        if (this.unbornChildren != null) {
            ListTag unbornChildrenTag = new ListTag();
            for (AbstractPhantHorse child : this.unbornChildren) {
                CompoundTag childNBT = new CompoundTag();
                unbornChildrenTag.add(childNBT);
            }
            compound.put("unborn_children", unbornChildrenTag);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        // Read the main part of the data
        readSubclassData(compound);
        // Ensure the true age matches the age
        if (this.trueAge < 0 != this.age < 0) {
            this.trueAge = this.age;
        }

        this.updateContainerEquipment();

        if (isMale()) {
            setFertile(true);
        }

        if (compound.contains("ArmorItem", 10)) {
            ItemStack itemstack = ItemStack.of(compound.getCompound("ArmorItem"));
            if (!itemstack.isEmpty() && this.isArmor(itemstack)) {
                this.inventory.setItem(1, itemstack);
            }
        }
        this.updateContainerEquipment();
    }


    private void readSubclassData(CompoundTag compound) {
        if (compound.contains("true_age")) {
            this.trueAge = compound.getInt("true_age");
        }
        if (compound.contains("gender")) {
            this.setMale(compound.getBoolean("gender"));
        } else {
            this.setMale(this.random.nextBoolean());
        }
        if (compound.contains("fertile")) {
            this.setFertile(compound.getBoolean("fertile"));
        }

        int pregnantSince = -1;
        if (compound.contains("pregnant_since")) {
            pregnantSince = compound.getInt("pregnant_since");
        }

        this.entityData.set(PREGNANT_SINCE, pregnantSince);
        if (compound.contains("unborn_children")) {
            Tag nbt = compound.get("unborn_children");
            if (nbt instanceof ListTag foalListTag) {
                for (Tag fnbt : foalListTag) {
                    if (!(fnbt instanceof CompoundTag foalNBT)) {
                        continue;
                    }
                    Species species = Species.valueOf(foalNBT.getString("species"));
                    AbstractPhantHorse foal = switch (species) {
                        case QUARTER_HORSE -> ModEntities.QUARTER_HORSE.get().create(level);
                        case WARMBLOOD -> ModEntities.WARMBLOOD_HORSE.get().create(level);
                    };
                    if (foal != null) {
                        this.unbornChildren.add(foal);
                    }
                }
            }
        }

    }


    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
    }

    //Interactions

    /*  @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!this.isBaby()) {
            if (this.isTamed() && player.isSecondaryUseActive()) {
                this.openInventory(player);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }
    } */


    //Pregnancy, Gender, Etc.

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
        return this.entityData.get(GENDER);
    }


    public void setMale(boolean gender) {
        this.entityData.set(GENDER, gender);
    }

    public boolean isFertile() {
        return this.entityData.get(FERTILE);
    }

    public void setFertile(boolean fertile) {
        if (isPregnant()) {
            fertile = true;
        }
        this.entityData.set(FERTILE, fertile);
    }

    @Override
    protected boolean canParent() {
        return super.canParent() && isFertile();
    }

    @Override
    public void setInLove(Player loveCause) {
        if (!isFertile()) {
            return;
        }
        super.setInLove(loveCause);
    }

    public boolean isPregnant() {
        return this.getPregnancyStart() >= 0;
    }

    public int getPregnancyStart() {
        return this.entityData.get(PREGNANT_SINCE);
    }

    public float getPregnancyProgress() {
        int passed = getAge() - getPregnancyStart();
        int total = PhantomEQCommonConfig.getHorsePregnancyLength();
        return (float) passed / (float) total;
    }

    public int getBirthAge() {
        return PhantomEQCommonConfig.getHorseBirthAge();
    }

    public int getRebreedTicks() {
        return PhantomEQCommonConfig.getHorseRebreedTicks(this.isMale());
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
            if (PhantomEQCommonConfig.isPregnancyEnabled()) {
                if (this.setPregnantWith(ageableentity, mate)) {
                    ageableentity = null;
                }
            } else {
                spawnChild(ageableentity, world);
            }
            foals.add(ageableentity);
        }
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
        if (child instanceof AbstractPhantHorse foal) {
            foal.setMale(this.random.nextBoolean());
            foal.setAge(PhantomEQCommonConfig.GROWTH.getMinAge());
        }
        return child;
    }

    public boolean setPregnantWith(AgeableMob child, AgeableMob otherParent) {
        if (otherParent instanceof IGeneticEntity) {
            IGeneticEntity otherGenetic = (IGeneticEntity)otherParent;
            if (this.isMale() == otherGenetic.isMale()) {
                return false;
            }
            else if (this.isMale() && !otherGenetic.isMale()) {
                return otherGenetic.setPregnantWith(child, this);
            }
        }
        if (this.isMale()) {
            return false;
        }

        if (child instanceof AbstractPhantHorse) {
            unbornChildren.add((AbstractPhantHorse) child);
            if (!this.level.isClientSide) {
                // Can't be a child
                this.trueAge = Math.max(0, this.trueAge);
                this.entityData.set(PREGNANT_SINCE, this.trueAge);
            }
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        // Keep track of age
        if (!this.level.isClientSide) {
            // For children, align with growing age in case they have been fed
            if (this.age < 0) {
                this.trueAge = this.age;
            } else {
                this.trueAge = Math.max(0, Math.max(trueAge, trueAge + 1));
            }
            // Allow imprecision
            final int c = 400;
            if (this.trueAge / c != this.getAge() / c
                    || (this.trueAge < 0 != this.getAge() < 0)) {
                this.setAge(this.trueAge);
            }
        }

        // Pregnancy
        if (!this.level.isClientSide && this.isPregnant()) {
            // Check pregnancy
            if (this.unbornChildren == null
                    || this.unbornChildren.size() == 0) {
                this.entityData.set(PREGNANT_SINCE, -1);
            }
            // Handle birth
            int totalLength = PhantomEQCommonConfig.getHorsePregnancyLength();
            int currentLength = this.trueAge - this.getPregnancyStart();
            if (currentLength >= totalLength) {
                for (AbstractPhantHorse child : unbornChildren) {
                    if (this.level instanceof ServerLevel) {
                        this.spawnChild(child, (ServerLevel) this.level);
                    }
                }
                this.unbornChildren = new ArrayList<>();
                this.entityData.set(PREGNANT_SINCE, -1);
            }
        }
    }

    public void aiStep() {
        if (this.unbornChildren != null && this.unbornChildren.size() > 0
                && this.getPregnancyStart() < 0) {
            this.entityData.set(PREGNANT_SINCE, 0);
        }
        super.aiStep();
    }

    public SpawnGroupData finalizeSpawn (@NotNull ServerLevelAccessor levelAccessor, @NotNull DifficultyInstance
        instance, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag){
            return super.finalizeSpawn(levelAccessor, instance, spawnType, groupData, tag);
        }
    }