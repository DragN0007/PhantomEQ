package com.phantomskeep.phantomeq.event;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.PEQEntities;
import com.phantomskeep.phantomeq.entity.other.Mouse;
import com.phantomskeep.phantomeq.render.MouseRender;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = PhantomEQ.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public class PEQEvent {

    @SubscribeEvent
    public static void entityAttrbiuteCreationEvent(EntityAttributeCreationEvent event) {

        event.put(PEQEntities.MOUSE.get(), Mouse.createAttributes().build());


        //Spawn Placements
        SpawnPlacements.register
                (PEQEntities.MOUSE.get(),
                SpawnPlacements.Type.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mouse::checkAnimalSpawnRules);
    }

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {

        EntityRenderers.register(PEQEntities.MOUSE.get(), MouseRender::new);

    }
}




