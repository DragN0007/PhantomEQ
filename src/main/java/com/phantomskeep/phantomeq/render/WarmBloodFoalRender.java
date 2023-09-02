package com.phantomskeep.phantomeq.render;

import com.phantomskeep.phantomeq.entity.WarmBloodEntity;
import com.phantomskeep.phantomeq.entity.WarmBloodFoalEntity;
import com.phantomskeep.phantomeq.model.WarmbloodFoalModel;
import com.phantomskeep.phantomeq.model.WarmbloodModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WarmBloodFoalRender extends GeoEntityRenderer<WarmBloodFoalEntity> {
    public WarmBloodFoalRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WarmbloodFoalModel());
    }
}
