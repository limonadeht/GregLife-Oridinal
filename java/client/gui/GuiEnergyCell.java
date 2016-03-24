package client.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import client.gui.container.ContainerEnergyCell;
import client.tileentity.TileEnergyCell;
import common.block.BlockEnergyCell;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiEnergyCell extends GuiContainer{

	private ResourceLocation texture = new ResourceLocation("simplygenerators", "textures/gui/EnergyCell.png");

	public TileEnergyCell energyCell;
	public BlockEnergyCell energyCellblock;
	public EntityPlayer player;

	private int guiUpdateTick;

	public GuiEnergyCell(InventoryPlayer player, TileEnergyCell tileentity){
		super(new ContainerEnergyCell(player, tileentity));

		this.energyCell = tileentity;

		this.xSize = 176;
		this.ySize = 188;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		this.drawInfomation(mouseX, mouseY);

		this.fontRendererObj.drawString(
				StatCollector.translateToLocal("Energy: " + energyCell.getEnergyStored() + " / " + energyCell.getMaxEnergyStored() + " Rf"), 7, 46, 0x990000);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3){
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

		float power = (float) energyCell.getEnergyStored() / (float) energyCell.getMaxEnergyStored() * -1F + 1F;
		drawTexturedModalRect(
				guiLeft + 151,
				guiTop + 39 + (int) (power * 50),
				xSize,
				(int) (power * 50),
				17, 49 - (int) (power * 50)
			);
	}

	private void drawInfomation(int x, int y){
		int minX = guiLeft + 151;
		int maxX = guiLeft + 168;
		int minY = guiTop + 39;
		int maxY = guiTop + 88;
		if(x >= minX && x <= maxX && y >= minY && y <= maxY)
		{
			this.drawHoveringText(Arrays.asList(energyCell.getMaxEnergyStored() + " / " + energyCell.getEnergyStored() + " Rf"), x -guiLeft - 6, y - guiTop, fontRendererObj);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui(){
		super.initGui();
		buttonList.clear();

		buttonList.add(new GuiButton(0, this.guiLeft +  124, this.guiTop + 64, 24, 14, "ESC"));//close_inventory
	}

	@Override
	protected void actionPerformed(GuiButton button){
		if(button.id == 0){
			this.mc.displayGuiScreen(null);//GUIを閉じる
		}
	}

	@Override
	public void updateScreen(){
		guiUpdateTick++;
		if(this.guiUpdateTick >= 10){
			this.initGui();
			this.guiUpdateTick = 0;
		}
		super.updateScreen();
	}
}
