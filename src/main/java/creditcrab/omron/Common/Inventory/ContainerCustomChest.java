package creditcrab.omron.Common.Inventory;

import creditcrab.omron.Common.TileEntities.TileEntityBase;
import creditcrab.omron.Common.TileEntities.TileEntityCustomChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCustomChest extends Container {

    public TileEntityCustomChest customChest;

    public ContainerCustomChest(InventoryPlayer inventory, TileEntityBase tile){


        this.customChest = (TileEntityCustomChest) tile;
        int currentslot = 0;
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(customChest,currentslot, 38+j*18, 80+i*18));
                currentslot++;
            }
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j+i*9+9, 33+8+j*18, 172+4+i*18));
            }
        }
        for(int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 33+8+i*18, 172+62));
        }

    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p, int i)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(i);
        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (i < 45)
            {
                if (!mergeItemStack(itemstack1, 45, inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!mergeItemStack(itemstack1, 0, 45, false))
            {
                return null;
            }
            if (itemstack1.stackSize == 0)
            {
                slot.putStack(null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.customChest.isUseableByPlayer(player);
    }
}


