package com.phantomskeep.phantomeq.block;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.block.decorvox.Bucket;
import com.phantomskeep.phantomeq.block.decorvox.FenceFeeder;
import com.phantomskeep.phantomeq.block.decorvox.WaterTrough;
import com.phantomskeep.phantomeq.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HayBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, PhantomEQ.MODID);


    //DECOR
    public static final RegistryObject<WaterTrough> WATER_TROUGH = registerBlock("water_trough",
            () -> new WaterTrough());
    public static final RegistryObject<Bucket> BUCKET = registerBlock("bucket",
            () -> new Bucket());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_BLACK = registerBlock("fence_feeder_black",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_BLUE = registerBlock("fence_feeder_blue",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_BROWN = registerBlock("fence_feeder_brown",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_CYAN = registerBlock("fence_feeder_cyan",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_GRAY = registerBlock("fence_feeder_gray",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_GREEN = registerBlock("fence_feeder_green",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_LIGHT_BLUE = registerBlock("fence_feeder_light_blue",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_LIGHT_GRAY = registerBlock("fence_feeder_light_gray",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_LIME = registerBlock("fence_feeder_lime",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_MAGENTA = registerBlock("fence_feeder_magenta",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_ORANGE = registerBlock("fence_feeder_orange",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_PINK = registerBlock("fence_feeder_pink",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_PURPLE = registerBlock("fence_feeder_purple",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_RED = registerBlock("fence_feeder_red",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_WHITE = registerBlock("fence_feeder_white",
            () -> new FenceFeeder());
    public static final RegistryObject<FenceFeeder> FENCE_FEEDER_YELLOW = registerBlock("fence_feeder_yellow",
            () -> new FenceFeeder());


    //BALES
    public static final RegistryObject<HayBlock> ALFALFA_BALE = registerFoodBlock("alfalfa_bale",
            () -> new HayBlock(Block.Properties.copy(Blocks.HAY_BLOCK)));





    private static ToIntFunction<BlockState> litBlockEmission(int p_50760_) {
        return (p_50763_) -> {
            return p_50763_.getValue(BlockStateProperties.LIT) ? p_50760_ : 0;
        };
    }

    private static <T extends Block>RegistryObject<T> registerBlockWithoutItem(String name, Supplier<T> block){
        return BLOCKS.register(name, block);
    }

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEM_DEFERRED.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(ModItems.DECOR)));
    }

    private static <T extends Block>RegistryObject<T> registerFoodBlock(String name, Supplier<T> block){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerFoodBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> void registerFoodBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEM_DEFERRED.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(ModItems.FOOD)));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
