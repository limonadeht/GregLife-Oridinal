package client.render;

import org.lwjgl.opengl.GL11;

import client.model.ModelUniversalGenerator;
import client.tileentity.TileUniversalGenerator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileUniversalGeneratorRenderer extends TileEntitySpecialRenderer{

	public static final TileUniversalGeneratorRenderer instance = new TileUniversalGeneratorRenderer();

	public static TileUniversalGenerator generator;

	private static final ResourceLocation texture = new ResourceLocation("simplygenerators", "textures/models/UniversalGenerator_off.png");
	//private static final ResourceLocation texture = new ResourceLocation("simplygenerators", "textures/models/UniversalGenerator.png");

	private ModelUniversalGenerator model;
	public EntityPlayer player;
	public ItemStack itemstack;

	public TileUniversalGeneratorRenderer()
	{
		this.model = new ModelUniversalGenerator();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f){
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180, 0, 0, 1);

		TileUniversalGenerator tile = (TileUniversalGenerator) tileentity;

		int k = 0;
		if(tile.getBlockMetadata() == 3){
			k = 180;
		}
		if(tile.getBlockMetadata() == 2){
			k = 0;
		}
		if(tile.getBlockMetadata() == 5){
			k = 90;
		}
		if(tile.getBlockMetadata() == 4){
			k = -90;
		}
		GL11.glRotatef(k, 0.0F, 1.0F, 0.0F);

		this.bindTexture(texture);
		this.model.renderModel(0.0625F);

		GL11.glPopMatrix();
	}
}
