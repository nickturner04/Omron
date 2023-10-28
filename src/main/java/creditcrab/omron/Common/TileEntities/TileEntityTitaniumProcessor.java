package creditcrab.omron.Common.TileEntities;

import creditcrab.omron.ModItems;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityTitaniumProcessor extends TileEntityInventoryBase implements IEnergyReceiver, IFluidHandler {

    private EnergyStorage energyStorage;

    public int currentTicks = 0;

    public int currentEnergy = 0;

    public int currentFluid = 0;


    private FluidTank tank = new FluidTank(32000);
    private final int maxEnergy = Integer.MAX_VALUE;

    public final int ticksPerOperation = 200;

    private final int waterPerTick = 20;
    private final int energyPerTick = 80;



    private boolean running = false;
    public TileEntityTitaniumProcessor() {
        super(4, "omron:TileEntityTitaniumProcessor");
        energyStorage = new EnergyStorage(60000,160,maxEnergy);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        switch (index){
            case 0:
                if (stack.getItem() instanceof IEnergyContainerItem){
                    return true;
                }
                return false;
            case 1:
                if (OreDictionary.getOreName(OreDictionary.getOreIDs(stack)[0]) == "ingotRutile")
                return true;
            case 2:
                if (stack.getItem() instanceof IFluidContainerItem){
                    return true;
                }
            case 3:
                return false;
        }
        return false;
    }

    @Override
    public void updateEntity(){

        super.updateEntity();
        if (!worldObj.isRemote){
            if (slots[0] != null && slots[0].getItem() instanceof IEnergyContainerItem){
                IEnergyContainerItem charger = (IEnergyContainerItem)slots[0].getItem();
                energyStorage.receiveEnergy(charger.extractEnergy(slots[0],Integer.MAX_VALUE,energyStorage.getEnergyStored() == energyStorage.getMaxEnergyStored()),false);
            }
            if (slots[2] != null && slots[2].getItem() instanceof IFluidContainerItem){
                IFluidContainerItem filler = (IFluidContainerItem)slots[2].getItem();
                ItemStack stack = slots[2];
                if (filler.getFluid(stack) != null){
                    if (filler.getFluid(stack).getFluid() == FluidRegistry.WATER){
                        tank.fill(filler.drain(stack,Integer.MAX_VALUE,true),true);
                    }
                }

            }

            if (running){
                if (canProgress()) progressTick();

                if (this.currentTicks == ticksPerOperation){
                    endOperation();
                }
            }
            else{
                startOperation();
            }

        }

    }

    private void startOperation(){
        if (canStart()){
            running = true;
            setBlockOnOff(1);
            decrStackSize(1,1);
        }
        else{
            running = false;
        }
    }

    public void endOperation(){
        running = false;
        currentTicks = 0;
        worldObj.playSound(xCoord,yCoord,zCoord, "omron:clang",1,1,false);
        if (slots[3] == null){
            slots[3] = new ItemStack(ModItems.ingotTitanium,1);
        }
        else{
            slots[3].stackSize++;
        }
        if (!canStart())setBlockOnOff(0);
    }

    private boolean canStart(){
        if (slots[1] != null && (slots[3] == null || slots[3].stackSize != 64)) return true;
        return false;
    }

    private boolean canProgress(){
        if (energyStorage.getEnergyStored() >= energyPerTick && tank.getFluidAmount() >= waterPerTick){
            return true;
        }
        return false;
    }

    private void progressTick(){
        tank.drain(waterPerTick,true);
        energyStorage.extractEnergy(energyPerTick,false);
        currentTicks ++;
    }


    public void setBlockOnOff(int state){
        state <<= 2;
        int meta = (worldObj.getBlockMetadata(xCoord,yCoord,zCoord) & 0b0011) | state;
        worldObj.setBlockMetadataWithNotify(xCoord,yCoord,zCoord,meta,2);
    }


    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return isItemValidForSlot(p_102007_1_,p_102007_2_);
    }

    public int getMaxOutput(){
        return energyStorage.getMaxExtract();
    }

    public int getMaxInput(){
        return energyStorage.getMaxReceive();
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        if (p_102008_1_ == 3) return true;
        return false;
    }

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        return energyStorage.receiveEnergy(i,b);
    }

    @Override
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        return true;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return tank.fill(resource,doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return this.tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return this.tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (fluid == FluidRegistry.WATER) return true;
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] {tank.getInfo()} ;
    }

    public int getFluidAmount(){
        return tank.getFluidAmount();
    }

    @Override
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync) {
        super.writeSyncableNBT(compound, isForSync);
        NBTTagList tagList = new NBTTagList();
        compound.setInteger("Flux",energyStorage.getEnergyStored());
        compound.setInteger("Fluid", tank.getFluidAmount());
        compound.setInteger("Progress",ticksElapsed);
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        super.readSyncableNBT(compound,isForSync);
        energyStorage.setEnergyStored(compound.getInteger("Flux"));
        tank.setFluid(new FluidStack(FluidRegistry.WATER,compound.getInteger("Fluid")));
        ticksElapsed = compound.getInteger("Progress");
    }
}
