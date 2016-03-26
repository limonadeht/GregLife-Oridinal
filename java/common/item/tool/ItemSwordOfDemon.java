package common.item.tool;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import cofh.api.energy.IEnergyContainerItem;
import common.SimplyGenerators;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import util.ItemNBTHelper;

public class ItemSwordOfDemon extends ItemSword implements IEnergyContainerItem{

	public int maxEnergy = 1000000000;
	private int maxTransfer = maxEnergy / 5;
	private int energyPerDamage = 800;

	protected String name;

	protected int exp;
	protected int maxExp;
	protected int level;
	protected int maxLevel;

	protected int damage;

	public ItemSwordOfDemon(String name, ToolMaterial material){
		super(material);
		this.name = name;
		this.setUnlocalizedName(name);
		this.setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);
		this.setTextureName("simplygenerators:sword_demon");

		this.setMaxLevel(300);
		this.setMaxExp(50);

		GameRegistry.registerItem(this, name);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4){
		list.add(EnumChatFormatting.LIGHT_PURPLE + "EnergyStored: " + this.getEnergyStored(stack) + " / " + this.maxEnergy);
		list.add(EnumChatFormatting.GRAY + "-----------------");
		list.add(EnumChatFormatting.GOLD + "Current EXP: " + EnumChatFormatting.GREEN + stack.stackTagCompound.getInteger("Exp") +
				EnumChatFormatting.GRAY + " / " + EnumChatFormatting.DARK_RED + stack.stackTagCompound.getInteger("Max Exp"));

		list.add(EnumChatFormatting.GOLD + "Current LEVEL: " + EnumChatFormatting.GREEN + stack.stackTagCompound.getInteger("Level") +
				EnumChatFormatting.GRAY + " / "+ EnumChatFormatting.DARK_RED + stack.stackTagCompound.getInteger("Max Level"));

		if(SimplyGenerators.commonProxy.isShiftKeyDown()){
			list.add("");
			list.add("EntityMob: 5exp");
			list.add("");
			if(stack.stackTagCompound != null){
				list.add("Nbt from: " + stack.stackTagCompound.getInteger("Exp") + " / " + stack.stackTagCompound.getInteger("Level") + " / " + stack.stackTagCompound.getInteger("Max Exp"));
			}
		}else{
			list.add("Press" + EnumChatFormatting.AQUA + EnumChatFormatting.ITALIC + " LSHIFT " + EnumChatFormatting.GRAY + "for Info");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list){
		list.add(ItemNBTHelper.setInteger(new ItemStack(item), "Energy", 0));
		list.add(ItemNBTHelper.setInteger(new ItemStack(item), "Energy", maxEnergy));
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - (double)ItemNBTHelper.getInteger(stack, "Energy", 0) / (double)getMaxEnergyStored(stack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getEnergyStored(stack) < getMaxEnergyStored(stack);
	}

	@Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10){

		/*if(!player.isSneaking()){
			NBTTagCompound nbt = stack.getTagCompound();
			if(nbt != null){
				nbt = new NBTTagCompound();

				int expTotal = stack.getTagCompound().getInteger("Exp") + exp;

				nbt.setInteger("Exp", expTotal);
				nbt.setInteger("Max Exp", this.maxExp);
				nbt.setInteger("Level", this.level);
				nbt.setInteger("Max Level", this.maxLevel);
				stack.setTagCompound(nbt);

				if(!world.isRemote){
					player.addChatComponentMessage(new ChatComponentText("Nbt set."));
				}
			}
		}*/
		return false;
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Multimap getItemAttributeModifiers(){
		 Multimap multimap = HashMultimap.create();

		 Minecraft mc = Minecraft.getMinecraft();

         multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
        		 new AttributeModifier(field_111210_e, "Weapon modifier", ItemNBTHelper.getInteger(new ItemStack(this), "Level", level) * 3, 0));
         return multimap;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5){
		while(exp >= maxExp || exp < 0){
			if(canLevelUp()){
				levelUp();
			}else if(canLevelDown()){
				levelDown();
			}
		}
	}

	public int getExp(){
		return exp;
	}

	public int getMaxExp(){
		return maxExp;
	}

	public int getLevel(){
		return level;
	}

	public int getMaxLevel(){
		return maxLevel;
	}

	public ItemSwordOfDemon setExp(int exp){
		this.exp = exp;
		return this;
	}

	public ItemSwordOfDemon setMaxExp(int maxExp){
		this.maxExp = maxExp;
		return this;
	}

	public ItemSwordOfDemon setLevel(int level){
		this.level = level;
		return this;
	}

	public ItemSwordOfDemon setMaxLevel(int maxLevel){
		this.maxLevel = maxLevel;
		return this;
	}


	public ItemSwordOfDemon addExp(int exp){
		this.exp += exp;
		return this;
	}

	public ItemSwordOfDemon removeExp(int exp){
		this.exp -= exp;
		return this;
	}

	public ItemSwordOfDemon addExp(int exp, int modifier){
		this.exp += (exp * modifier);
		return this;
	}

	public ItemSwordOfDemon removeExp(int exp, int modifier){
		this.exp -= (exp * modifier);
		return this;
	}

	public boolean isLeveledUp(){
		if (level == maxLevel){
			return true;
		}else{
			return false;
		}
	}

	public boolean isMaxed(){
		if (exp == maxExp && isLeveledUp()){
			return true;
		}else{
			return false;
		}
	}

	public boolean canLevelUp(){
		if (exp >= maxExp && !isLeveledUp()){
			return true;
		}else{
			return false;
		}
	}

	public boolean canLevelDown(){
		if (exp < 0){
			if (level != 1){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}

	public boolean canEarnExp(){
		if (exp > 0 || exp <= maxExp){
			return true;
		}else{
			return false;
		}
	}

	public boolean canLoseExp(){
		if (exp > 0){
			return true;
		}else{
			return false;
		}
	}

	public void levelUp(){
		level++;
		exp = (exp - maxExp);
		maxExp = (maxExp + (15 * level));

		Minecraft mc = Minecraft.getMinecraft();
		mc.thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GOLD + "Level Up to: " + this.getLevel()));
		mc.thePlayer.playSound("random.levelup", 1.0F, 1.0F);
	}

	public void levelDown(){
		level--;
		exp = (maxExp - (exp * -1));
		maxExp = (maxExp + (15 * level));
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

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity1, EntityLivingBase entity2){
		if(entity1 instanceof EntityMob){
			NBTTagCompound nbt = stack.getTagCompound();

			extractEnergy(stack, nbt.getInteger("Level") * energyPerDamage, false);

			/*//一度だけ実行
			for(int i = 0; i < 1; i++){
				exp = nbt.getInteger("Exp");
				maxExp = stack.getTagCompound().getInteger("Max Exp");
				level = stack.getTagCompound().getInteger("Level");
				maxLevel = stack.getTagCompound().getInteger("Max Level");
				Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Loaded NBT's"));
				i = 0;
			}*/

			addExp(500000);
			if(nbt != null){
				nbt = new NBTTagCompound();

				nbt.setInteger("Energy", this.getEnergyStored(stack));
				nbt.setInteger("Exp", this.exp);
				nbt.setInteger("Max Exp", this.maxExp);
				nbt.setInteger("Level", this.level);
				nbt.setInteger("Max Level", this.maxLevel);

				stack.setTagCompound(nbt);
			}
		}
		return super.hitEntity(stack, entity1, entity2);
	}
}
