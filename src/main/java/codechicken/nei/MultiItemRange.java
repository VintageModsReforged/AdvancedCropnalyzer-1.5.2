package codechicken.nei;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class MultiItemRange {
    public ArrayList<ItemRange> ranges = new ArrayList<ItemRange>();

    public MultiItemRange() {}

    public MultiItemRange add(ItemRange range) {
        this.ranges.add(range);
        return this;
    }

    public MultiItemRange add(int itemID) {
        return this.add(new ItemRange(itemID));
    }

    public MultiItemRange add(int itemID, int damageStart, int damageEnd) {
        return this.add(new ItemRange(itemID, damageStart, damageEnd));
    }

    public MultiItemRange add(int itemIDFirst, int itemIDLast) {
        return this.add(new ItemRange(itemIDFirst, itemIDLast));
    }

    public MultiItemRange add(ItemStack item, int damageStart, int damageEnd) {
        return this.add(item.itemID, damageStart, damageEnd);
    }

    public MultiItemRange add(Block block, int damageStart, int damageEnd) {
        return this.add(block.blockID, damageStart, damageEnd);
    }

    public MultiItemRange add(ItemStack item) {
        return this.add(item.itemID);
    }

    public MultiItemRange add(Block block) {
        return this.add(block.blockID);
    }
}
