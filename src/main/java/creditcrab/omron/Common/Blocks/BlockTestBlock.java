package creditcrab.omron.Common.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;

public class BlockTestBlock extends Block {
    public BlockTestBlock(){
        super(Material.rock);
        this.setHardness(0.5f);
        this.setHardness(0.55f);
        this.setBlockName("Omron_TestBlock");
        this.setBlockTextureName("Omron:testBlock");
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
}
