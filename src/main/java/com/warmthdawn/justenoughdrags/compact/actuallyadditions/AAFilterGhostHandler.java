package com.warmthdawn.justenoughdrags.compact.actuallyadditions;


import com.warmthdawn.justenoughdrags.jei.GenericGhostHandler;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiWtfMojang;
import de.ellpeck.actuallyadditions.mod.inventory.slot.SlotFilter;
import de.ellpeck.actuallyadditions.mod.util.StackUtil;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class AAFilterGhostHandler<T extends GuiWtfMojang> extends GenericGhostHandler<T> {
    public AAFilterGhostHandler() {
        super(null);
    }

    @Override
    public boolean isSlotValid(Slot slot, ItemStack stack, boolean doStart) {
        if (slot instanceof SlotFilter) {
            ItemStack stackInSlot = slot.getStack();
            return StackUtil.isValid(stack) && !SlotFilter.isFilter(stack) && !SlotFilter.isFilter(stackInSlot);
        }
        return false;
    }


}
