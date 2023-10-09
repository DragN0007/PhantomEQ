package com.phantomskeep.phantomeq.entity.util;

public class HorseInfo {
    public int health = 0;
    public int hunger = 0;
    public boolean isFemale = true;
    public boolean isGelded = false;
    public int pregnant = 0;
    public String name = "Horse";
    public String agePrefix = "ADULT";
    public Integer age = 0;
    public String sire = "Unknown";
    public String dam = "Unknown";
    public boolean created = false;

    public HorseInfo() {
    }

    public HorseInfo(String serialised) {
        if (serialised != null && !serialised.isEmpty()) {
            String[] splits = serialised.split("\\|");
            this.health = Integer.parseInt(splits[0]);
            this.hunger = Integer.parseInt(splits[1]);
            this.isFemale = Boolean.parseBoolean(splits[2]);
            this.isGelded = Boolean.parseBoolean(splits[3]);
            this.pregnant = Integer.parseInt(splits[4]);
            this.name = String.valueOf(splits[5]);
            this.agePrefix = String.valueOf(splits[6]);
            this.age = Integer.parseInt(splits[7]);
            this.sire = String.valueOf(splits[8]);
            this.dam = String.valueOf(splits[9]);
            this.created = true;
        }
    }

    public String serialiseToString() {
        return this.health + "|" +
                this.hunger + "|" +
                this.isFemale + "|" +
                this.pregnant + "|" +
                this.name + "|" +
                this.agePrefix + "|" +
                this.age + "|" +
                this.sire + "|" +
                this.dam + "|";
    }
}
