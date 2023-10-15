package com.phantomskeep.phantomeq.model.modeldata;

public enum BlanketType {
    NONE("");

    final String name;

    private BlanketType(String name){this.name = name;}
    public String getName(){return this.name;}
}

