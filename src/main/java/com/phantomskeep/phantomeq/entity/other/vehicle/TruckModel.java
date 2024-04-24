package com.phantomskeep.phantomeq.entity.other.vehicle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class TruckModel<T extends Entity> extends EntityModel<T> {

	private final ModelPart Body;
	private final ModelPart FrontWheels;
	private final ModelPart BackWheels;

	public TruckModel(ModelPart root) {
		this.Body = root.getChild("Body");
		this.FrontWheels = root.getChild("FrontWheels");
		this.BackWheels = root.getChild("BackWheels");
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		FrontWheels.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		BackWheels.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-22.0F, -12.0F, -47.0F, 44.0F, 5.0F, 102.0F, new CubeDeformation(0.0F))
				.texOffs(0, 108).addBox(3.0F, -14.0F, -9.0F, 16.0F, 2.0F, 17.0F, new CubeDeformation(0.0F))
				.texOffs(80, 140).addBox(3.0F, -27.0F, 8.0F, 16.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(80, 120).addBox(-19.0F, -14.0F, -9.0F, 16.0F, 2.0F, 17.0F, new CubeDeformation(0.0F))
				.texOffs(117, 140).addBox(-19.0F, -27.0F, 8.0F, 16.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(31, 86).addBox(17.0F, -20.1242F, 27.8423F, 6.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(226, 35).addBox(19.5F, -37.0F, -16.0F, 0.0F, 12.0F, 26.0F, new CubeDeformation(0.0F))
				.texOffs(209, 194).addBox(-19.5F, -37.0F, -16.0F, 0.0F, 12.0F, 26.0F, new CubeDeformation(0.0F))
				.texOffs(0, 86).addBox(-23.0F, -20.1242F, 27.8423F, 6.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(236, 192).addBox(-23.0F, -20.1242F, -25.1577F, 46.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(160, 108).addBox(-20.0F, -25.0F, -44.0F, 40.0F, 15.0F, 35.0F, new CubeDeformation(0.0F))
				.texOffs(210, 233).addBox(-15.0F, -26.0F, -43.0F, 30.0F, 1.0F, 26.0F, new CubeDeformation(0.0F))
				.texOffs(80, 124).addBox(19.0F, -25.0F, -21.0F, 2.0F, 15.0F, 75.0F, new CubeDeformation(0.0F))
				.texOffs(72, 199).addBox(18.9F, -36.6F, 9.0F, 1.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(58, 199).addBox(-19.9F, -36.6F, 9.0F, 1.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 108).addBox(-21.0F, -25.0F, -21.0F, 2.0F, 15.0F, 75.0F, new CubeDeformation(0.0F))
				.texOffs(266, 174).addBox(-19.0F, -25.0F, 51.0F, 38.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 128).addBox(-13.0F, -25.0F, -46.0F, 26.0F, 17.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(15, 230).addBox(-20.0F, -39.5255F, -2.9056F, 40.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = Body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(37, 0).addBox(-20.0F, -4.5F, 4.5F, 3.0F, 3.0F, 26.0F, new CubeDeformation(0.0F))
				.texOffs(37, 34).addBox(17.0F, -4.5F, 4.5F, 3.0F, 3.0F, 26.0F, new CubeDeformation(0.0F))
				.texOffs(191, 74).addBox(-19.0F, -3.6F, 4.4F, 38.0F, 0.0F, 26.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.5F, -23.5F, 0.6981F, 0.0F, 0.0F));

		PartDefinition cube_r2 = Body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, 49).addBox(17.0F, 26.8F, -9.0F, 3.0F, 2.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(14, 14).addBox(-20.0F, 26.8F, -9.0F, 3.0F, 2.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -30.197F, 38.847F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r3 = Body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(278, 124).addBox(-19.0F, -8.5F, 13.0F, 38.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -38.0255F, 4.0944F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r4 = Body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(182, 187).addBox(-23.0F, -30.3F, -23.5F, 6.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(27, 199).addBox(17.0F, -30.3F, -23.5F, 6.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.5F, 59.0F, 0.7418F, 0.0F, 0.0F));

		PartDefinition cube_r5 = Body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(62, 86).addBox(-23.0F, -2.5F, -16.0F, 6.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
				.texOffs(160, 184).addBox(17.0F, -2.5F, -16.0F, 6.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.5F, 47.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition Window_r1 = Body.addOrReplaceChild("Window_r1", CubeListBuilder.create().texOffs(128, 248).addBox(-17.0F, 29.0F, -8.5F, 34.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -29.6328F, 39.7413F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r6 = Body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(236, 204).addBox(-23.0F, -30.3F, -23.5F, 46.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.5F, 6.0F, 0.7418F, 0.0F, 0.0F));

		PartDefinition cube_r7 = Body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(80, 108).addBox(-23.0F, -2.5F, -16.0F, 46.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.5F, -6.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition cube_r8 = Body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(118, 224).addBox(-2.0F, -1.65F, 2.5F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(120, 226).addBox(-2.0F, -1.65F, 0.5F, 4.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.25F, 59.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition FrontWheels = partdefinition.addOrReplaceChild("FrontWheels", CubeListBuilder.create().texOffs(187, 159).addBox(13.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(187, 159).addBox(-27.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(253, 11).addBox(-26.5F, -2.5F, -2.5F, 45.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 15.5F, -20.5F));

		PartDefinition cube_r9 = FrontWheels.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(187, 159).addBox(-2.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(187, 159).addBox(-42.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r10 = FrontWheels.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(187, 159).addBox(-2.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(187, 159).addBox(-42.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r11 = FrontWheels.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(187, 159).addBox(-2.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(187, 159).addBox(-42.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));

		PartDefinition BackWheels = partdefinition.addOrReplaceChild("BackWheels", CubeListBuilder.create().texOffs(187, 159).addBox(13.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(187, 159).addBox(-27.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(253, 0).addBox(-26.4F, -2.5F, -2.5F, 45.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 15.5F, 32.5F));

		PartDefinition cube_r12 = BackWheels.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(187, 159).addBox(-2.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(187, 159).addBox(-42.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r13 = BackWheels.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(187, 159).addBox(-2.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(187, 159).addBox(-42.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r14 = BackWheels.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(187, 159).addBox(-2.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(187, 159).addBox(-42.0F, -8.5F, -3.5F, 6.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(15.0F, 0.0F, 0.0F, -2.3562F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}
}