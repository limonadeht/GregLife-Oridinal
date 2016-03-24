package common.item.tool;

import java.util.List;

import cofh.api.energy.IEnergyContainerItem;
import common.Contents;
import common.SimplyGenerators;
import common.entity.EntityRailGun;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import util.ItemNBTHelper;

public class ItemSoulariumCannon extends Item implements IEnergyContainerItem{

	private double damage = (double)ItemNBTHelper.getCompound(new ItemStack(Contents.soulariumCannon)).getInteger("DAMAGE");
	private double defaultDamage = 50.0F;

	public boolean MODE_RAIL_GUN = true;
	public boolean MODE_TELEPORT = false;

	public int maxEnergy = 10000000;
	private int maxTransfer = maxEnergy / 5;
	private int energyPerDamage = 800;

	public ItemSoulariumCannon(String name){
		this.maxStackSize = 1;
		this.setUnlocalizedName(name);
		this.setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);
		this.setNoRepair();
		this.setTextureName("simplygenerators:ender_cannon");
		this.bFull3D = true;
	}

	public boolean isItemTool(ItemStack par1ItemStack)
    {
       return true;
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list){
		list.add(ItemNBTHelper.setInteger(new ItemStack(item), "Energy", 0));
		list.add(ItemNBTHelper.setInteger(new ItemStack(item), "Energy", maxEnergy));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4){
		list.add(EnumChatFormatting.LIGHT_PURPLE + "EnergyStored: " + this.getEnergyStored(stack) + " / " + this.maxEnergy);
		list.add(EnumChatFormatting.GOLD + "RailGun Mode: " + ItemNBTHelper.getCompound(stack).getBoolean("MODE_RAIL_GUN"));
		list.add(EnumChatFormatting.GOLD + "Teleport Mode: " + ItemNBTHelper.getCompound(stack).getBoolean("MODE_TELEPORT"));
		list.add(EnumChatFormatting.GRAY + "-----------------");
		list.add(EnumChatFormatting.GOLD + "Damage: " + this.damage);

		if(SimplyGenerators.commonProxy.isShiftKeyDown()){
			list.add(" ");
			list.add("NBT Tags: " + ItemNBTHelper.getCompound(stack).getBoolean("MODE_RAIL_GUN") + " / " + ItemNBTHelper.getCompound(stack).getBoolean("MODE_TELEPORT"));
		}else{
			list.add("Press" + EnumChatFormatting.AQUA + EnumChatFormatting.ITALIC + " LSHIFT " + EnumChatFormatting.GRAY + "for Info");
		}
	}

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
    	//if(this.MODE_RAIL_GUN == true){
    	this.setTeleportMode(true);
    	if(!world.isRemote){
    		player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GOLD + "RailGun: " + this.getRailGunMode()));
    	}
    	//}

    	if(!world.isRemote){
    		world.spawnEntityInWorld(new EntityEnderPearl(world, player));
    	}

    	if(player.capabilities.isCreativeMode){
    		return stack;
    	}else{
    		extractEnergy(stack, energyPerDamage, false);
            world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            if(!world.isRemote && this.MODE_RAIL_GUN == true){
            	EntityRailGun railGunEntity = new EntityRailGun(world);
            	//railGunEntity.setDamage(damage);
                world.spawnEntityInWorld(new EntityRailGun(world));
            }
            if(!world.isRemote && this.MODE_TELEPORT == true){
            	world.spawnEntityInWorld(new EntityEnderPearl(world, player.posX, player.posY, player.posZ));
            }
    	}
		return stack;


    }

	public void onCreated(ItemStack stack, World world, EntityPlayer player){
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.stackTagCompound != null){
			if(stack.stackTagCompound.hasKey("MODE_RAIL_GUN")){
				nbt.getBoolean("MODE_RAIL_GUN");
			}else{
				nbt.setBoolean("MODE_RAIL_GUN", this.MODE_RAIL_GUN);
			}

			if(stack.stackTagCompound.hasKey("MODE_TELEPORT")){
				nbt.getBoolean("MODE_TELEPORT");
			}else{
				nbt.setBoolean("MODE_TELEPORT", this.MODE_TELEPORT);
			}

			if(stack.stackTagCompound.hasKey("DAMAGE")){
				nbt.getInteger("DAMAGE");
			}else{
				nbt.setInteger("DAMAGE", (int)defaultDamage);
			}
		}
	}

	public boolean getRailGunMode(){
		return this.MODE_RAIL_GUN;
	}

	public boolean getTeleportMode(){
		return this.MODE_TELEPORT;
	}

	public ItemSoulariumCannon setRailGunMode(boolean bool){
		this.MODE_RAIL_GUN = bool;
		this.MODE_TELEPORT = !bool;
		return this;
	}

	public ItemSoulariumCannon setTeleportMode(boolean bool){
		this.MODE_TELEPORT = bool;
		this.MODE_RAIL_GUN = !bool;
		return this;
	}

    @Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - (double)ItemNBTHelper.getInteger(stack, "Energy", 0) / (double)getMaxEnergyStored(stack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getEnergyStored(stack) < getMaxEnergyStored(stack);
	}

	/* IEnergyContainerItem */
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		int stored = ItemNBTHelper.getInteger(container, "Energy", 0);
		int receive = Math.min(maxReceive, Math.min(getMaxEnergyStored(container) - stored, maxTransfer));

		if (!simulate) {
			stored += receive;
			ItemNBTHelper.setInteger(container, "Energy", stored);
		}
		return receive;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

		int stored = ItemNBTHelper.getInteger(container, "Energy", 0);
		int extract = Math.min(maxExtract, stored);

		if (!simulate) {
			stored -= extract;
			ItemNBTHelper.setInteger(container, "Energy", stored);
		}
		return extract;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		return ItemNBTHelper.getInteger(container, "Energy", 0);
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {;
		return this.maxEnergy;
	}
}
