package dev.vintage.cropnalyzer;

import codechicken.nei.MultiItemRange;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import mods.vintage.core.VintageCore;

public class NEIConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {
        VintageCore.LOGGER.info("Loading NEI Plugin for IC2: Advanced Cropnalyzer!");
        MultiItemRange itemRange = new MultiItemRange();
        itemRange.add(AdvancedCropnalyzer.ADVANCED_CROPNALYZER.itemID);
        API.addSetRange("IC2.Addons.Advanced Cropnalyzer", itemRange);
        VintageCore.LOGGER.info("NEI Plugin for IC2: Advanced Cropnalyzer Loaded!");
    }

    @Override
    public String getName() {
        return AdvancedCropnalyzer.NAME;
    }

    @Override
    public String getVersion() {
        return "1.5.2-1.0.0";
    }
}
