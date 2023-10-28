package creditcrab.omron.Common.Items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockCustomChest extends ItemBlock {
    public ItemBlockCustomChest(Block block){
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack itemStack){
        return "customChest" +itemStack.getItemDamage();
    }

    @Override
    public int getMetadata (int meta){
        return meta;
    }
}
