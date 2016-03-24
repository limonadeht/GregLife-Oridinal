package client.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import client.gui.container.ContainerOreCreator;
import client.tileentity.TileOreCreator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiOreCreator extends GuiContainer{

	private ResourceLocation texture = new ResourceLocation("simplygenerators", "textures/gui/OreCreator.png");

	public TileOreCreator tile;

	public EntityPlayer player;

	private int guiUpdateTick;

	public GuiOreCreator(InventoryPlayer player, TileOreCreator tileentity){
		super(new ContainerOreCreator(player, tileentity));

		this.tile = tileentity;

		this.xSize = 176;
		this.ySize = 188;
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		this.drawInfomation(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3){
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);

		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

		float power = (float) tile.getEnergyStored() / (float) tile.getMaxEnergyStored() * -1F + 1F;
		drawTexturedModalRect(
				guiLeft + 152,
				guiTop + 40 + (int) (power * 50),
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
			this.drawHoveringText(Arrays.asList(tile.getMaxEnergyStored() + " / " + tile.getEnergyStored() + " Rf"), x -guiLeft - 6, y - guiTop, fontRendererObj);
		}
	}

	@Override
	public void initGui(){
		super.initGui();
		buttonList.clear();
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
