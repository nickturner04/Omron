package creditcrab.omron.Common.Items;

import creditcrab.omron.Omron;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class ItemMaterial extends Item {

    public static final String[] variantNames = {"ingotTitanium","ingotTitaniumImpure","cableElectrum","cableAluminium"};
    private IIcon[] iconList;

    public ItemMaterial(){
        setMaxStackSize(64);
        this.setCreativeTab(Omron.tabOmron);
        this.setHasSubtypes(true);
        this.setUnlocalizedName("omron:craftingMaterial");

        for (int i = 0; i < variantNames.length; i++) {
            LanguageRegistry.addName(new ItemStack(this,1,i),variantNames[i]);
        }
    }



    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for(int j = 0; j < variantNames.length; ++j) {
            list.add(new ItemStack(this, 1, j));
        }

    }


    @Override
    public IIcon getIconFromDamage(int i){
        return this.iconList[i];

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register){
        iconList = new IIcon[variantNames.length];
        for (int i = 0; i < variantNames.length; i++) {
            iconList[i] = register.registerIcon("Omron:" + variantNames[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getUnlocalizedName(ItemStack item) {
        return "omron:"+variantNames[item.getItemDamage()];
    }
}
