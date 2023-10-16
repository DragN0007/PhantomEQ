package com.phantomskeep.phantomeq.gui;

import com.phantomskeep.phantomeq.entity.AbstractPhantHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.HorseInventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class ContainerEventHandler {
    public static void editContainer(PlayerContainerEvent.Open event) {
        if (!(event.getContainer() instanceof HorseInventoryMenu horseContainer)) {
            return;
        }
        AbstractHorse horse = null;
        try {
            horse = ObfuscationReflectionHelper.getPrivateValue(HorseInventoryMenu.class, horseContainer, "f_39654_");
        }
        catch (ObfuscationReflectionHelper.UnableToAccessFieldException e) {
            System.err.println("Unable to access private value horse while replacing the horse container.");
            System.err.println(e);
        }

        if (!(horse instanceof AbstractPhantHorse horseGenetic)) {
            return;
        }
        replaceSaddleSlot(horseGenetic, horseContainer);
    }

    // Replace the saddle slot with one that accepts alternate saddles
    // This is called both on the server side from here and on the client side from HorseGui
    public static void replaceSaddleSlot(AbstractPhantHorse horse, AbstractContainerMenu container) {
        // This isn't getting called on dedicated servers, even though it is getting called on the server thread of an integrated client
        Slot saddleSlot = new Slot(horse.getHorseChest(), 0, 8, 18) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return horse.isSaddle(stack) && !this.hasItem() && horse.isSaddleable();
            }

            @OnlyIn(Dist.CLIENT)
            public boolean isEnabled() {
                return horse.isSaddleable();
            }
        };
        container.slots.set(0, saddleSlot);
    }
}