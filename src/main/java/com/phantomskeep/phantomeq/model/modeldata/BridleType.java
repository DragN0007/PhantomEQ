package com.phantomskeep.phantomeq.model.modeldata;

public enum BridleType {
    NONE(""),
    WESTERN("WesternBridle"),
    ENGLISH("EnglishBridle");

    final String name;

    private BridleType(String name){this.name = name;}
    public String getName(){return this.name;}
}
