package client.gui.container;

import client.tileentity.TileBoiler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerBoiler extends Container{

	@SuppressWarnings("unused")
	private TileBoiler boiler;

	public ContainerBoiler(InventoryPlayer player, TileBoiler tile){
		this.boiler = tile;

		for (int i = 0; i < 3; i++) {
			for(int k = 0; k < 9; k++) {
				this.addSlotToContainer(new Slot(player, k + i * 9 + 9, 8 + k * 18, 96 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 154));
		}
	}

	public boolean canInteractWith(EntityPlayer par1){
		return true;
	}
}
