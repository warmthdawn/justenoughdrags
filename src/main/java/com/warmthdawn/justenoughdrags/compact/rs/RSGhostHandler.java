package com.warmthdawn.justenoughdrags.compact.rs;

import com.raoulvdberge.refinedstorage.RS;
import com.raoulvdberge.refinedstorage.container.ContainerGrid;
import com.raoulvdberge.refinedstorage.container.slot.filter.SlotFilter;
import com.raoulvdberge.refinedstorage.container.slot.filter.SlotFilterFluid;
import com.raoulvdberge.refinedstorage.container.slot.legacy.SlotLegacyFilter;
import com.raoulvdberge.refinedstorage.gui.GuiBase;
import com.raoulvdberge.refinedstorage.gui.grid.GuiGrid;
import com.raoulvdberge.refinedstorage.network.MessageSlotFilterSet;
import com.raoulvdberge.refinedstorage.network.MessageSlotFilterSetFluid;
import com.raoulvdberge.refinedstorage.util.StackUtils;
import com.warmthdawn.justenoughdrags.compact.ae2.AEFluidGhostHandler;
import mezz.jei.api.gui.IGhostIngredientHandler;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class RSGhostHandler implements IGhostIngredientHandler<GuiBase> {

    @Override
    public <I> List<Target<I>> getTargets(GuiBase gui, I ingredient, boolean doStart) {


        List<Target<I>> targets = new ArrayList<>();

        //Only shift for pattern grid
        boolean isShiftDown = ((Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)));
        if (gui instanceof GuiGrid && !isShiftDown) {
            return targets;
        }


        for (Slot slot : gui.inventorySlots.inventorySlots) {
            if (!slot.isEnabled()) {
                continue;
            }


            Rectangle bounds = new Rectangle(gui.getGuiLeft() + slot.xPos, gui.getGuiTop() + slot.yPos, 17, 17);
            if (ingredient instanceof ItemStack && (slot instanceof SlotLegacyFilter || slot instanceof SlotFilter)) {
                targets.add(new Target<I>() {
                    @Override
                    public Rectangle getArea() {
                        return bounds;
                    }

                    @Override
                    public void accept(I ing) {
                        slot.putStack((ItemStack) ing);

                        RS.INSTANCE.network.sendToServer(new MessageSlotFilterSet(slot.slotNumber, (ItemStack) ing));
                    }
                });
            } else {
                FluidStack fluid = null;
                if (ingredient instanceof ItemStack) {
                    ItemStack stack = ((ItemStack) ingredient).copy();
                    fluid = FluidUtil.getFluidContained(stack);
                }
                if (ingredient instanceof FluidStack) {
                    fluid = (FluidStack) ingredient;
                }
                if (fluid != null) {
                    if (slot instanceof SlotFilterFluid) {
                        targets.add(new Target<I>() {
                            @Override
                            public Rectangle getArea() {
                                return bounds;
                            }

                            @Override
                            public void accept(I ing) {
                                FluidStack fluid = null;
                                if (ing instanceof ItemStack) {
                                    ItemStack stack = ((ItemStack) ing).copy();
                                    fluid = FluidUtil.getFluidContained(stack);
                                }
                                if (ing instanceof FluidStack) {
                                    fluid = (FluidStack) ing;
                                }
                                if (fluid != null) {
                                    RS.INSTANCE.network.sendToServer(new MessageSlotFilterSetFluid(slot.slotNumber, StackUtils.copy(fluid, Fluid.BUCKET_VOLUME)));

                                }

                            }
                        });
                    }
                }
            }

        }

        return targets;
    }

    @Override
    public void onComplete() {
        // NO OP
    }
}