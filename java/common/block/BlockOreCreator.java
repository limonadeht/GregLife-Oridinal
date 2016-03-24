package common.block;

import client.tileentity.TileOreCreator;
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
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import util.Utils;

public class BlockOreCreator extends BlockContainer{

	IIcon front, top;

	public final int energyTransfer;
	public final int energyCapacity;

	public BlockOreCreator(String name, int energyCapacity){
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
		return meta == 0 && side == 3 ? this.front : side == 1 ? this.top : (side == 0 ? this.top : (side == meta ? this.front : this.blockIcon));
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister){
		this.top = iconRegister.registerIcon("simplygenerators:oreCreator_port");
		this.front = iconRegister.registerIcon("simplygenerators:oreCreator_front");
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
		return new TileOreCreator(this.energyCapacity, this.energyTransfer);
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

		TileOreCreator tile = (TileOreCreator)world.getTileEntity(x, y, z);

		if(!player.isSneaking()){
			player.openGui(SimplyGenerators.Instance, 6, world, x, y, z);
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
	    TileOreCreator tileEntity = (TileOreCreator)world.getTileEntity(x, y, z);
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

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack){
		if(itemstack.stackTagCompound != null)
	      {
	        TileOreCreator tileEntity = (TileOreCreator)world.getTileEntity(x, y, z);

	        tileEntity.setEnergyStored(itemstack.stackTagCompound.getInteger("Energy"));

	        NBTTagCompound itemTag = itemstack.getTagCompound();
	      }
	}
}
