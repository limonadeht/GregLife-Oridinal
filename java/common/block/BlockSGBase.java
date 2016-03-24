
package common.block;

import java.util.List;

import common.SimplyGenerators;
import common.item.ItemBlockSGBase;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockSGBase extends BlockContainer
{
	public String name;
	public String[] subNames;
	public final IIcon[][] icons;
	protected final int iconDimensions;
	public boolean hasFlavour = false;
	public boolean isFlammable = false;
	public int[] lightOpacities;

	protected BlockSGBase(String name, Material mat, int iconDimensions, Class<? extends ItemBlockSGBase> itemBlock, String... subNames)
	{
		super(mat);
		this.adjustSound();
		this.subNames = subNames;
		this.name = name;
		this.iconDimensions = iconDimensions;
		this.icons = new IIcon[subNames.length][iconDimensions];
		this.lightOpacities = new int[subNames.length];
		this.setBlockName("sg."+name);
		GameRegistry.registerBlock(this, name);
		this.setCreativeTab(SimplyGenerators.SimplyGeneratorsDecoTab);
	}


	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
		Minecraft mc = Minecraft.getMinecraft();

		int metadatas = stack.getItemDamage();
		world.setBlockMetadataWithNotify(x, y, z, metadatas, 2);

		if(metadatas > 15){
			world.setBlockToAir(x, y, z);
			if(!world.isRemote){
				mc.thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "[SimplyGenerators] There is an error in the Please block."));
				mc.thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "[SimplyGenerators] Please report to mod author our server owner."));
			}
		}
	}

	public BlockSGBase setFlammable(boolean b)
	{
		this.isFlammable = b;
		return this;
	}

	public BlockSGBase setHasFlavour(boolean hasFlavour)
	{
		this.hasFlavour = hasFlavour;
		return this;
	}

	public BlockSGBase setMetaLightOpacity(int meta, int opacity)
	{
		if(meta>=0&&meta<this.lightOpacities.length)
			this.lightOpacities[meta]=opacity;
		return this;
	}

	void adjustSound()
	{
		if(this.blockMaterial==Material.anvil)
			this.stepSound = Block.soundTypeAnvil;
		else if(this.blockMaterial==Material.carpet||this.blockMaterial==Material.cloth)
			this.stepSound = Block.soundTypeCloth;
		else if(this.blockMaterial==Material.glass||this.blockMaterial==Material.ice)
			this.stepSound = Block.soundTypeGlass;
		else if(this.blockMaterial==Material.grass||this.blockMaterial==Material.tnt||this.blockMaterial==Material.plants||this.blockMaterial==Material.vine)
			this.stepSound = Block.soundTypeGrass;
		else if(this.blockMaterial==Material.ground)
			this.stepSound = Block.soundTypeGravel;
		else if(this.blockMaterial==Material.iron)
			this.stepSound = Block.soundTypeMetal;
		else if(this.blockMaterial==Material.sand)
			this.stepSound = Block.soundTypeSand;
		else if(this.blockMaterial==Material.snow)
			this.stepSound = Block.soundTypeSnow;
		else if(this.blockMaterial==Material.rock)
			this.stepSound = Block.soundTypeStone;
		else if(this.blockMaterial==Material.wood||this.blockMaterial==Material.cactus)
			this.stepSound = Block.soundTypeWood;
	}

	@Override
    public int getLightOpacity(IBlockAccess world, int x, int y, int z)
    {
		if (!(world instanceof World))
			return 255;
		World w = (World) world;
		if (!w.blockExists(x, y, z))
			return 255;
		int meta = world.getBlockMetadata(x,y,z);
		if(meta>=0&&meta<this.lightOpacities.length)
			return this.lightOpacities[meta];
        return getLightOpacity();
    }

	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for(int i=0; i<subNames.length; i++)
			list.add(new ItemStack(item, 1, i));
	}
	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return isFlammable;
	}
	@Override
	public int getFlammability(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return isFlammable?20:0;
	}
	@Override
	public int getFireSpreadSpeed(IBlockAccess world, int x, int y, int z, ForgeDirection face)
	{
		return isFlammable?5:0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if(meta<icons.length)
			return icons[meta][getSideForTexture(side)];
		return null;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if(meta<icons.length)
			return icons[meta][getSideForTexture(side)];
		return null;
	}
	protected int getSideForTexture(int side)
	{
		if(iconDimensions==2)
			return side==0||side==1?0: 1;
		if(iconDimensions==4)
			return side<2?side: side==2||side==3?2: 3;
		return Math.min(side, iconDimensions-1);
	}


	public abstract boolean allowHammerHarvest(int metadata);
	@Override
	public boolean isToolEffective(String type, int metadata)
	{
		return super.isToolEffective(type, metadata);
	}
	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta)
	{
		return super.canHarvestBlock(player, meta);
	}

	protected MovingObjectPosition doRayTraceOnBox(World world, int x, int y, int z,Vec3 vec0, Vec3 vec1, AxisAlignedBB box)
	{
		Vec3 vecMinX = vec0.getIntermediateWithXValue(vec1, box.minX);
		Vec3 vecMaxX = vec0.getIntermediateWithXValue(vec1, box.maxX);
		Vec3 vecMinY = vec0.getIntermediateWithYValue(vec1, box.minY);
		Vec3 vecMaxY = vec0.getIntermediateWithYValue(vec1, box.maxY);
		Vec3 vecMinZ = vec0.getIntermediateWithZValue(vec1, box.minZ);
		Vec3 vecMaxZ = vec0.getIntermediateWithZValue(vec1, box.maxZ);

		if (!this.isVecInsideYZBounds(world,x,y,z, vecMinX, box))
			vecMinX = null;
		if (!this.isVecInsideYZBounds(world,x,y,z, vecMaxX, box))
			vecMaxX = null;
		if (!this.isVecInsideXZBounds(world,x,y,z, vecMinY, box))
			vecMinY = null;
		if (!this.isVecInsideXZBounds(world,x,y,z, vecMaxY, box))
			vecMaxY = null;
		if (!this.isVecInsideXYBounds(world,x,y,z, vecMinZ, box))
			vecMinZ = null;
		if (!this.isVecInsideXYBounds(world,x,y,z, vecMaxZ, box))
			vecMaxZ = null;

		Vec3 vec38 = null;

		if (vecMinX != null && (vec38 == null || vec0.squareDistanceTo(vecMinX) < vec0.squareDistanceTo(vec38)))
			vec38 = vecMinX;
		if (vecMaxX != null && (vec38 == null || vec0.squareDistanceTo(vecMaxX) < vec0.squareDistanceTo(vec38)))
			vec38 = vecMaxX;
		if (vecMinY != null && (vec38 == null || vec0.squareDistanceTo(vecMinY) < vec0.squareDistanceTo(vec38)))
			vec38 = vecMinY;
		if (vecMaxY != null && (vec38 == null || vec0.squareDistanceTo(vecMaxY) < vec0.squareDistanceTo(vec38)))
			vec38 = vecMaxY;
		if (vecMinZ != null && (vec38 == null || vec0.squareDistanceTo(vecMinZ) < vec0.squareDistanceTo(vec38)))
			vec38 = vecMinZ;
		if (vecMaxZ != null && (vec38 == null || vec0.squareDistanceTo(vecMaxZ) < vec0.squareDistanceTo(vec38)))
			vec38 = vecMaxZ;

		if (vec38 == null)
			return null;
		else
		{
			byte b0 = -1;
			if (vec38 == vecMinX)
				b0 = 4;
			if (vec38 == vecMaxX)
				b0 = 5;
			if (vec38 == vecMinY)
				b0 = 0;
			if (vec38 == vecMaxY)
				b0 = 1;
			if (vec38 == vecMinZ)
				b0 = 2;
			if (vec38 == vecMaxZ)
				b0 = 3;
			return new MovingObjectPosition(x, y, z, b0, vec38.addVector((double)x, (double)y, (double)z));
		}
	}
	protected boolean isVecInsideYZBounds(World world, int x, int y, int z, Vec3 vec, AxisAlignedBB box)
	{
		return vec == null ? false : vec.yCoord>=box.minY && vec.yCoord<=box.maxY && vec.zCoord>=box.minZ && vec.zCoord<=box.maxZ;
	}
	protected boolean isVecInsideXZBounds(World world, int x, int y, int z, Vec3 vec, AxisAlignedBB box)
	{
		return vec == null ? false : vec.xCoord>=box.minX && vec.xCoord<=box.maxX && vec.zCoord>=box.minZ && vec.zCoord<=box.maxZ;
	}
	protected boolean isVecInsideXYBounds(World world, int x, int y, int z, Vec3 vec, AxisAlignedBB box)
	{
		return vec == null ? false : vec.xCoord>=box.minX && vec.xCoord<=box.maxX && vec.yCoord>=box.minY && vec.yCoord<=box.maxY;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void addCollisionBox(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity ent)
	{
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(x+this.minX, y+this.minY, z+this.minZ, x+this.maxX, y+this.maxY, z+this.maxZ);
		if (box != null && aabb.intersectsWith(box))
			list.add(box);
	}


	public static class BlockSGSimple extends BlockSGBase
	{
		public BlockSGSimple(String name, Material mat, Class<? extends ItemBlockSGBase> itemBlock,String... subNames)
		{
			super(name, mat, 1, itemBlock, subNames);
			for(int i=0;i<subNames.length;i++)
				this.setMetaLightOpacity(i, 255);
		}

		@Override
		public void registerBlockIcons(IIconRegister iconRegister)
		{
			for(int i=0;i<subNames.length;i++)
				icons[i][0] = iconRegister.registerIcon("simplygenerators:"+name+"_"+subNames[i]);
		}

		@Override
		public boolean hasTileEntity(int metadata)
		{
			return false;
		}
		@Override
		public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
		{
			return null;
		}
		@Override
		public boolean allowHammerHarvest(int metadata)
		{
			return false;
		}
	}
}