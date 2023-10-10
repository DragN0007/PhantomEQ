package com.phantomskeep.phantomeq.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class PhantomEQClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    static {
        BUILDER.push("Configs for Phantom Equestrian Mod!");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
