package com.phantomskeep.phantomeq.entity.genetics;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;

import java.util.concurrent.ThreadLocalRandom;

public class WarmbloodGenetics {

    public Coats generateNewCoat(LevelAccessor world, BlockPos pos, Boolean generateCoat) {
        return super.generateNewCoat(world, pos, generateCoat, this.coats);
    }

    public Coats generateFromParent(LevelAccessor world, BlockPos pos, String coat) {
        return super.generateFromParent(world, pos, this.coats, coat);
    }

    public Coats generateWildCoats() {
        int[] coatGenes = new int[1];

        //gene 1 chance here
        if (ThreadLocalRandom.current().nextInt(100) > this.GEN) {
            coatGenes[0] = ThreadLocalRandom.current().nextInt(4) + 1;
        } else {
            coatGenes[0] = 3;
        }
        //repeat for more genes; raise new int to be +1 to total amount of genes

        return new Coats(coatGenes);
    }
}
