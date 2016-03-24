package client.tileentity;

import cofh.api.energy.IEnergyHandler;
import common.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileOreCreator extends TileEntityBase implements IEnergyHandler, ISidedInventory{

	private EnergyStorage energyStorage;
	private int energyCapacity;

	private static final int[] slots_ore = new int[]{0};
	private ItemStack[] itemStacks = new ItemStack[1];

	public boolean isCoalOreMode = true;
	public boolean isIronOreMode = false;
	public boolean isGoldOreMode = false;
	public boolean isRedstoneOreMode = false;
	public boolean isLapisOreMode = false;
	public boolean isDiamondOreMode = false;

	public TileOreCreator(int energyCapacity, int energyTransfer){
		this.energyCapacity = energyCapacity;
		this.energyStorage = new EnergyStorage(energyCapacity, energyTransfer);
	}

	@Override
	public void updateEntity(){
		if(this.worldObj.isRemote) return;
		//this.createOre();

		this.markDirty();
	}

	public String getMode(int i){
		int e = this.getEnergyStored();
		if(e > 100000 && this.isCoalOreMode == true && i == 1){
			return "COAL";
		}

		if(e > 500000 && this.isIronOreMode == true && i == 2){
			return "IRON";
		}

		if(e > 1000000 && this.isGoldOreMode == true && i == 3){
			return "GOLD";
		}

		if(e > 1500000 && this.isRedstoneOreMode == true && i == 4){
			return "REDSTONE";
		}

		if(e > 2000000 && this.isLapisOreMode == true && i == 5){
			return "LAPIS";
		}

		if(e > 3000000 && this.isDiamondOreMode == true && i == 6){
			return "DIAMOND";
		}
		return "NONE";
	}

	protected void createOre(){
		int e = this.getEnergyStored();
		if(this.getMode(1) == "COAL"){
			this.setEnergyStored(e - 100000);
			this.setInventorySlotContents(1, new ItemStack(Item.getItemFromBlock(Blocks.coal_ore)));
		}
		if(this.getMode(2) == "IRON"){
			this.setEnergyStored(e - 500000);
			this.setInventorySlotContents(1, new ItemStack(Item.getItemFromBlock(Blocks.iron_ore)));
		}
		if(this.getMode(3) == "GOLD"){
			this.setEnergyStored(e - 1000000);
			this.setInventorySlotContents(1, new ItemStack(Item.getItemFromBlock(Blocks.gold_ore)));
		}
		if(this.getMode(4) == "REDSTONE"){
			this.setEnergyStored(e - 1500000);
			this.setInventorySlotContents(1, new ItemStack(Item.getItemFromBlock(Blocks.redstone_ore)));
		}
		if(this.getMode(5) == "LAPIS"){
			this.setEnergyStored(e - 2000000);
			this.setInventorySlotContents(1, new ItemStack(Item.getItemFromBlock(Blocks.lapis_ore)));
		}
		if(this.getMode(6) == "DIAMOND"){
			this.setEnergyStored(e - 3000000);
			this.setInventorySlotContents(1, new ItemStack(Item.getItemFromBlock(Blocks.diamond_ore)));
		}
	}

	@Override
	public void readCustomNBT(NBTTagCompound nbt, boolean descPacket){
		this.energyStorage.readFromNBT(nbt);

		NBTTagCompound[] tag = new NBTTagCompound[itemStacks.length];
		for(int i = 0; i < itemStacks.length; i++){
			tag[i] = nbt.getCompoundTag("Item" + i);
			itemStacks[i] = ItemStack.loadItemStackFromNBT(tag[i]);
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket){
		this.energyStorage.writeToNBT(nbt);

		NBTTagCompound[] tag = new NBTTagCompound[itemStacks.length];
		for(int i = 0; i < itemStacks.length; i++){
			tag[i] = new NBTTagCompound();
			if(this.itemStacks[i] != null){
				tag[i] = itemStacks[i].writeToNBT(tag[i]);
			}
			nbt.setTag("Item" + i, tag[i]);
		}
	}

	public boolean canConnectEnergy(ForgeDirection from){
		return true;
	}

	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
		return this.energyStorage.receiveEnergy(maxReceive, simulate);
	}

	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
		return 0;
	}

	public int getEnergyStored(ForgeDirection from){
		return this.energyStorage.getEnergyStored();
	}

	public int getMaxEnergyStored(ForgeDirection from){
		return this.energyStorage.getMaxEnergyStored();
	}

	public void setEnergyStored(int energyStored){
		this.energyStorage.setEnergyStored(energyStored);
	}

	public int getEnergyStored(){
		return getEnergyStored(ForgeDirection.DOWN);
	}

	public int getMaxEnergyStored(){
		return getMaxEnergyStored(ForgeDirection.DOWN);
	}

	public NBTTagCompound getItemNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		return nbt;
	}

	public static int getItemBurnTime(ItemStack itemstack){
		if(itemstack == null){
			return 0;
		}else{
			Item item = itemstack.getItem();
			if(item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.air){
				Block block = Block.getBlockFromItem(item);
				if(block == Blocks.wooden_slab){
					return 150;
				}
				if(block.getMaterial() == Material.wood){
					return 300;
				}
				if(block == Blocks.coal_block){
					return 16000;
				}
			}
            return GameRegistry.getFuelValue(itemstack);
		}
	}

	/*INVENTORY*/

	@SuppressWarnings("unused")
	private void detectAndSentChanges(boolean sendAnyway){}

	@SuppressWarnings("static-access")
	@Override
	public int getSizeInventory(){
		return this.slots_ore.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1){
		return this.itemStacks[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2){
		if (this.itemStacks[par1] != null)
		{
			ItemStack itemstack;

			if (this.itemStacks[par1].stackSize <= par2)
			{
				itemstack = this.itemStacks[par1];
				this.itemStacks[par1] = null;
				return itemstack;
			}
			else
			{
				itemstack = this.itemStacks[par1].splitStack(par2);

				if (this.itemStacks[par1].stackSize == 0)
				{
					this.itemStacks[par1] = null;
				}

				return itemstack;
			}
		}
		else
		{
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i){
		ItemStack item = this.getStackInSlot(i);
		if(item != null){
			this.setInventorySlotContents(i, null);
		}
		return item;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack){
		this.itemStacks[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()){
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public String getInventoryName(){
		return "container.oreCreator";
	}

	@Override
	public boolean hasCustomInventoryName(){
		return false;
	}

	@Override
	public int getInventoryStackLimit(){
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player){
		if(this.worldObj == null){
			return true;
		}
		if(this.worldObj.getTileEntity(xCoord, yCoord, zCoord) != this){
			return false;
		}
		return player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.4) < 64;
	}

	@Override
	public void openInventory(){}

	@Override
	public void closeInventory(){}

	//スロットに入れられるアイテム?
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack){
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int par1){
		return new int[1];
	}

	@Override
	public boolean canInsertItem(int par1, ItemStack par2, int par3){
		return true;
	}

	@Override
	public boolean canExtractItem(int par1, ItemStack par2, int par3){
		return getItemBurnTime(par2) == 0;
	}
}
