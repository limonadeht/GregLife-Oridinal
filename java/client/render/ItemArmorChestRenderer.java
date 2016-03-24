package client.render;

import org.lwjgl.opengl.GL11;

import client.model.ModelArmorOfDemon;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class ItemArmorChestRenderer implements IItemRenderer{

	public static final ItemArmorChestRenderer instance = new ItemArmorChestRenderer();

	private static final ResourceLocation texture = new ResourceLocation("simplygenerators", "textures/models/ArmorOfDemon.png");

	@Override
	public boolean handleRenderType(ItemStack itemstack, ItemRenderType type){
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data){
		GL11.glPushMatrix();
		GL11.glTranslated(0.5F, 1.0F, 0.5F);
		GL11.glRotatef(180, 0, 0, 1);
		GL11.glRotatef(180, 0, 1, 0);

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		ModelArmorOfDemon.instance.renderChest(0.0625F);

		GL11.glPopMatrix();
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}
}
