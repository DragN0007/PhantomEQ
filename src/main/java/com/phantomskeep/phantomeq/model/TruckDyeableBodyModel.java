package com.phantomskeep.phantomeq.model;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.TruckEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TruckDyeableBodyModel extends AnimatedGeoModel<TruckEntity> {

    public enum DefaultTruck {
        DEFAULT (new ResourceLocation(PhantomEQ.MODID, "textures/entities/pickupwhite.png"));

        
        public final ResourceLocation resourceLocation;
        DefaultTruck(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static DefaultTruck variantFromOrdinal(int variant) { return DefaultTruck.values()[variant % DefaultTruck.values().length];
        }
    }

    public static final ResourceLocation model = new ResourceLocation(PhantomEQ.MODID, "geo/pickup.geo.json");
    public static final ResourceLocation animation = new ResourceLocation(PhantomEQ.MODID, "animations/drive.animation.json");



    @Override
    public ResourceLocation getModelLocation(TruckEntity object) {
        return model;
    }

    @Override
    public ResourceLocation getTextureLocation(TruckEntity object) {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TruckEntity animatable) {
        return animation;
    }
}
