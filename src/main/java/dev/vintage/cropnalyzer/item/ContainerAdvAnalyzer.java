package dev.vintage.cropnalyzer.item;

import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.item.ItemCropSeed;
import ic2.core.slot.SlotCustom;
import ic2.core.slot.SlotDischarge;
import ic2.core.util.StackUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerAdvAnalyzer extends ContainerBase {

    public HandHeldAdvAnalyzer advAnalyzer;

    public ContainerAdvAnalyzer(EntityPlayer player, HandHeldAdvAnalyzer advAnalyzer) {
        super(advAnalyzer);
        this.advAnalyzer = advAnalyzer;
        this.addSlotToContainer(new SlotCustom(advAnalyzer, new Object[]{ItemCropSeed.class}, 0, 8, 7));
        this.addSlotToContainer(new SlotCustom(advAnalyzer, new Object[0], 1, 41, 7));
        this.addSlotToContainer(new SlotDischarge(advAnalyzer, 2, 152, 7));
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 155 + i * 18));
            }
        }

        for(int j = 0; j < 9; ++j) {
            this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 213));
        }
    }

    @Override
    public ItemStack slotClick(int slot, int button, int par3, EntityPlayer entityPlayer) {
        if (IC2.platform.isSimulating() && slot == -999 && (button == 0 || button == 1)) {
            ItemStack itemStackSlot = entityPlayer.inventory.getItemStack();
            if (itemStackSlot != null) {
                NBTTagCompound nbtTagCompoundSlot = StackUtil.getOrCreateNbtData(itemStackSlot);
                if (this.advAnalyzer.matchesUid(nbtTagCompoundSlot.getInteger("uid"))) {
                    entityPlayer.closeScreen();
                }
            }
        }

        return super.slotClick(slot, button, par3, entityPlayer);
    }

    @Override
    public void onCraftGuiClosed(EntityPlayer entityPlayer) {
        this.advAnalyzer.onGuiClosed(entityPlayer);
        super.onCraftGuiClosed(entityPlayer);
    }
}
