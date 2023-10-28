package creditcrab.omron.Common.TileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TileEntityInventoryBase extends TileEntityBase implements ISidedInventory {

    public ItemStack slots[];
    public String name;

    public TileEntityInventoryBase(int slots, String name){
        this.initializeSlots(slots);
        this.name = "container.omron."+name;

    }

    private void initializeSlots(int amount){
        this.slots = new ItemStack[amount];
    }


    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        if (this.slots.length > 0){
            int[] newSlots = new int[slots.length];
            for(int i = 0; i < newSlots.length; i++){
                newSlots[i] = i;
            }
            return newSlots;
        }
        else{
            return new int[0];
        }
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){
        if(!isForSync || this.shouldSyncSlots()){
            if(this.slots.length > 0){
                NBTTagList tagList = new NBTTagList();
                for(int currentIndex = 0; currentIndex < slots.length; currentIndex++){
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Slot", (byte)currentIndex);
                    if(slots[currentIndex] != null){
                        slots[currentIndex].writeToNBT(tagCompound);
                    }
                    tagList.appendTag(tagCompound);
                }
                compound.setTag("Items", tagList);
                //System.out.println(tagList);
            }
        }
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        if(!isForSync || this.shouldSyncSlots()){
            if(this.slots.length > 0){
                NBTTagList tagList = compound.getTagList("Items", 10);
                for(int i = 0; i < tagList.tagCount(); i++){
                    NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
                    byte slotIndex = tagCompound.getByte("Slot");
                    if(slotIndex >= 0 && slotIndex < slots.length){
                        slots[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
                    }
                }
            }
        }
    }

    private boolean shouldSyncSlots() {
        return false;
    }

    @Override
    public int getInventoryStackLimit(){
        return 64;
    }


    @Override
    public boolean isUseableByPlayer(EntityPlayer player){
        return player.getDistanceSq(xCoord+0.5D, yCoord+0.5D, zCoord+0.5D) <= 64;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i){
        return getStackInSlot(i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack){
        this.slots[i] = stack;
        this.markDirty();
    }

    @Override
    public int getSizeInventory(){
        return slots.length;
    }

    @Override
    public ItemStack getStackInSlot(int i){
        if(i < this.getSizeInventory()){
            return slots[i];
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int j){
        if(slots[i] != null){
            ItemStack stackAt;
            if(slots[i].stackSize <= j){
                stackAt = slots[i];
                slots[i] = null;
                this.markDirty();
                return stackAt;
            }
            else{
                stackAt = slots[i].splitStack(j);
                if(slots[i].stackSize == 0){
                    slots[i] = null;
                }
                this.markDirty();
                return stackAt;
            }
        }
        return null;
    }


    @Override
    public String getInventoryName(){
        return this.name;
    }

    @Override
    public boolean hasCustomInventoryName(){
        return false;
    }

    @Override
    public void openInventory(){

    }

    @Override
    public void closeInventory(){
    }
}
