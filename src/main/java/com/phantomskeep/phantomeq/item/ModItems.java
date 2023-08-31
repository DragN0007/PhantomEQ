package com.phantomskeep.phantomeq.item;


import com.phantomskeep.phantomeq.PhantomEQ;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, PhantomEQ.MODID);


    public static final RegistryObject<Item> PEQTEMP = ITEMS.register("peqtemp",
            () -> new Item(new Item.Properties().tab(ModItemGroup.PEQ)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}