package com.warmthdawn.justenoughdrags.compact.bm2;

import WayofTime.bloodmagic.client.gui.GuiItemRoutingNode;
import WayofTime.bloodmagic.item.routing.IRoutingFilterProvider;
import WayofTime.bloodmagic.util.GhostItemHelper;
import com.warmthdawn.justenoughdrags.jei.GenericGhostHandler;
import com.warmthdawn.justenoughdrags.network.NetworkHandler;
import com.warmthdawn.justenoughdrags.network.PacketSetContainerSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RoutingNodeGhostHandler extends GenericGhostHandler<GuiItemRoutingNode> {
    public RoutingNodeGhostHandler() {
        super(null);
    }

    @Override
    public boolean isSlotValid(Slot slot, ItemStack stack, boolean doStart) {
        return BMReflectionHelper.instanceOfGhostSlot(slot.getClass()) && BMReflectionHelper.canBeAccessed(slot);
    }

    @Override
    public <I> List<Target<I>> getTargets(GuiItemRoutingNode gui, I ingredient, boolean doStart) {
        List<Target<I>> targets = new ArrayList<>();
        if (ingredient instanceof ItemStack) {
            ItemStack stack = ((ItemStack) ingredient).copy();
            List<Slot> inventory = gui.inventorySlots.inventorySlots;
            if (inventory.size() > 1) {
                ItemStack filterStack = inventory.get(0).getStack();
                for (int i = 1; i < inventory.size(); i++) {
                    Slot slot = inventory.get(i);
                    if (isSlotValid(slot, stack, doStart)) {
                        targets.add(new GhostTarget<>(filterStack, slot, gui.getGuiLeft(), gui.getGuiTop()));
                    }
                }
            }
        }
        lastContainer = gui.inventorySlots;
        return targets;
    }

    private static class GhostTarget<I> extends GenericGhostHandler.GhostTarget<I> {
        private final ItemStack filterStack;

        public GhostTarget(ItemStack filterStack, Slot slot, int xoff, int yoff) {
            super(slot, xoff, yoff);
            this.filterStack = filterStack;
        }

        @Override
        public void accept(I ingredient) {
            if (ingredient instanceof ItemStack) {
                ItemStack stack = ((ItemStack) ingredient).copy();
                if (filterStack.getItem() instanceof IRoutingFilterProvider) {
                    stack = ((IRoutingFilterProvider) filterStack.getItem())
                            .getContainedStackForItem(filterStack, stack);
                } else {
                    GhostItemHelper.setItemGhostAmount(stack, 0);
                    stack.setCount(1);
                }

                slot.putStack(stack);
                slot.onSlotChanged();
                NetworkHandler.INSTANCE.sendToServer(new PacketSetContainerSlot(slot.slotNumber, stack));

            }
        }
    }

}
