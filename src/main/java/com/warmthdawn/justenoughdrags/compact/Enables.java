package com.warmthdawn.justenoughdrags.compact;

import net.minecraftforge.fml.common.Loader;

public class Enables {
    public static boolean REFINED_STORAGE = false;
    public static boolean MODULAR_ROUTERS = false;
    public static boolean ACTUALLY_ADDITIONS = false;
    public static boolean APPLIED_ENERGISTICS = false;
    public static boolean THERMAL_DYNAMICS = false;
    public static boolean CRAFT_TWEAKER = false;
    public static boolean BLOOD_MAGIC = false;
    public static boolean RFTOOLS = false;
    public static boolean TRANSLOCATORS = false;
    public static boolean RANDOM_THINGS = false;
    public static boolean XNET = false;

    public static void init() {
        Enables.CRAFT_TWEAKER = Loader.isModLoaded("crafttweaker");
        Enables.THERMAL_DYNAMICS = Loader.isModLoaded("thermaldynamics");
        Enables.APPLIED_ENERGISTICS = Loader.isModLoaded("appliedenergistics2");
        Enables.ACTUALLY_ADDITIONS = Loader.isModLoaded("actuallyadditions");
        Enables.BLOOD_MAGIC = Loader.isModLoaded("bloodmagic");
        Enables.TRANSLOCATORS = Loader.isModLoaded("translocators");
        Enables.RANDOM_THINGS = Loader.isModLoaded("randomthings");
        Enables.RFTOOLS = Loader.isModLoaded("rftools");
        Enables.MODULAR_ROUTERS = Loader.isModLoaded("modularrouters");
        Enables.XNET = Loader.isModLoaded("xnet");
        Enables.REFINED_STORAGE = Loader.isModLoaded("refinedstorage");
    }
}
