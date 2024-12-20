package dev.vintage.cropnalyzer;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.FMLInjectionData;
import dev.vintage.cropnalyzer.item.ItemAdvancedAnalyzer;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.Items;
import ic2.core.util.StackUtil;
import mods.vintage.core.helpers.ConfigHelper;
import mods.vintage.core.platform.lang.ILangProvider;
import mods.vintage.core.platform.lang.LangManager;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Mod(modid = AdvancedCropnalyzer.ID, name = AdvancedCropnalyzer.NAME, version = AdvancedCropnalyzer.VERSION, acceptedMinecraftVersions = AdvancedCropnalyzer.MINECRAFT, dependencies = AdvancedCropnalyzer.DEPS)
public class AdvancedCropnalyzer implements ILangProvider {

    public static final String ID = "advancedcropnalyzer";
    public static final String NAME = "IC2: Advanced Cropnalyzer";
    public static final String VERSION = "1.5.2-1.0.2";
    public static final String MINECRAFT = "[1.5.2]";
    public static final String DEPS = "required-after:VintageCore;required-after:IC2";

    public static Configuration CONFIG;
    public static int CROPNALYZER_ID = 12001;
    public static String[] LABGS;

    public static Item ADVANCED_CROPNALYZER;

    public AdvancedCropnalyzer() {}

    @Mod.PreInit
    public void preInit(FMLPreInitializationEvent e) {
        initConfig();
        LangManager.THIS.registerLangProvider(this);
        ADVANCED_CROPNALYZER = new ItemAdvancedAnalyzer(CROPNALYZER_ID);
    }

    @Mod.Init
    public void init(FMLInitializationEvent e) {
        GameRegistry.addRecipe(new ShapedOreRecipe(ADVANCED_CROPNALYZER,
                " D ", "RCR", "LRL",
                'D', Item.diamond,
                'R', Item.redstone,
                'L', new ItemStack(Item.dyePowder, 1, 4),
                'C', Items.getItem("cropnalyzer")));
    }

    public void initConfig() {
        CONFIG = new Configuration(new File((File) FMLInjectionData.data()[6], "config/advancedcropnalyzer.cfg"));
        CONFIG.load();
        CROPNALYZER_ID = ConfigHelper.getId(CONFIG, "IDs", "advanced_cropnalyzer", CROPNALYZER_ID);
        LABGS = ConfigHelper.getStrings(CONFIG, "localizations", "localizations", new String[] { "en_US", "ru_RU" }, "Supported localizations. Place your <name>.lang file in config/advancedcropnalyzer/lang folder or inside mods/advancedcropnalyzer/lang inside modJar");
        if (CONFIG.hasChanged()) CONFIG.save();
    }

    @Override
    public String getModid() {
        return ID;
    }

    @Override
    public List<String> getLocalizationList() {
        return Arrays.asList(LABGS);
    }

    public static ItemStack getCharged(Item item, int charge) {
        if (!(item instanceof IElectricItem)) {
            throw new IllegalArgumentException(item + " must be an instanceof IElectricItem");
        } else {
            ItemStack ret = new ItemStack(item);
            ElectricItem.manager.charge(ret, charge, Integer.MAX_VALUE, true, false);
            return ret;
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void addChargeVariants(Item item, List list) {
        list.add(getCharged(item, Integer.MAX_VALUE));
        list.add(getCharged(item, 0));
    }

    public static int getCharge(ItemStack stack) {
        NBTTagCompound tag = StackUtil.getOrCreateNbtData(stack);
        return tag.getInteger("charge");
    }

    public static boolean isShiftKeyDown() {
        return Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.keyCode);
    }
}
