package com.warmthdawn.justenoughdrags.jei;

import WayofTime.bloodmagic.client.gui.GuiItemRoutingNode;
import appeng.client.gui.implementations.*;
import appeng.fluids.client.gui.*;
import codechicken.lib.inventory.container.SlotDummy;
import cofh.thermaldynamics.gui.client.GuiDuctConnection;
import com.blamejared.ctgui.api.SlotRecipe;
import com.blamejared.ctgui.client.gui.craftingtable.GuiCraftingTable;
import com.warmthdawn.justenoughdrags.compact.Enables;
import com.warmthdawn.justenoughdrags.compact.actuallyadditions.AAFilterGhostHandler;
import com.warmthdawn.justenoughdrags.compact.ae2.*;
import com.warmthdawn.justenoughdrags.compact.bm2.RoutingNodeGhostHandler;
import com.warmthdawn.justenoughdrags.compact.mcjty.RFToolsGhostHandler;
import com.warmthdawn.justenoughdrags.compact.mrouters.MRFilterGhostHandler;
import com.warmthdawn.justenoughdrags.compact.rthings.RandomThingsGhostHandler;
import com.warmthdawn.justenoughdrags.compact.xnet.XNetControllerGhostHandler;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiFilter;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiLaserRelayItemWhitelist;
import de.ellpeck.actuallyadditions.mod.inventory.gui.GuiRangedCollector;
import mcjty.rftools.blocks.itemfilter.GuiItemFilter;
import mcjty.rftools.items.storage.GuiStorageFilter;
import mcjty.xnet.blocks.controller.gui.GuiController;
import me.desht.modularrouters.client.gui.filter.GuiBulkItemFilter;
import me.desht.modularrouters.client.gui.filter.GuiModFilter;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.gui.IGhostIngredientHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiFurnace;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {
    @Override
    public void register(IModRegistry registry) {
        if (Enables.CRAFT_TWEAKER) {
            registry.addGhostIngredientHandler(GuiCraftingTable.class, new GenericGhostHandler<>(SlotRecipe.class));
            registry.addGhostIngredientHandler(GuiFurnace.class, new GenericGhostHandler<>(SlotRecipe.class));
        }
        if (Enables.THERMAL_DYNAMICS)
            registry.addGhostIngredientHandler(GuiDuctConnection.class, new GenericGhostHandler<>(cofh.thermaldynamics.gui.slot.SlotFilter.class));
        if (Enables.APPLIED_ENERGISTICS) {
            registry.addGhostIngredientHandler(GuiPatternTerm.class, new AEPatternGhostHandler());
            registry.addGhostIngredientHandler(GuiLevelEmitter.class, new AEGenericGhostHandler<>());
            registry.addGhostIngredientHandler(GuiStorageBus.class, new AEGenericGhostHandler<>());
            registry.addGhostIngredientHandler(GuiFormationPlane.class, new AEGenericGhostHandler<>());
            registry.addGhostIngredientHandler(GuiCellWorkbench.class, new AEGenericGhostHandler<>());
            registry.addGhostIngredientHandler(GuiInterface.class, new AEInterfaceGhostHandler());
            registry.addGhostIngredientHandler(GuiUpgradeable.class, new AEIOBusGhostHandler());
            registry.addGhostIngredientHandler(GuiFluidIO.class, new AEFluidGhostHandler<>());
            registry.addGhostIngredientHandler(GuiFluidLevelEmitter.class, new AEFluidGhostHandler<>());
            registry.addGhostIngredientHandler(GuiFluidFormationPlane.class, new AEFluidGhostHandler<>());
            registry.addGhostIngredientHandler(GuiFluidStorageBus.class, new AEFluidGhostHandler<>());
            registry.addGhostIngredientHandler(GuiFluidInterface.class, new AEFluidGhostHandler<>());

        }
        if (Enables.ACTUALLY_ADDITIONS) {
            registry.addGhostIngredientHandler(GuiLaserRelayItemWhitelist.class, new AAFilterGhostHandler<>());
            registry.addGhostIngredientHandler(GuiFilter.class, new AAFilterGhostHandler<>());
            registry.addGhostIngredientHandler(GuiRangedCollector.class, new AAFilterGhostHandler<>());
        }
        if (Enables.RFTOOLS) {
            registry.addGhostIngredientHandler(GuiItemFilter.class, new RFToolsGhostHandler<>());
            registry.addGhostIngredientHandler(GuiStorageFilter.class, new RFToolsGhostHandler<>());
        }
        if (Enables.TRANSLOCATORS) {
//            registry.addGhostIngredientHandler(GuiTranslocator.class, new GenericGhostHandler<>(SlotDummy.class));
            registerByName(registry, "codechicken.translocators.client.gui.GuiTranslocator", new GenericGhostHandler<>(SlotDummy.class));
        }
        if (Enables.BLOOD_MAGIC) {
            registry.addGhostIngredientHandler(GuiItemRoutingNode.class, new RoutingNodeGhostHandler());
        }
        if (Enables.RANDOM_THINGS) {
            registry.addGhostIngredientHandler(lumien.randomthings.client.gui.GuiItemFilter.class, new RandomThingsGhostHandler());
        }

        if (Enables.MODULAR_ROUTERS) {
            registry.addGhostIngredientHandler(GuiBulkItemFilter.class, new MRFilterGhostHandler<>());
            registry.addGhostIngredientHandler(GuiModFilter.class, new MRFilterGhostHandler<>());
        }

        if (Enables.XNET) {
            registry.addGhostIngredientHandler(GuiController.class, new XNetControllerGhostHandler());
        }

    }


    @SuppressWarnings("unchecked")
    private <T extends GuiScreen> void registerByName(IModRegistry registry, String className, IGhostIngredientHandler<T> handler) {
        try {
            registry.addGhostIngredientHandler(
                (Class<T>) Class.forName(className),
                handler);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
