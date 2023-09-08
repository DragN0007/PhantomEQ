package com.phantomskeep.phantomeq.model;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.QuarterHorseEntity;
import com.phantomskeep.phantomeq.entity.QuarterHorseFoalEntity;
import com.phantomskeep.phantomeq.entity.WarmBloodFoalEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class QuarterHorseFoalModel extends AnimatedGeoModel<QuarterHorseFoalEntity> {

    public enum Variant {
        A (new ResourceLocation(PhantomEQ.MODID, "textures/entities/foal1.png")

        );

        
        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    public static final ResourceLocation model = new ResourceLocation(PhantomEQ.MODID, "geo/foal.geo.json");
    public static final ResourceLocation animation = new ResourceLocation(PhantomEQ.MODID, "animations/foal.animation.json");



    @Override
    public ResourceLocation getModelLocation(QuarterHorseFoalEntity object) {
        return model;
    }

    @Override
    public ResourceLocation getTextureLocation(QuarterHorseFoalEntity object) {
        return object.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(QuarterHorseFoalEntity animatable) {
        return animation;
    }
}
