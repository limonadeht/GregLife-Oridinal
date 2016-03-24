package common.block;

import common.SimplyGenerators;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockMachineFrame extends Block{

	public BlockMachineFrame(String name, String textureLocation){
		super(Material.iron);
		this.setBlockName(name);
		this.setBlockTextureName("simplygenerators:" + textureLocation);
		this.setStepSound(soundTypeMetal);
		this.setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);
	}

	public static class UniversalGenerator extends BlockMachineFrame{

		public UniversalGenerator(String name, String textureLocation){
			super(name, textureLocation);
		}
	}

	public static class EnergyCell extends BlockMachineFrame{

		public EnergyCell(String name, String textureLocation){
			super(name, textureLocation);
		}
	}
}
