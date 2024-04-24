package com.phantomskeep.phantomeq.entity;


import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.other.Mouse;
import com.phantomskeep.phantomeq.entity.other.vehicle.TruckRenderer;
import com.phantomskeep.phantomeq.entity.other.vehicle.EntityTypes;
import com.phantomskeep.phantomeq.item.PEQItems;
import com.phantomskeep.phantomeq.render.QuarterHorseRender;
import com.phantomskeep.phantomeq.render.WarmBloodRender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


@Mod.EventBusSubscriber(modid = PhantomEQ.MODID, bus = Bus.MOD)
public class PEQEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_DEFERRED
            = DeferredRegister.create(ForgeRegistries.ENTITIES, PhantomEQ.MODID);

    public static final RegistryObject<EntityType<QuarterHorseEntity>> QUARTER_HORSE
            = registerEntity("quarter_horse", QuarterHorseEntity::new, 1.2F, 1.6F);
    public static final RegistryObject<EntityType<WarmbloodHorseEntity>> WARMBLOOD_HORSE
            = registerEntity("warmblood_horse", WarmbloodHorseEntity::new, 1.2F, 1.6F);


    public static final RegistryObject<EntityType<Mouse>> MOUSE
            = registerEntity("mouse", Mouse::new, 0.4f,0.4f);



    public static RegistryObject<Item> QUARTER_HORSE_SPAWN_EGG
            = registerSpawnEgg("quarter_horse_spawn_egg", QUARTER_HORSE, 0x8B6C4C, 0x8B6C4C);
    public static RegistryObject<Item> WARMBLOOD_SPAWN_EGG
            = registerSpawnEgg("warmblood_spawn_egg", WARMBLOOD_HORSE, 0xC3A67C, 0x8F7C60);




    private static <T extends Animal> RegistryObject<EntityType<T>> registerEntity(
            String name, EntityType.EntityFactory<T> factory, float width, float height) {
        final ResourceLocation registryName = new ResourceLocation(PhantomEQ.MODID, name);
        return ENTITY_DEFERRED.register(name,
                () -> EntityType.Builder.of(factory, MobCategory.CREATURE).sized(width, height).build(registryName.toString())
        );
    }

    private static RegistryObject<Item> registerSpawnEgg(String name, RegistryObject<? extends EntityType<? extends Mob>> type, int primary, int secondary) {
        return PEQItems.ITEM_DEFERRED.register(name,
                () -> new ForgeSpawnEggItem(type, primary, secondary, new Item.Properties())
        );
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(QUARTER_HORSE.get(), AbstractHorse.createBaseHorseAttributes().build());
        event.put(WARMBLOOD_HORSE.get(), AbstractHorse.createBaseHorseAttributes().build());
    }

    public static void registerSpawnPlacements(){
        SpawnPlacements.register(QUARTER_HORSE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        SpawnPlacements.register(WARMBLOOD_HORSE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
    }
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(QUARTER_HORSE.get(), QuarterHorseRender::new);
        event.registerEntityRenderer(WARMBLOOD_HORSE.get(), WarmBloodRender::new);
        event.registerEntityRenderer(EntityTypes.TRUCK.get(), TruckRenderer::new);
    }

    //to be used for coat textures in future??
   /* @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerLayerDefinition(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(QuarterHorseRender.COAT_LAYER, QuarterHorseModel::createBodyLayer);
        event.registerLayerDefinition(WarmBloodRender.COAT_LAYER, WarmbloodModel::createBodyLayer);
        event.registerLayerDefinition(HorseArmorLayer.HORSE_ARMOR_LAYER, QuarterHorseModel::createArmorLayer);
        event.registerLayerDefinition(HorseArmorLayer.HORSE_ARMOR_LAYER, WarmbloodModel::createArmorLayer);
    } */


        }
