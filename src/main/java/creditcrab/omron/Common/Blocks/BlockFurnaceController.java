package creditcrab.omron.Common.Blocks;

import creditcrab.omron.Omron;
import net.minecraft.block.Block;

public class BlockFurnaceController extends BlockControllerBase{
    private static final Block[][][] structure = new Block[][][]
            {{
                    {Omron.testBlock,Omron.testBlock,Omron.testBlock},
                    {Omron.testBlock,Omron.testBlock,Omron.testBlock},
                    {Omron.testBlock,Omron.testBlock,Omron.testBlock}
            },{
                    {Omron.testBlock,Omron.testBlock,Omron.testBlock},
                    {Omron.testBlock,Omron.testBlock,Omron.testBlock},
                    {Omron.testBlock,Omron.testBlock,Omron.testBlock}
            },{
                    {Omron.testBlock,Omron.testBlock,Omron.testBlock},
                    {Omron.testBlock,Omron.testBlock,Omron.testBlock},
                    {Omron.testBlock,Omron.testBlock,Omron.testBlock}},
            };

    public BlockFurnaceController() {
        super(structure, 2, 1, 2);
        this.setBlockName("omron:blockFurnaceController");
    }
}
