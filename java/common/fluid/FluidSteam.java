package common.fluid;

import java.util.Random;

import common.SimplyGenerators;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public class FluidSteam extends BlockFluidClassic{

	@SideOnly(Side.CLIENT)
	protected IIcon baseIcon;
	@SideOnly(Side.CLIENT)
	protected IIcon sideIcon;

	public FluidSteam(Fluid fluid, Material material){
		super(fluid, material);
		this.setQuantaPerBlock(6);
		this.displacements.put(Blocks.water, false);
		this.displacements.put(Blocks.lava, false);
		this.setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return side != 0 && side != 1 ? this.sideIcon : this.baseIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.baseIcon = par1IconRegister.registerIcon("simplygenerators:fluid/steam");
		this.sideIcon = par1IconRegister.registerIcon("simplygenerators:fluid/steam");

	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		Material mat = world.getBlock(x, y, z).getMaterial();
		if (mat.isLiquid()) {
			return false;
		}
		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {
		Material mat = world.getBlock(x, y, z).getMaterial();
		if (mat.isLiquid()) {
			return false;
		}
		return super.displaceIfPossible(world, x, y, z);
	}

	@SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_){
    }
}