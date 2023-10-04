package com.phantomskeep.phantomeq.block.decorvox;

import com.phantomskeep.phantomeq.block.rotators.DecorRotator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

public class FenceFeeder extends DecorRotator {

    public FenceFeeder() {
        super(NORTH, EAST, SOUTH, WEST);
    }

    public static final VoxelShape NORTH = Stream.of(
            Block.box(5, 12, 9, 6, 15, 9),
            Block.box(10, 12, 9, 11, 15, 9),
            Block.box(5, 15, 7, 6, 15, 9),
            Block.box(10, 15, 7, 11, 15, 9),
            Block.box(9, 14, 6, 12, 15, 7),
            Block.box(4, 14, 6, 7, 15, 7),
            Block.box(4, 13, 6, 12, 14, 7),
            Block.box(4, 10.671498910433062, 13.307790411448291, 5, 11.671498910433062, 17.057790411448302),
            Block.box(11, 10.671498910433062, 13.307790411448291, 12, 11.671498910433062, 17.057790411448302),
            Block.box(10, 9.671498910433062, 14.307790411448291, 11, 10.671498910433062, 16.057790411448302),
            Block.box(5, 9.671498910433062, 14.307790411448291, 6, 10.671498910433062, 16.057790411448302),
            Block.box(11, 11, 1, 12, 12, 5),
            Block.box(10, 11, 5, 11, 12, 6),
            Block.box(5, 11, 5, 6, 12, 6),
            Block.box(4, 11, 1, 5, 12, 5),
            Block.box(5, 11, 0, 11, 12, 1),
            Block.box(5, 8, 1, 11, 11, 2),
            Block.box(5, 7, 2, 11, 8, 7),
            Block.box(5, 8, 6, 11, 13, 7),
            Block.box(5, 8, 2, 6, 11, 6),
            Block.box(10, 8, 2, 11, 11, 6)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape EAST = Stream.of(
            Block.box(7, 12, 5, 7, 15, 6),
            Block.box(7, 12, 10, 7, 15, 11),
            Block.box(7, 15, 5, 9, 15, 6),
            Block.box(7, 15, 10, 9, 15, 11),
            Block.box(9, 14, 9, 10, 15, 12),
            Block.box(9, 14, 4, 10, 15, 7),
            Block.box(9, 13, 4, 10, 14, 12),
            Block.box(-1.0577904114483019, 10.671498910433062, 4, 2.6922095885517088, 11.671498910433062, 5),
            Block.box(-1.0577904114483019, 10.671498910433062, 11, 2.6922095885517088, 11.671498910433062, 12),
            Block.box(-0.05779041144830188, 9.671498910433062, 10, 1.6922095885517088, 10.671498910433062, 11),
            Block.box(-0.05779041144830188, 9.671498910433062, 5, 1.6922095885517088, 10.671498910433062, 6),
            Block.box(11, 11, 11, 15, 12, 12),
            Block.box(10, 11, 10, 11, 12, 11),
            Block.box(10, 11, 5, 11, 12, 6),
            Block.box(11, 11, 4, 15, 12, 5),
            Block.box(15, 11, 5, 16, 12, 11),
            Block.box(14, 8, 5, 15, 11, 11),
            Block.box(9, 7, 5, 14, 8, 11),
            Block.box(9, 8, 5, 10, 13, 11),
            Block.box(10, 8, 5, 14, 11, 6),
            Block.box(10, 8, 10, 14, 11, 11)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape SOUTH = Stream.of(
            Block.box(10, 12, 7, 11, 15, 7),
            Block.box(5, 12, 7, 6, 15, 7),
            Block.box(10, 15, 7, 11, 15, 9),
            Block.box(5, 15, 7, 6, 15, 9),
            Block.box(4, 14, 9, 7, 15, 10),
            Block.box(9, 14, 9, 12, 15, 10),
            Block.box(4, 13, 9, 12, 14, 10),
            Block.box(11, 10.671498910433062, -1.0577904114483019, 12, 11.671498910433062, 2.6922095885517088),
            Block.box(4, 10.671498910433062, -1.0577904114483019, 5, 11.671498910433062, 2.6922095885517088),
            Block.box(5, 9.671498910433062, -0.05779041144830188, 6, 10.671498910433062, 1.6922095885517088),
            Block.box(10, 9.671498910433062, -0.05779041144830188, 11, 10.671498910433062, 1.6922095885517088),
            Block.box(4, 11, 11, 5, 12, 15),
            Block.box(5, 11, 10, 6, 12, 11),
            Block.box(10, 11, 10, 11, 12, 11),
            Block.box(11, 11, 11, 12, 12, 15),
            Block.box(5, 11, 15, 11, 12, 16),
            Block.box(5, 8, 14, 11, 11, 15),
            Block.box(5, 7, 9, 11, 8, 14),
            Block.box(5, 8, 9, 11, 13, 10),
            Block.box(10, 8, 10, 11, 11, 14),
            Block.box(5, 8, 10, 6, 11, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();

    public static final VoxelShape WEST = Stream.of(
            Block.box(9, 12, 10, 9, 15, 11),
            Block.box(9, 12, 5, 9, 15, 6),
            Block.box(7, 15, 10, 9, 15, 11),
            Block.box(7, 15, 5, 9, 15, 6),
            Block.box(6, 14, 4, 7, 15, 7),
            Block.box(6, 14, 9, 7, 15, 12),
            Block.box(6, 13, 4, 7, 14, 12),
            Block.box(13.307790411448291, 10.671498910433062, 11, 17.057790411448302, 11.671498910433062, 12),
            Block.box(13.307790411448291, 10.671498910433062, 4, 17.057790411448302, 11.671498910433062, 5),
            Block.box(14.307790411448291, 9.671498910433062, 5, 16.057790411448302, 10.671498910433062, 6),
            Block.box(14.307790411448291, 9.671498910433062, 10, 16.057790411448302, 10.671498910433062, 11),
            Block.box(1, 11, 4, 5, 12, 5),
            Block.box(5, 11, 5, 6, 12, 6),
            Block.box(5, 11, 10, 6, 12, 11),
            Block.box(1, 11, 11, 5, 12, 12),
            Block.box(0, 11, 5, 1, 12, 11),
            Block.box(1, 8, 5, 2, 11, 11),
            Block.box(2, 7, 5, 7, 8, 11),
            Block.box(6, 8, 5, 7, 13, 11),
            Block.box(2, 8, 10, 6, 11, 11),
            Block.box(2, 8, 5, 6, 11, 6)
    ).reduce((v1, v2) -> Shapes.join(v1, v2,BooleanOp.OR)).get();
}
