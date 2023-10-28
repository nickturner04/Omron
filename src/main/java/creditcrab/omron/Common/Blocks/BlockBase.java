package creditcrab.omron.Common.Blocks;

import creditcrab.omron.Omron;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {

    public BlockBase(Material material, String name){
        super(material);
        this.setBlockName(name);
        this.setCreativeTab(Omron.tabOmron);
    }
}
