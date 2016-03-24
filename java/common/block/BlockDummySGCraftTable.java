package common.block;

import common.Contents;
import common.SimplyGenerators;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockDummySGCraftTable extends Block{

	public int GuiId = 2;

	public BlockDummySGCraftTable(){
		super(Material.iron);

		this.setBlockName("sg.crafting");
		this.setStepSound(soundTypeCloth);
		this.setHardness(10.0F);
		this.setResistance(10.0F);
	}

	public int getFCraftTableGuiId(){
		return this.GuiId;
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
		if(world.getBlock(x, y-1, z) == Contents.SGCraftTable){
			player.openGui(SimplyGenerators.Instance, GuiId, world, x, y, z);
		}
		return true;
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack){
		if(world.getBlock(x, y-1, z) == Contents.SGCraftTable){
		}else{
			world.setBlockToAir(x, y, z);
		}
	}

	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta){
		if(world.getBlock(x, y-1, z) == Contents.SGCraftTable){
			world.setBlockToAir(x, y-1, z);
		}
	}
}
