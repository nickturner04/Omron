package creditcrab.omron.Common.Inventory.Gui;

import creditcrab.omron.Common.Inventory.ContainerCustomChest;
import creditcrab.omron.Common.TileEntities.TileEntityBase;
import creditcrab.omron.Common.TileEntities.TileEntityCustomChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ContainerCustomChestGui extends GuiContainer {

    TileEntityCustomChest chest;

    public ContainerCustomChestGui(InventoryPlayer inventoryPlayer, TileEntityBase chest) {
        super(new ContainerCustomChest(inventoryPlayer,chest));
        this.chest = (TileEntityCustomChest) chest;

        this.xSize = 176+33;
        this.ySize = 98+86+172;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        //AssetUtil.displayNameString(this.fontRendererObj, xSize, -10, this.chest.getInventoryName());
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float f, int x, int y){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("Omron:textures/gui/guiCustomChest.png"));
        this.drawTexturedModalRect(this.guiLeft+33, (this.guiTop+172) - 98, 0, 0, 171, 98);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("Omron:textures/gui/guiInventory.png"));
        this.drawTexturedModalRect(this.guiLeft+33, this.guiTop+172, 0, 0, 176, 86);
    }
}
