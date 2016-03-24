package common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockDenseRedstone extends Block{

	public BlockDenseRedstone(String name){
		super(Material.redstoneLight);
		this.setBlockName(name);
		this.setBlockTextureName("simplygenerators:dense_RedstoneBlock");
	}
}
