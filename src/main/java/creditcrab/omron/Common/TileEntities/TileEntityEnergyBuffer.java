package creditcrab.omron.Common.TileEntities;

import cofh.api.energy.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityEnergyBuffer extends TileEntityInventoryBase implements IEnergyReceiver, IEnergyProvider {

    private SideConfig[] sides = new SideConfig[] {SideConfig.BOTH,SideConfig.BOTH,SideConfig.BOTH,SideConfig.BOTH,SideConfig.BOTH,SideConfig.BOTH};

    private EnergyStorage energyStorage;
    private final int maxEnergy = Integer.MAX_VALUE;

    private final int maxOutput = 20;
    private int rfPerTick = 0;

    public TileEntityEnergyBuffer() {
        super(2, "omron:tileEntityEnergyBuffer");
        energyStorage = new EnergyStorage(maxEnergy,maxEnergy,maxEnergy);
    }

    public SideConfig getSideConfig(int side){

        return sides[getSideConfigNumber(side)];
    }

    public void setSideConfig(int side, SideConfig config){

    }

    public void cycleSideConfig(int side){
        sides[getSideConfigNumber(side)] = sides[getSideConfigNumber(side)].next();
    }
    public int getSideConfigNumber(int side){
        ForgeDirection direction = ForgeDirection.getOrientation(side);

        return getSideConfigFromDirection(direction);
    }

    private int getSideConfigFromDirection(ForgeDirection direction){
        switch (direction){
            case DOWN:
                return 0;
            case UP:
                return 1;
            case NORTH:
                return 2;
            case EAST:
                return 3;
            case SOUTH:
                return 4;
            case WEST:
                return 5;
        }
        return 0;
    }


    @Override
    public boolean canUpdate(){
        return true;
    }

    @Override
    public void updateEntity(){
        super.updateEntity();
        energyStorage.receiveEnergy(rfPerTick,false);
        for (int i = 0; i < 6; i++) {
            transferEnergy(i);
        }
        if (slots[1] != null && slots[1].getItem() instanceof IEnergyContainerItem){
            IEnergyContainerItem charger = (IEnergyContainerItem)slots[1].getItem();
            energyStorage.receiveEnergy(charger.extractEnergy(slots[1],Integer.MAX_VALUE,false),false);
        }
        if (slots[0] != null && slots[0].getItem() instanceof IEnergyContainerItem){
            IEnergyContainerItem reciever = (IEnergyContainerItem)slots[0].getItem();
            energyStorage.extractEnergy(reciever.receiveEnergy(slots[0],energyStorage.getEnergyStored(),false),false);
        }

    }

    protected void transferEnergy(int side){
        if (getSideConfig(side) == SideConfig.OUT || getSideConfig(side) == SideConfig.BOTH){
            ForgeDirection fd = ForgeDirection.VALID_DIRECTIONS[side];
            TileEntity tile = worldObj.getTileEntity(xCoord + fd.offsetX, yCoord + fd.offsetY,zCoord + fd.offsetZ);
            if (tile instanceof IEnergyReceiver){
                this.energyStorage.modifyEnergyStored(-((IEnergyReceiver)tile).receiveEnergy(fd.getOpposite(),Math.min(20,energyStorage.getEnergyStored()),false));
            }
        }
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if (stack.getItem() instanceof IEnergyContainerItem){
            return true;
        }
        return false;
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        return false;
    }

    @Override
    public int extractEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        SideConfig side = sides[getSideConfigFromDirection(forgeDirection)];
        if (side == SideConfig.OUT || side == SideConfig.BOTH){
            return energyStorage.extractEnergy(i,b);
        }
        else return 0;
    }

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        SideConfig side = sides[getSideConfigFromDirection(forgeDirection)];
        if (side == SideConfig.IN || side == SideConfig.BOTH){
            return energyStorage.receiveEnergy(i,b);
        }
        else return 0;
    }

    public int getMaxOutput(){
        return energyStorage.getMaxExtract();
    }

    public int getMaxInput(){
        return energyStorage.getMaxReceive();
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
    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync) {
        super.writeSyncableNBT(compound, isForSync);
        NBTTagList tagList = new NBTTagList();
        int[] sides = new int[6];
        for (int i = 0; i < 6; i++) {
            sides[i] = this.sides[i].ordinal();
        }
        compound.setIntArray("Sides",sides);
        compound.setInteger("Flux",energyStorage.getEnergyStored());
    }

    @Override
    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){
        super.readSyncableNBT(compound,isForSync);
        int[] sides = compound.getIntArray("Sides");
        for (int i = 0; i < 6; i++) {
            this.sides[i] = SideConfig.values()[sides[i]];
        }
        energyStorage.setEnergyStored(compound.getInteger("Flux"));
    }

    public enum SideConfig{
        BOTH,
        OUT,
        IN,
        NONE;

        public SideConfig next(){
            int next = this.ordinal() + 1;
            if (next == SideConfig.values().length) return BOTH;
            return SideConfig.values()[next];
        }

        public SideConfig next(SideConfig in){
            int next = in.ordinal() + 1;
            if (next == SideConfig.values().length) return BOTH;
            return SideConfig.values()[next];
        }
    }
}

