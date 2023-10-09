package com.phantomskeep.phantomeq.item;


import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.util.EntityTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SaddleItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems {

    public static final DeferredRegister<Item> ITEM_DEFERRED =
            DeferredRegister.create(ForgeRegistries.ITEMS, PhantomEQ.MODID);

    //SADDLES & TACK
    public static final RegistryObject<Item> PEQ_SADDLE = ITEM_DEFERRED.register("peq_saddle",
            () -> new PEQSaddleItem(new Item.Properties().stacksTo(1).tab(ModItemGroup.PEQ)));

    //HORSE ITEMS
    public static final RegistryObject<Item> ORGANIC_FEED = ITEM_DEFERRED.register("organic_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(ModItemGroup.PEQ)));
    public static final RegistryObject<Item> REGULAR_FEED = ITEM_DEFERRED.register("regular_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(ModItemGroup.PEQ)));
    public static final RegistryObject<Item> SPORT_FEED = ITEM_DEFERRED.register("sport_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(ModItemGroup.PEQ)));
    public static final RegistryObject<Item> MIXED_FEED = ITEM_DEFERRED.register("mixed_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(ModItemGroup.PEQ)));




    public static void register(IEventBus eventBus) {
        ITEM_DEFERRED.register(eventBus);
    }
}