package common;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import handler.GuiHandler;
import handler.PacketHandler;
import handler.SGEntityPropertiesEventHandler;
import handler.SGEventHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import server.CommonProxy;
import util.MessageMarks;
import util.PacketPipeline;

@Mod(modid = SimplyGenerators.MOD_ID, version = SimplyGenerators.VERSION)
public class SimplyGenerators {

	@Mod.Instance("SimplyGenerators")
	public static SimplyGenerators Instance;
	public static final String MOD_ID = "SimplyGenerators";
	public static final String VERSION = "Alpha-1.0";

	public static final PacketPipeline network = new PacketPipeline();

	@SidedProxy(clientSide = "client.ClientProxy", serverSide = "server.CommonProxy")
	public static CommonProxy commonProxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		try{
			config.load();
		}catch (Exception e){}
		finally{
			config.save();
		}



		Contents.instance.loadOreDictionary();

		PacketHandler.init();
	}

	@SuppressWarnings("static-access")
	@EventHandler
	public void Init(FMLPreInitializationEvent e){
		Contents.instance.addContents();
		Contents.instance.load();
		Recipes.instance.loadRecipe();

		commonProxy.LoadNEI();
		commonProxy.registerRenderers();
		commonProxy.registerTileEntity();

		NetworkRegistry.INSTANCE.registerGuiHandler(this.Instance, new GuiHandler());

		SGEventHandler handler = new SGEventHandler();

		MinecraftForge.EVENT_BUS.register(handler);

		FMLCommonHandler.instance().bus().register(handler);

		network.initialise();
		network.registerPacket(MessageMarks.class);

		SGEntityPropertiesEventHandler SGPropertiesEventHandler = new SGEntityPropertiesEventHandler();
		MinecraftForge.EVENT_BUS.register(SGPropertiesEventHandler);
		//FML Eventの登録。PlayerRespawnEvent
		FMLCommonHandler.instance().bus().register(SGPropertiesEventHandler);
	}

	@EventHandler
	public void postInitialise(FMLPostInitializationEvent e){
		network.postInitialise();
	}

	public static CreativeTabs SimplyGeneratorsTab = new CreativeTabs("Simply Generators"){

		public Item getTabIconItem(){
			return Item.getItemFromBlock(Contents.universalGenerator);
		}
	};

	public static CreativeTabs SimplyGeneratorsDecoTab = new CreativeTabs("Simply Generators.deco"){

		public Item getTabIconItem(){
			return Item.getItemFromBlock(Contents.blockDeco1);
		}
	};
}
