package client;

import org.lwjgl.input.Keyboard;

import client.render.ItemArmorBootsRenderer;
import client.render.ItemArmorChestRenderer;
import client.render.ItemArmorHelmRenderer;
import client.render.ItemArmorLegRenderer;
import client.render.ItemSGCraftTableRenderer;
import client.render.ItemUniversalGeneratorRenderer;
import client.render.TileSGCraftTableRenderer;
import client.render.TileUniversalGeneratorRenderer;
import client.tileentity.TileSGCraftTable;
import client.tileentity.TileUniversalGenerator;
import common.Contents;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Loader;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import recipe.LoadNEIConfig;
import server.CommonProxy;

public class ClientProxy extends CommonProxy{

	@Override
	public boolean isShiftKeyDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
	}

	@Override
	public boolean isSpaceKeyDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_SPACE);
	}

	@Override
	public void registerRenderers(){
		ClientRegistry.registerTileEntity(TileUniversalGenerator.class, "sg.render.generator.1", TileUniversalGeneratorRenderer.instance);
		ClientRegistry.registerTileEntity(TileSGCraftTable.class, "sg.render.crafting", TileSGCraftTableRenderer.instance);

		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Contents.universalGenerator), ItemUniversalGeneratorRenderer.instance);
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Contents.SGCraftTable), ItemSGCraftTableRenderer.instance);

		MinecraftForgeClient.registerItemRenderer(Contents.demon_Helmet, ItemArmorHelmRenderer.instance);
		MinecraftForgeClient.registerItemRenderer(Contents.demon_ChestPlate, ItemArmorChestRenderer.instance);
		MinecraftForgeClient.registerItemRenderer(Contents.demon_Leggins, ItemArmorLegRenderer.instance);
		MinecraftForgeClient.registerItemRenderer(Contents.demon_Boots, ItemArmorBootsRenderer.instance);
	}

	@Override
	public void LoadNEI(){
		if(Loader.isModLoaded("NotEnoughItems")){
			try{
	    		LoadNEIConfig.load();
	        }
	        catch(Exception e){
	        	e.printStackTrace(System.err);
	        }
		}
	}

	@Override
	public EntityPlayer getEntityPlayerInstance(){
		return Minecraft.getMinecraft().thePlayer;
	}
}
