package creditcrab.omron.Common.Inventory.Gui;

import creditcrab.omron.Common.Inventory.ContainerTitaniumProcessor;
import creditcrab.omron.Common.TileEntities.TileEntityBase;
import creditcrab.omron.Common.TileEntities.TileEntityTitaniumProcessor;
import creditcrab.omron.Util.AssetUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class GuiTitaniumProcessor extends GuiContainer {

    private TileEntityTitaniumProcessor tile;

    public GuiTitaniumProcessor(InventoryPlayer inventory, TileEntityBase tile) {
        super(new ContainerTitaniumProcessor(inventory,tile));
        this.tile = (TileEntityTitaniumProcessor) tile;
        this.xSize = 176+33;
        this.ySize = 98+86+172;
    }

    @Override
    public void drawGuiContainerForegroundLayer(int x, int y){
        //fontRendererObj.drawString("Titanium Purifier",this.xSize / 2,0, 0x0017ff);
        AssetUtil.displayNameString(fontRendererObj,this.xSize,65,"Titanium Purifier");
        fontRendererObj.drawString(tile.currentEnergy + " RF",33 + 22, guiTop,0xc22b2b);

        fontRendererObj.drawString(tile.currentFluid + " mB",129,guiTop, 0x0017ff);

        fontRendererObj.drawString((int)(100 *((float)tile.currentTicks / (float) tile.ticksPerOperation)) + "%",130,guiTop + 35, 1);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(new ResourceLocation("Omron:textures/gui/guiTitaniumProcessor.png"));
        this.drawTexturedModalRect(this.guiLeft+33, (this.guiTop+172) - 98, 0, 0, 177, 87);
        this.drawTexturedModalRect(this.guiLeft+33, this.guiTop+172, 0, 89, 177, 87);
        int fluxBarHeight = (int)(63 *((float)tile.currentEnergy / (float)tile.getMaxEnergyStored(ForgeDirection.DOWN)));
        this.drawTexturedModalRect(this.guiLeft+33 + 8, (this.guiTop+74 + 67) - fluxBarHeight,179,1,14,fluxBarHeight);
        this.drawTexturedModalRect(this.guiLeft+33 + 8, (this.guiTop+78) ,209,1,14,63);
        FluidStack fluid = tile.getTankInfo(ForgeDirection.UP)[0].fluid;
        if (fluid != null){
            int waterBarHeight = (int)(63 *((float)tile.currentFluid / (float)tile.getTankInfo(ForgeDirection.DOWN)[0].capacity));
            this.drawTexturedModalRect(this.guiLeft+33 + 81, (this.guiTop+74 + 67) - waterBarHeight,194,1,14,waterBarHeight);
        }
        this.drawTexturedModalRect(this.guiLeft+33 + 81, (this.guiTop+78) ,209,1,14,63);

    }

}
