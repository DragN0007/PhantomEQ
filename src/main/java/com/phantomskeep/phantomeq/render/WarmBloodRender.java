package com.phantomskeep.phantomeq.render;

import com.phantomskeep.phantomeq.entity.WarmbloodHorseEntity;
import com.phantomskeep.phantomeq.model.WarmbloodModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WarmBloodRender extends GeoEntityRenderer<WarmbloodHorseEntity> {
    public WarmBloodRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WarmbloodModel());
    }
}
