package common.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemSGCraftTable extends ItemBlock{

	public ItemSGCraftTable(Block block){
		super(block);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool){
		list.add("Advanced Crafting Tables!");
		list.add("This is not a TileEntity.");
	}
}
