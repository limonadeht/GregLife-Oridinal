package client.gui.container;

import client.tileentity.TileEnergyCell;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerEnergyCell extends Container{

	private TileEnergyCell energyCell;

	public ContainerEnergyCell(InventoryPlayer invPlayer, TileEnergyCell tileentity){
		this.energyCell = tileentity;

		for (int i = 0; i < 3; i++) {
			for(int k = 0; k < 9; k++) {
				this.addSlotToContainer(new Slot(invPlayer, k + i * 9 + 9, 8 + k * 18, 96 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 154));
		}
	}

	public void addCraftingToCrafters(ICrafting iCrafting){
		super.addCraftingToCrafters(iCrafting);
		iCrafting.sendProgressBarUpdate(this, 1, this.energyCell.getEnergyStored());
	}

	public void detectAndSendChanges(){
		super.detectAndSendChanges();
	}

	@SideOnly(Side.CLIENT)
	public void updareProgressBar(int par1, int par2){

	}

	@Override
	public boolean canInteractWith(EntityPlayer player){
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int clickedIndex){
		return null;
	}
}
