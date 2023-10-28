package creditcrab.omron.Common.TileEntities;

import creditcrab.omron.Network.VanillaPacketSyncer;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class TileEntityBase extends TileEntity {

    public static void init(){
        GameRegistry.registerTileEntity(TileEntityCustomChest.class,"omron:tileEntityCustomChest");
        GameRegistry.registerTileEntity(TileEntityFluidTank.class,"omron:tileEntityFluidTank");
        GameRegistry.registerTileEntity(TileEntityEnergyBuffer.class,"omron:tileEntityEnergyBuffer");
        GameRegistry.registerTileEntity(TileEntityTitaniumProcessor.class,"omron:tileEntityTitaniumProcessor");
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.readSyncableNBT(compound,false);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        this.writeSyncableNBT(compound,false);
    }

    @Override
    public void updateEntity(){
        this.ticksElapsed++;
    }

    @Override
    public Packet getDescriptionPacket(){
        NBTTagCompound tag = new NBTTagCompound();
        this.writeSyncableNBT(tag, true);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
        this.readSyncableNBT(pkt.func_148857_g(), true);
    }

    @Override
    public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta, int newMeta, World world, int x, int y, int z){
        return !(oldBlock.isAssociatedBlock(newBlock));
    }


    public void writeSyncableNBT(NBTTagCompound compound, boolean isForSync){

    }

    public void readSyncableNBT(NBTTagCompound compound, boolean isForSync){

    }

    protected boolean sendUpdateWithInterval(){
        if(this.ticksElapsed%10 == 0){
            this.sendUpdate();
            return true;
        }
        else{
            return false;
        }
    }

    public void sendUpdate(){
        VanillaPacketSyncer.sendTileToNearbyPlayers(this);
    }
    protected int ticksElapsed;
    public boolean isRedstonePowered;

}
