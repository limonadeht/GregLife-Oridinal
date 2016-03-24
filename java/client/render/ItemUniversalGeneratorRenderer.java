package client.render;

import org.lwjgl.opengl.GL11;

import client.model.ModelUniversalGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemUniversalGeneratorRenderer implements IItemRenderer{

	public static final ItemUniversalGeneratorRenderer instance = new ItemUniversalGeneratorRenderer();
	private static final ResourceLocation TEXTURE = new ResourceLocation("simplygenerators", "textures/models/UniversalGenerator.png");

	@Override
	public boolean handleRenderType(ItemStack itemstack, ItemRenderType type){
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data){
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.4F, 0.5F);
		GL11.glRotatef(180, 0, 0, 1);
		GL11.glRotatef(180, 0, 1, 0);//Y軸を中心に180℃回転。(角度, x軸, y軸, z軸)

		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		ModelUniversalGenerator.instance.renderModel(0.0625F);

		GL11.glPopMatrix();
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}
}
