package com.phantomskeep.phantomeq.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.phantomskeep.phantomeq.PhantomEQ;
import com.phantomskeep.phantomeq.config.PhantomEQCommonConfig;
import com.phantomskeep.phantomeq.entity.AbstractPhantHorse;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.HorseInventoryMenu;

//Borrowed from RHG
public class PhantHorseGUI extends HorseInventoryScreen {
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(PhantomEQ.MODID, "textures/gui/peqhorse.png");
    private final AbstractPhantHorse phantHorse;

    public PhantHorseGUI(HorseInventoryMenu container, Inventory playerInv, AbstractPhantHorse horse) {
        super(container, playerInv, horse);
        this.phantHorse = horse;
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F ,1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        this.blit(stack, x, y, 0, 0, this.imageWidth, this.imageHeight);

        /*
        //if it's a chested horse (ie. draft in the future), and has chest, get inventory columns for storage
        if (this.phantHorse != null) {
            AbstractChestedHorse abstractChestedHorse = this.phantHorse;
            if (abstractChestedHorse.hasChest()) {
                this.blit(stack, a + 79, b + 17, 0, this.imageHeight, abstractChestedHorse.getInventoryColumns() * 18, 54);
            }
            */

        //Draw the Saddle slot
        if(this.phantHorse.isSaddleable()) {
            this.blit(stack, x + 7, y + 35 - 18, 18, this.imageHeight +54, 18,18);
        // Also draw the Armour slot if saddleable
            this.blit(stack, x + 7, y + 35, 0, this.imageHeight + 54, 18, 18);
        // And the carpet slot
            this.blit(stack, x + 7, y + 35, 36, this.imageHeight + 54, 18, 18);

        }

        if (PhantomEQCommonConfig.isGenderEnabled()) {
            int iconWidth = 18;
            int iconHeight = 18;
            int textureX = 0;
            int renderX = x + 152;
            int renderY = y + 17;

            if (!(this.phantHorse.isMale())){
                textureX += iconWidth;
            }
            int textureY = 167;

            // If Male, render male icon
            if (this.phantHorse.isMale()) {
                textureX = 18;
            }

            //If Gelded, render other male icon
            if (!phantHorse.isFertile()) {
                textureY = 36;
            }
            this.blit(stack, renderX, renderY, textureX, textureY, iconWidth, iconHeight);
        }

         // InventoryScreen.renderEntityInInventory(a + 51, b + 60, 17, (float)(a + 51) - mouseX, (float)(b + 75 - 50) - mouseY, this.phantHorse);
    }

}
