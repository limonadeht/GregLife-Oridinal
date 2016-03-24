package util;

import client.gui.GuiRecipeUniversalGenerator;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiOpenEvent;

public class Events {

	public EntityPlayer player;

	@SubscribeEvent
	public void onGuiOpened(GuiOpenEvent event){
		if(event.gui instanceof GuiMainMenu){
			event.gui = new GuiRecipeUniversalGenerator();
		}
	}
}
