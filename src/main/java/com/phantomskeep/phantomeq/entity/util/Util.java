package com.phantomskeep.phantomeq.entity.util;

import net.minecraft.world.entity.animal.horse.AbstractHorse;

public class Util {
    public static boolean horseCanMate(AbstractHorse horse) {
        // This is the same as calling other.canMate() but doesn't require
        // reflection
        return !horse.isVehicle() && !horse.isPassenger() && horse.isTamed() && !horse.isBaby() && horse.getHealth() >= horse.getMaxHealth() && horse.isInLove();
    }
}
