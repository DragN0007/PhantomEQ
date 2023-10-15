package com.phantomskeep.phantomeq;

import com.mojang.logging.LogUtils;
import com.phantomskeep.phantomeq.block.ModBlocks;
import com.phantomskeep.phantomeq.config.PhantomEQClientConfig;
import com.phantomskeep.phantomeq.config.PhantomEQCommonConfig;
import com.phantomskeep.phantomeq.entity.ModEntities;
import com.phantomskeep.phantomeq.entity.util.EntityTypes;
import com.phantomskeep.phantomeq.item.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.stream.Collectors;

import static com.phantomskeep.phantomeq.PhantomEQ.MODID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MODID)
public class PhantomEQ {
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "phantomeq";
    public static PhantomEQ instance;

    public PhantomEQ() {
        instance = this;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PhantomEQCommonConfig.spec);

        // GeckoLib
        GeckoLib.initialize();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        registerDeferredRegistries(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(ModEntities::registerSpawnPlacements);
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        ModEntities.ENTITY_DEFERRED.register(modBus);
        ModItems.ITEM_DEFERRED.register(modBus);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // Some phantomskeep code to dispatch IMC to another mod
        InterModComms.sendTo("phantomeq", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // Some phantomskeep code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.messageSupplier().get()).
                collect(Collectors.toList()));
    }
}