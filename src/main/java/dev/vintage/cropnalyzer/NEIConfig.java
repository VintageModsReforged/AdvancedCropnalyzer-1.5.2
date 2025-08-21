package dev.vintage.cropnalyzer;

import mods.vintage.core.VintageCore;
import mods.vintage.core.helpers.nei.NEIHelper;

public class NEIConfig {

    public static void init() {
        VintageCore.LOGGER.info("Loading NEI Plugin for IC2: Advanced Cropnalyzer!");
        NEIHelper.addCategory("IC2.Addons.Advanced Cropnalyzer", AdvancedCropnalyzer.ADVANCED_CROPNALYZER.itemID);
        VintageCore.LOGGER.info("NEI Plugin for IC2: Advanced Cropnalyzer Loaded!");
    }
}
