package com.phantomskeep.phantomeq.gui;

import com.phantomskeep.phantomeq.entity.AbstractPhantHorse;
import com.phantomskeep.phantomeq.model.modeldata.HorseModelData;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class PhantEQContainer extends AbstractContainerMenu {
    private Container inventory;
    public HorseModelData modelData;
    public AbstractPhantHorse phantHorse;
    public int numberOfEquipmentSlots = 0;
    public int totalNumberOfAnimalSlots = 0;

    public PhantEQContainer (int p_i50066_1_, Inventory playerInventoryIn, HorseModelData modelData) {
        super(EventRegistry.PHANTEQ_CONTAINER, p_i50066_1_);
        SimpleContainer retrievedInventory = phantHorse.getEnhancedInventory();
    }
}
