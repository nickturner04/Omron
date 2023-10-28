package creditcrab.omron;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class OmronCreativeTab extends CreativeTabs {

    static ItemStack iconStack;
    public OmronCreativeTab(){super("Omron");}

    public static void init(){
        iconStack = new ItemStack(ModBlocks.blockTitaniumProcessor,0,Short.MAX_VALUE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack(){
        return  iconStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getTabIconItem() {
        return getIconItemStack().getItem();
    }
}
