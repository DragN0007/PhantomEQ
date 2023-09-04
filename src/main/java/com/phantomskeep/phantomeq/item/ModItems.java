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

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PhantomEQ.MODID);


    //SPAWN EGGS
    public static final RegistryObject<Item> WARMBLOOD_SPAWN_EGG = ITEMS.register("warmblood_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityTypes.WARMBLOOD, 0x8B6C4C, 0x8B6C4C, new Item.Properties().stacksTo(64).tab(ModItemGroup.PEQ)));

    //SADDLES & TACK
    public static final RegistryObject<SaddleItem> PEQ_SADDLE = ITEMS.register("peq_saddle",
            () -> new SaddleItem(new Item.Properties().stacksTo(1).tab(ModItemGroup.PEQ)));

    //HORSE ITEMS




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}