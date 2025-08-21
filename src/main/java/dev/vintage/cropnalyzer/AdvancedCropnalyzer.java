package dev.vintage.cropnalyzer;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dev.vintage.cropnalyzer.item.ItemAdvancedAnalyzer;
import ic2.api.item.Items;
import mods.vintage.core.platform.config.ConfigHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;

import static dev.vintage.cropnalyzer.AdvancedCropnalyzerConfig.CROPNALYZER_ID;

@Mod(modid = AdvancedCropnalyzer.ID, name = AdvancedCropnalyzer.NAME, useMetadata = true, dependencies = AdvancedCropnalyzer.DEPS)
public class AdvancedCropnalyzer {

    public static final String ID = "advancedcropnalyzer";
    public static final String NAME = "IC2: Advanced Cropnalyzer";
    public static final String DEPS = "required-after:VintageCore;required-after:IC2";

    ConfigHandler CONFIG_HANDLER = new ConfigHandler(ID);
    public static Configuration CONFIG;

    public static Item ADVANCED_CROPNALYZER;

    public AdvancedCropnalyzer() {}

    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent e) {
        CONFIG = new AdvancedCropnalyzerConfig();
        CONFIG_HANDLER.initIDs(CONFIG);
    }

    @Mod.Init
    public void init(FMLInitializationEvent e) {
        CONFIG_HANDLER.confirmIDs(CONFIG);
        ADVANCED_CROPNALYZER = new ItemAdvancedAnalyzer(CROPNALYZER_ID.get());
        NEIConfig.init();
        GameRegistry.addRecipe(new ShapedOreRecipe(ADVANCED_CROPNALYZER,
                " D ", "RCR", "LRL",
                'D', Item.diamond,
                'R', Item.redstone,
                'L', new ItemStack(Item.dyePowder, 1, 4),
                'C', Items.getItem("cropnalyzer")));
    }

    @Mod.PostInit
    public void postInit(FMLPostInitializationEvent e) {
        CONFIG_HANDLER.confirmOwnership(CONFIG);
    }
}
