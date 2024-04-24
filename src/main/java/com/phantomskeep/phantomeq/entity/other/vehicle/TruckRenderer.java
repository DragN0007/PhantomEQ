package com.phantomskeep.phantomeq.entity.other.vehicle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.phantomskeep.phantomeq.PhantomEQ;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TruckRenderer extends EntityRenderer<Truck> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(PhantomEQ.MODID, "truck"), "main");


    private final Model model;

    public TruckRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new TruckModel<>(context.bakeLayer(LAYER_LOCATION));
    }

    @Override
    public void render(Truck truck, float rotation, float p_114487_, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();

        poseStack.scale(-1, -1, 1);
        poseStack.translate(0, -1.5, 0);

        poseStack.mulPose(Vector3f.YP.rotationDegrees(rotation - 180));

        VertexConsumer vertexConsumer = bufferSource.getBuffer(model.renderType(truck.getTextureLocation()));
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        poseStack.popPose();
        super.render(truck, rotation, p_114487_, poseStack, bufferSource, packedLight);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(Truck truck) {
        return truck.getTextureLocation();
    }
}
