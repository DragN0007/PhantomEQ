package com.phantomskeep.phantomeq.entity.util;

import com.phantomskeep.phantomeq.entity.QuarterHorseEntity;
import com.phantomskeep.phantomeq.entity.QuarterHorseFoalEntity;
import com.phantomskeep.phantomeq.entity.WarmbloodHorseEntity;
import com.phantomskeep.phantomeq.entity.WarmBloodFoalEntity;
import com.phantomskeep.phantomeq.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.phantomskeep.phantomeq.PhantomEQ.MODID;

public class EntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);

    public static final RegistryObject<EntityType<WarmbloodHorseEntity>> WARMBLOOD =
            ENTITY_TYPES.register("warmblood",
            () -> EntityType.Builder.of(WarmbloodHorseEntity::new, MobCategory.CREATURE)
                    .sized(1.5f,2.2f)
                    .build(new ResourceLocation(MODID,"warmblood").toString()));

    public static final RegistryObject<EntityType<WarmBloodFoalEntity>> WARMBLOOD_FOAL =
            ENTITY_TYPES.register("warmblood_foal",
            () -> EntityType.Builder.of(WarmBloodFoalEntity::new, MobCategory.CREATURE)
                    .sized(1f,1f)
                    .build(new ResourceLocation(MODID,"warmblood_foal").toString()));


    public static final RegistryObject<EntityType<QuarterHorseEntity>> QUARTERHORSE =
            ENTITY_TYPES.register("quarterhorse",
            () -> EntityType.Builder.of(QuarterHorseEntity::new, MobCategory.CREATURE)
                    .sized(1.5f,2f)
                    .build(new ResourceLocation(MODID,"quarterhorse").toString()));

    public static final RegistryObject<EntityType<QuarterHorseFoalEntity>> QUARTERHORSE_FOAL =
            ENTITY_TYPES.register("quarterhorse_foal",
            () -> EntityType.Builder.of(QuarterHorseFoalEntity::new, MobCategory.CREATURE)
                    .sized(1f,1f)
                    .build(new ResourceLocation(MODID,"quarterhorse_foal").toString()));


}


