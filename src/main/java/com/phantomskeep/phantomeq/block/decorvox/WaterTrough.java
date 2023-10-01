package com.phantomskeep.phantomeq.block.decorvox;

import com.phantomskeep.phantomeq.block.rotators.DecorRotator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class WaterTrough extends DecorRotator {

    public WaterTrough() {
        super(NORTH, EAST, SOUTH, WEST);
    }

    public static final VoxelShape NORTH = Stream.of(
            Block.box(1, 0, 3, 2, 2, 13),
            Block.box(14, 0, 3, 15, 2, 13),
            Block.box(1, 3, 3, 2, 8, 13),
            Block.box(14, 3, 3, 15, 8, 13),
            Block.box(3, 3, 1, 13, 8, 2),
            Block.box(3, 3, 14, 13, 8, 15),
            Block.box(3, 9, 1, 13, 11, 2),
            Block.box(3, 9, 14, 13, 11, 15),
            Block.box(3, 0, 1, 13, 2, 2),
            Block.box(3, 0, 14, 13, 2, 15),
            Block.box(1, 9, 3, 2, 11, 13),
            Block.box(14, 9, 3, 15, 11, 13),
            Block.box(0, 11, 2, 2, 12, 14),
            Block.box(2, 0, 3, 3, 11, 13),
            Block.box(13, 0, 3, 14, 11, 13),
            Block.box(3, 0, 2, 13, 11, 3),
            Block.box(3, 0, 13, 13, 11, 14),
            Block.box(14, 11, 2, 16, 12, 14),
            Block.box(2, 11, 0, 14, 12, 2),
            Block.box(2, 11, 14, 14, 12, 16),
            Block.box(1, 11, 1, 2, 12, 2),
            Block.box(14, 11, 1, 15, 12, 2),
            Block.box(14, 11, 14, 15, 12, 15),
            Block.box(1, 11, 14, 2, 12, 15),
            Block.box(2, 0, 13, 3, 2, 14),
            Block.box(13, 0, 13, 14, 2, 14),
            Block.box(13, 0, 2, 14, 2, 3),
            Block.box(2, 0, 2, 3, 2, 3),
            Block.box(13, 3, 13, 14, 8, 14),
            Block.box(2, 3, 13, 3, 8, 14),
            Block.box(2, 3, 2, 3, 8, 3),
            Block.box(13, 3, 2, 14, 8, 3),
            Block.box(2, 9, 13, 3, 11, 14),
            Block.box(13, 9, 13, 14, 11, 14),
            Block.box(13, 9, 2, 14, 11, 3),
            Block.box(2, 9, 2, 3, 11, 3),
            Block.box(3, 0, 3, 13, 0, 13)
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
