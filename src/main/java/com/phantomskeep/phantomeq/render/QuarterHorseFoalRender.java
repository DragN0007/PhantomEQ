package com.phantomskeep.phantomeq.render;

import com.phantomskeep.phantomeq.entity.QuarterHorseEntity;
import com.phantomskeep.phantomeq.entity.QuarterHorseFoalEntity;
import com.phantomskeep.phantomeq.entity.WarmBloodFoalEntity;
import com.phantomskeep.phantomeq.model.QuarterHorseFoalModel;
import com.phantomskeep.phantomeq.model.QuarterHorseModel;
import com.phantomskeep.phantomeq.model.WarmbloodFoalModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class QuarterHorseFoalRender extends GeoEntityRenderer<QuarterHorseFoalEntity> {
    public QuarterHorseFoalRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new QuarterHorseFoalModel());
    }
}
