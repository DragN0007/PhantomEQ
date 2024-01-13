package com.phantomskeep.phantomeq.datagen;

import com.phantomskeep.phantomeq.block.PEQBlocks;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class PEQRecipeMaker extends RecipeProvider implements IConditionBuilder {
    public PEQRecipeMaker(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> pFinishedRecipeConsumer) {

        ShapedRecipeBuilder.shaped(PEQBlocks.ALFALFA_BALE.get())
                .define('A', Items.WHEAT)
                .pattern("AA")
                .pattern("AA")
//                .pattern("")
                .unlockedBy("has_wheat", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.WHEAT).build()))
                .save(pFinishedRecipeConsumer);
    }
}