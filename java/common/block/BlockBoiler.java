package common.block;

import client.tileentity.TileBoiler;
import common.SimplyGenerators;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import util.Utils;

public class BlockBoiler extends BlockContainer{

	public final int energyTransfer;
	public final int energyCapacity;

	public int maxTankStorage;

	@SuppressWarnings("unused")
	private static boolean keepInventory;

	public BlockBoiler(String name, int energyCapacity, int maxTankStorage){
		super(Material.iron);
		this.setBlockName(name);
		this.setHardness(100.0F);
		this.setStepSound(soundTypeMetal);
		this.setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);

		this.energyCapacity = energyCapacity;
		this.energyTransfer = (energyCapacity * 4);

		this.maxTankStorage = maxTankStorage;
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

	public int getFluidStored(ItemStack itemstack){
		if(itemstack.stackTagCompound != null){
			return itemstack.stackTagCompound.getInteger("waterTank");
		}
		if(itemstack.stackTagCompound != null){
			return itemstack.stackTagCompound.getInteger("steamTank");
		}
		return 0;
	}

	public TileEntity createNewTileEntity(World world, int meta){
		return new TileBoiler(this.energyCapacity, this.maxTankStorage);
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

		TileBoiler tile = (TileBoiler)world.getTileEntity(x, y, z);

		if(!world.isRemote){
			player.addChatComponentMessage(new ChatComponentText("WATER_TANK: " + tile.waterTank.getFluidAmount() + " mb"));
			player.addChatComponentMessage(new ChatComponentText("STEAM_TANK: " + tile.steamTank.getFluidAmount() + " mb"));
		}

		if(!player.isSneaking()){
			FMLNetworkHandler.openGui(player, SimplyGenerators.Instance, 4, world, x, y, z);
			player.inventory.markDirty();
			tile.markDirty();
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
	    TileBoiler tileEntity = (TileBoiler)world.getTileEntity(x, y, z);
	    int energyStored = tileEntity.getEnergyStored();
	    if (energyStored >= 1)
	    {
	      if (itemStack.getTagCompound() == null) {
	        itemStack.setTagCompound(new NBTTagCompound());
	      }
	      itemStack.getTagCompound().setInteger("Energy", energyStored);
	    }

	    if(tileEntity.waterTank.getFluidAmount() > 0){
	    	NBTTagCompound tankTag = tileEntity.getItemNBT();
			NBTTagCompound itemTag = Utils.getItemTag(itemStack);
	    	itemTag.setTag("waterTank", tankTag);
	    }
	    if(tileEntity.steamTank.getFluidAmount() > 0){
	    	NBTTagCompound tankTag1 = tileEntity.getItemNBT();
			NBTTagCompound itemTag1 = Utils.getItemTag(itemStack);
	    	itemTag1.setTag("steamTank", tankTag1);
	    }

	    world.setBlockToAir(x, y, z);
	    world.spawnEntityInWorld(entityItem);
	  }

	@SuppressWarnings("unused")
	private void setDefaultDirection(World world, int x, int y, int z) {
		if(!world.isRemote){
			Block b1 = world.getBlock(x, y, z - 1);
			Block b2 = world.getBlock(x, y, z + 1);
			Block b3 = world.getBlock(x - 1, y, z);
			Block b4 = world.getBlock(x + 1, y, z);

			byte b0=3;

			if(b1.func_149730_j()&&!b2.func_149730_j()){
				b0=3;
			}
			if(b2.func_149730_j()&&!b1.func_149730_j()){
				b0=2;
			}

			if(b3.func_149730_j()&&!b4.func_149730_j()){
				b0=5;
			}

			if(b4.func_149730_j()&&!b3.func_149730_j()){
				b0=4;
			}

			world.setBlockMetadataWithNotify(x, y, z, b0,2);

		}
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack){
		int l = MathHelper.floor_double((double)(entityplayer.rotationYaw*4.0f/360.F)+0.5D)&3;

		if(l==0){
			world.setBlockMetadataWithNotify(x,y, z, 2, 2);
		}
		if(l==1){
			world.setBlockMetadataWithNotify(x,y, z, 5, 2);
		}
		if(l==2){
			world.setBlockMetadataWithNotify(x,y, z, 3, 2);
		}
		if(l==3){
			world.setBlockMetadataWithNotify(x,y, z, 4, 2);
		}

		if(itemstack.stackTagCompound != null)
	      {
	        TileBoiler tileEntity = (TileBoiler)world.getTileEntity(x, y, z);

	        tileEntity.setEnergyStored(itemstack.stackTagCompound.getInteger("Energy"));

	        NBTTagCompound itemTag = itemstack.getTagCompound();

			if(itemTag != null && itemTag.hasKey("waterTank")){
				tileEntity.waterTank.readFromNBT(itemTag.getCompoundTag("waterTank"));
			}
			if(itemTag != null && itemTag.hasKey("steamTank")){
				tileEntity.steamTank.readFromNBT(itemTag.getCompoundTag("steamTank"));
			}
	      }
	}
}
