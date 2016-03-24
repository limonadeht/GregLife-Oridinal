package handler;

import client.gui.GuiBoiler;
import client.gui.GuiEnergyCell;
import client.gui.GuiOreCreator;
import client.gui.GuiSGCraftTable;
import client.gui.GuiSwordOfDemon;
import client.gui.GuiUniversalGenerator;
import client.gui.container.ContainerBoiler;
import client.gui.container.ContainerEnergyCell;
import client.gui.container.ContainerOreCreator;
import client.gui.container.ContainerSGCraftingTable;
import client.gui.container.ContainerSwordOfDemon;
import client.gui.container.ContainerUniversalGenerator;
import client.tileentity.TileBoiler;
import client.tileentity.TileEnergyCell;
import client.tileentity.TileOreCreator;
import client.tileentity.TileUniversalGenerator;
import common.Contents;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);

		if(entity instanceof TileUniversalGenerator){
			return new ContainerUniversalGenerator(player.inventory, (TileUniversalGenerator)entity);
		}
		if(entity instanceof TileBoiler){
			return new ContainerBoiler(player.inventory, (TileBoiler)entity);
		}
		if(entity instanceof TileEnergyCell || entity instanceof TileEnergyCell.T2 || entity instanceof TileEnergyCell.T3 || entity instanceof TileEnergyCell.T4){
			return new ContainerEnergyCell(player.inventory, (TileEnergyCell)entity);
		}
		if(entity instanceof TileOreCreator){
			return new ContainerOreCreator(player.inventory, (TileOreCreator)entity);
		}
		if(ID == 2){
			return ID == 2 && world.getBlock(x, y, z) == Contents.SGCraftTable ? new ContainerSGCraftingTable(player.inventory, world, x, y, z) : null;
		}
		if(ID == 7){
			return new ContainerSwordOfDemon(x, y, z);
		}
/*		if(ID == 3){
			return ID == 3 && world.getBlock(x, y, z) == Contents.universalGenerator ? new ContainerUniversalGeneratorRecipe(player.inventory): null;
		}*/

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);

		if(entity instanceof TileUniversalGenerator){
			return new GuiUniversalGenerator(player.inventory, (TileUniversalGenerator)entity);
		}
		if(entity instanceof TileBoiler){
			return new GuiBoiler(player.inventory, (TileBoiler)entity);
		}
		if(entity instanceof TileEnergyCell || entity instanceof TileEnergyCell.T2 || entity instanceof TileEnergyCell.T3 || entity instanceof TileEnergyCell.T4){
			return new GuiEnergyCell(player.inventory, (TileEnergyCell)entity);
		}
		if(entity instanceof TileOreCreator){
			return new GuiOreCreator(player.inventory, (TileOreCreator)entity);
		}
		if(ID == 2){
			return ID == 2 && world.getBlock(x, y, z) == Contents.SGCraftTable ? new GuiSGCraftTable(player.inventory, world, x, y, z) : null;
		}
		if(ID == 7){
			return new GuiSwordOfDemon(x, y, z);
		}
/*		if(ID == 3){
			return ID == 3 && world.getBlock(x, y, z) == Contents.universalGenerator ? new GuiRecipeUniversalGenerator(player.inventory) : null;
		}*/

		return null;
	}
}
