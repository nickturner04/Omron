package creditcrab.omron.Common.TileEntities;

import net.minecraft.item.ItemStack;

public class TileEntityCustomChest extends TileEntityInventoryBase  {


    public TileEntityCustomChest(){
        super(5*9,"customChest");

    }

    @Override
    public boolean canUpdate(){
        return false;
    }
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return true;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        return true;
    }

}
