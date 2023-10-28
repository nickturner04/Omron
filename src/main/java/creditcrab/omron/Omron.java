package creditcrab.omron;

import creditcrab.omron.Common.Blocks.BlockTestBlock;
import creditcrab.omron.Common.CommonProxy;
import creditcrab.omron.Common.Inventory.Gui.GuiHandler;
import creditcrab.omron.Common.TileEntities.TileEntityBase;
import creditcrab.omron.Compat.WailaCompat;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;

import cpw.mods.fml.common.Mod;


@Mod(
        modid = "omron",
        name = "omron",
        version = "GRADLETOKEN_VERSION",
        dependencies = "after:CodeChickenLib@[1.1.5.7,);" + "after:NotEnoughItems@[2.3.20-GTNH,)",
        guiFactory = "WayofTime.alchemicalWizardry.client.gui.ConfigGuiFactory")
public class Omron {
@Mod.Instance("omron")
    public  static Omron instance;

@SidedProxy(clientSide = "creditcrab.omron.Client.ClientProxy",serverSide = "creditcrab.omron.Common.CommonProxy")
    public static CommonProxy proxy;

    public static Block testBlock;

    public static  CreativeTabs tabOmron;

    @Mod.EventHandler
    // preInit "Run before anything else. Read your config, create blocks, items,
    // etc., and register them with the GameRegistry."
    public void preInit(FMLPreInitializationEvent event) {
        testBlock = new BlockTestBlock();
        GameRegistry.registerBlock(testBlock,"testBlock");
        ModItems.init();
        ModItems.registerItems();
        ModBlocks.init();
        ModBlocks.registerBlocks();

        tabOmron = new OmronCreativeTab();
        TileEntityBase.init();

        proxy.preInit(event);

    }

    @Mod.EventHandler
    // load "Do your mod setup. Build whatever data structures you care about. Register recipes."
    public void init(FMLInitializationEvent event) {
        WailaCompat.init();
        OmronCreativeTab.init();
        GuiHandler.init();
        proxy.init(event);

    }

    @Mod.EventHandler
    // postInit "Handle interaction with other mods, complete your setup based on this."
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverAboutToStart(FMLServerAboutToStartEvent event) {
        proxy.serverAboutToStart(event);
    }

    @Mod.EventHandler
    // register server commands in this event handler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    @Mod.EventHandler
    public void serverStarted(FMLServerStartedEvent event) {
        proxy.serverStarted(event);
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        proxy.serverStopping(event);
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        proxy.serverStopped(event);
    }


}
