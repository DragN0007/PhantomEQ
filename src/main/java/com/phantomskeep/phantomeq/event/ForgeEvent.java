package com.phantomskeep.phantomeq.event;


import com.phantomskeep.phantomeq.entity.util.EntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
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
                event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityTypes.WARMBLOOD.get(), 4, 2, 4));

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