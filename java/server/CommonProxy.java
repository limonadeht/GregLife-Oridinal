package server;

import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy {

	public boolean isShiftKeyDown() {
		return false;
	}

	public boolean isSpaceKeyDown() {
		return false;
	}

	public void registerRenderers(){

	}

	public void registerTileEntitys(){

	}

	public void LoadNEI() {}

	public EntityPlayer getEntityPlayerInstance() {return null;}
}
