package client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class ModelUniversalGenerator extends ModelBase{

	public static final ModelUniversalGenerator instance = new ModelUniversalGenerator();

  //fields
    ModelRenderer Bottom1;
    ModelRenderer Bottom2;
    ModelRenderer Rod1;
    ModelRenderer Rod2;
    ModelRenderer Rod3;
    ModelRenderer Rod4;
    ModelRenderer CenterRod1;
    ModelRenderer CenterRod2;
    ModelRenderer CenterRod3;
    ModelRenderer CenterRod4;
    ModelRenderer CenterCeiling;
    ModelRenderer CenterPillar;
    ModelRenderer Ceiling1;
    ModelRenderer Ceiling2;
    ModelRenderer FrontPanel;
    ModelRenderer Rods1;
    ModelRenderer Rods2;
    ModelRenderer Rods3;
    ModelRenderer Rods4;
    ModelRenderer Rods5;
    ModelRenderer Rods6;
    ModelRenderer Rods7;
    ModelRenderer Rods8;

  public ModelUniversalGenerator()
  {
    textureWidth = 128;
    textureHeight = 128;

      Bottom1 = new ModelRenderer(this, 0, 0);
      Bottom1.addBox(0F, 0F, 0F, 16, 1, 16);
      Bottom1.setRotationPoint(-8F, 23F, -8F);
      Bottom1.setTextureSize(128, 128);
      Bottom1.mirror = true;
      setRotation(Bottom1, 0F, 0F, 0F);
      Bottom2 = new ModelRenderer(this, 0, 19);
      Bottom2.addBox(0F, 0F, 0F, 14, 1, 14);
      Bottom2.setRotationPoint(-7F, 22F, -7F);
      Bottom2.setTextureSize(128, 128);
      Bottom2.mirror = true;
      setRotation(Bottom2, 0F, 0F, 0F);
      Rod1 = new ModelRenderer(this, 0, 36);
      Rod1.addBox(0F, 0F, 0F, 2, 14, 2);
      Rod1.setRotationPoint(-8F, 9F, -8F);
      Rod1.setTextureSize(128, 128);
      Rod1.mirror = true;
      setRotation(Rod1, 0F, 0F, 0F);
      Rod2 = new ModelRenderer(this, 9, 36);
      Rod2.addBox(0F, 0F, 0F, 2, 14, 2);
      Rod2.setRotationPoint(6F, 9F, -8F);
      Rod2.setTextureSize(128, 128);
      Rod2.mirror = true;
      setRotation(Rod2, 0F, 0F, 0F);
      Rod3 = new ModelRenderer(this, 18, 36);
      Rod3.addBox(0F, 0F, 0F, 2, 14, 2);
      Rod3.setRotationPoint(-8F, 9F, 6F);
      Rod3.setTextureSize(128, 128);
      Rod3.mirror = true;
      setRotation(Rod3, 0F, 0F, 0F);
      Rod4 = new ModelRenderer(this, 27, 36);
      Rod4.addBox(0F, 0F, 0F, 2, 14, 2);
      Rod4.setRotationPoint(6F, 9F, 6F);
      Rod4.setTextureSize(128, 128);
      Rod4.mirror = true;
      setRotation(Rod4, 0F, 0F, 0F);
      CenterRod1 = new ModelRenderer(this, 0, 54);
      CenterRod1.addBox(0F, 0F, 0F, 1, 8, 1);
      CenterRod1.setRotationPoint(-5F, 14F, -5F);
      CenterRod1.setTextureSize(128, 128);
      CenterRod1.mirror = true;
      setRotation(CenterRod1, 0F, 0F, 0F);
      CenterRod2 = new ModelRenderer(this, 5, 54);
      CenterRod2.addBox(0F, 0F, 0F, 1, 8, 1);
      CenterRod2.setRotationPoint(4F, 14F, -5F);
      CenterRod2.setTextureSize(128, 128);
      CenterRod2.mirror = true;
      setRotation(CenterRod2, 0F, 0F, 0F);
      CenterRod3 = new ModelRenderer(this, 10, 54);
      CenterRod3.addBox(0F, 0F, 0F, 1, 8, 1);
      CenterRod3.setRotationPoint(-5F, 14F, 4F);
      CenterRod3.setTextureSize(128, 128);
      CenterRod3.mirror = true;
      setRotation(CenterRod3, 0F, 0F, 0F);
      CenterRod4 = new ModelRenderer(this, 15, 54);
      CenterRod4.addBox(0F, 0F, 0F, 1, 8, 1);
      CenterRod4.setRotationPoint(4F, 14F, 4F);
      CenterRod4.setTextureSize(128, 128);
      CenterRod4.mirror = true;
      setRotation(CenterRod4, 0F, 0F, 0F);
      CenterCeiling = new ModelRenderer(this, 58, 20);
      CenterCeiling.addBox(0F, 0F, 0F, 12, 1, 12);
      CenterCeiling.setRotationPoint(-6F, 13F, -6F);
      CenterCeiling.setTextureSize(128, 128);
      CenterCeiling.mirror = true;
      setRotation(CenterCeiling, 0F, 0F, 0F);
      CenterPillar = new ModelRenderer(this, 0, 65);
      CenterPillar.addBox(0F, 0F, 0F, 6, 2, 6);
      CenterPillar.setRotationPoint(-3F, 11F, -3F);
      CenterPillar.setTextureSize(128, 128);
      CenterPillar.mirror = true;
      setRotation(CenterPillar, 0F, 0F, 0F);
      Ceiling1 = new ModelRenderer(this, 57, 35);
      Ceiling1.addBox(0F, 0F, 0F, 14, 1, 14);
      Ceiling1.setRotationPoint(-7F, 10F, -7F);
      Ceiling1.setTextureSize(128, 128);
      Ceiling1.mirror = true;
      setRotation(Ceiling1, 0F, 0F, 0F);
      Ceiling2 = new ModelRenderer(this, 0, 0);
      Ceiling2.addBox(0F, 0F, 0F, 16, 1, 16);
      Ceiling2.setRotationPoint(-8F, 8F, -8F);
      Ceiling2.setTextureSize(128, 128);
      Ceiling2.mirror = true;
      setRotation(Ceiling2, 0F, 0F, 0F);
      FrontPanel = new ModelRenderer(this, 20, 54);
      FrontPanel.addBox(0F, 0F, 0F, 8, 5, 1);
      FrontPanel.setRotationPoint(-4F, 12F, -8F);
      FrontPanel.setTextureSize(128, 128);
      FrontPanel.mirror = true;
      setRotation(FrontPanel, 0F, 0F, 0F);
      Rods1 = new ModelRenderer(this, 0, 75);
      Rods1.addBox(0F, 0F, 0F, 18, 1, 1);
      Rods1.setRotationPoint(-6F, 9F, -7F);
      Rods1.setTextureSize(128, 128);
      Rods1.mirror = true;
      setRotation(Rods1, 0F, 0F, 0.8028515F);
      Rods2 = new ModelRenderer(this, 0, 78);
      Rods2.addBox(0F, 0F, 0F, 19, 1, 1);
      Rods2.setRotationPoint(-7F, 22F, -7F);
      Rods2.setTextureSize(128, 128);
      Rods2.mirror = true;
      setRotation(Rods2, 0F, 0F, -0.8203047F);
      Rods3 = new ModelRenderer(this, 0, 81);
      Rods3.addBox(0F, 0F, 0F, 1, 1, 19);
      Rods3.setRotationPoint(6F, 22F, -7F);
      Rods3.setTextureSize(128, 128);
      Rods3.mirror = true;
      setRotation(Rods3, 0.8028515F, 0F, 0F);
      Rods4 = new ModelRenderer(this, 41, 82);
      Rods4.addBox(0F, 0F, 0F, 1, 1, 18);
      Rods4.setRotationPoint(6F, 9F, -6F);
      Rods4.setTextureSize(128, 128);
      Rods4.mirror = true;
      setRotation(Rods4, -0.8028515F, 0F, 0F);
      Rods5 = new ModelRenderer(this, 0, 103);
      Rods5.addBox(0F, 0F, 0F, 1, 1, 19);
      Rods5.setRotationPoint(-7F, 22F, -7F);
      Rods5.setTextureSize(128, 128);
      Rods5.mirror = true;
      setRotation(Rods5, 0.8028515F, 0F, 0F);
      Rods6 = new ModelRenderer(this, 41, 103);
      Rods6.addBox(0F, 0F, 0F, 1, 1, 20);
      Rods6.setRotationPoint(-7F, 8F, -7F);
      Rods6.setTextureSize(128, 128);
      Rods6.mirror = true;
      setRotation(Rods6, -0.8028515F, 0F, 0F);
      Rods7 = new ModelRenderer(this, 41, 78);
      Rods7.addBox(0F, 0F, 0F, 19, 1, 1);
      Rods7.setRotationPoint(-7F, 22F, 6F);
      Rods7.setTextureSize(128, 128);
      Rods7.mirror = true;
      setRotation(Rods7, 0F, 0F, -0.8028515F);
      Rods8 = new ModelRenderer(this, 39, 75);
      Rods8.addBox(0F, 0F, 0F, 18, 1, 1);
      Rods8.setRotationPoint(-6F, 9F, 6F);
      Rods8.setTextureSize(128, 128);
      Rods8.mirror = true;
      setRotation(Rods8, 0F, 0F, 0.8028515F);
  }

  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    Bottom1.render(f5);
    Bottom2.render(f5);
    Rod1.render(f5);
    Rod2.render(f5);
    Rod3.render(f5);
    Rod4.render(f5);
    CenterRod1.render(f5);
    CenterRod2.render(f5);
    CenterRod3.render(f5);
    CenterRod4.render(f5);
    CenterCeiling.render(f5);
    CenterPillar.render(f5);
    Ceiling1.render(f5);
    Ceiling2.render(f5);
    FrontPanel.render(f5);
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

  public void renderModel(float f5){
	  Bottom1.render(f5);
	  Bottom2.render(f5);
	  Rod1.render(f5);
	  Rod2.render(f5);
	  Rod3.render(f5);
	  Rod4.render(f5);
	  CenterRod1.render(f5);
	  CenterRod2.render(f5);
	  CenterRod3.render(f5);
	  CenterRod4.render(f5);
	  CenterCeiling.render(f5);
	  CenterPillar.render(f5);
	  Ceiling1.render(f5);
	  Ceiling2.render(f5);
	  FrontPanel.render(f5);
	  Rods1.render(f5);
	  Rods2.render(f5);
	  Rods3.render(f5);
	  Rods4.render(f5);
	  Rods5.render(f5);
	  Rods6.render(f5);
	  Rods7.render(f5);
	  Rods8.render(f5);
  }

  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, EntityPlayer player)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, player);
  }

}