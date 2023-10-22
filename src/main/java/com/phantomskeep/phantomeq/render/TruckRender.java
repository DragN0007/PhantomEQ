package com.phantomskeep.phantomeq.render;

import com.phantomskeep.phantomeq.entity.TruckEntity;
import com.phantomskeep.phantomeq.model.TruckDyeableBodyModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TruckRender extends GeoEntityRenderer<TruckEntity> {
    public TruckRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TruckDyeableBodyModel());
    }
}
