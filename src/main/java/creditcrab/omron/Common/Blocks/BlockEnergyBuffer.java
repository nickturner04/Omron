package creditcrab.omron.Common.Blocks;

import creditcrab.omron.Common.Inventory.Gui.GuiHandler;
import creditcrab.omron.Common.TileEntities.TileEntityEnergyBuffer;
import creditcrab.omron.Omron;
import cofh.api.item.IToolHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnergyBuffer extends BlockContainerBase{

    @SideOnly(Side.CLIENT)
    private IIcon iconTop;

    @SideOnly(Side.CLIENT)
    private IIcon iconSideOut;

    @SideOnly(Side.CLIENT)
    private IIcon iconSideIn;

    @SideOnly(Side.CLIENT)
    private IIcon iconSideNone;


    public BlockEnergyBuffer() {
        super(Material.iron);
        setBlockName("omron:blockEnergyBuffer");
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityEnergyBuffer();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9){
        ItemStack item = player.getCurrentEquippedItem();
        TileEntityEnergyBuffer tile = (TileEntityEnergyBuffer) world.getTileEntity(x, y, z);
        if (item != null){
            if (item.getItem() instanceof IToolHammer || item.getItem() == Items.stick){

                if (!world.isRemote){
                    String currentSide = tile.getSideConfig(side).toString();
                    tile.cycleSideConfig(side);
                    String nextSide = tile.getSideConfig(side).toString();

                    System.out.println(currentSide + " -> " + nextSide);
                    String allSides = "";
                    for (int i = 0; i < 6; i++) {
                        allSides += tile.getSideConfig(i).toString();
                        allSides += "|";
                    }
                    System.out.println(allSides);


                }
                else{
                    tile.cycleSideConfig(side);
                }
                world.markBlockForUpdate(x,y,z);
                tile.markDirty();
                return true;
            }


        }
        if(!world.isRemote) {

            player.openGui(Omron.instance, GuiHandler.GuiTypes.ENERGYBUFFER.ordinal(), world, x, y, z);

            return true;
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess worldIn, int x, int y, int z, int side){
        TileEntityEnergyBuffer tile = (TileEntityEnergyBuffer) worldIn.getTileEntity(x,y,z);
        switch (tile.getSideConfig(side)){
            case IN:return this.iconSideIn;
            case OUT:return this.iconSideOut;
            case NONE: return this.iconSideNone;
            default: return this.blockIcon;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon){
        blockIcon = icon.registerIcon("Omron:blockEnergyBuffer");
        iconTop = icon.registerIcon("Omron:blockFluidFurnaceTop");
        iconSideOut = icon.registerIcon("Omron:blockEnergyBufferSideOut");
        iconSideIn = icon.registerIcon("Omron:blockEnergyBufferSideIn");
        iconSideNone = icon.registerIcon("Omron:blockEnergyBufferSideNone");
    }
}
