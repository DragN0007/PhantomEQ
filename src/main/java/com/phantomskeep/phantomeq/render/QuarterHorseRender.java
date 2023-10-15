package com.phantomskeep.phantomeq.render;

import com.google.common.collect.Maps;
import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.QuarterHorseEntity;
import com.phantomskeep.phantomeq.model.QuarterHorseModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import java.util.Map;

public class QuarterHorseRender extends GeoEntityRenderer<QuarterHorseEntity> {

    private static final Map<TextureLayer, ResourceLocation> LAYERED_LOCATION_CACHE = Maps.newHashMap();
    public static final ModelLayerLocation COAT_LAYER = new ModelLayerLocation(new ResourceLocation(PhantomEQ.MODID, "quarter_horse"), "quarter_horse");

    //create layers for coat and armour
    public QuarterHorseRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new QuarterHorseModel());
    }
}
