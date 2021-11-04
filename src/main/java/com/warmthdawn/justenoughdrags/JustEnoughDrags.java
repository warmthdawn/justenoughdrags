package com.warmthdawn.justenoughdrags;

import com.warmthdawn.justenoughdrags.compact.Enables;
import com.warmthdawn.justenoughdrags.compact.bm2.BMReflectionHelper;
import com.warmthdawn.justenoughdrags.compact.xnet.XNetReflectionHelper;
import com.warmthdawn.justenoughdrags.network.NetworkHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = JustEnoughDrags.MODID, name = JustEnoughDrags.NAME, version = JustEnoughDrags.VERSION)
public class JustEnoughDrags {
    public static final String MODID = "justenoughdrags";
    public static final String NAME = "Just Enough Drags";
    public static final String VERSION = "@VERSION@";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        Enables.init();
        BMReflectionHelper.init();
        XNetReflectionHelper.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkHandler.registerMessages(MODID);
    }
}
