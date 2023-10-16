package com.phantomskeep.phantomeq.entity;

import com.phantomskeep.phantomeq.config.PhantomEQCommonConfig;
import com.phantomskeep.phantomeq.entity.genetics.Breed;
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
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractPhantHorse extends AbstractChestedHorse {

    protected static final EntityDataAccessor<Integer> AGE = SynchedEntityData.defineId(AbstractPhantHorse.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> GENDER = SynchedEntityData.defineId(AbstractPhantHorse.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Boolean> FERTILE = SynchedEntityData.defineId(AbstractPhantHorse.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> PREGNANT_SINCE = SynchedEntityData.defineId(AbstractPhantHorse.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> OWNED_BY = SynchedEntityData.defineId(AbstractPhantHorse.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Byte> DATA_ID_FLAGS = SynchedEntityData.defineId(AbstractPhantHorse.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Optional<UUID>> DATA_ID_OWNER_UUID = SynchedEntityData.defineId(AbstractPhantHorse.class, EntityDataSerializers.OPTIONAL_UUID);
    protected int trueAge;

    protected List<AbstractPhantHorse> unbornChildren = new ArrayList<>();


    public AbstractPhantHorse(EntityType<? extends AbstractChestedHorse> entityType, Level level) {
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
        this.entityData.define(DATA_ID_FLAGS, (byte)0);
        this.entityData.define(DATA_ID_OWNER_UUID, Optional.empty());
    }


    //Ownership Stuff Here

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

        /** Inventory Handling */
        public ItemStack getArmor() {
            return this.getItemBySlot(EquipmentSlot.CHEST);
        }

    private void setArmor(ItemStack itemstack) {
        this.setItemSlot(EquipmentSlot.CHEST, itemstack);
        this.setDropChance(EquipmentSlot.CHEST, 0.0F);
    }


    @Override
    protected void updateContainerEquipment() {
        if (!this.level.isClientSide()) {
            super.updateContainerEquipment();
            this.setArmorStack(this.inventory.getItem(1));
            this.setDropChance(EquipmentSlot.CHEST, 0.0F);
        }
    }

    /* not in use yet */
    private void setArmorStack(ItemStack itemstack) {
        // this.func_213805_k(itemStack);
        this.setArmor(itemstack);

            }

    @Override
    public boolean canWearArmor() {
        return true;
    }

    @Override
    public boolean isArmor(ItemStack stack) {
        if (stack.getItem() instanceof BlockItem
                && ((BlockItem)stack.getItem()).getBlock() instanceof WoolCarpetBlock) {
            return true;
        }
        if (stack.getItem() instanceof HorseArmorItem) {
            HorseArmorItem armor = (HorseArmorItem)(stack.getItem());
            return armor.getProtection() == 0;
        }
        return false;
    }

    public void containerChanged(Container invBasic) {
        ItemStack itemstack = this.getArmor();
        super.containerChanged(invBasic);
        ItemStack itemstack1 = this.getArmor();
        if (this.tickCount > 20 && this.isArmor(itemstack1) && itemstack != itemstack1) {
            this.playSound(SoundEvents.HORSE_ARMOR, 0.5F, 1.0F);
        }
    }

    public SimpleContainer getHorseChest() {
        return this.inventory;
    }

    /** Taming */
    public boolean isTamed() {
        return this.getFlag(2);
    }

    @Nullable
    public UUID getOwnerUUID() {
        return this.entityData.get(DATA_ID_OWNER_UUID).orElse((UUID)null);
    }

    public void setOwnerUUID(@Nullable UUID p_30587_) {
        this.entityData.set(DATA_ID_OWNER_UUID, Optional.ofNullable(p_30587_));
    }
    public void setTamed(boolean p_30652_) {
        this.setFlag(2, p_30652_);
    }

    protected boolean getFlag(int p_30648_) {
        return (this.entityData.get(DATA_ID_FLAGS) & p_30648_) != 0;
    }

    protected void setFlag(int p_30598_, boolean p_30599_) {
        byte b0 = this.entityData.get(DATA_ID_FLAGS);
        if (p_30599_) {
            this.entityData.set(DATA_ID_FLAGS, (byte)(b0 | p_30598_));
        } else {
            this.entityData.set(DATA_ID_FLAGS, (byte)(b0 & ~p_30598_));
        }

    }


    /** Interactions */

    private boolean itemInteract(Player player, ItemStack itemstack, InteractionHand hand) {

        // Only allow taming with an empty hand
        if (!this.isTamed()) {
            this.makeMad();
            return true;
        }

        // If tame, equip saddle
        if (!this.isSaddled() && isSaddle(itemstack) && this.isSaddleable()) {
            if (!this.level.isClientSide) {
                ItemStack saddle = itemstack.split(1);
                this.inventory.setItem(0, saddle);
            }
            return true;
        }
        // If tame, equip armor
        if (this.isArmor(itemstack)) {
            if (this.inventory.getItem(1).isEmpty()) {
                if (!this.level.isClientSide) {
                    ItemStack armor = itemstack.split(1);
                    this.inventory.setItem(1, armor);
                }
            }
            else {
                this.openInventory(player);
            }
            return true;
        }
        // Nothing left
        return false;
    }

    public boolean isSaddle(ItemStack stack) {
        return stack.isEmpty() || stack.is(Items.SADDLE);
    }
    // Override to allow alternate saddles to be equipped
    @Override
    public SlotAccess getSlot(int slot) {
        int num = slot - 400;
        if (num == 0) {
            return new SlotAccess() {
                public ItemStack get() {
                    return AbstractPhantHorse.this.inventory.getItem(slot);
                }

                public boolean set(ItemStack stack) {
                    if (!isSaddle(stack)) {
                        return false;
                    } else {
                        AbstractPhantHorse.this.inventory.setItem(slot, stack);
                        AbstractPhantHorse.this.updateContainerEquipment();
                        return true;
                    }
                }
            };
        }
        return super.getSlot(slot);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!this.isBaby()) {
            if (this.isTamed() && player.isSecondaryUseActive()) {
                this.openInventory(player);
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }

        // Only interact items with horses that aren't being ridden by another player
        if (!itemstack.isEmpty() && !this.isVehicle()) {
            // Try to eat it
            if (this.isFood(itemstack)) {
                // Eat the item
                return this.fedFood(player, itemstack);
            }
            // See if the item interacts with us
            InteractionResult actionresulttype = itemstack.interactLivingEntity(player, this, hand);
            if (actionresulttype.consumesAction()) {
                return actionresulttype;
            }
            // See if we interact with the item
            if (itemInteract(player, itemstack, hand)) {
                return InteractionResult.sidedSuccess(this.level.isClientSide);
            }
        }

        if (!this.isBaby()) {
            this.doPlayerRide(player);
            return InteractionResult.sidedSuccess(this.level.isClientSide);
        }
        // else
        return super.mobInteract(player, hand);
    }


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