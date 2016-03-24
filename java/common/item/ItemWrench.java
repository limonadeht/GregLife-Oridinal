package common.item;

import buildcraft.api.tools.IToolWrench;
import common.SimplyGenerators;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class ItemWrench extends Item implements IToolWrench{

	public ItemWrench(){
		this.setUnlocalizedName("sg.wrench");
		this.setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);
		this.setFull3D();
		this.setTextureName("simplyGenerator:itemMaterial_circuit");
	}

	@Override
	public boolean canWrench(EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3){
		return true;
	}

	@Override
	public void wrenchUsed(EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3){
	}
}
