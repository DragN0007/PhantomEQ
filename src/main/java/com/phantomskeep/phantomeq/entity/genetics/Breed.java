package com.phantomskeep.phantomeq.entity.genetics;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Map;

public class Breed<T extends Enum<T>> {
    // The breed's internal name
    public String name;
    // How many members the breed has in the real world
    public int population;
    // The enums represent genes
    // The list of floats determines the likelihood of each allele
    public Map<T, List<Float>> genes;
    // If a gene is not in this breed's map, it will add it from the parent
    public Breed<T> parent = null;

    public static final List<Float> DEFAULT_FREQUENCIES = ImmutableList.of(1f);

    public Breed() {
    }

}
