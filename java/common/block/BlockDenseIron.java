package common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDenseIron extends Block{

	public BlockDenseIron(String name){
		super(Material.iron);
		this.setBlockName(name);
		this.setBlockTextureName("simplygenerators:dense_ironBlock");
	}
}
