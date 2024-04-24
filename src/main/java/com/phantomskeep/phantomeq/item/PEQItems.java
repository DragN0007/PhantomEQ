package com.phantomskeep.phantomeq.item;


import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.PEQEntities;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;


public class PEQItems {
    public static final DeferredRegister<Item> ITEM_DEFERRED =
            DeferredRegister.create(ForgeRegistries.ITEMS, PhantomEQ.MODID);

    //SADDLES & TACK
    public static final RegistryObject<Item> PEQ_SADDLE = ITEM_DEFERRED.register("peq_saddle",
            () -> new PEQSaddleItem(new Item.Properties().stacksTo(1).tab(PEQItems.PEQ)));

    //HORSE ITEMS
    public static final RegistryObject<Item> ORGANIC_FEED = ITEM_DEFERRED.register("organic_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(PEQItems.PEQ)));
    public static final RegistryObject<Item> REGULAR_FEED = ITEM_DEFERRED.register("regular_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(PEQItems.PEQ)));
    public static final RegistryObject<Item> SPORT_FEED = ITEM_DEFERRED.register("sport_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(PEQItems.PEQ)));
    public static final RegistryObject<Item> MIXED_FEED = ITEM_DEFERRED.register("mixed_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(PEQItems.PEQ)));

    //SPAWNEGGS
    public static final RegistryObject<Item> MOUSE_SPAWN_EGG = ITEM_DEFERRED.register("mouse_spawn_egg",
            () -> new ForgeSpawnEggItem(PEQEntities.MOUSE, 0x85755e, 0x9a8484, new Item.Properties().stacksTo(64).tab(PEQItems.PEQ)));

    public static final RegistryObject<Item> TRUCK_ITEM = ITEM_DEFERRED.register("truck_item", TruckItem::new);


    //MODTAB ICONS
    public static final RegistryObject<Item> MODTAB_1 = ITEM_DEFERRED.register("modtab_1",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MODTAB_2 = ITEM_DEFERRED.register("modtab_2",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MODTAB_3 = ITEM_DEFERRED.register("modtab_3",
            () -> new Item(new Item.Properties()));

    //MODTAB
    public static final CreativeModeTab PEQ = new CreativeModeTab("peq") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(PEQItems.MODTAB_1.get());
        }
    };

    public static final CreativeModeTab DECOR = new CreativeModeTab("decor") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(PEQItems.MODTAB_2.get());
        }
    };

    public static final CreativeModeTab FOOD = new CreativeModeTab("food") {
        @Override
        public ItemStack makeIcon() { return new ItemStack(PEQItems.MODTAB_3.get()); }
    };
}