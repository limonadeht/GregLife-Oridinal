package client.render;

import org.lwjgl.opengl.GL11;

import client.model.ModelSGCraftTable;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemSGCraftTableRenderer implements IItemRenderer{

	public static final ItemSGCraftTableRenderer instance = new ItemSGCraftTableRenderer();

	private static final ResourceLocation texture = new ResourceLocation("simplygenerators", "textures/models/SGCraftTable.png");

	@Override
	public boolean handleRenderType(ItemStack itemstack, ItemRenderType type){
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data){
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.4F, 0.5F);
		GL11.glRotatef(180, 0, 0, 1);

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		ModelSGCraftTable.instance.renderModel(0.0625F);

		GL11.glPopMatrix();
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}
}
