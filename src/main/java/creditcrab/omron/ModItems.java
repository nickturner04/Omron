package creditcrab.omron;

import creditcrab.omron.Common.Items.Equipment.OmronArmor;
import creditcrab.omron.Common.Items.ItemMaterial;
import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created with IntelliJ IDEA. User: Pokefenn Date: 17/01/14 Time: 19:48
 */
public class ModItems {

    public static Item ingotTitanium;
    public static Item helmetOmron,chestplateOmron,leggingsOmron,bootsOmron;

    public static void init(){
        helmetOmron = new OmronArmor(0);
        chestplateOmron = new OmronArmor(1);
        leggingsOmron = new OmronArmor(2);
        bootsOmron = new OmronArmor(3);
        ingotTitanium = new ItemMaterial();
    }

    public static void registerItems() {
        GameRegistry.registerItem(helmetOmron,"helmetOmron");
        GameRegistry.registerItem(chestplateOmron,"chestplateOmron");
        GameRegistry.registerItem(leggingsOmron,"leggingsOmron");
        GameRegistry.registerItem(bootsOmron,"bootsOmron");
        GameRegistry.registerItem(ingotTitanium,"ingotTitanium");

        OreDictionary.registerOre("ingotRutile",new ItemStack(ingotTitanium,1,1));
        OreDictionary.registerOre("ingotTitanium",new ItemStack(ingotTitanium,1,0));
        OreDictionary.registerOre("cableElectrum",new ItemStack(ingotTitanium,1,2));
        OreDictionary.registerOre("cableAluminium",new ItemStack(ingotTitanium,1,3));
        OreDictionary.registerOre("cableAluminum",new ItemStack(ingotTitanium,1,3));
    }
}
