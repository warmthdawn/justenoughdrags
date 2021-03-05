package com.warmthdawn.justenoughdrags.compact.ae2;

import appeng.client.gui.AEBaseGui;
import appeng.container.slot.OptionalSlotFakeTypeOnly;
import appeng.container.slot.SlotFakeTypeOnly;
import com.warmthdawn.justenoughdrags.jei.GenericGhostHandler;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class AEGenericGhostHandler<T extends AEBaseGui> extends GenericGhostHandler<T> {
    public AEGenericGhostHandler() {
        super(null);
    }

    @Override
    public boolean isSlotValid(Slot slot, ItemStack stack, boolean doStart) {
        if (slot instanceof SlotFakeTypeOnly) {
            return true;
        } else if (slot instanceof OptionalSlotFakeTypeOnly) {
            return ((OptionalSlotFakeTypeOnly) slot).isSlotEnabled();
        }
        return false;
    }
}
