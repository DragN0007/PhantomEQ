package com.phantomskeep.phantomeq.render;

import com.phantomskeep.phantomeq.entity.WarmbloodHorseEntity;
import com.phantomskeep.phantomeq.model.WarmbloodHorseModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WarmBloodHorseRender extends GeoEntityRenderer<WarmbloodHorseEntity> {
    public WarmBloodHorseRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WarmbloodHorseModel());
    }
}
