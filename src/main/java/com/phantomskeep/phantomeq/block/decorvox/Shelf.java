package com.phantomskeep.phantomeq.block.decorvox;

import com.phantomskeep.phantomeq.block.rotators.DecorRotator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class Shelf extends DecorRotator {

    public Shelf() {
        super(NORTH, EAST, SOUTH, WEST);
    }

    public static final VoxelShape NORTH = Stream.of(
            Block.box(-0.1999999999999993, 0, 11, 16, 0.5, 16),
            Block.box(-1, 31.5, 9.6, 17, 32, 16),
            Block.box(-0.5999999999999996, 7.500000000000002, 10.399999999999999, 16.6, 8.000000000000002, 15.8),
            Block.box(-0.5999999999999996, 15.5, 10.399999999999999, 16.6, 16, 15.8),
            Block.box(-0.5999999999999996, 23.5, 10.399999999999999, 16.6, 24, 15.8),
            Block.box(-0.8000000000000007, 0, 10.1, -0.09999999999999964, 31.5, 15.9),
            Block.box(16.1, 0, 10.1, 16.8, 32, 15.200000000000001),
            Block.box(-0.6999999999999993, 0, 15.200000000000003, 16.8, 31.5, 15.900000000000002)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape EAST = Stream.of(
            Block.box(-16, 0, 0, 32, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape SOUTH = Stream.of(
            Block.box(0, 0, -16, 16, 16, 32)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape WEST = Stream.of(
            Block.box(-16, 0, 0, 32, 16, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();


}
