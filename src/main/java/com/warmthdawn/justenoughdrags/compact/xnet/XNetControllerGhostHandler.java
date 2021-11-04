package com.warmthdawn.justenoughdrags.compact.xnet;

import mcjty.lib.gui.Window;
import mcjty.lib.gui.widgets.AbstractContainerWidget;
import mcjty.lib.gui.widgets.BlockRender;
import mcjty.lib.gui.widgets.Panel;
import mcjty.lib.gui.widgets.Widget;
import mcjty.xnet.blocks.controller.gui.AbstractEditorPanel;
import mcjty.xnet.blocks.controller.gui.ConnectorEditorPanel;
import mcjty.xnet.blocks.controller.gui.GuiController;
import mezz.jei.api.gui.IGhostIngredientHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XNetControllerGhostHandler implements IGhostIngredientHandler<GuiController> {


    private static boolean findWidgetPosition(Panel parent, Widget<?> target, Rectangle out) {
        for (Widget<?> child : parent.getChildren()) {
            if (target == child) {
                out.setBounds(target.getBounds().x + 1, target.getBounds().y + 1, 14, 14);
                return true;
            }
            if (child instanceof Panel) {
                boolean hasTarget = findWidgetPosition((Panel) child, target, out);
                if (hasTarget) {
                    out.x += child.getBounds().x;
                    out.y += child.getBounds().y;
                    return true;
                }
            }
        }
        return false;
    }

    private static Rectangle findWidgetPosition(Widget<?> target) {
        Widget<?> toplevel = target.getWindow().getToplevel();
        if (!(toplevel instanceof Panel)) {
            return null;
        }
        Rectangle result = new Rectangle();
        if (findWidgetPosition((Panel) toplevel, target, result)) {
            result.x += toplevel.getBounds().x;
            result.y += toplevel.getBounds().y;
            return result;
        }
        return null;
    }

    @Override
    public <I> List<Target<I>> getTargets(GuiController gui, I ingredient, boolean doStart) {
        if (!(ingredient instanceof ItemStack)) {
            return Collections.emptyList();
        }
        List<Target<I>> result = new ArrayList<>();
        Panel panel = XNetReflectionHelper.getConnectorEditPanel(gui);
        if (panel == null) {
            return result;
        }
        for (Widget<?> child : panel.getChildren()) {
            if (!(child instanceof BlockRender)) {
                continue;
            }
            if (!child.isEnabledAndVisible()) {
                continue;
            }

            BlockRender blockRender = (BlockRender) child;
            Rectangle position = findWidgetPosition(blockRender);
            if (position == null) {
                continue;
            }

            Tuple<AbstractEditorPanel, String> ghostSlotInfo = XNetReflectionHelper.findGhostSlotInfo(blockRender);
            if (ghostSlotInfo != null) {

                String tag = ghostSlotInfo.getSecond();
                AbstractEditorPanel editorPanel = ghostSlotInfo.getFirst();
                if (editorPanel instanceof ConnectorEditorPanel) {
                    result.add(new Target<I>() {
                        @Override
                        public Rectangle getArea() {
                            return position;
                        }

                        @Override
                        public void accept(I ingredient) {
                            if (ingredient instanceof ItemStack) {
                                ItemStack stack = ((ItemStack) ingredient).copy();
                                stack.setCount(1);
                                blockRender.setRenderItem(stack);
                                XNetReflectionHelper.updateEditorPanel(editorPanel, tag, stack);
                            }
                        }
                    });
                }

            }
        }


        return result;
    }

    @Override
    public void onComplete() {

    }
}
