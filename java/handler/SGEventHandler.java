package handler;

import cofh.api.energy.IEnergyContainerItem;
import common.Contents;
import common.item.armor.CustomArmorHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import scala.tools.nsc.doc.base.Tooltip;
import util.ItemNBTHelper;

public class SGEventHandler {

	private EntityPlayer player;
	private Tooltip toolttip;

	@SubscribeEvent
	public void playerTickEvent(PlayerTickEvent e){
		CustomArmorHandler.onPlayerTick(e);
	}

	@SubscribeEvent
	public void onRenderGameOverrlay(RenderGameOverlayEvent e){
		if(!e.isCancelable() && e.type == ElementType.EXPERIENCE){
			Minecraft mc = Minecraft.getMinecraft();

			ItemStack hand = mc.thePlayer.inventory.getCurrentItem();

			World world = mc.theWorld;

			if(hand != null){
				if(hand.getItem() != null){
					if(hand.getItem() instanceof IEnergyContainerItem){
						mc.fontRenderer.drawString(
								StatCollector.translateToLocal(EnumChatFormatting.AQUA + hand.getDisplayName() + EnumChatFormatting.GREEN +
										" Battery: " + EnumChatFormatting.LIGHT_PURPLE + ItemNBTHelper.getCompound(hand).getInteger("Energy")), 7, 46, 0x000000);
					}
				}
			}

			if(hand != null){
				if(hand.getItem() != null){
					if(hand.getItem() == Contents.sword_of_demon){
						world.spawnParticle("reddust", mc.thePlayer.posX + (world.rand.nextDouble() - 0.5D) * (double)mc.thePlayer.width, mc.thePlayer.posY + world.rand.nextDouble() * (double)mc.thePlayer.height - (double)mc.thePlayer.yOffset, mc.thePlayer.posZ + (world.rand.nextDouble() - 0.5D) * (double)mc.thePlayer.width, 0.0D, 0.0D, 0.0D);
					}
				}
			}

			ItemStack helm = mc.thePlayer.inventory.armorInventory[3];
			ItemStack chest = mc.thePlayer.inventory.armorInventory[2];
			ItemStack leg = mc.thePlayer.inventory.armorInventory[1];
			ItemStack boots = mc.thePlayer.inventory.armorInventory[0];

			if(helm != null){
				if(helm.getItem() != null){
					if(helm.getItem() == Contents.demon_Helmet){
						mc.fontRenderer.drawString(
								StatCollector.translateToLocal(EnumChatFormatting.GREEN +
										"Helmet Battery: " + EnumChatFormatting.LIGHT_PURPLE + ItemNBTHelper.getCompound(helm).getInteger("Energy")), 7, 6, 0x000000);
					}
				}
			}

			if(chest != null){
				if(chest.getItem() != null){
					if(chest.getItem() == Contents.demon_ChestPlate){
						mc.fontRenderer.drawString(
								StatCollector.translateToLocal(EnumChatFormatting.GREEN +
										"ChestPlate Battery: " + EnumChatFormatting.LIGHT_PURPLE + ItemNBTHelper.getCompound(chest).getInteger("Energy")), 7, 16, 0x000000);
					}
				}
			}

			if(leg != null){
				if(leg.getItem() != null){
					if(leg.getItem() == Contents.demon_Leggins){
						mc.fontRenderer.drawString(
								StatCollector.translateToLocal(EnumChatFormatting.GREEN +
										"Leggings Battery: " + EnumChatFormatting.LIGHT_PURPLE + ItemNBTHelper.getCompound(leg).getInteger("Energy")), 7, 26, 0x000000);
					}
				}
			}

			if(boots != null){
				if(boots.getItem() != null){
					if(boots.getItem() == Contents.demon_Boots){
						mc.fontRenderer.drawString(
								StatCollector.translateToLocal(EnumChatFormatting.GREEN +
										"Boots Battery: " + EnumChatFormatting.LIGHT_PURPLE + ItemNBTHelper.getCompound(boots).getInteger("Energy")), 7, 36, 0x000000);
					}
				}
			}

			/*//If Armor Fullset's Tooltip Display
			if(helm != null && chest != null && leg != null && boots != null){
				if(helm.getItem() != null && chest.getItem() != null && leg.getItem() != null && boots.getItem() != null){
					if(helm.getItem() == Contents.demon_Helmet && chest.getItem() == Contents.demon_ChestPlate && leg.getItem() == Contents.demon_Leggins && boots.getItem() == Contents.demon_Boots){
						GuiDraw.drawTooltipBox(3, 2, 50, 40);
					}
				}
			}*/

			/*if(helm != null && chest != null && leg != null && boots != null){
				if(helm.getItem() != null && chest.getItem() != null && leg.getItem() != null && boots.getItem() != null){
					if(helm.getItem() == Contents.demon_Helmet && chest.getItem() == Contents.demon_ChestPlate && leg.getItem() == Contents.demon_Leggins && boots.getItem() == Contents.demon_Boots){
					}
				}
			}*/
		}
	}
}
