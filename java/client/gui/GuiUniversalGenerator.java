package client.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import client.gui.container.ContainerUniversalGenerator;
import client.tileentity.TileUniversalGenerator;
import common.block.BlockUniversalGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiUniversalGenerator extends GuiContainer{

	public static final ResourceLocation TEXTURE = new ResourceLocation("simplygenerators", "textures/gui/generator1_gui.png");

	public TileUniversalGenerator generator;
	public BlockUniversalGenerator generator1;
	public World world;
	int x, y, z;
	public EntityPlayer player;

	private int guiUpdateTick;

	public GuiUniversalGenerator(InventoryPlayer invPlayer, TileUniversalGenerator entity){
		super(new ContainerUniversalGenerator(invPlayer, entity));

		this.generator = entity;

		this.xSize = 176;
		this.ySize = 166;
	}

	public void drawGuiContainerForegroundLayer(int par1, int par2){
		this.drawInfomation(par1, par2);

		this.fontRendererObj.drawString(
				StatCollector.translateToLocal("UniversalGenerator"), 7, 6, 0xFF33FF);
		this.fontRendererObj.drawString(
				StatCollector.translateToLocal("Max Generate: " + generator.getMaxGenerateRf()), 7, 16, 0x000000);
		if(generator.burnTimeRemaining < 0){
			this.fontRendererObj.drawString(
					StatCollector.translateToLocal("Current Generate: 0 Rf/t"), 7, 26, 0x000000);
		}else{
			this.fontRendererObj.drawString(
					StatCollector.translateToLocal("Current Generate: " + generator.getMaxGenerateRf() + " Rf/t"), 7, 26, 0x000000);
		}
		this.fontRendererObj.drawString(
				StatCollector.translateToLocal("Storage: "), 7, 36, 0x000000);
		this.fontRendererObj.drawString(
				StatCollector.translateToLocal("Energy: " + generator.getEnergyStored() + " / " + generator.getMaxEnergyStored() + " Rf"), 7, 46, 0x990000);
		this.fontRendererObj.drawString(
				StatCollector.translateToLocal("Fluid: " + generator.tank.getFluidName() + ":" + generator.tank.getFluidAmount() + " / " + generator.tank.getCapacity()), 7, 56, 0x3399FF);
		this.fontRendererObj.drawString(
				StatCollector.translateToLocal("Burning at: " + generator.burnTimeRemaining), 7, 66, 0x660000);
		/*String name = this.generator.hasCustomInventoryName() ? this.generator.getInventoryName() : I18n.format(this.generator.getInventoryName(), new Object[0]);
		this.fontRendererObj.drawString(name, this.xSize / 2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 118, this.ySize - 96 + 2, 4210752);*/
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3){
		GL11.glColor4f(1F, 1F, 1F, 1F);

		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);

		int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

		if(generator.isBurning()){
			int i1 = this.generator.getBurnTimeRemainingScaled(13);
			this.drawTexturedModalRect(k + 152, l + 3 + 12 - i1, 176, 11 - i1, 14, i1 + 1);
		}
	}

	private void drawInfomation(int x, int y){
		int minX = guiLeft + 151;
		int maxX = guiLeft + 168;
		int minY = guiTop + 19;
		int maxY = guiTop + 36;
		if(x >= minX && x <= maxX && y >= minY && y <= maxY){
			this.drawHoveringText(Arrays.asList(EnumChatFormatting.LIGHT_PURPLE + "Put the fuel"), x -guiLeft - 6, y - guiTop, fontRendererObj);
		}

		int minX1 = guiLeft + 151;
		int maxX1 = guiLeft + 168;
		int minY1 = guiTop + 39;
		int maxY1 = guiTop + 79;
		if(x >= minX1 && x <= maxX1 && y >= minY1 && y <= maxY1){
			this.drawHoveringText(Arrays.asList("Fluid: " + this.generator.tank.getFluidName() + " : " + this.generator.tank.getFluidAmount()), x -guiLeft - 6, y - guiTop, fontRendererObj);
		}

		int minX2 = guiLeft + 153;
		int maxX2 = guiLeft + 165;
		int minY2 = guiTop + 4;
		int maxY2 = guiTop + 16;
		if(x >= minX2 && x <= maxX2 && y >= minY2 && y <= maxY2){
			this.drawHoveringText(Arrays.asList(EnumChatFormatting.DARK_RED + "Burning at:" + generator.burnTimeRemaining), x -guiLeft - 6, y - guiTop, fontRendererObj);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui(){
		super.initGui();
		buttonList.clear();

		//左から、(ID, 横軸座標, 縦軸座標, 横長さ, 縦長さ-1, 文字)
		buttonList.add(new GuiButton(0, this.guiLeft +  124, this.guiTop + 64, 24, 14, "ESC"));//close_inventory
		buttonList.add(new GuiButton(1, this.guiLeft +  92, this.guiTop + 64, 30, 12, "Recipe"));//close_inventory
		//buttonList.add(new GuiButton(1, this.guiLeft + 116, this.guiTop + 64, 16, 15, ""));//clear_energy
		//buttonList.add(new GuiButton(2, this.guiLeft + 134, this.guiTop + 64, 16, 15, ""));//clear_fluid
	}

	@Override
	protected void actionPerformed(GuiButton button){
		if(button.id == 0){
			this.mc.displayGuiScreen(null);//GUIを閉じる
		}
		if(button.id == 1){
			new GuiRecipeUniversalGenerator();
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
