package creditcrab.omron.Common.Blocks;

import creditcrab.omron.Common.Inventory.Gui.GuiHandler;
import creditcrab.omron.Common.TileEntities.TileEntityTitaniumProcessor;
import creditcrab.omron.Omron;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTitaniumProcessor extends BlockContainerBase{

    @SideOnly(Side.CLIENT)
    private IIcon iconTop;

    @SideOnly(Side.CLIENT)
    private IIcon iconFrontOff;

    @SideOnly(Side.CLIENT)
    private IIcon iconFrontOn;

    public BlockTitaniumProcessor() {
        super(Material.iron);
        setBlockName("blockTitaniumProcessor");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTitaniumProcessor();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9){
        //TileEntityTitaniumProcessor tile = (TileEntityTitaniumProcessor) world.getTileEntity(x, y, z);
        if(!world.isRemote) {

            player.openGui(Omron.instance, GuiHandler.GuiTypes.TITANIUMPROCESSOR.ordinal(), world, x, y, z);
            return true;
        }
        return false;
    }

    private String formatToBinary(int num){
        return String.format("%4s", Integer.toBinaryString(num)).replace(' ','0');
    }



    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn)
    {
        //worldIn.setBlockMetadataWithNotify(x, y, z, 0b1111, 2);
        byte l = (byte) (MathHelper.floor_double((double)(placer.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3);
        byte meta = (byte) worldIn.getBlockMetadata(x,y,z) ;
        byte metawithmask = (byte) (meta & 0b1100);
        byte rot = (byte) (l | metawithmask);
        //if (worldIn.isRemote){
            //System.out.println("pre-existing meta: " +formatToBinary(meta) );
            //System.out.println("meta with mask applied: " +formatToBinary(metawithmask) );
            //System.out.println("rotation: " + formatToBinary(l));
            //System.out.println("final rotation: " + formatToBinary(rot));
        //}
        worldIn.setBlockMetadataWithNotify(x, y, z, rot, 2);


    }
    @Override
    @SideOnly(Side.CLIENT)
    public final IIcon getIcon(int side, int meta){
        int rot = meta & 0b0011;
        int light = (meta & 0b1100) >> 2;
        return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : (rot == 2 && side == 2 ? (light == 1 ? iconFrontOn : iconFrontOff) : (rot == 3 && side == 5 ? (light == 1 ? iconFrontOn : iconFrontOff) : (rot == 0 && side == 3 ? (light == 1 ? iconFrontOn : iconFrontOff) : (rot == 1 && side == 4 ? (light == 1 ? iconFrontOn : iconFrontOff) : this.blockIcon)))));
    }

    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return (world.getBlockMetadata(x, y, z)& 0b1100) >> 2 == 1 ? 12 : 0;
    }
    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon){
        blockIcon = icon.registerIcon("Omron:blockTitaniumProcessorSide");
        iconTop = icon.registerIcon("Omron:blockTitaniumProcessorTop");
        iconFrontOn = icon.registerIcon("Omron:blockTitaniumProcessorOn");
        iconFrontOff = icon.registerIcon("Omron:blockTitaniumProcessorOff");
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6){
        this.dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }
}
