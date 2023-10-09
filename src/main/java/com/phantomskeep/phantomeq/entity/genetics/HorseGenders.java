package com.phantomskeep.phantomeq.entity.genetics;

import java.util.*;

public enum HorseGenders {
    MARE(1, true),
    STALLION(2, true),
    FILLY(3, true),
    COLT(4, true),
    GELDING(5, false);


    private static final HorseGenders[] VALUES = Arrays.stream(values())
            .sorted(Comparator.comparingInt(HorseGenders::getId))
            .toArray(HorseGenders[]::new);
    private final int id;
    private final boolean naturallyOccurring;

    HorseGenders(int id, boolean naturallyOccurring) {
        this.id = id;
        this.naturallyOccurring = naturallyOccurring;
    }

    public static int getIndexFromId(int id) {
        for (int i = 0; i < VALUES.length; i++) {
            if (id == VALUES[i].getId()) {
                return i;
            }
        }
        return 1;
    }


    public int getId() {
        return this.id;
    }

    public boolean isNaturallyOccurring() {
        return naturallyOccurring;
    }
}