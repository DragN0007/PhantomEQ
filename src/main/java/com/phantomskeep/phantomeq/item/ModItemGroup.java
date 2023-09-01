package com.phantomskeep.phantomeq.item;


import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroup {

    public static final CreativeModeTab PEQ = new CreativeModeTab("PEQ") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.PEQ_SADDLE.get());
        }
    };


}
