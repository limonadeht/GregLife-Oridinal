package handler;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map.Entry;

import client.gui.GuiDummy;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import recipe.NEIRecipeRegister;

public class CatchRecipeHandler extends TemplateRecipeHandler{

	/*表示用のレシピをHashMap型で用意します。*/
	private HashMap<ItemStack, ItemStack> newRecipe;

	/*表示用のHashMapの取得。
	  ここでは、NewRecipeRegister.classで用意しておいた架空のレシピを取得します。
	  （独自レシピの作成については割愛します。）*/
	private HashMap<ItemStack, ItemStack> recipeLoader() {
		if (NEIRecipeRegister.newRecipeList != null && !NEIRecipeRegister.newRecipeList.isEmpty()) {
			this.newRecipe = NEIRecipeRegister.newRecipeList;
		}
		return this.newRecipe;
	}

	public class recipeCacher extends CachedRecipe {

		/*インプット、アウトプットそれぞれの表示用ItemStackです。
		  表示させる位置などの情報を持たせることが出来ます。*/
		private PositionedStack input;
		private PositionedStack result;

		/*表示用のItemStackの、画面上の座標を登録しています。
		  この座標がずれると、レシピ表示画面に表示されるアイコンがズレるので気をつけましょう。*/
		public recipeCacher(ItemStack in, ItemStack out) {
			in.stackSize = 1;
			this.input = new PositionedStack(in, 48, 21);
			this.result= new PositionedStack(out, 102, 21);
		}

		@Override
		public PositionedStack getResult() {
			return this.result;
		}

		@Override
		public PositionedStack getIngredient()
        {
            return this.input;
        }

	}

	public PositionedStack getResult() {
	    return null;
	}

	/*ここではダミー用のGUIを登録していますが、
	  独自クラフトシステム用に用意したGUIがあれば流用できます。
	  <br>ここで登録をしたguiからレシピ画面に飛ぶことが出来るようになります。
	  <br>もちろん、（魔術系MODのように）可愛らしいイラストなどを用意して表示させることも出来ます。*/
	@Override
	public Class<? extends GuiContainer> getGuiClass() {
	    return GuiDummy.class;
	}

	/*登録用の文字列です*/
	@Override
	public String getOverlayIdentifier() {
	  return "UniversalGeneratorRecipe";
	}

	//ここで登録をした範囲内をGUIでクリックすると文字列のレシピ画面を表示します.
	@Override
	public void loadTransferRects() {
	    transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(65, 25, 20, 20), "SampleRecipe"));
	}

	/*以下のメソッドは、NEIのGUI画面上でアイコンをクリックしたり、
	  プログレスバーの部分をクリックした時に呼び出されるメソッド*/

    @Override
    public void loadCraftingRecipes(String outputId, Object... results)
    {
        if(outputId.equals("UniversalGeneratorRecipe"))
        {
            HashMap<ItemStack, ItemStack> recipes = (HashMap<ItemStack, ItemStack>) this.recipeLoader();

            if(recipes == null || recipes.isEmpty())return;
            for(Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
            {
                ItemStack item = recipe.getValue();
                ItemStack in = recipe.getKey();
                arecipes.add(new recipeCacher(in, item));
            }
        }
        else
        {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result)
    {

	HashMap<ItemStack, ItemStack> recipes = (HashMap<ItemStack, ItemStack>) this.recipeLoader();

        if(recipes == null || recipes.isEmpty())return;
        for(Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
        {
            ItemStack item = recipe.getValue();
            ItemStack in = recipe.getKey();
            if(NEIServerUtils.areStacksSameType(item, result))
            {
                arecipes.add(new recipeCacher(in, item));
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient)
    {

	HashMap<ItemStack, ItemStack> recipes = (HashMap<ItemStack, ItemStack>) this.recipeLoader();

        if(recipes == null || recipes.isEmpty())return;
        for(Entry<ItemStack, ItemStack> recipe : recipes.entrySet())
        {
            ItemStack item = recipe.getValue();
            ItemStack in = recipe.getKey();
            if(ingredient.getItem() == in.getItem() && ingredient.getItemDamage() == in.getItemDamage())
            {
                arecipes.add(new recipeCacher(ingredient, item));
            }
        }
    }

	/*レシピ画面に表示される名前です。*/
	@Override
	public String getRecipeName() {
		return "SG Crafting Table";
	}

	/*レシピ画面に使われる背景画像の場所です。*/
	@Override
	public String getGuiTexture() {
		return "simplygenerators:textures/gui/UniversalGeneratorRecipe.png";
	}
}
