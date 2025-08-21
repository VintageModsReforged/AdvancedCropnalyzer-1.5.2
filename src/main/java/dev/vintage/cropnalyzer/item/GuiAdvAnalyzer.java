package dev.vintage.cropnalyzer.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.vintage.core.platform.lang.Translator;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiAdvAnalyzer extends GuiContainer {

    public ContainerAdvAnalyzer containerAdvAnalyzer;

    public GuiAdvAnalyzer(ContainerAdvAnalyzer containerAdvAnalyzer) {
        super(containerAdvAnalyzer);
        this.containerAdvAnalyzer = containerAdvAnalyzer;
        this.xSize = 176;
        this.ySize = 237;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRenderer.drawString(Translator.RESET.format("item.advanced.cropnalyzer.name"), 8, 37, 4210752);
        int level = this.containerAdvAnalyzer.advAnalyzer.getScannedLevel();
        if (level > -1) {
            if (level == 0) {
                this.fontRenderer.drawString(Translator.RESET.format("crop.discovered.by.unknown"), 8, 37, 16777215);
            } else {
                this.fontRenderer.drawString(this.containerAdvAnalyzer.advAnalyzer.getSeedName(), 8, 52, 16777215);
                if (level >= 2) {
                    this.fontRenderer.drawString(Translator.RESET.format("analyzer.gui.tier", this.containerAdvAnalyzer.advAnalyzer.getSeedTier()), 8, 65, 16777215);
                    this.fontRenderer.drawString(Translator.RESET.format("analyzer.gui.discovered.by"), 8, 88, 16777215);
                    this.fontRenderer.drawString(this.containerAdvAnalyzer.advAnalyzer.getSeedDiscovered(), 8, 101, 16777215);
                }

                if (level >= 3) {
                    this.fontRenderer.drawString(this.containerAdvAnalyzer.advAnalyzer.getSeedDesc(0), 8, 124, 16777215);
                    this.fontRenderer.drawString(this.containerAdvAnalyzer.advAnalyzer.getSeedDesc(1), 8, 137, 16777215);
                }

                if (level >= 4) {
                    this.fontRenderer.drawString(Translator.DARK_GREEN.format("analyzer.gui.growth"), 118, 52, 0);
                    this.fontRenderer.drawString(Translator.DARK_GREEN.literal("" + this.containerAdvAnalyzer.advAnalyzer.getSeedGrowth()), 118, 65, 0);
                    this.fontRenderer.drawString(Translator.GOLD.format("analyzer.gui.gain"), 118, 88, 0);
                    this.fontRenderer.drawString(Translator.GOLD.literal("" + this.containerAdvAnalyzer.advAnalyzer.getSeedGain()), 118, 101, 0);
                    this.fontRenderer.drawString(Translator.DARK_AQUA.format("analyzer.gui.resist"), 118, 124, 0);
                    this.fontRenderer.drawString(Translator.DARK_AQUA.literal("" + this.containerAdvAnalyzer.advAnalyzer.getSeedResistence()), 118, 137, 0);
                }

            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture("/mods/advancedcropnalyzer/textures/gui/adv_analyzer.png");
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
    }
}
