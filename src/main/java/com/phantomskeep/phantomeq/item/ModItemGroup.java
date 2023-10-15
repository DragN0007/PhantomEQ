package com.phantomskeep.phantomeq.item;


import com.phantomskeep.phantomeq.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroup {

    public static final CreativeModeTab PEQ = new CreativeModeTab("PEQ") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.PEQ_SADDLE.get());
        }
    };

    public static final CreativeModeTab DECOR = new CreativeModeTab("DECOR") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.WATER_TROUGH.get());
        }
    };

    public static final CreativeModeTab FOOD = new CreativeModeTab("FOOD") {
        @Override
        public ItemStack makeIcon() { return new ItemStack(ModItems.REGULAR_FEED.get()); }
    };


}
