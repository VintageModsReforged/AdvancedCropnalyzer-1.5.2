package dev.vintage.cropnalyzer;

import mods.vintage.core.helpers.ConfigHelper;
import mods.vintage.core.platform.config.ItemBlockID;
import mods.vintage.core.platform.lang.LocalizationProvider;
import net.minecraftforge.common.Configuration;

@LocalizationProvider
public class AdvancedCropnalyzerConfig extends Configuration {

    @LocalizationProvider.List(modId = AdvancedCropnalyzer.ID)
    public static String[] LANGS;

    public static ItemBlockID CROPNALYZER_ID = ItemBlockID.ofItem("advanced_cropnalyzer", 12001);

    public AdvancedCropnalyzerConfig() {
        super(ConfigHelper.getConfigFileFor(AdvancedCropnalyzer.ID));
        load();
        LANGS = ConfigHelper.getLocalizations(this, new String[] { "en_US", "ru_RU" }, AdvancedCropnalyzer.ID);
        if (this.hasChanged()) this.save();
    }
}
