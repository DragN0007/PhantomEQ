package com.phantomskeep.phantomeq.event;


import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)

public class ForgeEvent {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void registerBiomes(BiomeLoadingEvent event) {
        switch (event.getCategory()) {

            case MESA:
                break;

            case PLAINS:

                break;

            case SWAMP:
                break;

            case TAIGA:

                break;

            case EXTREME_HILLS:
                break;

            case BEACH:
                break;

            case FOREST:

                break;

            case RIVER:
                break;

            case DESERT:
                break;

            case SAVANNA:

                break;

            case JUNGLE:
                break;

            case MOUNTAIN:
                break;

            case ICY:
                break;

            case MUSHROOM:
                break;

            case UNDERGROUND:
                break;

            case OCEAN:
                break;

            case NETHER:
                break;
        }
    }
}