package creditcrab.omron.Common.Inventory.Gui;

import creditcrab.omron.Common.Inventory.ContainerCustomChest;
import creditcrab.omron.Common.Inventory.ContainerEnergyBuffer;
import creditcrab.omron.Common.Inventory.ContainerTitaniumProcessor;
import creditcrab.omron.Common.TileEntities.TileEntityBase;
import creditcrab.omron.Omron;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    public static void init(){
        NetworkRegistry.INSTANCE.registerGuiHandler(Omron.instance,new GuiHandler());
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntityBase tile = null;
        if (GuiTypes.values()[ID].checkTileEntity){
            tile = (TileEntityBase)world.getTileEntity(x,y,z);
        }
        switch (GuiTypes.values()[ID]){
            case CHEST:
                return new ContainerCustomChest(player.inventory,tile);
            case ENERGYBUFFER:
                return new ContainerEnergyBuffer(player.inventory,tile);
            case TITANIUMPROCESSOR:
                return new ContainerTitaniumProcessor(player.inventory, tile);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntityBase tile = null;
        if (GuiTypes.values()[ID].checkTileEntity){
            tile = (TileEntityBase)world.getTileEntity(x,y,z);
        }
        switch (GuiTypes.values()[ID]){
            case CHEST:
                return new ContainerCustomChestGui(player.inventory,tile);
            case ENERGYBUFFER:
                return new GuiEnergyBuffer(player.inventory,tile);
            case TITANIUMPROCESSOR:
                return new GuiTitaniumProcessor(player.inventory, tile);
            default:
                return null;
        }
    }

    public enum GuiTypes{
        CHEST,
        ENERGYBUFFER,

        TITANIUMPROCESSOR;
        public boolean checkTileEntity;

        GuiTypes(){
            this(true);
        }

        GuiTypes(boolean checkTileEntity){
            this.checkTileEntity = checkTileEntity;
        }
    }
}
