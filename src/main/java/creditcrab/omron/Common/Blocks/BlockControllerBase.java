package creditcrab.omron.Common.Blocks;

import codechicken.lib.vec.Vector3;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockControllerBase extends BlockContainerBase {

    private int xoffset;
    private int yoffset;
    private int zoffset;

    private Block[][][] validstructure;
    public BlockControllerBase(Block[][][] structure, int xoffset, int yoffset, int zoffset) {
        super(Material.iron);
        this.xoffset = xoffset;
        this.yoffset = yoffset;
        this.zoffset = zoffset;
        this.validstructure = structure;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float par7, float par8, float par9){
        Vector3 start = new Vector3(x,y,z);
        player.addChatComponentMessage(new ChatComponentText(""+side){});
        if (side == 0 || side == 1) return false;
        ForgeDirection dir = ForgeDirection.VALID_DIRECTIONS[side].getOpposite();

        double angle = (Math.PI / 2) * side;
        int x2 = (int)(Math.cos(angle) * xoffset - Math.sin(angle) * zoffset);
        int z2 = (int)(Math.sin(angle) * xoffset + Math.cos(angle) * zoffset);
        Vector3 pos = new Vector3(x + dir.offsetX,y + dir.offsetY,z + dir.offsetZ);
        Vector3 startpos = new Vector3(x + xoffset, y + yoffset, z + zoffset);
        startpos = startpos.rotate(startpos.angle(pos),new Vector3(0,1,0));

        world.setBlock((int)pos.x,(int)pos.y,(int) pos.z,Blocks.coal_block);
        world.setBlock((int)startpos.x,(int)startpos.y,(int)startpos.z,Blocks.iron_block);



        /*

        int xstart = x - xoffset;
        int ystart = y - yoffset;
        int zstart = z - zoffset;
        String outstring = "|";
        Block[][][] structure = new Block[validstructure.length][validstructure[0].length][validstructure[0][0].length];

        for (int i = 0; i < validstructure.length; i++) {
            for (int j = 0; j < validstructure[0].length; j++) {
                for (int k = 0; k < validstructure[0][0].length; k++) {
                    world.setBlock(xstart + i,ystart + j, zstart + k, Blocks.pumpkin);
                    //outstring += (block.toString() + "|");
                }
            }
        }
        player.addChatComponentMessage(new ChatComponentText(outstring) {
        });
        */
        return false;
    }
}
