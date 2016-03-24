package common.item.armor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

import common.SimplyGenerators;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import util.ItemNBTHelper;

public class CustomArmorHandler {

	public static final UUID WALK_SPEED_UUID = UUID.fromString("0ea6ce8e-d2e8-11e5-ab30-625662870761");
	public static Map<EntityPlayer, Boolean> playersWithFlight = new WeakHashMap<EntityPlayer, Boolean>();
	public static List<String> playersWithUphillStep = new ArrayList<String>();

	private static void setPlayerFlySpeed(EntityPlayer player, float speed) {
		player.capabilities.setFlySpeed(speed);
	}

	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

		EntityPlayer player = event.player;
		ArmorSummery summery = new ArmorSummery().getSummery(player);

		tickArmorEffects(summery, player);
	}

	@SuppressWarnings("static-access")
	public static void tickArmorEffects(ArmorSummery summery, EntityPlayer player){
	//region/*----------------- Flight ------------------*/

	if (summery != null && summery.flight[0]) {
		playersWithFlight.put(player, true);
		player.capabilities.allowFlying = true;
		//if (summery.flight[1]) player.capabilities.isFlying = true;
		if (player.worldObj.isRemote) setPlayerFlySpeed(player, 0.05F + (0.05F * summery.flightSpeedModifier));
		if ((!player.onGround && player.capabilities.isFlying) && player.motionY != 0 && summery.flightVModifier > 0){
			if (SimplyGenerators.Instance.commonProxy.isSpaceKeyDown() && !SimplyGenerators.Instance.commonProxy.isShiftKeyDown()){
				//LogHelper.info(player.motionY);
				player.motionY = 0.225F * summery.flightVModifier;
			}
			if (SimplyGenerators.Instance.commonProxy.isShiftKeyDown() && !SimplyGenerators.Instance.commonProxy.isSpaceKeyDown()){
				player.motionY = -0.225F * summery.flightVModifier;
			}
		}

		/*if (summery.flight[2] && player.moveForward == 0 && player.moveStrafing == 0 && player.capabilities.isFlying){
			player.motionX *= 0.5;
			player.motionZ *= 0.5;
		}*/

	} else {

		if (!playersWithFlight.containsKey(player)) {
			playersWithFlight.put(player, false);
		}

		if (playersWithFlight.get(player) && !player.worldObj.isRemote) {
			playersWithFlight.put(player, false);

			if (!player.capabilities.isCreativeMode) {
				player.capabilities.allowFlying = false;
				player.capabilities.isFlying = false;
				player.sendPlayerAbilities();
			}

			if (player.worldObj.isRemote)setPlayerFlySpeed(player, 0.05F);
		}
	}
	//endregion

	//region/*---------------- Swiftness ----------------*/

	IAttribute speedAttr = SharedMonsterAttributes.movementSpeed;
	if (summery != null && summery.speedModifier > 0)
	{
		double value = summery.speedModifier;
		if (player.getEntityAttribute(speedAttr).getModifier(WALK_SPEED_UUID) == null) {
			player.getEntityAttribute(speedAttr).applyModifier(new AttributeModifier(WALK_SPEED_UUID, speedAttr.getAttributeUnlocalizedName(), value, 1));
		}
		else if (player.getEntityAttribute(speedAttr).getModifier(WALK_SPEED_UUID).getAmount() != value){
			player.getEntityAttribute(speedAttr).removeModifier(player.getEntityAttribute(speedAttr).getModifier(WALK_SPEED_UUID));
			player.getEntityAttribute(speedAttr).applyModifier(new AttributeModifier(WALK_SPEED_UUID, speedAttr.getAttributeUnlocalizedName(), value, 1));
		}

		if (!player.onGround && player.ridingEntity == null) player.jumpMovementFactor = 0.02F + (0.02F * summery.speedModifier);
	}
	else if (player.getEntityAttribute(speedAttr).getModifier(WALK_SPEED_UUID) != null)
	{
		player.getEntityAttribute(speedAttr).removeModifier(player.getEntityAttribute(speedAttr).getModifier(WALK_SPEED_UUID));
	}

	//endregion

	//region/*---------------- HillStep -----------------*/
	if (summery != null && player.worldObj.isRemote) {
		boolean highStepListed = playersWithUphillStep.contains(player.getDisplayName()) && player.stepHeight >= 1f;
		boolean hasHighStep = summery.hasHillStep;

		if (hasHighStep && !highStepListed) {
			playersWithUphillStep.add(player.getDisplayName());
			player.stepHeight = 1f;
		}

		if (!hasHighStep && highStepListed) {
			playersWithUphillStep.remove(player.getDisplayName());
			player.stepHeight = 0.5F;
		}
	}


}

public static class ArmorSummery {
	/*---- Shield ----*/
	/**Max protection points from all equipped armor peaces*/
	public float maxProtectionPoints = 0F;
	/**Total protection points from all equipped armor peaces*/
	public float protectionPoints = 0F;
	/**Number of quipped armor peaces*/
	public int peaces = 0;
	/**Point  Allocation, The number of points on each peace*/
	public float[] allocation;
	/**How many points have been drained from each armor peace*/
	public float[] pointsDown;
	/**The armor peaces (Index will contain null if peace is not present)*/
	public ItemStack[] armorStacks;
	/**Mean Fatigue*/
	public float entropy = 0F;
	/**Mean Recovery Points*/
	public int meanRecoveryPoints = 0;
	/**Total RF stored in the armor*/
	public int totalEnergyStored = 0;
	/**Total Max RF storage for the armor*/
	public int maxTotalEnergyStorage = 0;
	/**RF stored in each armor peace*/
	public int[] energyAllocation;
	/*---- Effects ----*/
	public boolean[] flight = new boolean[] {false, false, false};
	public float flightVModifier = 0F;
	public float speedModifier = 0F;
	public float jumpModifier = 0F;
	public float fireResistance = 0F;
	public float flightSpeedModifier = 0;
	public boolean hasHillStep = false;
	public boolean hasArmorDemon = false;

	public ArmorSummery getSummery(EntityPlayer player){
		ItemStack[] armorSlots = player.inventory.armorInventory;
		float totalEntropy = 0;
		int totalRecoveryPoints = 0;

		allocation = new float[armorSlots.length];
		armorStacks = new ItemStack[armorSlots.length];
		pointsDown = new float[armorSlots.length];
		energyAllocation = new int[armorSlots.length];

		for (int i = 0; i < armorSlots.length; i++){
			ItemStack stack = armorSlots[i];
			if (stack == null || !(stack.getItem() instanceof ICustomArmor)) continue;
			ICustomArmor armor = (ICustomArmor)stack.getItem();
			peaces++;
			allocation[i] = ItemNBTHelper.getFloat(stack, "ProtectionPoints", 0);
			protectionPoints += allocation[i];
			totalEntropy += ItemNBTHelper.getFloat(stack, "ShieldEntropy", 0);
			armorStacks[i] = stack;
			//totalRecoveryPoints += IUpgradableItem.EnumUpgrade.SHIELD_RECOVERY.getUpgradePoints(stack);
			float maxPoints = armor.getProtectionPoints(stack);
			pointsDown[i] = maxPoints - allocation[i];
			maxProtectionPoints += maxPoints;
			energyAllocation[i] = armor.getEnergyStored(stack);
			totalEnergyStored += energyAllocation[i];
			maxTotalEnergyStorage += armor.getMaxEnergyStored(stack);
			if (stack.getItem() instanceof ArmorOfDemon) hasArmorDemon = true;

			fireResistance += armor.getFireResistance(stack);

			switch (i){
				case 2:
					flight = armor.hasFlight(stack);
					if (flight[0]) {
						flightVModifier = armor.getFlightVModifier(stack, player);
						flightSpeedModifier = armor.getFlightSpeedModifier(stack, player);
					}
					break;
				case 1:
					speedModifier = armor.getSpeedModifier(stack, player);
					break;
				case 0:
					hasHillStep = armor.hasHillStep(stack, player);
					jumpModifier = armor.getJumpModifier(stack, player);
					break;
			}
		}

		if (peaces == 0) return null;

		entropy = totalEntropy / peaces;
		meanRecoveryPoints = totalRecoveryPoints / peaces;

		return this;
	}
}
}
