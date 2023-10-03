package com.phantomskeep.phantomeq.model;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.WarmBloodEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WarmbloodModel extends AnimatedGeoModel<WarmBloodEntity> {

    public enum Variant {
        A (new ResourceLocation(PhantomEQ.MODID, "textures/entities/henry.png")),
        B (new ResourceLocation(PhantomEQ.MODID, "textures/entities/shadowmere.png"));

        
        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation model = new ResourceLocation(PhantomEQ.MODID, "geo/warmblood.geo.json");
    public static final ResourceLocation animation = new ResourceLocation(PhantomEQ.MODID, "animations/warmblood.animation.json");



    @Override
    public ResourceLocation getModelLocation(WarmBloodEntity object) {
        return model;
    }

    @Override
    public ResourceLocation getTextureLocation(WarmBloodEntity object) {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(WarmBloodEntity animatable) {
        return animation;
    }
}
