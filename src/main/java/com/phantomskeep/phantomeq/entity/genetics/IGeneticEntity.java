package com.phantomskeep.phantomeq.entity.genetics;

import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.level.levelgen.RandomSource;

public interface IGeneticEntity<T extends Enum<T>> {

    //Borrowed from RHG

        RandomSource getRand();

        boolean isMale();
        void setMale(boolean gender);

        boolean isFertile();
        void setFertile(boolean fertile);

        int getRebreedTicks();

        int getBirthAge();
        int getTrueAge();

        default float getFractionGrown() {
            int age = getTrueAge();
            if (age < 0) {
               // if (HorseConfig.GROWTH.growGradually.get()) {//
                    int minAge = getBirthAge();
                    float fractionGrown = (minAge - age) / (float)minAge;
                    return Math.max(0, fractionGrown);
                }
                return 0;
        }

        // Return true if successful, false otherwise
        // Reasons for returning false could be if the animal is male or the mate is female
        // (This prevents spawn eggs from starting a pregnancy.)
        boolean setPregnantWith(AgeableMob child, AgeableMob otherParent);


        float getMotherSize();
        void setMotherSize(float size);
    }
}
