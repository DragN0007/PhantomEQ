package com.phantomskeep.phantomeq.event;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.PEQEntities;
import com.phantomskeep.phantomeq.entity.other.Mouse;
import com.phantomskeep.phantomeq.entity.other.MouseRender;
import com.phantomskeep.phantomeq.entity.other.vehicle.EntityTypes;
import com.phantomskeep.phantomeq.entity.other.vehicle.TruckModel;
import com.phantomskeep.phantomeq.entity.other.vehicle.TruckRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
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

    @SubscribeEvent
    public static void registerLayerDefinitionEvent(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(TruckRenderer.LAYER_LOCATION, TruckModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRendererEvent(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityTypes.TRUCK.get(), TruckRenderer::new);
    }
}




