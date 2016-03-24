package common.block;

import client.tileentity.TileTankStorage;
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
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import util.Utils;

public class BlockTankStorage extends BlockContainer{

	IIcon bottom;

	public final int tankMaxStorage;

	public BlockTankStorage(String name, int maxStorage){
		super(Material.iron);
		this.setBlockName(name);
		this.setHardness(10.0F);
		this.setStepSound(soundTypeGlass);
		this.setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);

		this.tankMaxStorage = maxStorage;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta){
		return meta == 0 && side == 3 ? this.blockIcon : side == 1 ? this.blockIcon : (side == 0 ? this.blockIcon : (side == meta ? this.blockIcon : this.blockIcon));
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconregister){
		this.blockIcon = iconregister.registerIcon("simplygenerators:storage.1");
		this.bottom = iconregister.registerIcon("simplygenerators:storage.1_bottom");
	}

	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

	public TileEntity createNewTileEntity(World world, int meta){
		return new TileTankStorage(tankMaxStorage);
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float f1, float f2, float f3){
		TileTankStorage tile = (TileTankStorage)world.getTileEntity(x, y, z);

		if(player.getCurrentEquippedItem() != null){
			if(Utils.hasUsableWrench(player, x, y, z)){
				if((!world.isRemote) && (player.isSneaking())){
					dismantleBlock(world, x, y, z);
					return true;
				}
				world.notifyBlocksOfNeighborChange(x, y, z, this);
			}
		}

        ItemStack itemstack = player.inventory.getCurrentItem();
        if(tile != null){
        	FluidStack fluid = tile.tank.getFluid();
        	if(itemstack == null){
        		String s = "";
        		if(fluid != null && fluid.getFluid() != null){
        			s = "Contain a fluid: " + fluid.getFluid().getLocalizedName(fluid) + tile.tank.getFluidAmount() + " / " + tile.tankMaxStorage;
        		}else{
        			s = "Not contain a fluid.";
        		}
        		if(!world.isRemote)player.addChatMessage(new ChatComponentText(s));
        		return true;
        	}else{
        		FluidStack fluid2 =  FluidContainerRegistry.getFluidForFilledItem(itemstack);
        		if(fluid2 != null && fluid2.getFluid() != null){
        			int put = tile.fill(ForgeDirection.UNKNOWN, fluid2, false);
        			if(put == fluid2.amount){
        				tile.fill(ForgeDirection.UNKNOWN, fluid2, true);

        				ItemStack emptyContainer = FluidContainerRegistry.drainFluidContainer(itemstack);
        				if(emptyContainer != null){
        					if(!player.inventory.addItemStackToInventory(emptyContainer.copy())){
        						player.entityDropItem(emptyContainer.copy(), 1);
        					}
        				}

        				if(!player.capabilities.isCreativeMode && itemstack.stackSize-- <= 0){
        					player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
        				}
        				tile.markDirty();
        				player.inventory.markDirty();
        				world.markBlockForUpdate(x, y, z);
        				world.playSoundAtEntity(player, "random.pop", 0.4F, 1.8F);
        				return true;
        			}
        		}else{
        			if(fluid != null && fluid.getFluid() != null){
        				if(fluid.amount < 1000)return true;
        				ItemStack get = FluidContainerRegistry.fillFluidContainer(new FluidStack(fluid.getFluid(), 1000), itemstack);
        				if(get != null){
        					tile.drain(ForgeDirection.UNKNOWN, 1000, true);
        					if(!player.inventory.addItemStackToInventory(get.copy())){
        						player.entityDropItem(get.copy(), 1);
        					}
        					if(!player.capabilities.isCreativeMode && itemstack.stackSize-- <= 0){
        						player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
        					}
        					tile.markDirty();
        					player.inventory.markDirty();
        					world.markBlockForUpdate(x, y, z);
        					world.playSoundAtEntity(player, "random.pop", 0.4F, 1.8F);
        				}
        				return true;
        			}else{
        				return true;
        			}
        		}
        	}
        }
		return false;
	}

	public void dismantleBlock(World world, int x, int y, int z)
	  {
	    ItemStack itemStack = new ItemStack(this);
	    float motion = 0.7F;
	    double motionX = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    double motionY = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    double motionZ = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    EntityItem entityItem = new EntityItem(world, x + motionX, y + motionY, z + motionZ, itemStack);
	    TileTankStorage tileEntity = (TileTankStorage)world.getTileEntity(x, y, z);

	    if(tileEntity.tank.getFluidAmount() > 0){
	    	NBTTagCompound tankTag = tileEntity.getItemNBT();
			NBTTagCompound itemTag = Utils.getItemTag(itemStack);
	    	itemTag.setTag("tank", tankTag);
	    }

	    world.setBlockToAir(x, y, z);
	    world.spawnEntityInWorld(entityItem);
	  }

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack){
		if(itemstack.stackTagCompound != null)
	      {
	        TileTankStorage tileEntity = (TileTankStorage)world.getTileEntity(x, y, z);

	        NBTTagCompound itemTag = itemstack.getTagCompound();

			if(itemTag != null && itemTag.hasKey("tank")){
				tileEntity.tank.readFromNBT(itemTag.getCompoundTag("tank"));
			}
	      }
	}

	public static class Reinforced extends BlockTankStorage{

		public Reinforced(String name, int maxStorage){
			super(name, maxStorage);
		}

		public TileEntity createNewTileEntity(World world, int meta){
			return new TileTankStorage.Reinforced(this.tankMaxStorage);
		}

		public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityplayer, ItemStack itemstack){
			if(itemstack.stackTagCompound != null)
		      {
		        TileTankStorage.Reinforced tileEntity = (TileTankStorage.Reinforced)world.getTileEntity(x, y, z);

		        NBTTagCompound itemTag = itemstack.getTagCompound();

				if(itemTag != null && itemTag.hasKey("tank")){
					tileEntity.tank.readFromNBT(itemTag.getCompoundTag("tank"));
				}
		      }
		}
	}
}
