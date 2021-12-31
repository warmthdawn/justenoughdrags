package com.warmthdawn.justenoughdrags;

import com.warmthdawn.justenoughdrags.compact.Enables;
import com.warmthdawn.justenoughdrags.compact.bm2.BMReflectionHelper;
import com.warmthdawn.justenoughdrags.compact.xnet.XNetReflectionHelper;
import com.warmthdawn.justenoughdrags.network.NetworkHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@Mod(modid = JustEnoughDrags.MODID,
    name = JustEnoughDrags.NAME,
    version = JustEnoughDrags.VERSION
)
public class JustEnoughDrags {
    public static final String MODID = "justenoughdrags";
    public static final String NAME = "Just Enough Drags";
    public static final String VERSION = "@VERSION@";

    private static Logger logger;

    @Mod.Instance
    public static JustEnoughDrags INSTANCE;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        Enables.init();
        if(FMLCommonHandler.instance().getSide().isClient()) {
            BMReflectionHelper.init();
            XNetReflectionHelper.init();
        }
    }


    private boolean serverInstalled;

    public boolean isServerInstalled() {
        return serverInstalled;
    }

    @NetworkCheckHandler
    public boolean checkModLists(Map<String, String> modList, Side side) {
        if (side == Side.SERVER) {
            serverInstalled = modList.containsKey(MODID);
        }

        return true;
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        NetworkHandler.registerMessages(MODID);
    }
}
