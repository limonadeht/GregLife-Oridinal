package client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiRecipeUniversalGenerator extends GuiScreen{

	public static final ResourceLocation TEXTURE = new ResourceLocation("simplygenerators", "textures/gui/UniversalGeneratorRecipe.png");

	@Override
	public void drawScreen(int mouseX, int mouseY, float tick){
		this.drawDefaultBackground();

		Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);

		super.drawScreen(mouseX, mouseY, tick);
	}

	/*private void drawInfomation(int x, int y){
		int minX = guiLeft + 1;
		int maxX = guiLeft + 350;
		int minY = guiTop + 1;
		int maxY = guiTop + 208;
		if(x >= minX && x <= maxX && y >= minY && y <= maxY){
			this.drawHoveringText(Arrays.asList(EnumChatFormatting.LIGHT_PURPLE + "Requires:"), x -guiLeft - 6, y - guiTop, fontRendererObj);
			this.drawHoveringText(Arrays.asList("Block of Diamond, 1"), x -guiLeft - 6, y - guiTop, fontRendererObj);
			this.drawHoveringText(Arrays.asList("Block of Gold, 4"), x -guiLeft - 6, y - guiTop, fontRendererObj);
			this.drawHoveringText(Arrays.asList("Block of Iron, 12"), x -guiLeft - 6, y - guiTop, fontRendererObj);
			this.drawHoveringText(Arrays.asList("Basic Circuit, 4"), x -guiLeft - 6, y - guiTop, fontRendererObj);
			this.drawHoveringText(Arrays.asList("Fluid Storage, 1"), x -guiLeft - 6, y - guiTop, fontRendererObj);
			this.drawHoveringText(Arrays.asList("Reinforced Fluid Storage, 1"), x -guiLeft - 6, y - guiTop, fontRendererObj);
			this.drawHoveringText(Arrays.asList("Nether Star, 1"), x -guiLeft - 6, y - guiTop, fontRendererObj);
			this.drawHoveringText(Arrays.asList("Machine Frame(Universal Generator), 1"), x -guiLeft - 6, y - guiTop, fontRendererObj);
		}
	}*/
}
