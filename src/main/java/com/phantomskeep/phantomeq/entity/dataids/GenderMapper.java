package com.phantomskeep.phantomeq.entity.dataids;

import java.util.*;

public class GenderMapper {
    private static final HashMap<HorseGenders, HorseGenders> variantMap =
            new HashMap() {
                {
                    put(HorseGenders.MARE, HorseGenders.FILLY);
                    put(HorseGenders.STALLION, HorseGenders.COLT);
                }
            };

    protected static HorseGenders parentToFoalGender(HorseGenders parent) {
        return variantMap.get(parent);
    }

    protected static HorseGenders foalToParentGender(HorseGenders foal) {
        List<HorseGenders> applicableParentGender = new ArrayList<>();
        for (Map.Entry<HorseGenders, HorseGenders> entry : variantMap.entrySet()) {
            if (entry.getValue() == foal) {
                applicableParentGender.add(entry.getKey());
            }
        }
        Collections.shuffle(applicableParentGender);
        return applicableParentGender.get(0);
    }
}