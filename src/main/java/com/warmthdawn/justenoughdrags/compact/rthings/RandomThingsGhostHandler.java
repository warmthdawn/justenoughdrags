package com.warmthdawn.justenoughdrags.compact.rthings;

import com.warmthdawn.justenoughdrags.jei.GenericGhostHandler;
import lumien.randomthings.client.gui.GuiItemFilter;
import lumien.randomthings.container.slots.SlotGhost;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author WarmthDawn
 * @since 2021-03-05
 */
public class RandomThingsGhostHandler extends GenericGhostHandler<GuiItemFilter> {
    public RandomThingsGhostHandler() {
        super(null);
    }

    @Override
    public boolean isSlotValid(Slot slot, ItemStack stack, boolean doStart) {
        return slot instanceof SlotGhost;
    }
}
