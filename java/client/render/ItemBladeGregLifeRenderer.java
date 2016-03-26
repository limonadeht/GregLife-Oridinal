package client.render;

import org.lwjgl.opengl.GL11;

import client.model.ModelBladeGregLife;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemBladeGregLifeRenderer implements IItemRenderer{

	public static final ItemBladeGregLifeRenderer instance = new ItemBladeGregLifeRenderer();
	private static final ResourceLocation TEXTURE = new ResourceLocation("simplygenerators", "textures/models/Blade.png");

	@Override
	public boolean handleRenderType(ItemStack itemstack, ItemRenderType type){
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data){
		GL11.glPushMatrix();

		float scala = 2.5F;

		GL11.glScalef(scala, scala, scala);

		GL11.glRotatef(0F, -10.0F, 0.0F, 0.0F);
		GL11.glRotatef(8F, 0.0F, -10.0F, 0.0F);
		GL11.glRotatef(125F, 0.0F, 0.0F, -10.0F);

		GL11.glTranslatef(-0.304800F, -0.500F, -0.09F);
		/*GL11.glTranslated(0.5F, 1.3F, 0.5F);
		GL11.glRotatef(180, 0, 0, 1);
		GL11.glRotatef(180, 0, 1, 0);//Y軸を中心に180℃回転。(角度, x軸, y軸, z軸)
		GL11.glRotatef(130, 1, 0, 0);*/

		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
		ModelBladeGregLife.instance.renderModels(0.0625F);

		GL11.glPopMatrix();
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}
}
