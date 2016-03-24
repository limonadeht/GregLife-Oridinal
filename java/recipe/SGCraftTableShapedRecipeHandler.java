package recipe;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import client.gui.GuiSGCraftTable;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.ShapedRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.StatCollector;

public class SGCraftTableShapedRecipeHandler extends ShapedRecipeHandler{

	public class CachedSGRecipe extends CachedRecipe{

		public CachedSGRecipe(SGCraftTableShapedRecipes recipe){
			this(recipe.recipeWidth, recipe.recipeHeight, recipe.recipeItems, recipe.getRecipeOutput());
		}

		public CachedSGRecipe(int width, int height, Object[] items, ItemStack out){
			this.result = new PositionedStack(out, 138, 44);
			this.ingredients = new ArrayList<PositionedStack>();
			this.setIngredients(width, height, items);
		}

		public void setIngredients(int width, int height, Object[] items){
			for(int x = 0; x < width; x++){
				for(int y = 0; y < height; y++){
					if(items[y * width + x] == null){
						continue;
					}
					int ex = 3 + x * 18;
					int wy = 3 + y * 18;
					if(wy == 129){
						if(ex == 3 || ex == 129)
							ex -= 1;
						else if(ex == 21 || ex == 147)
							ex += 1;
					}
						PositionedStack stack = new PositionedStack(items[y * width + x], ex, wy);
						stack.setMaxSize(1);
						this.ingredients.add(stack);
				}
			}
		}

		@Override
        public ArrayList<PositionedStack> getIngredients()
        {
            return (ArrayList<PositionedStack>) getCycledIngredients(SGCraftTableShapedRecipeHandler.this.cycleticks / 20, this.ingredients);
        }

        @Override
        public PositionedStack getResult()
        {
            return this.result;
        }

        public void computeVisuals() {
            for (PositionedStack p : ingredients)
                p.generatePermutations();
        }

        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;
	}

	@Override
    public int recipiesPerPage()
    {
        return 1;
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass()
    {
        return GuiSGCraftTable.class;
    }

    @Override
    public String getRecipeName()
    {
        return StatCollector.translateToLocal("crafting.simplyGenerators");
    }

    @SuppressWarnings("unchecked")
	@Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("extreme") && getClass() == SGCraftTableShapedRecipeHandler.class) {
            for (IRecipe irecipe : (List<IRecipe>) SGCraftTableCraftingManager.getInstance().getRecipeList()) {
                CachedSGRecipe recipe = null;
                if (irecipe instanceof SGCraftTableShapedRecipes)
                    recipe = new CachedSGRecipe((SGCraftTableShapedRecipes) irecipe);
                /*else if (irecipe instanceof ExtremeShapedOreRecipe)
                    recipe = forgeExtremeShapedRecipe((ExtremeShapedOreRecipe) irecipe);*/

                if (recipe == null)
                    continue;

                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public void loadCraftingRecipes(ItemStack result) {
        for (IRecipe irecipe : (List<IRecipe>) SGCraftTableCraftingManager.getInstance().getRecipeList()) {
            if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result)) {
                CachedSGRecipe recipe = null;
                if (irecipe instanceof SGCraftTableShapedRecipes)
                    recipe = new CachedSGRecipe((SGCraftTableShapedRecipes) irecipe);
               /* else if (irecipe instanceof ExtremeShapedOreRecipe)
                    recipe = forgeExtremeShapedRecipe((ExtremeShapedOreRecipe) irecipe);*/

                if (recipe == null)
                    continue;

                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public void loadUsageRecipes(ItemStack ingredient) {
        for (IRecipe irecipe : (List<IRecipe>) SGCraftTableCraftingManager.getInstance().getRecipeList()) {
            CachedSGRecipe recipe = null;
            if (irecipe instanceof SGCraftTableShapedRecipes)
                recipe = new CachedSGRecipe((SGCraftTableShapedRecipes) irecipe);
            /*else if (irecipe instanceof ExtremeShapedOreRecipe)
                recipe = forgeExtremeShapedRecipe((ExtremeShapedOreRecipe) irecipe);*/

            if (recipe == null || !recipe.contains(recipe.ingredients, ingredient.getItem()))
                continue;

            recipe.computeVisuals();
            if (recipe.contains(recipe.ingredients, ingredient)) {
                recipe.setIngredientPermutation(recipe.ingredients, ingredient);
                arecipes.add(recipe);
            }
        }
    }

    /*public CachedSGRecipe forgeExtremeShapedRecipe(SGCraftTableShapedRecipes recipe) {
        int width = recipe.width;
        int height = recipe.height;

        Object[] items = recipe.getInput();
        for (Object item : items)
            if (item instanceof List && ((List<?>) item).isEmpty())//ore handler, no ores
                return null;

        return new CachedSGRecipe(width, height, items, recipe.getRecipeOutput());
    }*/

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(166, 74, 24, 18), "simplyGeneratorsCrafting"));
    }

    @Override
    public String getOverlayIdentifier() {
        return "simplyGeneratorsCrafting";
    }

    @Override
    public String getGuiTexture()
    {
        return "simplyGenerators:textures/gui/dummyGui.png";
    }

    @Override
    public boolean hasOverlay(GuiContainer gui, Container container, int recipe)
    {
        return RecipeInfo.hasDefaultOverlay(gui, "simplyGeneratorsCrafting");
    }
}
