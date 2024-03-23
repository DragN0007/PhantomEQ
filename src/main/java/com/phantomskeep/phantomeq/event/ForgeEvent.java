package com.phantomskeep.phantomeq.event;


import com.phantomskeep.phantomeq.entity.PEQEntities;
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
                event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(PEQEntities.MOUSE.get(), 6, 1, 3));
                break;

            case SWAMP:
                break;

            case TAIGA:
                event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(PEQEntities.MOUSE.get(), 6, 1, 3));
                break;

            case EXTREME_HILLS:
                event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(PEQEntities.MOUSE.get(), 6, 1, 3));
                break;

            case BEACH:
                break;

            case FOREST:
                event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(PEQEntities.MOUSE.get(), 6, 1, 3));
                break;

            case RIVER:
                break;

            case DESERT:
                event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(PEQEntities.MOUSE.get(), 6, 1, 3));
                break;

            case SAVANNA:
                event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(PEQEntities.MOUSE.get(), 6, 1, 3));
                break;

            case JUNGLE:
                event.getSpawns().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(PEQEntities.MOUSE.get(), 6, 1, 3));
                break;

            case OCEAN:
                break;

            case NETHER:
                break;
        }
    }
}