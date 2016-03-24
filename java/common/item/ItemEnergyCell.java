package common.item;

import java.util.List;

import common.block.BlockEnergyCell;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ItemEnergyCell extends ItemBlock{

	public ItemEnergyCell(Block block){
		super(block);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool){
		BlockEnergyCell tile = (BlockEnergyCell)this.field_150939_a;

		list.add("I am Energy Storage");
		list.add("Max Storage: " + tile.getEnergyCapacity() + " Rf");
		list.add("Max Transfer: " + tile.getEnergyTransfer() + " Rf/t");

		if(itemStack.stackTagCompound != null){
			list.add("Current Storage: " + EnumChatFormatting.AQUA +
					tile.getEnergyStored(itemStack) + " / " +tile.getEnergyCapacity() + " Rf");
		}
	}
}
