package nbt;

import net.minecraft.nbt.NBTTagCompound;

public class NBTSaveObject implements INBTSaveObject{

	String savedir;
	String savefile;

	public NBTSaveObject()
	{
		this.savedir = "/SimplyGeneratorData";
		this.savefile = "/SimplyGeneratorData.dat";
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		System.out.println("write");
	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		System.out.println("read");
	}

	public String getSaveDir()
	{
		return savedir;
	}

	public String getSaveFile()
	{
		return savefile;
	}
}
