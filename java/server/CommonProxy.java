package server;

import client.tileentity.TileBoiler;
import client.tileentity.TileEnergyCell;
import client.tileentity.TileOreCreator;
import client.tileentity.TileSGCraftTable;
import client.tileentity.TileTankStorage;
import cpw.mods.fml.common.registry.GameRegistry;
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

	public void registerTileEntity(){
		GameRegistry.registerTileEntity(TileTankStorage.class, "sg.tile.storage.1");
		GameRegistry.registerTileEntity(TileTankStorage.Reinforced.class, "sg.tile.storage.2");
		GameRegistry.registerTileEntity(TileEnergyCell.class, "sg.tile.energyCell.t1");
		GameRegistry.registerTileEntity(TileEnergyCell.T2.class, "sg.tile.energyCell.t2");
		GameRegistry.registerTileEntity(TileEnergyCell.T3.class, "sg.tile.energyCell.t3");
		GameRegistry.registerTileEntity(TileEnergyCell.T4.class, "sg.tile.energyCell.t4");
		GameRegistry.registerTileEntity(TileBoiler.class, "sg.tile.boiler");
		GameRegistry.registerTileEntity(TileOreCreator.class, "sg.tile.oreCreator");
		GameRegistry.registerTileEntity(TileSGCraftTable.class, "sg.tile.crafting");
	}

	public void LoadNEI() {}

	public EntityPlayer getEntityPlayerInstance() {return null;}
}
