package creditcrab.omron.Common.Blocks;


import creditcrab.omron.Common.Inventory.Gui.GuiHandler;
import creditcrab.omron.Common.TileEntities.TileEntityCustomChest;
import creditcrab.omron.Omron;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class BlockCustomChest extends BlockContainerBase {

    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    public BlockCustomChest(){
        super(Material.wood);
        this.setBlockName("blockCustomChest");
        this.setHardness(2);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        switch (meta){
            case 0:
                return new TileEntityCustomChest();
            case 1:
                return new TileEntityCustomChest();
            default:
                return new TileEntityCustomChest();
        }

    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < 3; i++) {
            par3List.add(new ItemStack(this,1,i));
        }
    }

    @Override
    public boolean hasTileEntity(){ return true;}

    @Override
    @SideOnly(Side.CLIENT)
    public final IIcon getIcon(int side, int meta){
        return side == 1 || side == 0 ? iconTop : this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon){
        blockIcon = icon.registerIcon("Omron:blockCustomChest");
        iconTop = icon.registerIcon("Omron:blockFluidFurnaceTop");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9){
        if(!world.isRemote){
            TileEntityCustomChest chest = (TileEntityCustomChest)world.getTileEntity(x,y,z);
            if(chest != null  ){
                player.openGui(Omron.instance, GuiHandler.GuiTypes.CHEST.ordinal(), world, x, y, z);
            }
            return true;
        }
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }
}
