package creditcrab.omron.Util;

import net.minecraft.client.gui.FontRenderer;

public class AssetUtil {

    public static void displayNameString(FontRenderer fontRendererObj, int xPos, int yPos, String name){
        fontRendererObj.drawString(name, xPos / 2 - fontRendererObj.getStringWidth(name) / 2, yPos, 16777215);
    }
}
