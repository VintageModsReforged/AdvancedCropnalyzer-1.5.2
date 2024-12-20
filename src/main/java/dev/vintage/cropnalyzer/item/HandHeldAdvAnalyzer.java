package dev.vintage.cropnalyzer.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.ContainerBase;
import ic2.core.item.tool.HandHeldCropnalyzer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class HandHeldAdvAnalyzer extends HandHeldCropnalyzer {

    public HandHeldAdvAnalyzer(EntityPlayer entityPlayer, ItemStack itemStack) {
        super(entityPlayer, itemStack);
    }

    @Override
    public ContainerBase getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerAdvAnalyzer(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GuiAdvAnalyzer(new ContainerAdvAnalyzer(entityPlayer, this));
    }
}
