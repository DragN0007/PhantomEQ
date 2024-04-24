package com.phantomskeep.phantomeq.entity.other.vehicle;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.QuarterHorseEntity;
import com.phantomskeep.phantomeq.entity.WarmbloodHorseEntity;
import com.phantomskeep.phantomeq.entity.other.vehicle.Truck;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.phantomskeep.phantomeq.PhantomEQ.MODID;

public class EntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);

    public static final RegistryObject<EntityType<Truck>> TRUCK = ENTITY_TYPES.register("truck",
            () -> EntityType.Builder.of(Truck::new, MobCategory.MISC).sized(2.5f, 2.5f).build(new ResourceLocation(MODID, "truck").toString()));
}


