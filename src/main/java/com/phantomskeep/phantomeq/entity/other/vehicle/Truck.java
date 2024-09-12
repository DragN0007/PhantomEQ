package com.phantomskeep.phantomeq.entity.other.vehicle;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.item.PEQItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Truck extends Entity {
    private static final EntityDataAccessor<ResourceLocation> TEXTURE = (EntityDataAccessor<ResourceLocation>) SynchedEntityData.defineId(Truck.class, PhantomEQ.RESOURCE_SERIALIZER.get().getSerializer());
    private static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_white.png");

    private static final Map<DyeItem, ResourceLocation> COLOR_MAP = new HashMap<>() {{
        put(DyeItem.byColor(DyeColor.BLACK), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_black.png"));
        put(DyeItem.byColor(DyeColor.BLUE), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_blue.png"));
        put(DyeItem.byColor(DyeColor.BROWN), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_white.png"));
        put(DyeItem.byColor(DyeColor.CYAN), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_white.png"));
        put(DyeItem.byColor(DyeColor.GRAY), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_white.png"));
        put(DyeItem.byColor(DyeColor.LIGHT_BLUE), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_white.png"));
        put(DyeItem.byColor(DyeColor.LIGHT_GRAY), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_white.png"));
        put(DyeItem.byColor(DyeColor.LIME), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_white.png"));
        put(DyeItem.byColor(DyeColor.MAGENTA), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_white.png"));
        put(DyeItem.byColor(DyeColor.ORANGE), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_white.png"));
        put(DyeItem.byColor(DyeColor.PINK), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_pink.png"));
        put(DyeItem.byColor(DyeColor.PURPLE), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_purple.png"));
        put(DyeItem.byColor(DyeColor.RED), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_red.png"));
        put(DyeItem.byColor(DyeColor.WHITE), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_white.png"));
        put(DyeItem.byColor(DyeColor.GREEN), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_green.png"));
        put(DyeItem.byColor(DyeColor.YELLOW), new ResourceLocation(PhantomEQ.MODID, "textures/entities/truck_white.png"));
    }};

    private float yRotTick = 0;
    @Override
    public void tick() {
        super.tick();
    }

    public Truck(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }



    private static final EntityDataAccessor<Integer> DATA_ID_HURT = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_ID_HURTDIR = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(Boat.class, EntityDataSerializers.FLOAT);
    public void setDamage(float p_38312_) {
        this.entityData.set(DATA_ID_DAMAGE, p_38312_);
    }
    public float getDamage() {
        return this.entityData.get(DATA_ID_DAMAGE);
    }
    public void setHurtTime(int p_38355_) {
        this.entityData.set(DATA_ID_HURT, p_38355_);
    }
    public int getHurtTime() {
        return this.entityData.get(DATA_ID_HURT);
    }
    public void setHurtDir(int p_38363_) {
        this.entityData.set(DATA_ID_HURTDIR, p_38363_);
    }
    public int getHurtDir() {
        return this.entityData.get(DATA_ID_HURTDIR);
    }

    public boolean hurt(DamageSource p_38319_, float p_38320_) {
        if (this.isInvulnerableTo(p_38319_)) {
            return false;
        } else if (!this.level.isClientSide && !this.isRemoved()) {
            this.setHurtDir(-this.getHurtDir());
            this.setHurtTime(10);
            this.setDamage(this.getDamage() + p_38320_ * 10.0F);
            this.markHurt();
            this.gameEvent(GameEvent.ENTITY_DAMAGED, p_38319_.getEntity());
            boolean flag = p_38319_.getEntity() instanceof Player && ((Player)p_38319_.getEntity()).getAbilities().instabuild;
            if (flag || this.getDamage() > 40.0F) {
                if (!flag && this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                    this.spawnAtLocation(this.getDropItem());
                }

                this.discard();
            }

            return true;
        } else {
            return true;
        }
    }

    public void animateHurt() {
        this.setHurtDir(-this.getHurtDir());
        this.setHurtTime(10);
        this.setDamage(this.getDamage() * 11.0F);
    }

    public Item getDropItem() {
        return PEQItems.TRUCK_ITEM.get();
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }


    public double getPassengersRidingOffset() {
        return 1D;
    }
    @Override
    public void positionRider(Entity entity) {
        if(this.hasPassenger(entity)) {
            double rad = (Math.PI * this.getYRot()) / 180;

            double x = this.position().x + (0.5 * Math.sin(rad));
            double y = this.position().y + 0.4;
            double z = this.position().z + (1.7 * Math.cos(rad));

            entity.setPos(x, y, z);
        }
    }

    @Override
    @NotNull
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if(itemStack.getItem() instanceof DyeItem dyeItem) {
            this.level.playSound(player, this, SoundEvents.DYE_USE, SoundSource.PLAYERS, 1f, 1f);

            if(!this.level.isClientSide) {
                this.entityData.set(TEXTURE, COLOR_MAP.get(dyeItem));
                itemStack.shrink(1);
            }

            return InteractionResult.sidedSuccess(this.level.isClientSide);
        } else if(!this.level.isClientSide){
            return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
        return super.interact(player, hand);
    }

    public ResourceLocation getTextureLocation() {
        return this.entityData.get(TEXTURE);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(TEXTURE, DEFAULT_TEXTURE);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        String textureString = compoundTag.getString("Texture");
        ResourceLocation texture = ResourceLocation.tryParse(textureString);
        this.entityData.set(TEXTURE, texture == null ? DEFAULT_TEXTURE : texture);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putString("Texture", this.entityData.get(TEXTURE).toString());
    }

    @Override
    @NotNull
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
