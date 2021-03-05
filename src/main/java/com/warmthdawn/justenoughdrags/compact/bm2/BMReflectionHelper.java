package com.warmthdawn.justenoughdrags.compact.bm2;

import WayofTime.bloodmagic.tile.container.ContainerItemRoutingNode;
import com.warmthdawn.justenoughdrags.compact.Enables;
import net.minecraft.inventory.Slot;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class BMReflectionHelper {
    public static Class<?> classSlotGhostItem;
    public static Method methodCanBeAccessed;

    public static void init() {
        if (Enables.BLOOD_MAGIC) {
            Class<?>[] classes = ContainerItemRoutingNode.class.getDeclaredClasses();
            for (Class<?> c : classes) {
                if (Slot.class.isAssignableFrom(c)) {
                    classSlotGhostItem = c;
                    break;
                }
            }
            try {
                if (classSlotGhostItem != null) {
                    methodCanBeAccessed = classSlotGhostItem.getDeclaredMethod("canBeAccessed");
                    methodCanBeAccessed.setAccessible(true);
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> boolean instanceOfGhostSlot(Class<T> cls) {
        return classSlotGhostItem != null && classSlotGhostItem.isAssignableFrom(cls);
    }

    public static boolean canBeAccessed(Object obj) {
        try {
            if (methodCanBeAccessed != null) {
                return Boolean.TRUE.equals(methodCanBeAccessed.invoke(obj));
            }
        } catch (IllegalAccessException | InvocationTargetException ignored) {

        }
        return false;
    }
}
