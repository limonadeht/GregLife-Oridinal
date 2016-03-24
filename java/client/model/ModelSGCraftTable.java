package client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ModelSGCraftTable extends ModelBase{

	public static final ModelSGCraftTable instance = new ModelSGCraftTable();

  //fields
    ModelRenderer Bottom;
    ModelRenderer Rod1;
    ModelRenderer Rod2;
    ModelRenderer Rod3;
    ModelRenderer Rod4;
    ModelRenderer Ceiling1;
    ModelRenderer Ceiling2;
    ModelRenderer Ceiling3;
    ModelRenderer MiniCube1;
    ModelRenderer MiniCube2;
    ModelRenderer MiniCube3;
    ModelRenderer MiniCube4;
    ModelRenderer Crafting;
    ModelRenderer Rods1;
    ModelRenderer Rods2;
    ModelRenderer Rods3;
    ModelRenderer Rods4;
    ModelRenderer Rods5;
    ModelRenderer Rods6;
    ModelRenderer Rods7;
    ModelRenderer Rods8;

  public ModelSGCraftTable()
  {
    textureWidth = 128;
    textureHeight = 128;

      Bottom = new ModelRenderer(this, 0, 0);
      Bottom.addBox(0F, 0F, 0F, 16, 1, 16);
      Bottom.setRotationPoint(-8F, 23F, -8F);
      Bottom.setTextureSize(128, 128);
      Bottom.mirror = true;
      setRotation(Bottom, 0F, 0F, 0F);
      Rod1 = new ModelRenderer(this, 0, 19);
      Rod1.addBox(0F, 0F, 0F, 3, 4, 3);
      Rod1.setRotationPoint(-7F, 19F, -7F);
      Rod1.setTextureSize(128, 128);
      Rod1.mirror = true;
      setRotation(Rod1, 0F, 0F, 0F);
      Rod2 = new ModelRenderer(this, 13, 19);
      Rod2.addBox(0F, 0F, 0F, 3, 4, 3);
      Rod2.setRotationPoint(4F, 19F, -7F);
      Rod2.setTextureSize(128, 128);
      Rod2.mirror = true;
      setRotation(Rod2, 0F, 0F, 0F);
      Rod3 = new ModelRenderer(this, 26, 19);
      Rod3.addBox(0F, 0F, 0F, 3, 4, 3);
      Rod3.setRotationPoint(-7F, 19F, 4F);
      Rod3.setTextureSize(128, 128);
      Rod3.mirror = true;
      setRotation(Rod3, 0F, 0F, 0F);
      Rod4 = new ModelRenderer(this, 26, 19);
      Rod4.addBox(0F, 0F, 0F, 3, 4, 3);
      Rod4.setRotationPoint(4F, 19F, 4F);
      Rod4.setTextureSize(128, 128);
      Rod4.mirror = true;
      setRotation(Rod4, 0F, 0F, 0F);
      Ceiling1 = new ModelRenderer(this, 0, 0);
      Ceiling1.addBox(0F, 0F, 0F, 16, 1, 16);
      Ceiling1.setRotationPoint(-8F, 18F, -8F);
      Ceiling1.setTextureSize(128, 128);
      Ceiling1.mirror = true;
      setRotation(Ceiling1, 0F, 0F, 0F);
      Ceiling2 = new ModelRenderer(this, 0, 28);
      Ceiling2.addBox(0F, 0F, 0F, 14, 1, 14);
      Ceiling2.setRotationPoint(-7F, 17F, -7F);
      Ceiling2.setTextureSize(128, 128);
      Ceiling2.mirror = true;
      setRotation(Ceiling2, 0F, 0F, 0F);
      Ceiling3 = new ModelRenderer(this, 0, 0);
      Ceiling3.addBox(0F, 0F, 0F, 16, 1, 16);
      Ceiling3.setRotationPoint(-8F, 16F, -8F);
      Ceiling3.setTextureSize(128, 128);
      Ceiling3.mirror = true;
      setRotation(Ceiling3, 0F, 0F, 0F);
      MiniCube1 = new ModelRenderer(this, 0, 44);
      MiniCube1.addBox(0F, 0F, 0F, 1, 1, 1);
      MiniCube1.setRotationPoint(7F, 17F, -8F);
      MiniCube1.setTextureSize(128, 128);
      MiniCube1.mirror = true;
      setRotation(MiniCube1, 0F, 0F, 0F);
      MiniCube2 = new ModelRenderer(this, 0, 44);
      MiniCube2.addBox(0F, 0F, 0F, 1, 1, 1);
      MiniCube2.setRotationPoint(-8F, 17F, -8F);
      MiniCube2.setTextureSize(128, 128);
      MiniCube2.mirror = true;
      setRotation(MiniCube2, 0F, 0F, 0F);
      MiniCube3 = new ModelRenderer(this, 0, 44);
      MiniCube3.addBox(0F, 0F, 0F, 1, 1, 1);
      MiniCube3.setRotationPoint(7F, 17F, 7F);
      MiniCube3.setTextureSize(128, 128);
      MiniCube3.mirror = true;
      setRotation(MiniCube3, 0F, 0F, 0F);
      MiniCube4 = new ModelRenderer(this, 0, 44);
      MiniCube4.addBox(0F, 0F, 0F, 1, 1, 1);
      MiniCube4.setRotationPoint(-8F, 17F, 7F);
      MiniCube4.setTextureSize(128, 128);
      MiniCube4.mirror = true;
      setRotation(MiniCube4, 0F, 0F, 0F);
      Crafting = new ModelRenderer(this, 65, 0);
      Crafting.addBox(0F, 0F, 0F, 12, 1, 12);
      Crafting.setRotationPoint(-6F, 15F, -6F);
      Crafting.setTextureSize(128, 128);
      Crafting.mirror = true;
      setRotation(Crafting, 0F, 0F, 0F);
      Rods1 = new ModelRenderer(this, 0, 63);
      Rods1.addBox(0F, 0F, 0F, 10, 1, 1);
      Rods1.setRotationPoint(-5F, 23F, -6F);
      Rods1.setTextureSize(128, 128);
      Rods1.mirror = true;
      setRotation(Rods1, 0F, 0F, -0.4537856F);
      Rods2 = new ModelRenderer(this, 0, 59);
      Rods2.addBox(0F, 0F, 0F, 11, 1, 1);
      Rods2.setRotationPoint(-5F, 18F, -6F);
      Rods2.setTextureSize(128, 128);
      Rods2.mirror = true;
      setRotation(Rods2, 0F, 0F, 0.4537856F);
      Rods3 = new ModelRenderer(this, 0, 70);
      Rods3.addBox(0F, 0F, 0F, 1, 1, 10);
      Rods3.setRotationPoint(5F, 23F, -5F);
      Rods3.setTextureSize(128, 128);
      Rods3.mirror = true;
      setRotation(Rods3, 0.4537856F, 0F, 0F);
      Rods4 = new ModelRenderer(this, 0, 83);
      Rods4.addBox(0F, 0F, 0F, 1, 1, 11);
      Rods4.setRotationPoint(5F, 18F, -5F);
      Rods4.setTextureSize(128, 128);
      Rods4.mirror = true;
      setRotation(Rods4, -0.4712389F, 0F, 0F);
      Rods5 = new ModelRenderer(this, 23, 70);
      Rods5.addBox(0F, 0F, 0F, 1, 1, 10);
      Rods5.setRotationPoint(-6F, 23F, -5F);
      Rods5.setTextureSize(128, 128);
      Rods5.mirror = true;
      setRotation(Rods5, 0.4537856F, 0F, 0F);
      Rods6 = new ModelRenderer(this, 25, 83);
      Rods6.addBox(0F, 0F, 0F, 1, 1, 11);
      Rods6.setRotationPoint(-6F, 18F, -5F);
      Rods6.setTextureSize(128, 128);
      Rods6.mirror = true;
      setRotation(Rods6, -0.4363323F, 0F, 0F);
      Rods7 = new ModelRenderer(this, 23, 63);
      Rods7.addBox(0F, 0F, 0F, 10, 1, 1);
      Rods7.setRotationPoint(-5F, 23F, 5F);
      Rods7.setTextureSize(128, 128);
      Rods7.mirror = true;
      setRotation(Rods7, 0F, 0F, -0.4537856F);
      Rods8 = new ModelRenderer(this, 25, 59);
      Rods8.addBox(0F, 0F, 0F, 11, 1, 1);
      Rods8.setRotationPoint(-5F, 18F, 5F);
      Rods8.setTextureSize(128, 128);
      Rods8.mirror = true;
      setRotation(Rods8, 0F, 0F, 0.4537856F);
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Bottom.render(f5);
    Rod1.render(f5);
    Rod2.render(f5);
    Rod3.render(f5);
    Rod4.render(f5);
    Ceiling1.render(f5);
    Ceiling2.render(f5);
    Ceiling3.render(f5);
    MiniCube1.render(f5);
    MiniCube2.render(f5);
    MiniCube3.render(f5);
    MiniCube4.render(f5);
    Crafting.render(f5);
    Rods1.render(f5);
    Rods2.render(f5);
    Rods3.render(f5);
    Rods4.render(f5);
    Rods5.render(f5);
    Rods6.render(f5);
    Rods7.render(f5);
    Rods8.render(f5);
  }

  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }

  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityPlayer player)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, player);
  }

  public void renderModel(float f5){
	  Bottom.render(f5);
	    Rod1.render(f5);
	    Rod2.render(f5);
	    Rod3.render(f5);
	    Rod4.render(f5);
	    Ceiling1.render(f5);
	    Ceiling2.render(f5);
	    Ceiling3.render(f5);
	    MiniCube1.render(f5);
	    MiniCube2.render(f5);
	    MiniCube3.render(f5);
	    MiniCube4.render(f5);
	    Crafting.render(f5);
	    Rods1.render(f5);
	    Rods2.render(f5);
	    Rods3.render(f5);
	    Rods4.render(f5);
	    Rods5.render(f5);
	    Rods6.render(f5);
	    Rods7.render(f5);
	    Rods8.render(f5);
  }
}
