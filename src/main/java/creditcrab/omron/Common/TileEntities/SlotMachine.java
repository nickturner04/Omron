package creditcrab.omron.Common.TileEntities;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.oredict.OreDictionary;

public class SlotMachine extends Slot {

    //0 = Charger
    //1 = Ingot
    //2 = Fluid
    //3 = Output
    int type;
    public SlotMachine(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_,int type) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        this.type = type;
    }
@Override
    public boolean isItemValid(ItemStack stack){
    switch (type){
        case 0:
            if (stack.getItem() instanceof IEnergyContainerItem){
                return true;
            }
            return false;
        case 1:
            if (OreDictionary.getOreIDs(stack).length > 0)
                if (OreDictionary.getOreName(OreDictionary.getOreIDs(stack)[0]) == "ingotRutile") return true;
        case 2:
            if (stack.getItem() instanceof IFluidContainerItem){
                return true;
            }
        case 3:
            return false;
    }
    return false;
}

}
