package common.item;

import java.util.List;

import common.SimplyGenerators;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemGCore extends Item{

	public boolean isActivate = false;

	public ItemGCore(){
		this.setUnlocalizedName("sg.Gcore");
		this.setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);
		this.setFull3D();
		this.setTextureName("simplyGenerator:Gcore");
	}

	@Override
	public boolean hasEffect(ItemStack itemStack){
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool){
		list.add(EnumChatFormatting.GOLD + "I am G CORE!");

		if(itemStack.stackTagCompound != null){
			String owner = itemStack.stackTagCompound.getString("owner");
			String modes = "";
			boolean mode = itemStack.stackTagCompound.getBoolean("mode");

			if(mode = true){
				modes = "Activated";
			}else{
				modes = "Offline";
			}

			list.add(EnumChatFormatting.DARK_GRAY + "OWNER: " + owner);
			list.add("Mode: " + EnumChatFormatting.GREEN + modes);
		}
	}

	public void onUpdate(ItemStack itemStack, World par2World, Entity par3Entity, int par4, boolean par5){
		/*if(!itemStack.isItemEnchantable()){
			itemStack.addEnchantment(Enchantment.sharpness, 30000);
			itemStack.addEnchantment(Enchantment.unbreaking, 30000);
		}*/

	}

	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
	    itemStack.stackTagCompound = new NBTTagCompound();
	    itemStack.stackTagCompound.setString("owner", player.getDisplayName());
	    itemStack.stackTagCompound.setBoolean("mode", this.isActivate);
	}

	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ){
		return true;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float p, float q, float r){
		super.onItemUse(itemstack, entityplayer, world, x, y, z, side, p, q, r);

		if(itemstack.stackTagCompound != null){
			itemstack.stackTagCompound.setString("owner", entityplayer.getDisplayName());
			return true;
		}

		return true;
	}
}
