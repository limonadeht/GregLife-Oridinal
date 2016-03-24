package client.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public class ContainerUniversalGeneratorRecipe extends Container{

	public ContainerUniversalGeneratorRecipe(InventoryPlayer invPlayer){

	}

	public void addCraftingToCrafters(ICrafting iCrafting){

	}

	@Override
	public boolean canInteractWith(EntityPlayer player){
		return false;
	}
}
