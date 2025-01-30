package com.phantomskeep.phantomeq.entity.horse.warmblood;

import com.mojang.blaze3d.vertex.PoseStack;
import com.phantomskeep.phantomeq.entity.horse.warmblood.layer.WarmbloodDetailLayer;
import com.phantomskeep.phantomeq.entity.horse.warmblood.layer.WarmbloodPatternLayer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.ExtendedGeoEntityRenderer;

public class WarmBloodRender extends ExtendedGeoEntityRenderer<Warmblood> {
    public WarmBloodRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new WarmbloodModel());
        this.addLayer(new WarmbloodPatternLayer(this));
        this.addLayer(new WarmbloodDetailLayer(this));
    }

    @Override
    protected boolean isArmorBone(GeoBone bone) {
        return false;
    }

    @Nullable
    @Override
    protected ResourceLocation getTextureForBone(String boneName, Warmblood animatable) {
        return null;
    }

    @Nullable
    @Override
    protected ItemStack getHeldItemForBone(String boneName, Warmblood animatable) {
        return null;
    }

    @Override
    protected ItemTransforms.TransformType getCameraTransformForItemAtBone(ItemStack stack, String boneName) {
        return null;
    }

    @Nullable
    @Override
    protected BlockState getHeldBlockForBone(String boneName, Warmblood animatable) {
        return null;
    }

    @Override
    protected void preRenderItem(PoseStack poseStack, ItemStack stack, String boneName, Warmblood animatable, IBone bone) {

    }

    @Override
    protected void preRenderBlock(PoseStack poseStack, BlockState state, String boneName, Warmblood animatable) {

    }

    @Override
    protected void postRenderItem(PoseStack poseStack, ItemStack stack, String boneName, Warmblood animatable, IBone bone) {

    }

    @Override
    protected void postRenderBlock(PoseStack poseStack, BlockState state, String boneName, Warmblood animatable) {

    }
}
