package creditcrab.omron.Common.TileEntities;

import net.minecraft.block.Block;

public abstract class TileEntityMultiblockBase extends TileEntityInventoryBase{

    protected boolean activated;
    Block[][][] structure;

    public TileEntityMultiblockBase(int slots, String name) {
        super(slots, name);
    }
}
