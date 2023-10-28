package creditcrab.omron.Compat;

import creditcrab.omron.Common.Blocks.BlockFluidTank;
import creditcrab.omron.Common.Blocks.BlockTitaniumProcessor;
import cpw.mods.fml.common.event.FMLInterModComms;
import mcp.mobius.waila.api.IWailaRegistrar;

public class WailaCompat {
    public static void callbackRegister(IWailaRegistrar registrar){
        registrar.registerStackProvider(new WailaMachineHandler(), BlockTitaniumProcessor.class);
        registrar.registerBodyProvider(new WailaMachineHandler(), BlockTitaniumProcessor.class);
    }

    public static void init(){
        FMLInterModComms.sendMessage("Waila","register", WailaCompat.class.getName() + ".callbackRegister");
    }
}
