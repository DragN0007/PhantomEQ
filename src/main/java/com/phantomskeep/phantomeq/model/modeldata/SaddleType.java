package com.phantomskeep.phantomeq.model.modeldata;

public enum SaddleType {
    NONE(""),
    WESTERN("WesternSaddle"),
    ENGLISH("EnglishSaddle"),
    BAREBACK("BarebackPad"),
    VANILLA("Saddle");

    final String name;

    private SaddleType(String name){this.name = name;}
    public String getName(){return this.name;}
}
