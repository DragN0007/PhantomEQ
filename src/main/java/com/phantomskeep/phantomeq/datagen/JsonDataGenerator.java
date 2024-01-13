package com.phantomskeep.phantomeq.datagen;

import com.phantomskeep.phantomeq.PhantomEQ;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = PhantomEQ.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JsonDataGenerator {

    //IMPORTANT: To generate files made by the Data Generator, you must "Run Data" before running client.

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(new PEQRecipeMaker(generator));
        generator.addProvider(new PEQLootTableProvider(generator));
        generator.addProvider(new PEQBlockstateProvider(generator, existingFileHelper));
        generator.addProvider(new PEQItemModelProvider(generator, existingFileHelper));
    }
}
