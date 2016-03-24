package client.tileentity;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import common.block.BlockEnergyCell;
import common.energy.EnergyStorage;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEnergyCell extends TileEntity implements IEnergyHandler{

	private EnergyStorage energyStorage;
	@SuppressWarnings("unused")
	private int energyCapacity;

	public TileEnergyCell(int energyCapacity, int energyTransfer){
		this.energyCapacity = energyCapacity;
		this.energyStorage = new EnergyStorage(energyCapacity, energyTransfer);
	}

	@Override
	public void updateEntity(){
		if(this.worldObj.isRemote) return;

		if((this.energyStorage.getEnergyStored() > 0)){
			for(int i = 0; i < 6; i++){
				TileEntity tile = worldObj.getTileEntity(xCoord + ForgeDirection.getOrientation(i).offsetX, yCoord + ForgeDirection.getOrientation(i).offsetY, zCoord + ForgeDirection.getOrientation(i).offsetZ);
				if(tile != null && tile instanceof IEnergyReceiver){
					this.energyStorage.extractEnergy(((IEnergyReceiver)tile).receiveEnergy(ForgeDirection.getOrientation(i).getOpposite(), this.energyStorage.extractEnergy(this.energyStorage.getMaxExtract(), true), false), false);
				}
			}
		}
		if(this.energyStorage.getEnergyStored() > 0){
			BlockEnergyCell.updateFurnaceBlockState(this.energyStorage.getEnergyStored() > 0, worldObj, xCoord, yCoord, zCoord);
		}
	}

	/*@Override
	public void readCustomNBT(NBTTagCompound nbt, boolean descPacket){
		this.energyStorage.readFromNBT(nbt);
	}

	@Override
	public void writeCustomNBT(NBTTagCompound nbt, boolean descPacket){
		this.energyStorage.writeToNBT(nbt);
	}*/

	@Override
	public void readFromNBT(NBTTagCompound nbt){
		this.energyStorage.readFromNBT(nbt);
		//super.readFromNBT(nbt);
	}

	@Override
    public void writeToNBT(NBTTagCompound nbt){
		this.energyStorage.writeToNBT(nbt);
		//super.writeToNBT(nbt);
	}

	public Packet getDescriptionPacket(){
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		this.writeToNBT(nbttagcompound);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, nbttagcompound);
	}

	public void onDataPacket(NetworkManager networkManager, S35PacketUpdateTileEntity packet){
		this.readFromNBT(packet.func_148857_g());
	}

	public boolean canConnectEnergy(ForgeDirection from){
		return true;
	}

	public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate){
		return this.energyStorage.receiveEnergy(maxReceive, simulate);
	}

	public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate){
		return this.energyStorage.extractEnergy(maxExtract, simulate);
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

	public static class T2 extends TileEnergyCell{

		public T2(int energyCapacity, int energyTransfer){
			super(energyCapacity, energyTransfer);
		}
	}

	public static class T3 extends TileEnergyCell{

		public T3(int energyCapacity, int energyTransfer){
			super(energyCapacity, energyTransfer);
		}
	}

	public static class T4 extends TileEnergyCell{

		public T4(int energyCapacity, int energyTransfer){
			super(energyCapacity, energyTransfer);
		}
	}
}
