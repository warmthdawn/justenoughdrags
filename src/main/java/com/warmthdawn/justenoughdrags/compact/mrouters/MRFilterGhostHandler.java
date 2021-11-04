package com.warmthdawn.justenoughdrags.compact.mrouters;

import com.warmthdawn.justenoughdrags.jei.GenericGhostHandler;
import me.desht.modularrouters.client.gui.widgets.GuiContainerBase;
import me.desht.modularrouters.container.handler.GhostItemHandler;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class MRFilterGhostHandler<T extends GuiContainerBase> extends GenericGhostHandler<T> {
    public <I extends Slot> MRFilterGhostHandler() {
        super(null);
    }

    @Override
    public boolean isSlotValid(Slot slot, ItemStack stack, boolean doStart) {
        if (!(slot instanceof SlotItemHandler)) {
            return false;
        }
        SlotItemHandler handlerSlot = (SlotItemHandler) slot;
        return handlerSlot.getItemHandler() instanceof GhostItemHandler;
    }
}
