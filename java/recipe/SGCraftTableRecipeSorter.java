package recipe;

import java.util.Comparator;

import net.minecraft.item.crafting.IRecipe;

@SuppressWarnings("rawtypes")
public class SGCraftTableRecipeSorter implements Comparator{

	final SGCraftTableCraftingManager fCraftTable;

	public SGCraftTableRecipeSorter(SGCraftTableCraftingManager manager){
		this.fCraftTable = manager;
	}

	public int compareRecipes(IRecipe iRecipe, IRecipe iRecipe2){
		return iRecipe instanceof SGCraftTableShapelessRecipes && iRecipe2 instanceof SGCraftTableShapedRecipes ? 1 : (iRecipe2 instanceof SGCraftTableShapelessRecipes && iRecipe instanceof SGCraftTableShapedRecipes ? -1 : (iRecipe2.getRecipeSize() < iRecipe.getRecipeSize() ? -1 : (iRecipe2.getRecipeSize() > iRecipe.getRecipeSize() ? 1 : 0)));
	}

	@Override
	public int compare(Object o1, Object o2) {
		return this.compareRecipes((IRecipe)o1, (IRecipe)o2);
	}
}
