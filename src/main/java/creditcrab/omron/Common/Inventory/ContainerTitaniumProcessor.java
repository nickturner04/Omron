package creditcrab.omron.Common.Inventory;

import creditcrab.omron.Common.TileEntities.SlotMachine;
import creditcrab.omron.Common.TileEntities.TileEntityBase;
import creditcrab.omron.Common.TileEntities.TileEntityTitaniumProcessor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

public class ContainerTitaniumProcessor extends Container {

    public TileEntityTitaniumProcessor tile;

    private int lastProgress;
    private int lastEnergy;
    private int lastFluid;

    public ContainerTitaniumProcessor(InventoryPlayer inventory, TileEntityBase tile){
        this.tile = (TileEntityTitaniumProcessor) tile;

        this.addSlotToContainer(new SlotMachine(this.tile,0,40,142,0));
        this.addSlotToContainer(new SlotMachine(this.tile,1,77,108,1));
        this.addSlotToContainer(new SlotMachine(this.tile,2,113,142,2));
        this.addSlotToContainer(new SlotMachine(this.tile,3,158,108,3));

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j+i*9+9, 33+8+j*18, 172+4+i*18));
            }
        }
        for(int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 33+8+i*18, 172+62));
        }
    }

    public void addCraftingToCrafters(ICrafting p_75132_1_)
    {
        super.addCraftingToCrafters(p_75132_1_);
        p_75132_1_.sendProgressBarUpdate(this, 0, this.tile.currentTicks);
        p_75132_1_.sendProgressBarUpdate(this, 1, this.tile.getEnergyStored(ForgeDirection.UP));
        p_75132_1_.sendProgressBarUpdate(this, 2, this.tile.getFluidAmount());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastProgress != this.tile.currentTicks)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tile.currentTicks);
            }

            if (this.lastEnergy != this.tile.getEnergyStored(ForgeDirection.UP))
            {
                icrafting.sendProgressBarUpdate(this, 1, this.tile.getEnergyStored(ForgeDirection.UP));
            }

            if (this.lastFluid != this.tile.getFluidAmount())
            {
                icrafting.sendProgressBarUpdate(this, 2, this.tile.getFluidAmount());
            }
        }

        this.lastProgress = this.tile.currentTicks;
        this.lastEnergy = this.tile.getEnergyStored(ForgeDirection.UP);
        this.lastFluid = this.tile.getFluidAmount();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int p_75137_1_, int p_75137_2_)
    {
        if (p_75137_1_ == 0)
        {
            this.tile.currentTicks = p_75137_2_;
        }

        if (p_75137_1_ == 1)
        {
            this.tile.currentEnergy = p_75137_2_;
        }

        if (p_75137_1_ == 2)
        {
            this.tile.currentFluid = p_75137_2_;
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p, int i)
    {
        return null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
