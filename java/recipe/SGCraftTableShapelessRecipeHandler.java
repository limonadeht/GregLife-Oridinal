package recipe;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.opengl.GL11;

import client.gui.GuiSGCraftTable;
import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.RecipeInfo;
import codechicken.nei.recipe.ShapelessRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.StatCollector;

public class SGCraftTableShapelessRecipeHandler extends ShapelessRecipeHandler{

	public class CachedExtremeShapelessRecipe extends CachedRecipe
    {
        public CachedExtremeShapelessRecipe() {
            ingredients = new ArrayList<PositionedStack>();
        }

        public CachedExtremeShapelessRecipe(ItemStack output) {
            this();
            setResult(output);
        }

        public CachedExtremeShapelessRecipe(Object[] input, ItemStack output) {
            this(Arrays.asList(input), output);
        }

        public CachedExtremeShapelessRecipe(List<?> input, ItemStack output) {
            this(output);
            setIngredients(input);
        }

        public void setIngredients(List<?> items) {
            ingredients.clear();
            for (int ingred = 0; ingred < items.size(); ingred++) {
                int ex = 3 + (ingred % 9) * 18;
                int wy = 3 + (ingred / 9) * 18;
                if(wy == 129){
                    if(ex == 3 || ex == 129)
                        ex -= 1;
                    else if(ex == 21 || ex == 147)
                        ex += 1;
                }
                PositionedStack stack = new PositionedStack(items.get(ingred), ex, wy);
                stack.setMaxSize(1);
                ingredients.add(stack);
            }
        }

        public void setResult(ItemStack output) {
            result = new PositionedStack(output, 201, 75);
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(cycleticks / 20, ingredients);
        }

        @Override
        public PositionedStack getResult() {
            return result;
        }

        public ArrayList<PositionedStack> ingredients;
        public PositionedStack result;
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("simplyGenerators.Crafting.2");
    }

    @SuppressWarnings("unchecked")
	@Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("simplyGeneratorsCrafting") && getClass() == SGCraftTableShapelessRecipeHandler.class) {
            List<IRecipe> allrecipes = SGCraftTableCraftingManager.getInstance().getRecipeList();
            for (IRecipe irecipe : allrecipes) {
                CachedExtremeShapelessRecipe recipe = null;
                if (irecipe instanceof SGCraftTableShapelessRecipes)
                    recipe = shapelessRecipe((SGCraftTableShapelessRecipes) irecipe);
                /*else if (irecipe instanceof ShapelessOreRecipe)
                    recipe = forgeExtremeShapelessRecipe((ShapelessOreRecipe) irecipe);*/

                if (recipe == null)
                    continue;

                arecipes.add(recipe);
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public void loadCraftingRecipes(ItemStack result) {
        List<IRecipe> allrecipes = SGCraftTableCraftingManager.getInstance().getRecipeList();
        for (IRecipe irecipe : allrecipes) {
            if (NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result)) {
                CachedExtremeShapelessRecipe recipe = null;
                if (irecipe instanceof SGCraftTableShapelessRecipes)
                    recipe = shapelessRecipe((SGCraftTableShapelessRecipes) irecipe);
                /*else if (irecipe instanceof ShapelessOreRecipe)
                    recipe = forgeExtremeShapelessRecipe((ShapelessOreRecipe) irecipe);*/

                if (recipe == null)
                    continue;

                arecipes.add(recipe);
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public void loadUsageRecipes(ItemStack ingredient) {
        List<IRecipe> allrecipes = SGCraftTableCraftingManager.getInstance().getRecipeList();
        for (IRecipe irecipe : allrecipes) {
            CachedExtremeShapelessRecipe recipe = null;
            if (irecipe instanceof SGCraftTableShapelessRecipes)
                recipe = shapelessRecipe((SGCraftTableShapelessRecipes) irecipe);
            /*else if (irecipe instanceof ShapelessOreRecipe)
                recipe = forgeExtremeShapelessRecipe((ShapelessOreRecipe) irecipe);*/

            if (recipe == null)
                continue;

            if (recipe.contains(recipe.ingredients, ingredient)) {
                recipe.setIngredientPermutation(recipe.ingredients, ingredient);
                arecipes.add(recipe);
            }
        }
    }

    private CachedExtremeShapelessRecipe shapelessRecipe(SGCraftTableShapelessRecipes recipe) {
        if(recipe.recipeItems == null) //because some mod subclasses actually do this
            return null;

        return new CachedExtremeShapelessRecipe(recipe.recipeItems, recipe.getRecipeOutput());
    }

    /*public CachedExtremeShapelessRecipe forgeExtremeShapelessRecipe(SGCraftTableShapelessRecipes recipe) {
        ArrayList<Object> items = recipe.getInput();

        for (Object item : items)
            if (item instanceof List && ((List<?>) item).isEmpty())//ore handler, no ores
                return null;

        return new CachedExtremeShapelessRecipe(items, recipe.getRecipeOutput());
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

    @Override
    public void drawBackground(int recipe)
    {
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(-9, -20, 0, 0, 256, 208);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
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
}
