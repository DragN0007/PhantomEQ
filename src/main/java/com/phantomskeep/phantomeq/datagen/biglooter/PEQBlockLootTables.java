package com.phantomskeep.phantomeq.datagen.biglooter;

import com.phantomskeep.phantomeq.block.PEQBlocks;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class PEQBlockLootTables extends BlockLoot {
    @Override
    protected void addTables() {
        this.dropSelf(PEQBlocks.ALFALFA_BALE.get());

        this.dropSelf(PEQBlocks.BUCKET.get());
        this.dropSelf(PEQBlocks.WATER_TROUGH.get());

        this.dropSelf(PEQBlocks.FENCE_FEEDER_BLACK.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_BLUE.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_BROWN.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_CYAN.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_GRAY.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_GREEN.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_LIGHT_BLUE.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_LIGHT_GRAY.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_LIME.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_MAGENTA.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_ORANGE.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_PINK.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_PURPLE.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_RED.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_WHITE.get());
        this.dropSelf(PEQBlocks.FENCE_FEEDER_YELLOW.get());

        this.dropSelf(PEQBlocks.JUMP1_M.get());
        this.dropSelf(PEQBlocks.JUMP1_S_BLACK.get());
        this.dropSelf(PEQBlocks.JUMP1_S_BLUE.get());
        this.dropSelf(PEQBlocks.JUMP1_S_CYAN.get());
        this.dropSelf(PEQBlocks.JUMP1_S_GREEN.get());
        this.dropSelf(PEQBlocks.JUMP1_S_ORANGE.get());
        this.dropSelf(PEQBlocks.JUMP1_S_PINK.get());
        this.dropSelf(PEQBlocks.JUMP1_S_PURPLE.get());
        this.dropSelf(PEQBlocks.JUMP1_S_RED.get());
        this.dropSelf(PEQBlocks.JUMP1_S_YELLOW.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return PEQBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
