package common;

import common.block.BlockBoiler;
import common.block.BlockDenseIron;
import common.block.BlockDenseRedstone;
import common.block.BlockEnergyCell;
import common.block.BlockMachineFrame;
import common.block.BlockOreCreator;
import common.block.BlockReinforcedGlass;
import common.block.BlockSGBase;
import common.block.BlockTankStorage;
import common.block.BlockUniversalGenerator;
import common.block.SGCraftTable;
import common.entity.EntityRailGun;
import common.entity.EntityTeleporter;
import common.fluid.BlockFluidSteam;
import common.item.ItemBase;
import common.item.ItemBlockSGBase;
import common.item.ItemBoiler;
import common.item.ItemEnergyCell;
import common.item.ItemFlintAndSteelTier;
import common.item.ItemSGCraftTable;
import common.item.ItemTankStorage;
import common.item.ItemUniversalGenerator;
import common.item.armor.ArmorOfDemon;
import common.item.tool.ItemSoulariumCannon;
import common.item.tool.ItemSwordOfDemon;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class Contents {

	public static Contents instance = new Contents();

	public static ArmorMaterial ARMOR_OF_DEMON = EnumHelper.addArmorMaterial("ARMOR_OF_DEMON", -1, new int[]{10,13,16,10}, 30);
	public static ToolMaterial TOOL_DEMON = EnumHelper.addToolMaterial("TOOL_DEMON", 5, -1, 10, 0.1F, 30);

	public static Item flindAndSteelTier;
	public static Item itemMaterial;
	public static Item itemCores;
	//public static Item wrench;
	//public static Item G_Core;
	public static Item soulariumCannon;

	public static ItemArmor demon_Helmet;
	public static ItemArmor demon_ChestPlate;
	public static ItemArmor demon_Leggins;
	public static ItemArmor demon_Boots;

	public static ItemSword sword_of_demon;

	public static Block universalGenerator;
	public static Block tankStorage;
	public static Block tankStorageReinforced;
	public static Block energyCell_t1;
	public static Block energyCell_t2;
	public static Block energyCell_t3;
	public static Block energyCell_t4;
	public static Block boiler;
	public static Block oreCreator;
	public static Block SGCraftTable;
	public static Block reinforcedGlass;
	public static Block denseRedstoneBlock;
	public static Block denseIronBlock;

	public static BlockSGBase blockDeco;
	public static BlockSGBase blockDeco1;

	public static Block machineFrame;

	public static Fluid fluidSteam;
	public static Block blockFluidSteam;

	private Contents(){}

	public void loadOreDictionary(){
		//OreDictionaly
		OreDictionary.registerOre("circuitBasic", new ItemStack(itemMaterial, 1, 0));
		OreDictionary.registerOre("gearDiamond", new ItemStack(itemMaterial, 1, 1));
		OreDictionary.registerOre("blockGlassHardened", new ItemStack(Item.getItemFromBlock(reinforcedGlass)));
	}

	@SuppressWarnings("static-access")
	public void load(){
		GameRegistry.registerItem(flindAndSteelTier, "sg.flintAndSteelTier");
		GameRegistry.registerItem(soulariumCannon, "sg.soulariumCannon");
		//GameRegistry.registerItem(G_Core, "sg.Gcore");
		//GameRegistry.registerItem(wrench, "sg.wrench");

		GameRegistry.registerBlock(universalGenerator, ItemUniversalGenerator.class, "sg.tile.generator.1");
		GameRegistry.registerBlock(tankStorage, ItemTankStorage.class, "sg.tile.storage.1");
		GameRegistry.registerBlock(tankStorageReinforced, ItemTankStorage.class, "sg.tile.storage.2");
		GameRegistry.registerBlock(energyCell_t1, ItemEnergyCell.class, "sg.tile.energyCell.t1");
		GameRegistry.registerBlock(energyCell_t2, ItemEnergyCell.class, "sg.tile.energyCell.t2");
		GameRegistry.registerBlock(energyCell_t3, ItemEnergyCell.class, "sg.tile.energyCell.t3");
		GameRegistry.registerBlock(energyCell_t4, ItemEnergyCell.class, "sg.tile.energyCell.t4");
		GameRegistry.registerBlock(boiler, ItemBoiler.class, "sg.tile.boiler");
		GameRegistry.registerBlock(oreCreator, "sg.tile.oreCreator");
		GameRegistry.registerBlock(SGCraftTable, ItemSGCraftTable.class, "sg.tile.crafting");
		GameRegistry.registerBlock(reinforcedGlass, "sg.tile.blockDeco.1");
		GameRegistry.registerBlock(denseRedstoneBlock, "sg.tile.blockDeco.2");
		GameRegistry.registerBlock(denseIronBlock, "sg.tile.blockDeco.3");

		//Machine Frame
		GameRegistry.registerBlock(machineFrame, "sg.tile.machineFrame");

		//Fluid
		fluidSteam = new Fluid("steam").setUnlocalizedName("sg.fluid.steam");
		FluidRegistry.registerFluid(fluidSteam);
		blockFluidSteam = new BlockFluidSteam(fluidSteam, Material.water).setBlockName("sg.fluid.steam");
		GameRegistry.registerBlock(blockFluidSteam, "sg.tile.fluid.steam");

		EntityRegistry.registerGlobalEntityID(EntityRailGun.class, "sg.entity.railGun", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.instance().registerModEntity(EntityRailGun.class, "sg.entity.railGun", 1, SimplyGenerators.MOD_ID, 128, 1, true);

		EntityRegistry.registerGlobalEntityID(EntityTeleporter.class, "sg.entity.teleporter", EntityRegistry.findGlobalUniqueEntityId());
		EntityRegistry.instance().registerModEntity(EntityTeleporter.class, "sg.entity.teleporter", 1, SimplyGenerators.MOD_ID, 128, 1, true);
	}

	static void addContents(){
		flindAndSteelTier = new ItemFlintAndSteelTier().setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);

		universalGenerator = new BlockUniversalGenerator(false, "sg.generator.1", Material.iron, 320);
		tankStorage = new BlockTankStorage("sg.storage.1", 16000);
		tankStorageReinforced = new BlockTankStorage.Reinforced("sg.storage.2", 100000000);
		energyCell_t1 = new BlockEnergyCell("sg.energyCell.t1", 1000000);
		energyCell_t2 = new BlockEnergyCell.T2("sg.energyCell.t2", 10000000);
		energyCell_t3 = new BlockEnergyCell.T3("sg.energyCell.t3", 100000000);
		energyCell_t4 = new BlockEnergyCell.T4("sg.energyCell.t4", 1000000000);
		boiler = new BlockBoiler("sg.boiler", 100000, 16000);
		oreCreator = new BlockOreCreator("sg.oreCreator", 10000000);
		SGCraftTable = new SGCraftTable();
		reinforcedGlass = new BlockReinforcedGlass();
		denseRedstoneBlock = new BlockDenseRedstone("sg.denseBlock.1").setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);
		denseIronBlock = new BlockDenseIron("sg.denseBlock.2").setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);

		blockDeco = (BlockSGBase) new BlockSGBase.BlockSGSimple("deco",Material.rock, ItemBlockSGBase.class,
				"stone.1","stone.2","stone.3","stone.4","stone.5","stone.6","stone.7","stone.8","stone.9","stone.10","stone.11","stone.12","stone.13","stone.14","stone.14"
		).setHardness(3f).setResistance(5f);

		blockDeco1 = (BlockSGBase) new BlockSGBase.BlockSGSimple("deco.1", Material.rock, ItemBlockSGBase.class,
				"cobblestone","cobblestone_mossy","clay","fan",
				"wood.1","wood.2",
				"glass.1","glass.2","glass.3",
				"tnt.1","tnt.2",
				"wool.1","wool.2","wool.3",
				"lamp.1","lamp.2"
		);

		//Machine Frame
		machineFrame = new BlockMachineFrame.UniversalGenerator("sg.machineFrame", "base_t1");

		//wrench = new ItemWrench();

		//Meta Items
		itemMaterial = new ItemBase("itemMaterial", 64,
		"circuit"
		/*"gearWood", "gearStone", "gearIron", "gearGold", "gearDiamond"*/
		);

		itemCores = new ItemBase("armorCore", 8
		);

		demon_Helmet = new ArmorOfDemon(ARMOR_OF_DEMON, 0, "demon_helm");
		demon_ChestPlate = new ArmorOfDemon(ARMOR_OF_DEMON, 1, "demon_chest");
		demon_Leggins = new ArmorOfDemon(ARMOR_OF_DEMON, 2, "demon_leggins");
		demon_Boots = new ArmorOfDemon(ARMOR_OF_DEMON, 3, "demon_boots");

		sword_of_demon = new ItemSwordOfDemon("sg.swordDemon", TOOL_DEMON);

		soulariumCannon = new ItemSoulariumCannon("sg.soulariumCannon");
	}
}
