package com.phantomskeep.phantomeq.render;

import com.phantomskeep.phantomeq.entity.QuarterHorseEntity;
import com.phantomskeep.phantomeq.model.QuarterHorseModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class QuarterHorseRender extends GeoEntityRenderer<QuarterHorseEntity> {
    public QuarterHorseRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new QuarterHorseModel());
    }
}
