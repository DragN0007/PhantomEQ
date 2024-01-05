package com.phantomskeep.phantomeq.item;


import com.phantomskeep.phantomeq.PhantomEQ;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;


public class ModItems {
    public static final DeferredRegister<Item> ITEM_DEFERRED =
            DeferredRegister.create(ForgeRegistries.ITEMS, PhantomEQ.MODID);

    //SADDLES & TACK
    public static final RegistryObject<Item> PEQ_SADDLE = ITEM_DEFERRED.register("peq_saddle",
            () -> new PEQSaddleItem(new Item.Properties().stacksTo(1).tab(ModItems.PEQ)));

    //HORSE ITEMS
    public static final RegistryObject<Item> ORGANIC_FEED = ITEM_DEFERRED.register("organic_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(ModItems.PEQ)));
    public static final RegistryObject<Item> REGULAR_FEED = ITEM_DEFERRED.register("regular_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(ModItems.PEQ)));
    public static final RegistryObject<Item> SPORT_FEED = ITEM_DEFERRED.register("sport_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(ModItems.PEQ)));
    public static final RegistryObject<Item> MIXED_FEED = ITEM_DEFERRED.register("mixed_feed",
            () -> new Item(new Item.Properties().stacksTo(64).tab(ModItems.PEQ)));



    //MODTAB ICONS
    public static final RegistryObject<Item> MODTAB_1 = ITEM_DEFERRED.register("modtab_1",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MODTAB_2 = ITEM_DEFERRED.register("modtab_2",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MODTAB_3 = ITEM_DEFERRED.register("modtab_3",
            () -> new Item(new Item.Properties()));

    //MODTAB
    public static final CreativeModeTab PEQ = new CreativeModeTab("PEQ") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.MODTAB_1.get());
        }
    };

    public static final CreativeModeTab DECOR = new CreativeModeTab("DECOR") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(ModItems.MODTAB_2.get());
        }
    };

    public static final CreativeModeTab FOOD = new CreativeModeTab("FOOD") {
        @Override
        public ItemStack makeIcon() { return new ItemStack(ModItems.MODTAB_3.get()); }
    };
}