package client.gui.container;

import client.tileentity.TileOreCreator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerOreCreator extends Container{

	private TileOreCreator tile;

	public ContainerOreCreator(InventoryPlayer player, TileOreCreator tileentity){
		this.tile = tileentity;

		this.addSlotToContainer(new Slot(tileentity, 0, 80, 41));

		for (int i = 0; i < 3; i++) {
			for(int k = 0; k < 9; k++) {
				this.addSlotToContainer(new Slot(player, k + i * 9 + 9, 8 + k * 18, 96 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 154));
		}
	}

	public void addCraftingToCrafters(ICrafting iCrafting){
		super.addCraftingToCrafters(iCrafting);
		iCrafting.sendProgressBarUpdate(this, 1, this.tile.getEnergyStored());
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
