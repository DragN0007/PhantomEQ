package com.phantomskeep.phantomeq;

import com.mojang.logging.LogUtils;
import com.phantomskeep.phantomeq.block.PEQBlocks;
import com.phantomskeep.phantomeq.entity.PEQEntities;
import com.phantomskeep.phantomeq.item.PEQItems;
import com.phantomskeep.phantomeq.util.config.PhantomEQCommonConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.stream.Collectors;

import static com.phantomskeep.phantomeq.PhantomEQ.MODID;

@Mod(MODID)
public class PhantomEQ {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String MODID = "phantomeq";
    public static PhantomEQ instance;

    public static final DeferredRegister<DataSerializerEntry> SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.DATA_SERIALIZERS, MODID);
    public static final RegistryObject<DataSerializerEntry> RESOURCE_SERIALIZER = SERIALIZERS.register("resource_serializer", () -> new DataSerializerEntry(new EntityDataSerializer<ResourceLocation>() {
        @Override
        public void write(FriendlyByteBuf buf, ResourceLocation resourceLocation) {
            buf.writeResourceLocation(resourceLocation);
        }
        @Override
        public ResourceLocation read(FriendlyByteBuf buf) {
            return buf.readResourceLocation();
        }
        @Override
        public ResourceLocation copy(ResourceLocation resourceLocation) {
            return resourceLocation;
        }
    }));

    public PhantomEQ() {
        instance = this;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, PhantomEQCommonConfig.spec);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);

        registerDeferredRegistries(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(PEQEntities::registerSpawnPlacements);
    }

    public static void registerDeferredRegistries(IEventBus modBus) {
        PEQEntities.ENTITY_DEFERRED.register(modBus);
        PEQItems.ITEM_DEFERRED.register(modBus);
        PEQBlocks.register(modBus);
        SERIALIZERS.register(modBus);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo("phantomeq", "helloworld", () -> {
            LOGGER.info("Hello world from the MDK");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m -> m.messageSupplier().get()).
                collect(Collectors.toList()));
    }
}