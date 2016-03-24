package client.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import client.gui.container.ContainerBoiler;
import client.tileentity.TileBoiler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiBoiler extends GuiContainer{

	public static final ResourceLocation TEXTURE = new ResourceLocation("simplygenerators", "textures/gui/Boiler.png");

	public TileBoiler boiler;

	public EntityPlayer player;

	private int guiUpdateTick;

	public GuiBoiler(InventoryPlayer player, TileBoiler tile){
		super(new ContainerBoiler(player, tile));

		this.boiler = tile;

		this.xSize = 176;
		this.ySize = 188;
	}

	public void drawGuiContainerForegroundLayer(int par1, int par2){
		this.drawInfomation(par1, par2);

		this.fontRendererObj.drawString(
				StatCollector.translateToLocal("Fluid: " + boiler.waterTank.getFluidName() + ":" + boiler.waterTank.getFluidAmount() + " / " + boiler.waterTank.getCapacity()), 7, 56, 0x3399FF);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3){
		GL11.glColor4f(1F, 1F, 1F, 1F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);

		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}

	private void drawInfomation(int x, int y){
		int minX = guiLeft + 47;
		int maxX = guiLeft + 64;
		int minY = guiTop + 26;
		int maxY = guiTop + 66;
		if(x >= minX && x <= maxX && y >= minY && y <= maxY){
			this.drawHoveringText(Arrays.asList(EnumChatFormatting.LIGHT_PURPLE + "EnergyStorage: " + boiler.getEnergyStored() + " / " + boiler.getMaxEnergyStored()), x -guiLeft - 6, y - guiTop, fontRendererObj);
		}

		int minX1 = guiLeft + 66;
		int maxX1 = guiLeft + 83;
		int minY1 = guiTop + 26;
		int maxY1 = guiTop + 66;
		if(x >= minX1 && x <= maxX1 && y >= minY1 && y <= maxY1){
			this.drawHoveringText(Arrays.asList(boiler.waterTank.getFluidAmount() + " / 16000"), x -guiLeft - 6, y - guiTop, fontRendererObj);
		}

		int minX2 = guiLeft + 116;
		int maxX2 = guiLeft + 133;
		int minY2 = guiTop + 26;
		int maxY2 = guiTop + 66;
		if(x >= minX2 && x <= maxX2 && y >= minY2 && y <= maxY2){
			this.drawHoveringText(Arrays.asList(boiler.steamTank.getFluidAmount() + " / 16000"), x -guiLeft - 6, y - guiTop, fontRendererObj);
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
