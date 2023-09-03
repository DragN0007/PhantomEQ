package com.phantomskeep.phantomeq.event;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.WarmBloodEntity;
import com.phantomskeep.phantomeq.entity.WarmBloodFoalEntity;
import com.phantomskeep.phantomeq.entity.util.EntityTypes;
import com.phantomskeep.phantomeq.render.WarmBloodFoalRender;
import com.phantomskeep.phantomeq.render.WarmBloodRender;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = PhantomEQ.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class PhantomEQEvent {
    @SubscribeEvent
    public static void entityAttrbiuteCreationEvent(EntityAttributeCreationEvent event) {

        event.put(EntityTypes.WARMBLOOD.get(), WarmBloodEntity.createBaseHorseAttributes().build());

        SpawnPlacements.register
                (EntityTypes.WARMBLOOD.get(),
                        SpawnPlacements.Type.ON_GROUND,
                        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WarmBloodEntity::checkAnimalSpawnRules);

        event.put(EntityTypes.WARMBLOOD_FOAL.get(), WarmBloodFoalEntity.createBaseHorseAttributes().build());

        SpawnPlacements.register
                (EntityTypes.WARMBLOOD_FOAL.get(),
                        SpawnPlacements.Type.ON_GROUND,
                        Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WarmBloodFoalEntity::checkAnimalSpawnRules);


    }

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {

        EntityRenderers.register(EntityTypes.WARMBLOOD.get(), WarmBloodRender::new);
        EntityRenderers.register(EntityTypes.WARMBLOOD_FOAL.get(), WarmBloodFoalRender::new);


    }
}




