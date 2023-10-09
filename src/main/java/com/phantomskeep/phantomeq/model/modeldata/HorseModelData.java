package com.phantomskeep.phantomeq.model.modeldata;

import com.google.common.collect.Maps;
import com.mojang.math.Vector3f;

import java.util.Map;

public class HorseModelData {
    public Map<String, Vector3f> offsets = Maps.newHashMap();

    //honse size variation
    public float size = 1.0F;

    //is wearing tack??
    public SaddleType saddle;
    public BridleType bridle;
    public BlanketType blanket;
    public boolean saddlepad;
    public boolean boots;
    public boolean bonnet;
    public boolean halter;

    //snackin'
    public int isEating;
    public int isDrinking;

    //timers for animations
    public long clientGameTime;
    public float random;
    public boolean playIdleAnim;
    public int idleAnimTimer;

    public HorseModelData() {
        this.saddle = SaddleType.NONE;
        this.bridle = BridleType.NONE;
        this.blanket = BlanketType.NONE;
        this.isEating = 0;
        this.isDrinking = 0;
        this.clientGameTime = 0L;
        this.playIdleAnim = true;
        this.idleAnimTimer = 0;
    }
}
