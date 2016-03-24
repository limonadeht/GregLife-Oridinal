package recipe;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

public class OreCreatorRecipes{

	private static final OreCreatorRecipes instance = new OreCreatorRecipes();

	public int energyUsed;

	private Map smeltingList = new HashMap();
    private Map experienceList = new HashMap();

    public static OreCreatorRecipes getInstance(){
    	return instance;
    }

    private OreCreatorRecipes(){

    }

    public void registerRecipe(int energyUsed, ItemStack output){
    	instance.registerRecipe(energyUsed, output);
    }
}
