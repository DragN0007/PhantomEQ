package com.phantomskeep.phantomeq.datagen;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.block.PEQBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.HayBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class PEQBlockstateProvider extends BlockStateProvider {
    public PEQBlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, PhantomEQ.MODID, exFileHelper);
    }
    @Override
    protected void registerStatesAndModels() {


        //Dont use this one as an example because the way i made it kinda sucks lmao
        simpleBlock((HayBlock) (PEQBlocks.ALFALFA_BALE.get()));
        simpleBlockItem(PEQBlocks.ALFALFA_BALE.get(), models().cubeColumnHorizontal(PEQBlocks.ALFALFA_BALE.get().getRegistryName().getPath(),
                blockTexture(PEQBlocks.ALFALFA_BALE.get()), blockTexture(PEQBlocks.ALFALFA_BALE.get())));
    }

}
