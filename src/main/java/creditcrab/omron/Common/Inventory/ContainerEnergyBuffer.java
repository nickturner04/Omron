package creditcrab.omron.Common.Inventory;

import creditcrab.omron.Common.TileEntities.TileEntityBase;
import creditcrab.omron.Common.TileEntities.TileEntityEnergyBuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerEnergyBuffer extends Container {

    public TileEntityEnergyBuffer buffer;

    public ContainerEnergyBuffer(InventoryPlayer inventory, TileEntityBase tile){
        buffer = (TileEntityEnergyBuffer)tile;

        this.addSlotToContainer(new Slot(buffer,0,40,78));
        this.addSlotToContainer(new Slot(buffer,1,40,142));

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
        return null;
    }
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return buffer.isUseableByPlayer(player);
    }
}
