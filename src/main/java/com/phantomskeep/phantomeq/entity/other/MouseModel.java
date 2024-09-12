package com.phantomskeep.phantomeq.entity.other;


import com.phantomskeep.phantomeq.PhantomEQ;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MouseModel extends AnimatedGeoModel<Mouse> {

    public enum Variant {
        ONE (new ResourceLocation(PhantomEQ.MODID, "textures/entities/mouse_1.png")),
        TWO (new ResourceLocation(PhantomEQ.MODID, "textures/entities/mouse_2.png")),
        THREE (new ResourceLocation(PhantomEQ.MODID, "textures/entities/mouse_3.png")),
        FOUR (new ResourceLocation(PhantomEQ.MODID, "textures/entities/mouse_4.png")),
        FIVE (new ResourceLocation(PhantomEQ.MODID, "textures/entities/mouse_5.png")),
        SIX (new ResourceLocation(PhantomEQ.MODID, "textures/entities/mouse_6.png")),
        SEVEN (new ResourceLocation(PhantomEQ.MODID, "textures/entities/mouse_7.png")),
        EIGHT (new ResourceLocation(PhantomEQ.MODID, "textures/entities/mouse_8.png")),
        NINE (new ResourceLocation(PhantomEQ.MODID, "textures/entities/mouse_9.png")),
        TEN (new ResourceLocation(PhantomEQ.MODID, "textures/entities/mouse_10.png"));

        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation model = new ResourceLocation(PhantomEQ.MODID, "geo/mouse.geo.json");
    public static final ResourceLocation babyModel = new ResourceLocation(PhantomEQ.MODID, "geo/baby_mouse.geo.json");
    public static final ResourceLocation animation = new ResourceLocation(PhantomEQ.MODID, "animations/mouse.animation.json");



    @Override
    public ResourceLocation getModelLocation(Mouse object) {
        if(object.isBaby())
            return babyModel;
        return model;
    }

    @Override
    public ResourceLocation getTextureLocation(Mouse object) {
        if(object.isBaby())
            return (new ResourceLocation(PhantomEQ.MODID, "textures/entities/baby_mouse.png"));
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Mouse animatable) {
        return animation;
    }
}
