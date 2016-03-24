package client.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerSwordOfDemon extends Container{

	private String repairedItemName;
	private ItemStack itemstack;

    int xCoord, yCoord, zCoord;
    public ContainerSwordOfDemon(int x, int y, int z) {
        this.xCoord = x;
        this.yCoord = y;
        this.zCoord = z;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    public void updateItemName(String name)
    {
        this.repairedItemName = name;

        itemstack.setStackDisplayName(this.repairedItemName);
    }
}
