package common.item;

import java.util.List;

import common.block.BlockSGBase;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockSGBase extends ItemBlock
{
	public ItemBlockSGBase(Block b)
	{
		super(b);
		if(((BlockSGBase)b).subNames.length>1)
			setHasSubtypes(true);
	}

	@Override
	public int getMetadata (int damageValue)
	{
		return damageValue;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List itemList)
	{
		this.field_150939_a.getSubBlocks(item, tab, itemList);
	}
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		if(((BlockSGBase)field_150939_a).subNames!=null && ((BlockSGBase)field_150939_a).subNames.length>0)
			return getUnlocalizedName()+"."+((BlockSGBase)field_150939_a).subNames[ Math.min(((BlockSGBase)field_150939_a).subNames.length-1, itemstack.getItemDamage())];
		return super.getUnlocalizedName(itemstack);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advInfo)
	{
		if(((BlockSGBase)field_150939_a).hasFlavour)
			list.add(StatCollector.translateToLocal(((BlockSGBase)field_150939_a).name+"."+((BlockSGBase)field_150939_a).subNames[ Math.min(((BlockSGBase)field_150939_a).subNames.length-1, stack.getItemDamage())]));
	}
}