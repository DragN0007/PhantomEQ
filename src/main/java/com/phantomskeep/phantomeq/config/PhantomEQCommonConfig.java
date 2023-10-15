package com.phantomskeep.phantomeq.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class PhantomEQCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;


    public static final ForgeConfigSpec.ConfigValue<Integer> UTAH_WEIGHT;





    static {
        BUILDER.push("Configs for Phantom Equestrian Mod!");


        UTAH_WEIGHT = BUILDER.comment("How often should this dino spawn? Default is 6.")
                .define("UTAH Spawn Weight", 6);



        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
