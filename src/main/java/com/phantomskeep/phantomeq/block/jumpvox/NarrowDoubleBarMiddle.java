package com.phantomskeep.phantomeq.block.jumpvox;

import com.phantomskeep.phantomeq.block.rotators.DecorRotator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class NarrowDoubleBarMiddle extends DecorRotator {

    public NarrowDoubleBarMiddle() {
        super(NORTH, EAST, SOUTH, WEST);
    }

    public static final VoxelShape NORTH = Stream.of(
            Block.box(0, 7, 3, 16, 9, 5),
            Block.box(0, 7, 11, 16, 9, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape EAST = Stream.of(
            Block.box(11, 7, 0, 13, 9, 16),
            Block.box(3, 7, 0, 5, 9, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape SOUTH = Stream.of(
            Block.box(0, 7, 3, 16, 9, 5),
            Block.box(0, 7, 11, 16, 9, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape WEST = Stream.of(
            Block.box(11, 7, 0, 13, 9, 16),
            Block.box(3, 7, 0, 5, 9, 16)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();


}
