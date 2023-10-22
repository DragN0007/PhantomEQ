package com.phantomskeep.phantomeq.item;


import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.util.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PhantomEQ.MODID);


    //SPAWN EGGS
    public static final RegistryObject<Item> WARMBLOOD_SPAWN_EGG = ITEMS.register("warmblood_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypes.WARMBLOOD, 0x8B6C4C, 0x8B6C4C, new Item.Properties().stacksTo(64).tab(ModItemGroup.PEQ)));
    public static final RegistryObject<Item> QUARTERHORSE_SPAWN_EGG = ITEMS.register("quarterhorse_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypes.QUARTERHORSE, 0xC3A67C, 0x8F7C60, new Item.Properties().stacksTo(64).tab(ModItemGroup.PEQ)));
    public static final RegistryObject<Item> TRUCK_SPAWNER = ITEMS.register("truck_spawner",
            () -> new ForgeSpawnEggItem(EntityTypes.TRUCK, 0xC3A67C, 0x8F7C60, new Item.Properties().stacksTo(64).tab(ModItemGroup.PEQ)));

    //SADDLES & TACK
    public static final RegistryObject<Item> PEQ_SADDLE = ITEMS.register("peq_saddle",
            () -> new PEQSaddleItem(new Item.Properties().stacksTo(1).tab(ModItemGroup.PEQ)));

    //HORSE ITEMS
    public static final RegistryObject<Item> ORGANIC_FEED = ITEMS.register("organic_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(ModItemGroup.FOOD)));
    public static final RegistryObject<Item> REGULAR_FEED = ITEMS.register("regular_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(ModItemGroup.FOOD)));
    public static final RegistryObject<Item> SPORT_FEED = ITEMS.register("sport_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(ModItemGroup.FOOD)));
    public static final RegistryObject<Item> MIXED_FEED = ITEMS.register("mixed_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(ModItemGroup.FOOD)));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}