package com.phantomskeep.phantomeq.entity.horse.warmblood.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.entity.horse.warmblood.Warmblood;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class WarmbloodDetailLayer extends GeoLayerRenderer<Warmblood> {
    public WarmbloodDetailLayer(IGeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Warmblood entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType renderType = RenderType.entityCutout(((Warmblood)entityLivingBaseIn).getDetailLocation());
        matrixStackIn.pushPose();
        matrixStackIn.scale(1.0f, 1.0f, 1.0f);
        matrixStackIn.translate(0.0d, 0.0d, 0.0d);
        this.getRenderer().render(
                this.getEntityModel().getModel(this.getEntityModel().getModelLocation(entityLivingBaseIn)),
                entityLivingBaseIn,
                partialTicks,
                renderType,
                matrixStackIn,
                bufferIn,
                bufferIn.getBuffer(renderType), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
        matrixStackIn.popPose();
    }

    public enum DetailOverlay {
        PLACEHOLDER(new ResourceLocation(PhantomEQ.MODID, "textures/entity/horse/overlay/overlay_appaloosa.png"));

        //Add new entries to bottom when mod is public, else horses will change textures during update.

        public final ResourceLocation resourceLocation;
        DetailOverlay(ResourceLocation resourceLocation) {
            this.resourceLocation = resourceLocation;
        }

        public static DetailOverlay detailOverlayFromOrdinal(int detailOverlay) { return DetailOverlay.values()[detailOverlay % DetailOverlay.values().length];
        }
    }

}
