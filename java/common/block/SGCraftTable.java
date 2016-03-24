package common.block;

import client.tileentity.TileSGCraftTable;
import common.SimplyGenerators;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SGCraftTable extends BlockContainer{

	public int GuiId = 2;

	@SideOnly(Side.CLIENT)
	private IIcon top;

	public SGCraftTable(){
		super(Material.iron);

		this.setBlockName("sg.crafting");
		this.setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);
		this.setStepSound(soundTypeMetal);
		this.setHardness(10.0F);
		this.setResistance(10.0F);
	}

	/*@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta){
		return side == 1 ? this.top : this.blockIcon;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister){
		this.top = iconRegister.registerIcon("simplygenerators:SGCraftTable_top");
		this.blockIcon = iconRegister.registerIcon("simplygenerators:SGCraftTable_blockIcon");
	}*/

	public boolean isOpaqueCube(){
		return false;
	}

	public boolean renderAsNormalBlock(){
		 return false;
	}

	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side){
        return false;
    }

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4){
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int x, int y, int z){
		return AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)y + 0.5D, (double)(z + 1));
	}

	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int x, int y, int z){
		return AxisAlignedBB.getBoundingBox((double)x, (double)y, (double)z, (double)(x + 1), (double)y + 0.5D, (double)(z + 1));
	}

	public int getFCraftTableGuiId(){
		return this.GuiId;
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
			player.openGui(SimplyGenerators.Instance, GuiId, world, x, y, z);
		return true;
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack){
	}

	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta){
	}

	@Override
	public TileEntity createNewTileEntity(World world, int p_149915_2_){
		return new TileSGCraftTable();
	}

	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer){
	    return true;
	}

	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer){
	    return true;
	}
}
