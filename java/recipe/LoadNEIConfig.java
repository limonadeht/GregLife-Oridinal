package recipe;

import codechicken.nei.api.API;
import handler.CatchRecipeHandler;

public class LoadNEIConfig {

	public static CatchRecipeHandler catchRecipe;

	public static void load()
	{
		catchRecipe = new CatchRecipeHandler();
		SGCraftTableShapedRecipeHandler shaped = new SGCraftTableShapedRecipeHandler();
		SGCraftTableShapelessRecipeHandler shapeless = new SGCraftTableShapelessRecipeHandler();

		API.registerRecipeHandler(catchRecipe);
		API.registerRecipeHandler(shaped);
		API.registerRecipeHandler(shapeless);
		//API.registerUsageHandler(catchRecipe);
		//API.registerGuiOverlay(GuiDummy.class, catchRecipe.getOverlayIdentifier(), 0, 0);
	}
}
