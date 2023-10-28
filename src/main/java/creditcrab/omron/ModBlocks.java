package creditcrab.omron;

import creditcrab.omron.Common.Blocks.*;
import creditcrab.omron.Common.Items.ItemBlockCustomChest;
import net.minecraft.block.Block;

import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Created with IntelliJ IDEA. User: Pokefenn Date: 17/01/14 Time: 19:48
 */
public class ModBlocks {
    public static Block blockCustomChest;
    //public static Block blockEnergyBuffer;

    public static Block blockTitaniumProcessor;

    public static void init() {

        //blockCustomChest = new BlockCustomChest();
        //blockEnergyBuffer = new BlockEnergyBuffer();
        blockTitaniumProcessor = new BlockTitaniumProcessor();
    }

    public static void registerBlocks(){
        //GameRegistry.registerBlock(blockCustomChest, ItemBlockCustomChest.class,"blockCustomChest");
        //GameRegistry.registerBlock(blockEnergyBuffer,"blockEnergyBuffer");
        GameRegistry.registerBlock(blockTitaniumProcessor,"blockTitaniumProcessor");
    }
}
