package com.phantomskeep.phantomeq.util;

import com.phantomskeep.phantomeq.PhantomEQ;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {
    public static class Items {

        private static TagKey<Item> tag (String name) {
            return ItemTags.create(new ResourceLocation(PhantomEQ.MODID, name));
        }
        private static TagKey<Item> forgeTag (String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }

}
