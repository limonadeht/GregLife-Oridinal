package client.tileentity;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import common.block.BlockUniversalGenerator;
import common.energy.EnergyStorage;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import util.TankType;

public class TileUniversalGenerator extends TileEntityBase implements IEnergyHandler, IFluidHandler, ISidedInventory{

	public int direction;

	private EnergyStorage energyStorage;
	private int energyGeneration;

	public TankType tank = new TankType(16000);

	private static final int[] slots_fuel = new int[]{0};
	private ItemStack[] itemStacks = new ItemStack[1];
	public int burnTime = 1;
	public int burnTimeRemaining = 0;
	private int burnSpeed = 6;
	public boolean isBurning = false;
	public boolean isBurningCach = false;
	private String localizedName;

	@SuppressWarnings("unused")
	private int tick;
	private int EPBT = 14;

	public TileUniversalGenerator(int energyGeneration, int energyTransfer, int energyCapacity){
		this.energyGeneration = energyGeneration;
		this.energyStorage = new EnergyStorage(energyCapacity, energyTransfer);
	}

	@SideOnly(Side.CLIENT)
	public IIcon getFluidIcon(){
		Fluid fluid = this.tank.getFluidType();
    	return fluid != null ? fluid.getIcon() : null;
    }

	@Override
	public void updateEntity(){
		if(this.worldObj.isRemote) return;

		this.isBurning = this.burnTimeRemaining > 0 && this.energyStorage.getEnergyStored() < this.energyStorage.getMaxEnergyStored();
		if(this.burnTimeRemaining > 0 && this.energyStorage.getEnergyStored() < this.energyStorage.getMaxEnergyStored()){
			this.burnTimeRemaining -= this.burnSpeed;
			this.energyStorage.setEnergyStored(this.energyStorage.getEnergyStored() + Math.min(this.burnSpeed * EPBT, this.energyStorage.getMaxEnergyStored() - this.energyStorage.getEnergyStored()));
		}else if(this.burnTimeRemaining <= 0) this.tryRefuel();
		if((this.energyStorage.getEnergyStored() > 0)){
			for(int i = 0; i < 6; i++){
				TileEntity tile = worldObj.getTileEntity(xCoord + ForgeDirection.getOrientation(i).offsetX, yCoord + ForgeDirection.getOrientation(i).offsetY, zCoord + ForgeDirection.getOrientation(i).offsetZ);
				if(tile != null && tile instanceof IEnergyReceiver){
					this.energyStorage.extractEnergy(((IEnergyReceiver)tile).receiveEnergy(ForgeDirection.getOrientation(i).getOpposite(), this.energyStorage.extractEnergy(this.energyStorage.getMaxExtract(), true), false), false);
				}
			}
		}
		this.tick++;

		if(this.burnTime > 0){
			BlockUniversalGenerator.updateFurnaceBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
		}
	}

	@Override
	public void readCustomNBT(NBTTagCompound nbt, boolean descPacket){
		this.energyGeneration = nbt.getInteger("EnergyGeneration");
		this.energyStorage.readFromNBT(nbt);

		this.tank = new TankType(16000);
		if(nbt.hasKey("tank")){
		    this.tank.readFromNBT(nbt.getCompoundTag("tank"));
		}

		NBTTagCompound[] tag = new NBTTagCompound[itemStacks.length];
		for(int i = 0; i < itemStacks.length; i++){
			tag[i] = nbt.getCompoundTag("Item" + i);
			itemStacks[i] = ItemStack.loadItemStackFromNBT(tag[i]);
		}
		this.burnTime = nbt.getInteger("BurnTime");
		this.burnTimeRemaining = nbt.getInteger("BurnTimeRemaining");
	}

	@Override
	public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket){
		nbt.setInteger("EnergyGeneration", this.energyGeneration);
		this.energyStorage.writeToNBT(nbt);

		NBTTagCompound tank = new NBTTagCompound();
		this.tank.writeToNBT(tank);
		nbt.setTag("tank", tank);

		NBTTagCompound[] tag = new NBTTagCompound[itemStacks.length];
		for(int i = 0; i < itemStacks.length; i++){
			tag[i] = new NBTTagCompound();
			if(this.itemStacks[i] != null){
				tag[i] = itemStacks[i].writeToNBT(tag[i]);
			}
			nbt.setTag("Item" + i, tag[i]);
		}
		nbt.setInteger("BurnTime", this.burnTime);
		nbt.setInteger("BurnTimeRemaining", this.burnTimeRemaining);
	}

	public static TankType loadTank(NBTTagCompound nbtRoot) {
	    int tankType = nbtRoot.getInteger("tankType");
	    tankType = MathHelper.clamp_int(tankType, 0, 1);
	    TankType ret;

	    ret = new TankType(8000);

	    if(nbtRoot.hasKey("tank")) {
	        FluidStack fl = FluidStack.loadFluidStackFromNBT((NBTTagCompound) nbtRoot.getTag("tank"));
	        ret.setFluid(fl);
	      }else{
	        ret.setFluid(null);
	      }
	      return ret;
	}

	public boolean canConnectEnergy(ForgeDirection from){
		return true;
	}

	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
		return 0;
	}

	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
		return this.energyStorage.extractEnergy(this.energyStorage.getMaxExtract(), simulate);
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

	public int getMaxGenerateRf(){
		return this.energyGeneration;
	}

	public NBTTagCompound getItemNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		tank.writeToNBT(nbt);
		return nbt;
	}

	public IFluidTank getTank() {
		return tank;
	}

	public void onBlockPlacedBy(EntityLivingBase placer, ItemStack stack) {
		NBTTagCompound itemTag = stack.getTagCompound();

		if (itemTag != null && itemTag.hasKey("tank")) {
			tank.readFromNBT(itemTag.getCompoundTag("tank"));
		}
	}

	/*IFluidHandler*/

	public static int getTankCapacity() {
		return FluidContainerRegistry.BUCKET_VOLUME * 1;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		if(resource == null){
			return null;
		}
		if(tank.getFluidType() == resource.getFluid()){
			return tank.drain(resource.amount, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return this.tank.drain(maxDrain, doDrain);
	}

	//
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		//if (resource == null || resource.getFluid() == null){
		if (resource == null || resource.getFluid() == null){
			return 0;
		}

		FluidStack current = this.tank.getFluid();
		FluidStack resourceCopy = resource.copy();
		if (current != null && current.amount > 0 && !current.isFluidEqual(resourceCopy)){
			return 0;
		}

		int i = 0;
		int used = this.tank.fill(resourceCopy, doFill);
		resourceCopy.amount -= used;
		i += used;

		return i;
	}


	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fluid != null && this.tank.isEmpty() && this.tank.getFluidType() == null;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	/*GUI, Inventory*/

	public boolean isBurning(){
		return this.burnTime > 0;
	}

	@SuppressWarnings("unused")
	private void detectAndSentChanges(boolean sendAnyway){
	}

	public int getBurnTimeRemainingScaled(int i){
		/*if(this.burnTimeRemaining == 0){
			this.burnTimeRemaining = this.burnSpeed;
		}
		return this.burnTime * i / this.burnSpeed;*/

		if(this.burnTimeRemaining == 0){
			this.burnTimeRemaining = -2;
		}
		return this.burnTime * i / this.burnTimeRemaining;
	}

	public void tryRefuel() {
		if (burnTimeRemaining > 0 || this.energyStorage.getEnergyStored() >= this.energyStorage.getMaxEnergyStored()) return;
		if (itemStacks[0] != null && itemStacks[0].stackSize > 0) {
			int itemBurnTime = getItemBurnTime(itemStacks[0]);

			if (itemBurnTime > 0) {
				--itemStacks[0].stackSize;
				if (this.itemStacks[0].stackSize == 0)
				{
					this.itemStacks[0] = itemStacks[0].getItem().getContainerItem(itemStacks[0]);
				}
				burnTime = itemBurnTime;
				burnTimeRemaining = itemBurnTime;
			}
		}
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
			if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item instanceof ItemHoe && ((ItemHoe)item).getToolMaterialName().equals("WOOD")) return 200;
            if (item == Items.stick) return 100;
            if (item == Items.coal) return 1600;
            if (item == Items.lava_bucket) return 20000;
            if (item == Item.getItemFromBlock(Blocks.sapling)) return 100;
            if (item == Items.blaze_rod) return 2400;
            return GameRegistry.getFuelValue(itemstack);
		}
	}

	public void setGuiDisplayName(String displayName){
		this.localizedName = displayName;
	}


	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.localizedName : "container.UniversalGenerator";
	}


	public boolean hasCustomInventoryName() {
		return this.localizedName != null && this.localizedName.length() > 0;
	}

	@SuppressWarnings("static-access")
	public int getSizeInventory() {
		return this.slots_fuel.length;
	}

	@Override
	public ItemStack getStackInSlot(int par1){
		return this.itemStacks[par1];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2){
		ItemStack itemstack = this.getStackInSlot(par1);
		if(itemstack != null){
			if(itemstack.stackSize <= par2){
				this.setInventorySlotContents(par1, null);
			}else{
				itemstack = itemstack.splitStack(par2);
				if(itemstack.stackSize == 0){
					this.setInventorySlotContents(par1, null);
				}
			}
		}
		return itemstack;
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
	public int getInventoryStackLimit(){
		return 64;
	}

	@Override
	public void openInventory(){}

	@Override
	public void closeInventory(){}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack){
		return getItemBurnTime(stack) < 0;
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

	public void setEnergyToAir(){
		this.energyStorage.setEnergyStored(0);
	}

	public void setFluidAmountToAir(){
		this.tank.setAmount(0);
	}
}
