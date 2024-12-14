package dev.vintage.cropnalyzer.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dev.vintage.cropnalyzer.AdvancedCropnalyzer;
import ic2.api.crops.CropCard;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityCrop;
import ic2.core.block.crop.IC2Crops;
import ic2.core.item.IHandHeldInventory;
import ic2.core.item.tool.ContainerCropnalyzer;
import ic2.core.item.tool.HandHeldCropnalyzer;
import ic2.core.util.StackUtil;
import mods.vintage.core.platform.lang.FormattedTranslator;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class ItemAdvancedAnalyzer extends Item implements IHandHeldInventory, IElectricItem {

    public ItemAdvancedAnalyzer(int id) {
        super(id);
        this.setMaxStackSize(1);
        this.setMaxDamage(27);
        this.setNoRepair();
        this.setUnlocalizedName("advanced.cropnalyzer");
        this.setCreativeTab(IC2.tabIC2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(int is, CreativeTabs tabs, List items) {
        AdvancedCropnalyzer.addChargeVariants(this, items);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister icons) {
        this.itemIcon = icons.registerIcon(AdvancedCropnalyzer.ID + ":advanced_cropnalyzer");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemDisplayName(ItemStack stack) {
        return FormattedTranslator.GREEN.literal(super.getItemDisplayName(stack));
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean isDebugMode) {
        tooltip.add(FormattedTranslator.AQUA.format("analyzer.message.info.energy", AdvancedCropnalyzer.getCharge(stack), this.getMaxCharge(stack), FormattedTranslator.WHITE.format("analyzer.message.info.energy.tier", FormattedTranslator.YELLOW.literal(this.getTier(stack) + ""))));
        tooltip.add(FormattedTranslator.YELLOW.format("analyzer.tooltip.desc"));
        if (AdvancedCropnalyzer.isShiftKeyDown()) {
            tooltip.add(FormattedTranslator.GRAY.format("analyzer.message.info.click.block", FormattedTranslator.GOLD.literal(AdvancedCropnalyzer.SNEAK_KEY), FormattedTranslator.GOLD.literal("Right Click"), FormattedTranslator.GREEN.format("analyzer.message.info.crop.info")));
        } else {
            tooltip.add(FormattedTranslator.GRAY.format("analyzer.message.info.press", FormattedTranslator.GREEN.format(AdvancedCropnalyzer.SNEAK_KEY)));
        }
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (IC2.platform.isSimulating()) {
            if (player.isSneaking()) {
                TileEntity clickedTile = world.getBlockTileEntity(x, y, z);
                if (clickedTile instanceof TileEntityCrop) {
                    TileEntityCrop cropTile = (TileEntityCrop) clickedTile;
                    int scanLevel = cropTile.getScanLevel();
                    // stats info
                    int growth = cropTile.getGrowth();
                    int gain = cropTile.getGain();
                    int resistance = cropTile.getResistance();
                    // storage info
                    int fertilizer = cropTile.getNutrientStorage();
                    int water = cropTile.getHydrationStorage();
                    int weedex = cropTile.getWeedExStorage();
                    // environment info
                    int nutrients = cropTile.getNutrients();
                    int humidity = cropTile.getHumidity();
                    int env = cropTile.getAirQuality();
                    int light = cropTile.getLightLevel();
                    if (cropTile.getID() != -1) {
                        CropCard crop = IC2Crops.instance.getCropList()[cropTile.getID()];
                        if (crop != null) {
                            if (scanLevel < 4 && ElectricItem.manager.canUse(stack, 500)) {
                                cropTile.setScanLevel((byte) ++scanLevel);
                                IC2.platform.messagePlayer(player, FormattedTranslator.GREEN.format("crop.scan.level.set", FormattedTranslator.AQUA.literal("" + scanLevel)));
                                ElectricItem.manager.use(stack, 500, player);
                            }
                            if (scanLevel >= 4) {
                                IC2.platform.messagePlayer(player, "=======================");
                                IC2.platform.messagePlayer(player, FormattedTranslator.WHITE.format("crop.name", FormattedTranslator.GREEN.literal(crop.name())));
                            }

                            if (scanLevel < 1 && !crop.isWeed(cropTile)) {
                                IC2.platform.messagePlayer(player, FormattedTranslator.WHITE.format("crop.discovered.by", FormattedTranslator.AQUA.format("crop.discovered.by.unknown")));
                            } else if (scanLevel >= 4) {
                                IC2.platform.messagePlayer(player, FormattedTranslator.WHITE.format("crop.discovered.by", FormattedTranslator.AQUA.literal(crop.discoveredBy())));
                                IC2.platform.messagePlayer(player, FormattedTranslator.YELLOW.format("crop.stats"));
                                IC2.platform.messagePlayer(player, FormattedTranslator.DARK_GREEN.format("crop.stats.growth", growth, 31));
                                IC2.platform.messagePlayer(player, FormattedTranslator.GOLD.format("crop.stats.gain", gain, 31));
                                IC2.platform.messagePlayer(player, FormattedTranslator.DARK_AQUA.format("crop.stats.resistance", resistance, 31));

                                int stress = (crop.tier() - 1) * 4 + growth + gain + resistance;
                                int maxStress = crop.weightInfluences(cropTile, humidity, nutrients, env) * 5;
                                IC2.platform.messagePlayer(player, FormattedTranslator.AQUA.format("crop.stats.needs", stress, maxStress));
                            }


                        }
                        if (scanLevel >= 4) {
                            IC2.platform.messagePlayer(player, FormattedTranslator.YELLOW.format("crop.storage"));
                            IC2.platform.messagePlayer(player, FormattedTranslator.GOLD.format("crop.storage.fertilizer", fertilizer, 100));
                            IC2.platform.messagePlayer(player, FormattedTranslator.BLUE.format("crop.storage.water", water, 200));
                            IC2.platform.messagePlayer(player, FormattedTranslator.LIGHT_PURPLE.format("crop.storage.weedex", weedex, 150));

                            IC2.platform.messagePlayer(player, FormattedTranslator.YELLOW.format("crop.environment"));
                            IC2.platform.messagePlayer(player, FormattedTranslator.GREEN.format("crop.env.nutrients", nutrients, 20));
                            IC2.platform.messagePlayer(player, FormattedTranslator.DARK_AQUA.format("crop.env.humidity", humidity, 20));
                            IC2.platform.messagePlayer(player, FormattedTranslator.AQUA.format("crop.env.env", env, 10));
                            IC2.platform.messagePlayer(player, FormattedTranslator.YELLOW.format("crop.env.light", light, 15));
                            IC2.platform.messagePlayer(player, "=======================");
                        }
                    } else {
                        IC2.platform.messagePlayer(player, FormattedTranslator.WHITE.format("crop.name", FormattedTranslator.GREEN.format("crop.discovered.by.unknown")));
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (IC2.platform.isSimulating()) {
            if (!player.isSneaking()) {
                IC2.platform.launchGui(player, this.getInventory(player, stack));
            }
        }
        return stack;
    }

    @Override
    public IHasGui getInventory(EntityPlayer player, ItemStack stack) {
        return new HandHeldCropnalyzer(player, stack);
    }

    @Override
    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
        if (item != null && player.openContainer instanceof ContainerCropnalyzer) {
            HandHeldCropnalyzer cropnalyzer = ((ContainerCropnalyzer)player.openContainer).cropnalyzer;
            NBTTagCompound tag = StackUtil.getOrCreateNbtData(item);
            if (cropnalyzer.matchesUid(tag.getInteger("uid"))) {
                player.closeScreen();
            }
        }
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, int blockID, int x, int y, int z, EntityLiving user) {
        return false;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLiving attacker, EntityLiving target) {
        return false;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public int getChargedItemId(ItemStack stack) {
        return this.itemID;
    }

    @Override
    public int getEmptyItemId(ItemStack stack) {
        return this.itemID;
    }

    @Override
    public int getMaxCharge(ItemStack stack) {
        return 10000;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 1;
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return 500;
    }
}
