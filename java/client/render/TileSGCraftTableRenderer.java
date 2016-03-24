package client.render;

import org.lwjgl.opengl.GL11;

import client.model.ModelSGCraftTable;
import client.tileentity.TileSGCraftTable;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class TileSGCraftTableRenderer extends TileEntitySpecialRenderer{

	public static final TileSGCraftTableRenderer instance = new TileSGCraftTableRenderer();

	public static TileSGCraftTable craftTable;
	private ModelSGCraftTable model;

	private static final ResourceLocation texture = new ResourceLocation("simplygenerators", "textures/models/SGCraftTable.png");

	public TileSGCraftTableRenderer(){
		this.model = new ModelSGCraftTable();
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f){
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glRotatef(180, 0, 0, 1);

		this.bindTexture(texture);
		this.model.renderModel(0.0625F);

		GL11.glPopMatrix();
	}
}
