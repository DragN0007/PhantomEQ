package com.phantomskeep.phantomeq.datagen;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.item.PEQItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class PEQItemModelProvider extends ItemModelProvider {
    public PEQItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, PhantomEQ.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(PEQItems.MIXED_FEED.get());
        simpleItem(PEQItems.ORGANIC_FEED.get());
        simpleItem(PEQItems.SPORT_FEED.get());
        simpleItem(PEQItems.REGULAR_FEED.get());
        simpleItem(PEQItems.TRUCK_ITEM.get());
    }

    private ItemModelBuilder simpleItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PhantomEQ.MODID,"items/" + item.getRegistryName().getPath()));
    }

    private ItemModelBuilder handheldItem(Item item) {
        return withExistingParent(item.getRegistryName().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(PhantomEQ.MODID,"items/" + item.getRegistryName().getPath()));
    }
}