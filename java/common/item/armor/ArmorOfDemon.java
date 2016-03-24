package common.item.armor;

import java.util.Collection;
import java.util.List;

import common.Contents;
import common.SimplyGenerators;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import util.ItemNBTHelper;

public class ArmorOfDemon extends ItemArmor implements ISpecialArmor, ICustomArmor{

	@SideOnly(Side.CLIENT)
	private IIcon helmetIcon, chestPlateIcon, legginsIcon, bootsIcon;

	private int maxEnergy = 100000000;
	private int maxTransfer = maxEnergy / 5;
	private int energyPerDamage = 800;

	public ArmorOfDemon(ArmorMaterial material, int armorType, String name){
		super(material, 0, armorType);
		this.setUnlocalizedName(name);
		this.setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);
		GameRegistry.registerItem(this, name);
	}

	@Override
	public boolean isItemTool(ItemStack itemstack){
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list){
		list.add(ItemNBTHelper.setInteger(new ItemStack(item), "Energy", 0));
		list.add(ItemNBTHelper.setInteger(new ItemStack(item), "Energy", maxEnergy));
	}

	@Override
	public String getUnlocalizedName(){
		return String.format("item.%s%s", SimplyGenerators.MOD_ID.toLowerCase() + ":", super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf(".") + 1));
	}

	@Override
	public String getUnlocalizedName(final ItemStack itemstack){
		return getUnlocalizedName();
	}


	public IIcon getIconIndex(ItemStack stack) {
		if (stack.getItem() == Contents.demon_Helmet) return helmetIcon;
		else if (stack.getItem() == Contents.demon_ChestPlate) return chestPlateIcon;
		else if (stack.getItem() == Contents.demon_Leggins) return legginsIcon;
		else return bootsIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type){
        return "simplygenerators:textures/armor/ArmorOfDemon_a.png";
		//return "simplygenerators:textures/models/ArmorOfDemon.png";
	}

	@Override
	public EnumRarity getRarity(ItemStack p_77613_1_) {
		return EnumRarity.epic;
	}

	@Override
	public double getDurabilityForDisplay(ItemStack stack) {
		return 1D - (double)ItemNBTHelper.getInteger(stack, "Energy", 0) / (double)getMaxEnergyStored(stack);
	}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return getEnergyStored(stack) < getMaxEnergyStored(stack);
	}

	protected float getProtectionShare() {
		switch (armorType) {
			case 0:
				return 0.15F;
			case 1:
				return 0.40F;
			case 2:
				return 0.30F;
			case 3:
				return 0.15F;
		}
		return 0;
	}

	//region ISpecialArmor
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if (source.isUnblockable() || source.isDamageAbsolute() || source.isMagicDamage()) return new ArmorProperties(0, damageReduceAmount / 100D, 15);
		return new ArmorProperties(0, damageReduceAmount / 24.5D, 1000);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return (int)(getProtectionShare() * 20D);
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot){
		extractEnergy(stack, damage * energyPerDamage, false);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (stack == null) return;

		if (stack.getItem() == Contents.demon_Helmet){
			if (world.isRemote) return;
			if (this.getEnergyStored(stack) >= 5000 && clearNegativeEffects(player)) this.extractEnergy(stack, 5000, false);
			if (player.worldObj.getBlockLightValue((int)Math.floor(player.posX), (int) player.posY + 1, (int)Math.floor(player.posZ)) < 5)
			{
				player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 419, 0, true));
			}
			else if (player.isPotionActive(Potion.nightVision.id)) player.removePotionEffect(Potion.nightVision.id);
		}

		if (stack.getItem() == Contents.demon_Leggins && player.isSprinting() && !player.capabilities.isCreativeMode){
			this.extractEnergy(stack, player.capabilities.isFlying ? 160 : 80, false);
		}

		if(player.getHealth() < player.getMaxHealth()){
			player.heal(player.getMaxHealth());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4){
		list.add(EnumChatFormatting.LIGHT_PURPLE + "EnergyStored: " + this.getEnergyStored(stack) + " / " + this.maxEnergy);
		list.add(EnumChatFormatting.GOLD + "Flying Mode: " + EnumChatFormatting.GREEN + player.capabilities.allowFlying);
		list.add(EnumChatFormatting.GRAY + "-----------------");
		list.add(EnumChatFormatting.GOLD + "Falling Damage: " + EnumChatFormatting.GREEN + player.fallDistance);
		list.add(EnumChatFormatting.GOLD + "Speed Modifier: " + EnumChatFormatting.GREEN + this.getSpeedModifier(stack, player));
		list.add(EnumChatFormatting.GOLD + "Jump Modifier: " + EnumChatFormatting.GREEN + this.getJumpModifier(stack, player));
		list.add(EnumChatFormatting.GOLD + "Flight Speed Modifier: " + EnumChatFormatting.GREEN + this.getFlightSpeedModifier(stack, player));
		list.add(EnumChatFormatting.GOLD + "Flight V Modifier: " + EnumChatFormatting.GREEN + this.getFlightVModifier(stack, player));
	}

	@SuppressWarnings("unchecked")
	public boolean clearNegativeEffects(Entity par3Entity) {
		boolean flag = false;
		if (par3Entity.ticksExisted % 20 == 0) {
			if (par3Entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) par3Entity;

				Collection<PotionEffect> potions = player.getActivePotionEffects();

				if (player.isBurning()) {
					player.extinguish();
					flag = true;
				} else for (PotionEffect potion : potions) {
					int id = potion.getPotionID();
					if (ReflectionHelper.getPrivateValue(Potion.class, Potion.potionTypes[id], new String[]{"isBadEffect", "field_76418_K", "J"}) != null) {
						if (potion.getPotionID() == Potion.digSlowdown.id) break;
						if ((player.getHeldItem() == null) || id != 2){
							player.removePotionEffect(id);
							flag = true;
						}
						break;
					}
				}
			}
		}
		return flag;
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
	public float getProtectionPoints(ItemStack stack) {
		return 1000;
	}

	@Override
	public int getRecoveryPoints(ItemStack stack) {
		return 0;
	}

	@Override
	public float getSpeedModifier(ItemStack stack, EntityPlayer player) {
		return 1.0F;
	}

	@Override
	public float getJumpModifier(ItemStack stack, EntityPlayer player) {
		return 1.0F;
	}

	@Override
	public boolean hasHillStep(ItemStack stack, EntityPlayer player) {
		return true;
	}

	@Override
	public float getFireResistance(ItemStack stack) {
		return 1000;
	}

	@Override
	public boolean[] hasFlight(ItemStack stack) {
		return new boolean[]{true};
	}

	@Override
	public float getFlightSpeedModifier(ItemStack stack, EntityPlayer player) {
		return 3.0F;
	}

	@Override
	public float getFlightVModifier(ItemStack stack, EntityPlayer player) {
		return 3.0F;
	}
}
