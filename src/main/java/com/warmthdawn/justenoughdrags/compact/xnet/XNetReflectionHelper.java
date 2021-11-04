package com.warmthdawn.justenoughdrags.compact.xnet;

import WayofTime.bloodmagic.tile.container.ContainerItemRoutingNode;
import com.warmthdawn.justenoughdrags.compact.Enables;
import mcjty.lib.gui.events.BlockRenderEvent;
import mcjty.lib.gui.widgets.BlockRender;
import mcjty.lib.gui.widgets.Panel;
import mcjty.xnet.blocks.controller.gui.AbstractEditorPanel;
import mcjty.xnet.blocks.controller.gui.GuiController;
import mezz.jei.api.gui.IGhostIngredientHandler;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tuple;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class XNetReflectionHelper {
    private static Field fieldConnectorEditPanel;

    private static Field fieldSelectionEvents;

    private static Class<?> classGhostSelectionEvent;
    private static Field editorPanelVal;
    private static Field tagVal;

    private static Method methodUpdate;


    public static void init() {
        if (Enables.XNET) {
            try {
                classGhostSelectionEvent = Class.forName("mcjty.xnet.blocks.controller.gui.AbstractEditorPanel$1");
                editorPanelVal = classGhostSelectionEvent.getDeclaredField("this$0");
                tagVal = classGhostSelectionEvent.getDeclaredField("val$tag");
                editorPanelVal.setAccessible(true);
                tagVal.setAccessible(true);

                fieldConnectorEditPanel = GuiController.class.getDeclaredField("connectorEditPanel");
                fieldConnectorEditPanel.setAccessible(true);
                fieldSelectionEvents = BlockRender.class.getDeclaredField("selectionEvents");
                fieldSelectionEvents.setAccessible(true);
                methodUpdate = AbstractEditorPanel.class.getDeclaredMethod("update", String.class, Object.class);
                methodUpdate.setAccessible(true);

            } catch (NoSuchMethodException | NoSuchFieldException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static Panel getConnectorEditPanel(GuiController obj) {
        try {
            if (fieldConnectorEditPanel != null) {
                Object o = fieldConnectorEditPanel.get(obj);
                if (o instanceof Panel) {
                    return (Panel) o;
                }
            }
        } catch (IllegalAccessException | IllegalArgumentException ignored) {

        }
        return null;
    }

    public static void updateEditorPanel(AbstractEditorPanel obj, String tag, Object value) {
        try {
            if (methodUpdate != null) {
                methodUpdate.invoke(obj, tag, value);
            }
        } catch (IllegalAccessException | InvocationTargetException ignored) {

        }
    }


    public static Tuple<AbstractEditorPanel, String> findGhostSlotInfo(BlockRender obj) {

        try {
            if (fieldSelectionEvents != null) {
                Object o = fieldSelectionEvents.get(obj);
                if (o instanceof List<?>) {
                    for (Object event : ((List<?>) o)) {
                        if (!classGhostSelectionEvent.isInstance(event)) {
                            continue;
                        }
                        Object editorPanel = editorPanelVal.get(event);
                        Object tag = tagVal.get(event);
                        if (editorPanel instanceof AbstractEditorPanel && tag instanceof String) {
                            return new Tuple<>((AbstractEditorPanel) editorPanel, (String) tag);
                        }
                        break;
                    }

                }
            }
        } catch (IllegalAccessException | IllegalArgumentException ignored) {

        }
        return null;
    }
}
