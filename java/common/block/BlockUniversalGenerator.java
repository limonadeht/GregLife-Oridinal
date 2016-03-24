package common.block;

import java.util.Random;

import client.tileentity.TileUniversalGenerator;
import common.Contents;
import common.SimplyGenerators;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import util.Utils;

public class BlockUniversalGenerator extends BlockContainer{

	IIcon front, top, bottom;

	public final int energyGeneration;
	public final int energyTransfer;
	public final int energyCapacity;

	public int recipeGuiOpend;

	public final boolean isActive;

	@SuppressWarnings("unused")
	private static boolean keepInventory;
	@SuppressWarnings("unused")
	private Random rand = new Random();

	public BlockUniversalGenerator(boolean isActive, String name, Material material, int energyGeneration){
		super(material);
		this.setBlockName(name);
		this.setHardness(100.0F);
		this.setStepSound(soundTypeMetal);
		this.setCreativeTab(SimplyGenerators.SimplyGeneratorsTab);

		this.energyGeneration = energyGeneration;
		this.energyTransfer = (energyGeneration * 5);
		this.energyCapacity = (energyGeneration * 2000);

		this.isActive = isActive;
	}

	public boolean isOpaqueCube(){
		return false;
	}

	public boolean renderAsNormalBlock(){
		 return false;
	}

	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side){
        return false;
    }

	public int getEnergyGeneration(){
		return this.energyGeneration;
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
			return itemstack.stackTagCompound.getInteger("productTank");
		}
		return 0;
	}

	public TileEntity createNewTileEntity(World world, int meta){
		return new TileUniversalGenerator(this.energyGeneration, this.energyTransfer, this.energyCapacity);
	}

	public boolean onBlockEventReceived(World world, int x, int y, int z, int eventNumber, int eventArgument){
	    super.onBlockEventReceived(world, x, y, z, eventNumber, eventArgument);

	    TileEntity tileentity = world.getTileEntity(x, y, z);
	    if (tileentity != null) {
	      return tileentity.receiveClientEvent(eventNumber, eventArgument);
	    }
	    return false;
	}


	public ItemStack itemstack;

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float f1, float f2, float f3){

		TileUniversalGenerator tile = (TileUniversalGenerator)world.getTileEntity(x, y, z);

		if(!player.isSneaking()){
			FMLNetworkHandler.openGui(player, SimplyGenerators.Instance, 1, world, x, y, z);
		}

		if(player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() == Items.flint_and_steel){
			tile.burnTimeRemaining += 1000;
			if(world.isRemote)player.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Fire"));
			player.inventory.getCurrentItem().damageItem(30, player);
			return false;
		}

		if(player.getCurrentEquippedItem() != null){
			if(Utils.hasUsableWrench(player, x, y, z))
		      {
				/*if(tile.productTank.getFluidAmount() > 0 && tile.productTank.getFluidType() == MaterialRegister.fluidBioDiesel){
					tile.productTank.setAmount(0);
				}else{
					return false;
				}*/
		        if ((!world.isRemote) && (player.isSneaking()))
		        {
		          dismantleBlock(world, x, y, z);

		          return true;
		        }
		        world.notifyBlocksOfNeighborChange(x, y, z, this);
		      }
		    }
		if(player.isSneaking()){
			FluidStack fluid = tile.tank.getFluid();
    		String s = "";
    		String s2 = "";
    		if(fluid != null && fluid.getFluid() != null){
    			s = EnumChatFormatting.GREEN + "Fluid Storage: " + fluid.getFluid().getLocalizedName(fluid) + " " + fluid.amount + " mB";
    			s2 = EnumChatFormatting.GREEN + "Energy Storage: " + tile.getEnergyStored() + " / " + tile.getMaxEnergyStored();
    		}else{
    			s = EnumChatFormatting.GREEN + "Not contain a fluids.";
    			s2 = EnumChatFormatting.GREEN + "Energy Storage: " + tile.getEnergyStored() + " / " + tile.getMaxEnergyStored();
    		}
    		if (!world.isRemote) player.addChatMessage(new ChatComponentText(s));
    		if (!world.isRemote) player.addChatMessage(new ChatComponentText(s2));
		}

		    	tile.markDirty();
		        player.inventory.markDirty();
		        world.markBlockForUpdate(x, y, z);

		        return true;
	}

	public void dismantleBlock(World world, int x, int y, int z)
	  {
	    ItemStack itemStack = new ItemStack(this);
	    float motion = 0.7F;
	    double motionX = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    double motionY = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    double motionZ = world.rand.nextFloat() * motion + (1.0F - motion) * 0.5D;
	    EntityItem entityItem = new EntityItem(world, x + motionX, y + motionY, z + motionZ, itemStack);
	    TileUniversalGenerator tileEntity = (TileUniversalGenerator)world.getTileEntity(x, y, z);
	    int energyStored = tileEntity.getEnergyStored();
	    if (energyStored >= 1)
	    {
	      if (itemStack.getTagCompound() == null) {
	        itemStack.setTagCompound(new NBTTagCompound());
	      }
	      itemStack.getTagCompound().setInteger("Energy", energyStored);
	    }
	    if(tileEntity.tank.getFluidAmount() > 0){
	    	NBTTagCompound tankTag = tileEntity.getItemNBT();
			NBTTagCompound itemTag = Utils.getItemTag(itemStack);
	    	itemTag.setTag("tank", tankTag);
	    }

	    /*NBTTagCompound itemTag1 = tileEntity.getItemNBT();
	    NBTTagCompound itemTag2 = Utils.getItemTag(itemStack);
	    itemTag2.setTag("Item", itemTag1);*/

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
		TileUniversalGenerator tile = (TileUniversalGenerator)world.getTileEntity(x, y, z);
		tile.direction = MathHelper.floor_double((double)(entityplayer.rotationYaw*4.0f/360.F)+0.5D)&3;

		if(tile.direction==0){
			world.setBlockMetadataWithNotify(x,y, z, 2, 2);
		}
		if(tile.direction==1){
			world.setBlockMetadataWithNotify(x,y, z, 5, 2);
		}
		if(tile.direction==2){
			world.setBlockMetadataWithNotify(x,y, z, 3, 2);
		}
		if(tile.direction==3){
			world.setBlockMetadataWithNotify(x,y, z, 4, 2);
		}

		if(itemstack.stackTagCompound != null)
	      {
	        TileUniversalGenerator tileEntity = (TileUniversalGenerator)world.getTileEntity(x, y, z);

	        tileEntity.setEnergyStored(itemstack.stackTagCompound.getInteger("Energy"));

	        NBTTagCompound itemTag = itemstack.getTagCompound();

			if(itemTag != null && itemTag.hasKey("tank")){
				tileEntity.tank.readFromNBT(itemTag.getCompoundTag("tank"));
			}
	      }
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random){
		TileUniversalGenerator tile = (TileUniversalGenerator)world.getTileEntity(x, y, z);
		if(tile.burnTimeRemaining > 0){
			int direction = world.getBlockMetadata(x, y, z);

			float x1 = (float)x + 0.5F;
			float y1 = (float)y + random.nextFloat();
			float z1 = (float)z + 0.5F;

			float f = 0.52F;
			float f1 = random.nextFloat() * 0.6F - 0.3F;

			if(direction == 4){
				world.spawnParticle("smoke", (double)(x1 - f), (double)(y1), (double)(z1 + f1), 0D, 0D, 0D);
				world.spawnParticle("flame", (double)(x1 - f), (double)(y1), (double)(z1 + f1), 0D, 0D, 0D);
			}

			if(direction == 5){
				world.spawnParticle("smoke", (double)(x1 + f), (double)(y1), (double)(z1 + f1), 0D, 0D, 0D);
				world.spawnParticle("flame", (double)(x1 + f), (double)(y1), (double)(z1 + f1), 0D, 0D, 0D);
			}

			if(direction == 2){
				world.spawnParticle("smoke", (double)(x1 + f1), (double)(y1), (double)(z1 - f), 0D, 0D, 0D);
				world.spawnParticle("flame", (double)(x1 + f1), (double)(y1), (double)(z1 - f), 0D, 0D, 0D);
			}

			if(direction == 3){
				world.spawnParticle("smoke", (double)(x1 + f1), (double)(y1), (double)(z1 + f), 0D, 0D, 0D);
				world.spawnParticle("flame", (double)(x1 + f1), (double)(y1), (double)(z1 + f), 0D, 0D, 0D);
			}
		}
	}

	public static void updateFurnaceBlockState(boolean p_149931_0_, World p_149931_1_, int p_149931_2_, int p_149931_3_, int p_149931_4_)
    {
        int l = p_149931_1_.getBlockMetadata(p_149931_2_, p_149931_3_, p_149931_4_);
        TileEntity tileentity = p_149931_1_.getTileEntity(p_149931_2_, p_149931_3_, p_149931_4_);

        if (p_149931_0_)
        {
            p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, Contents.universalGenerator);
        }
        else
        {
            p_149931_1_.setBlock(p_149931_2_, p_149931_3_, p_149931_4_, Contents.universalGenerator);
        }

        p_149931_1_.setBlockMetadataWithNotify(p_149931_2_, p_149931_3_, p_149931_4_, l, 2);

        if (tileentity != null)
        {
            tileentity.validate();
            p_149931_1_.setTileEntity(p_149931_2_, p_149931_3_, p_149931_4_, tileentity);
        }
    }

	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer){
	    return true;
	}

	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer){
	    return true;
	}
}
