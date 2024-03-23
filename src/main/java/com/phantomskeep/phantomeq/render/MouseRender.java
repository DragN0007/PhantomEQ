package com.phantomskeep.phantomeq.render;

import com.phantomskeep.phantomeq.entity.other.Mouse;
import com.phantomskeep.phantomeq.model.MouseModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MouseRender extends GeoEntityRenderer<Mouse> {
    public MouseRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MouseModel());
    }

}
