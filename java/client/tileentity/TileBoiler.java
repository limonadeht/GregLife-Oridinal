package client.tileentity;

import cofh.api.energy.IEnergyReceiver;
import common.energy.EnergyStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import util.TankType;

public class TileBoiler extends TileEntityBase implements IEnergyReceiver, IFluidHandler{

	private EnergyStorage energyStorage;
	@SuppressWarnings("unused")
	private int energyCapacity;

	public int maxTankStorage;
	public TankType waterTank = new TankType(16000);
	public TankType steamTank = new TankType(16000);

	FluidStack steam = new FluidStack(FluidRegistry.getFluid("steam"), 16000);


	public TileBoiler(int energyCapacity, int maxTankStorage){
		this.energyCapacity = energyCapacity;
		this.energyStorage = new EnergyStorage(energyCapacity, (energyCapacity*3));
	}

	@SideOnly(Side.CLIENT)
	public IIcon getFluidIcon(){
		Fluid fluid = this.waterTank.getFluidType();
    	return fluid != null ? fluid.getIcon() : null;
    }

	@Override
	public void updateEntity(){
		if(this.worldObj.isRemote) return;

	}

	@Override
	public void readCustomNBT(NBTTagCompound nbt, boolean descPacket){
		this.energyStorage.readFromNBT(nbt);

		this.waterTank = new TankType(16000);
		if(nbt.hasKey("waterTank")){
		    this.waterTank.readFromNBT(nbt.getCompoundTag("waterTank"));
		}
		this.steamTank = new TankType(16000);
		if(nbt.hasKey("steamTank")){
		    this.waterTank.readFromNBT(nbt.getCompoundTag("steamTank"));
		}
	}

	@Override
	public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket){
		this.energyStorage.writeToNBT(nbt);

		NBTTagCompound waterTank = new NBTTagCompound();
		NBTTagCompound steamTank = new NBTTagCompound();
		this.waterTank.writeToNBT(waterTank);
		this.steamTank.writeToNBT(steamTank);
		nbt.setTag("waterTank", waterTank);
		nbt.setTag("steamTank", steamTank);
	}

	public static TankType loadTank(NBTTagCompound nbtRoot) {
	    int tankType = nbtRoot.getInteger("tankType");
	    tankType = MathHelper.clamp_int(tankType, 0, 1);
	    TankType ret;

	    ret = new TankType(16000);

	    if(nbtRoot.hasKey("waterTank")){
	    	FluidStack fl = FluidStack.loadFluidStackFromNBT((NBTTagCompound) nbtRoot.getTag("waterTank"));
	        ret.setFluid(fl);
	    }else{
	    	ret.setFluid(null);
	    }

	    if(nbtRoot.hasKey("steamTank")){
	    	FluidStack fl2 = FluidStack.loadFluidStackFromNBT((NBTTagCompound) nbtRoot.getTag("steamTank"));
	        ret.setFluid(fl2);
	    }else{
	    	ret.setFluid(null);
	    }

	    return ret;
	}

	public boolean canConnectEnergy(ForgeDirection from){
		return true;
	}

	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
		return this.energyStorage.receiveEnergy(maxReceive, simulate);
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

	public NBTTagCompound getItemNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		waterTank.writeToNBT(nbt);
		steamTank.writeToNBT(nbt);
		return nbt;
	}

	public IFluidTank getTank(int i) {
		if(i == 1){
			return this.steamTank;
		}
		return this.waterTank;
	}

	public void onBlockPlacedBy(EntityLivingBase placer, ItemStack stack) {
		NBTTagCompound itemTag = stack.getTagCompound();

		if (itemTag != null && itemTag.hasKey("waterTank")) {
			waterTank.readFromNBT(itemTag.getCompoundTag("waterTank"));
		}
		if (itemTag != null && itemTag.hasKey("steamTank")) {
			steamTank.readFromNBT(itemTag.getCompoundTag("steamTank"));
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
		if(waterTank.getFluidType() == resource.getFluid()){
			return waterTank.drain(resource.amount, doDrain);
		}
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain){
		return this.waterTank.drain(maxDrain, doDrain);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill){
		if (resource == null || resource.getFluid() == null){
			return 0;
		}

		if(resource.getFluid() == this.steam.getFluid()){
			return this.steamTank.fill(resource, doFill);
		}
		if(resource.getFluid() == FluidRegistry.WATER){
			return this.waterTank.fill(resource, doFill);
		}

		return 0;
	}


	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fluid != null && this.waterTank.isEmpty() && this.waterTank.getFluidType() == null;
		//return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{waterTank.getInfo(), steamTank.getInfo()};
	}
}
