package common.block;

import common.SimplyGenerators;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockReinforcedGlass extends Block{

	public BlockReinforcedGlass(){
		super(Material.glass);
		this.setBlockName("sg.reinforcedGlass");
		this.setBlockTextureName("simplyGenerators:ReinforcedGlass");
		this.setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);
		this.setStepSound(soundTypeGlass);
		this.setHardness(1000000.0F);
		this.setResistance(20.0F);
		this.setHarvestLevel("pickaxe", 3);
	}

	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }
}
