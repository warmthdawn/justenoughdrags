package com.warmthdawn.justenoughdrags.compact.mcjty;

import com.warmthdawn.justenoughdrags.jei.GenericGhostHandler;
import mcjty.lib.container.GhostSlot;
import mcjty.lib.gui.GenericGuiContainer;

public class RFToolsGhostHandler<T extends GenericGuiContainer<?>> extends GenericGhostHandler<T> {

    public RFToolsGhostHandler() {
        super(GhostSlot.class);
    }
}
