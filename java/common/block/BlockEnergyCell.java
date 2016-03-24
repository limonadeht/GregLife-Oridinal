package common.block;

import client.tileentity.TileEnergyCell;
import common.Contents;
import common.SimplyGenerators;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import util.Utils;

public class BlockEnergyCell extends BlockContainer{

	public final int energyTransfer;
	public final int energyCapacity;

	@SideOnly(Side.CLIENT)
	private IIcon side;

	public BlockEnergyCell(String name, int energyCapacity){
		super(Material.iron);
		this.setBlockName(name);
		this.setHardness(100.0F);
		this.setStepSound(soundTypeMetal);
		this.setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);

		this.energyCapacity = energyCapacity;
		this.energyTransfer = (energyCapacity * 2);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta){
		return side == 1 ? this.blockIcon : this.side;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister){
		this.side = iconRegister.registerIcon("simplygenerators:energy_cell_port");
		this.blockIcon = iconRegister.registerIcon("simplygenerators:base_t1");
	}

	public int getEnergyTransfer(){
		return this.energyTransfer;
	}

	public int getEnergyCapacity(){
		return this.energyCapacity;
	}

	public int getEnergyStored(ItemStack itemStack){
	    if (itemStack.stackTagCompound != null) {
	      return itemStack.stackTagCompound.getInteger("Energy");
	    }
	    return 0;
	}

	public TileEntity createNewTileEntity(World world, int meta){
		return new TileEnergyCell(this.energyCapacity, energyTransfer);
	}

	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventNumber, int eventArgument){
	    super.onBlockEventReceived(world, x, y, z, eventNumber, eventArgument);

	    TileEntity tileentity = world.getTileEntity(x, y, z);
	    if (tileentity != null) {
	      return tileentity.receiveClientEvent(eventNumber, eventArgument);
	    }
	    return false;
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float f1, float f2, float f3){

		TileEnergyCell tile = (TileEnergyCell)world.getTileEntity(x, y, z);

		if(!world.isRemote){
			player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GRAY + "Debug: " + tile.getEnergyStored() + " / " + tile.getMaxEnergyStored()));
		}

		if(!player.isSneaking()){
			player.openGui(SimplyGenerators.Instance, 5, world, x, y, z);
		}

		if(player.getCurrentEquippedItem() != null){
			if(Utils.hasUsableWrench(player, x, y, z)){
		        if ((!world.isRemote) && (player.isSneaking())){
		          dismantleBlock(world, x, y, z);
		          return true;
		        }
		        world.notifyBlocksOfNeighborChange(x, y, z, this);
		      }
		    }

		    tile.markDirty();
		    player.inventory.markDirty();
		    world.markBlockForUpdate(x, y, z);
		    return true;
	}

	public void dismantleBlock(World world, int x, int y, int z){
	    ItemStack itemStack = new ItemStack(this);
	    float motion = 0.7F;
	    double motionX = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    double motionY = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    double motionZ = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    EntityItem entityItem = new EntityItem(world, x + motionX, y + motionY, z + motionZ, itemStack);
	    TileEnergyCell tileEntity = (TileEnergyCell)world.getTileEntity(x, y, z);
	    int energyStored = tileEntity.getEnergyStored();
	    if (energyStored >= 1)
	    {
	      if (itemStack.getTagCompound() == null) {
	        itemStack.setTagCompound(new NBTTagCompound());
	      }
	      itemStack.getTagCompound().setInteger("Energy", energyStored);
	    }

	    world.setBlockToAir(x, y, z);
	    world.spawnEntityInWorld(entityItem);
	  }

	@SuppressWarnings("unused")
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack){
		if(itemstack.stackTagCompound != null)
	      {
	        TileEnergyCell tileEntity = (TileEnergyCell)world.getTileEntity(x, y, z);

	        tileEntity.setEnergyStored(itemstack.stackTagCompound.getInteger("Energy"));

	        NBTTagCompound itemTag = itemstack.getTagCompound();
	      }
	}

	public static void updateFurnaceBlockState(boolean required, World world, int x, int y, int z)
    {
        int l = world.getBlockMetadata(x, y, z);
        TileEntity tileentity = world.getTileEntity(x, y, z);

        if (required){
        	if(world.getBlock(x, y, z) == Contents.energyCell_t1){
        		world.setBlock(x, y, z, Contents.energyCell_t1);
        	}
        	if(world.getBlock(x, y, z) == Contents.energyCell_t2){
        		world.setBlock(x, y, z, Contents.energyCell_t2);
        	}
        	if(world.getBlock(x, y, z) == Contents.energyCell_t3){
        		world.setBlock(x, y, z, Contents.energyCell_t3);
        	}
        	if(world.getBlock(x, y, z) == Contents.energyCell_t4){
        		world.setBlock(x, y, z, Contents.energyCell_t4);
        	}
        }else{
        	if(world.getBlock(x, y, z) == Contents.energyCell_t1){
        		world.setBlock(x, y, z, Contents.energyCell_t1);
        	}
        	if(world.getBlock(x, y, z) == Contents.energyCell_t2){
        		world.setBlock(x, y, z, Contents.energyCell_t2);
        	}
        	if(world.getBlock(x, y, z) == Contents.energyCell_t3){
        		world.setBlock(x, y, z, Contents.energyCell_t3);
        	}
        	if(world.getBlock(x, y, z) == Contents.energyCell_t4){
        		world.setBlock(x, y, z, Contents.energyCell_t4);
        	}
        }
        world.setBlockMetadataWithNotify(x, y, z, l, 2);

        if (tileentity != null){
            tileentity.validate();
            world.setTileEntity(x, y, z, tileentity);
        }
    }

	public static class T2 extends BlockEnergyCell{

		public T2(String name, int energyCapacity){
			super(name, energyCapacity);
		}

		public TileEntity createNewTileEntity(World world, int meta){
			return new TileEnergyCell.T2(energyCapacity, energyTransfer);
		}
	}

	public static class T3 extends BlockEnergyCell{

		public T3(String name, int energyCapacity){
			super(name, energyCapacity);
		}

		public TileEntity createNewTileEntity(World world, int meta){
			return new TileEnergyCell.T3(energyCapacity, energyTransfer);
		}
	}

	public static class T4 extends BlockEnergyCell{

		public T4(String name, int energyCapacity){
			super(name, energyCapacity);
		}

		public TileEntity createNewTileEntity(World world, int meta){
			return new TileEnergyCell.T4(energyCapacity, energyTransfer);
		}
	}
}
