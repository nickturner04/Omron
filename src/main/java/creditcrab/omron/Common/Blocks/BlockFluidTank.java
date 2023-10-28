package creditcrab.omron.Common.Blocks;

import creditcrab.omron.Common.TileEntities.TileEntityFluidTank;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class BlockFluidTank extends BlockContainerBase{
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;

    public BlockFluidTank() {
        super(Material.iron);
        this.setBlockName("omron:blockFluidTank");
    }


    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFluidTank();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public final IIcon getIcon(int side, int meta){
        return side == 1 || side == 0 ? iconTop : this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister icon){
        blockIcon = icon.registerIcon("Omron:blockFluidTank");
        iconTop = icon.registerIcon("Omron:blockFluidFurnaceTop");
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        if (world.isRemote) return true;
        ItemStack item = player.getCurrentEquippedItem();

        TileEntityFluidTank tile = (TileEntityFluidTank) world.getTileEntity(x, y, z);
        FluidTankInfo tankInfo = tile.getTankInfo(ForgeDirection.UP)[0];
        if (item != null){
            if(item.getItem() == Items.stick){
                System.out.println(tankInfo.fluid.amount + "/" + tankInfo.capacity);
                return true;
            }
            if (FluidContainerRegistry.isEmptyContainer(item)) {
                ItemStack filled = FluidContainerRegistry.fillFluidContainer(tankInfo.fluid, item);
                if (filled != null) {
                    int a = FluidContainerRegistry.getFluidForFilledItem(filled).amount;
                    if (player.capabilities.isCreativeMode) {
                        tile.drain(ForgeDirection.DOWN, a, true);
                    } else if (item.stackSize == 1) {
                        player.setCurrentItemOrArmor(0, filled);
                        tile.drain(ForgeDirection.DOWN, a, true);
                    } else if (player.inventory.addItemStackToInventory(filled)) {
                        final ItemStack itemStack = item;
                        --itemStack.stackSize;
                        tile.drain(ForgeDirection.DOWN, a, true);
                        if (player instanceof EntityPlayerMP) {
                            ((EntityPlayerMP) player).mcServer.getConfigurationManager().syncPlayerInventory((EntityPlayerMP) player);
                        }
                    }
                    if (tile.getTankInfo(ForgeDirection.DOWN)[0].fluid == null) {
                        world.markBlockForUpdate(x, y, z);
                    }
                    return true;
                }

            } else if (FluidContainerRegistry.isFilledContainer(item)) {
                final FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(item);
                if (tile.fill(ForgeDirection.UP, fluid, false) == fluid.amount) {
                    if (player.capabilities.isCreativeMode) {
                        tile.fill(ForgeDirection.UP, fluid, true);
                    } else {
                        ItemStack c = null;
                        if (item.getItem().hasContainerItem(item)) {
                            c = item.getItem().getContainerItem(item);
                        }
                        if (c == null || item.stackSize == 1 || player.inventory.addItemStackToInventory(c)) {
                            tile.fill(ForgeDirection.UP, fluid, true);
                            if (item.stackSize == 1) {
                                player.setCurrentItemOrArmor(0, c);
                            } else if (item.stackSize > 1) {
                                final ItemStack itemStack2 = item;
                                --itemStack2.stackSize;
                            }
                        }
                    }
                    return true;
                }
                return true;
            }
        }

        return false;
    }
}
