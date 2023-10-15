package com.phantomskeep.phantomeq.model;

import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.QuarterHorseEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class QuarterHorseModel extends AnimatedGeoModel<QuarterHorseEntity> {

    public enum Variant {
        A (new ResourceLocation(PhantomEQ.MODID, "textures/entities/henry.png")

        );

        
        public final ResourceLocation resourceLocation;
        Variant(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static Variant variantFromOrdinal(int variant) { return Variant.values()[variant % Variant.values().length];
        }
    }

    @Override
    public ResourceLocation getModelLocation(QuarterHorseEntity quarterHorseEntity) {
        return quarterHorseEntity.isBaby() ? new ResourceLocation(PhantomEQ.MODID, "geo/foal.geo.json") : new ResourceLocation(PhantomEQ.MODID, "geo/warmblood.geo.json");
    }

    // CHANGE THIS WHEN IMPLEMENTING COATS. CURRENTLY JUST A PLACEHOLDER!!
    @Override
    public ResourceLocation getTextureLocation(QuarterHorseEntity quarterHorseEntity) {
        return quarterHorseEntity.isBaby() ? new ResourceLocation(PhantomEQ.MODID, "textures/entities/foal1.png") : new ResourceLocation(PhantomEQ.MODID, "textures/entities/henry.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(QuarterHorseEntity quarterHorseEntity) {
        return quarterHorseEntity.isBaby() ? new ResourceLocation(PhantomEQ.MODID, "animations/foal.animation.json") : new ResourceLocation(PhantomEQ.MODID, "animations/warmblood.animation.json");
    }
}
