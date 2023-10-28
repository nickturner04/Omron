package creditcrab.omron.Common.Inventory.Gui;

import creditcrab.omron.Common.Inventory.ContainerEnergyBuffer;
import creditcrab.omron.Common.TileEntities.TileEntityBase;
import creditcrab.omron.Common.TileEntities.TileEntityEnergyBuffer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class GuiEnergyBuffer extends GuiContainer {

    private TileEntityEnergyBuffer buffer;

    public GuiEnergyBuffer(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerEnergyBuffer(inventory,tile));
        buffer = (TileEntityEnergyBuffer)tile;
        this.xSize = 176+33;
        this.ySize = 98+86+172;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        fontRendererObj.drawString("Energy Buffer",xSize - 150,guiTop, 1);
        fontRendererObj.drawString("Energy Stored: " + buffer.getEnergyStored(ForgeDirection.UP) + " RF",xSize - 150,guiTop + 10, 1);
        fontRendererObj.drawString("Max. RF In: " + buffer.getMaxInput() + " RF/t",xSize - 150,guiTop + 20, 1);
        fontRendererObj.drawString("Max. RF Out: " + buffer.getMaxOutput() + " RF/t",xSize - 150,guiTop + 30, 1);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("Omron:textures/gui/guiEnergyBuffer.png"));
        this.drawTexturedModalRect(this.guiLeft+33, (this.guiTop+172) - 98, 0, 0, 177, 87);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("Omron:textures/gui/guiEnergyBuffer.png"));
        this.drawTexturedModalRect(this.guiLeft+33, this.guiTop+172, 0, 89, 177, 87);
        int fluxBarHeight = (int)(46 * ((float)buffer.getEnergyStored(ForgeDirection.DOWN) / (float)buffer.getMaxEnergyStored(ForgeDirection.DOWN)));
        this.drawTexturedModalRect(this.guiLeft+33 + 8, (this.guiTop+74 + 67) - fluxBarHeight,179,1,14,fluxBarHeight);

    }
}
