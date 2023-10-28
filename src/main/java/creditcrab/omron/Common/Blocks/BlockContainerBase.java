package creditcrab.omron.Common.Blocks;


import creditcrab.omron.Common.TileEntities.TileEntityInventoryBase;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockContainerBase extends BlockContainer {
    public BlockContainerBase(Material material) {
        super(material);
    }

    public void dropInventory(World world, int x, int y, int z){
        if(!world.isRemote){
            TileEntity aTile = world.getTileEntity(x, y, z);
            if(aTile instanceof TileEntityInventoryBase){
                TileEntityInventoryBase tile = (TileEntityInventoryBase)aTile;
                if(tile.getSizeInventory() > 0){
                    for(int i = 0; i < tile.getSizeInventory(); i++){
                        this.dropSlotFromInventory(i, tile, world, x, y, z);
                    }
                }
            }
        }
    }

    public void dropSlotFromInventory(int i, TileEntityInventoryBase tile, World world, int x, int y, int z){
        ItemStack stack = tile.getStackInSlot(i);
        if(stack != null && stack.stackSize > 0){
            //float dX = Util.RANDOM.nextFloat()*0.8F+0.1F;
            //float dY = Util.RANDOM.nextFloat()*0.8F+0.1F;
            //float dZ = Util.RANDOM.nextFloat()*0.8F+0.1F;
            EntityItem entityItem = new EntityItem(world, x, y, z, stack.copy());
            if(stack.hasTagCompound()){
                entityItem.getEntityItem().setTagCompound((NBTTagCompound)stack.getTagCompound().copy());
            }
            world.spawnEntityInWorld(entityItem);
        }
        tile.setInventorySlotContents(i, null);
    }
}
