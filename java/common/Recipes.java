package common;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import recipe.SGCraftTableCraftingManager;

public class Recipes {

	public static Recipes instance = new Recipes();
	public SGCraftTableCraftingManager recipe;

	@SuppressWarnings("static-access")
	public void loadRecipe(){
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(Contents.SGCraftTable)),
				new Object[]{
						"AAA",
						"ABA",
						"AAA",
						'A', new ItemStack(Item.getItemFromBlock(Blocks.crafting_table)),
						'B', new ItemStack(Item.getItemFromBlock(Blocks.iron_block))
				});

		recipe.getInstance().addRecipe(new ItemStack(Item.getItemFromBlock(Contents.universalGenerator)),
				new Object[]{
						"ABBBA",
						"BCDCB",
						"BEFGB",
						"BCHCB",
						"ABBBA",

						'A', Blocks.gold_block,
						'B', Blocks.iron_block,
						'C', new ItemStack(Contents.itemMaterial, 1, 0),
						'D', Blocks.diamond_block,
						'E', Contents.tankStorage,
						'F', Contents.machineFrame,
						'G', Contents.tankStorageReinforced,
						'H', Items.nether_star
						});

		recipe.getInstance().addRecipe(new ItemStack(Item.getItemFromBlock(Contents.reinforcedGlass)),
				new Object[]{
						"BBBBB",
						"BAAAB",
						"BAAAB",
						"BAAAB",
						"BBBBB",

						'A', new ItemStack(Blocks.glass, 1, OreDictionary.getOreID("blockGlass")),
						'B', new ItemStack(Blocks.obsidian, 1, OreDictionary.getOreID("blockObsidian"))
				});

		recipe.getInstance().addRecipe(new ItemStack(Item.getItemFromBlock(Contents.tankStorage)),
				new Object[]{
						"AAAAA",
						"ABCBA",
						"ACDCA",
						"ABCBA",
						"AAAAA",

						'A', new ItemStack(Contents.reinforcedGlass, 1, OreDictionary.getOreID("blockGlassHardened")),
						'B', new ItemStack(Blocks.hopper, 1, OreDictionary.getOreID("blockHopper")),
						'C', new ItemStack(Contents.itemMaterial, 1, 0),
						'D', new ItemStack(Blocks.iron_block, 1, OreDictionary.getOreID("blockIron"))
				});

		recipe.getInstance().addRecipe(new ItemStack(Item.getItemFromBlock(Contents.tankStorageReinforced)),
				new Object[]{
						"ABBBA",
						"ACDCA",
						"ADEDA",
						"ACDCA",
						"ABBBA",

						'A', new ItemStack(Blocks.iron_block, 1, OreDictionary.getOreID("blockIron")),
						'B', new ItemStack(Contents.tankStorage, 1),
						'C', new ItemStack(Blocks.gold_block, 1, OreDictionary.getOreID("blockGold")),
						'D', new ItemStack(Blocks.diamond_block, 1, OreDictionary.getOreID("blockDiamond")),
						'E', new ItemStack(Items.nether_star, 1, OreDictionary.getOreID("itemNetherStar"))
				});

		recipe.getInstance().addRecipe(new ItemStack(Item.getItemFromBlock(Contents.denseIronBlock)),
				new Object[]{
						"AAAAA",
						"AAAAA",
						"AAAAA",
						"AAAAA",
						"AAAAA",

						'A', new ItemStack(Item.getItemFromBlock(Blocks.iron_block))
				});

		recipe.getInstance().addRecipe(new ItemStack(Item.getItemFromBlock(Contents.denseRedstoneBlock)),
				new Object[]{
						"AAAAA",
						"AAAAA",
						"AAAAA",
						"AAAAA",
						"AAAAA",

						'A', new ItemStack(Item.getItemFromBlock(Blocks.redstone_block))
				});

		recipe.getInstance().addRecipe(new ItemStack(Contents.itemMaterial, 1, 0),
				new Object[]{
						"ABBBA",
						"BCCCB",
						"BCDCB",
						"BCCCB",
						"ABBBA",

						'A', new ItemStack(Items.gold_ingot),
						'B', new ItemStack(Items.iron_ingot),
						'C', new ItemStack(Items.dye, 1, 4),
						'D', new ItemStack(Item.getItemFromBlock(Contents.denseRedstoneBlock))
				});

		recipe.getInstance().addRecipe(new ItemStack(Item.getItemFromBlock(Contents.machineFrame)),
				new Object[]{
						"AABAA",
						"ACDCA",
						"BDEDB",
						"ACDCA",
						"AABAA",

						'A', new ItemStack(Item.getItemFromBlock(Blocks.iron_block)),
						'B', new ItemStack(Contents.itemMaterial, 1, 0),
						'C', new ItemStack(Item.getItemFromBlock(Contents.denseRedstoneBlock)),
						'D', new ItemStack(Item.getItemFromBlock(Contents.tankStorage)),
						'E', new ItemStack(Item.getItemFromBlock(Contents.denseIronBlock))
				});

		recipe.getInstance().addRecipe(new ItemStack(Item.getItemFromBlock(Contents.energyCell_t1)),
				new Object[]{
						"NNNNN",
						"NAAAN",
						"NABAN",
						"NAAAN",
						"NNNNN",

						'N', new ItemStack(Items.iron_ingot),
						'A', new ItemStack(Item.getItemFromBlock(Contents.denseRedstoneBlock)),
						'B', new ItemStack(Item.getItemFromBlock(Contents.machineFrame))
				});

		recipe.getInstance().addRecipe(new ItemStack(Item.getItemFromBlock(Contents.energyCell_t2)),
				new Object[]{
						"NNNNN",
						"NAAAN",
						"NABAN",
						"NAAAN",
						"NNNNN",

						'N', new ItemStack(Items.iron_ingot),
						'A', new ItemStack(Item.getItemFromBlock(Contents.denseRedstoneBlock)),
						'B', new ItemStack(Item.getItemFromBlock(Contents.energyCell_t1))
				});

		recipe.getInstance().addRecipe(new ItemStack(Item.getItemFromBlock(Contents.energyCell_t3)),
				new Object[]{
						"NNNNN",
						"NAAAN",
						"NABAN",
						"NAAAN",
						"NNNNN",

						'N', new ItemStack(Items.iron_ingot),
						'A', new ItemStack(Item.getItemFromBlock(Contents.denseRedstoneBlock)),
						'B', new ItemStack(Item.getItemFromBlock(Contents.energyCell_t2))
				});

		recipe.getInstance().addRecipe(new ItemStack(Item.getItemFromBlock(Contents.energyCell_t4)),
				new Object[]{
						"NNNNN",
						"NAAAN",
						"NABAN",
						"NAAAN",
						"NNNNN",

						'N', new ItemStack(Items.iron_ingot),
						'A', new ItemStack(Item.getItemFromBlock(Contents.denseRedstoneBlock)),
						'B', new ItemStack(Item.getItemFromBlock(Contents.energyCell_t3))
				});
	}
}
