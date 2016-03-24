package nbt;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSaveObject
{
	public void writeToNBT(NBTTagCompound nbttagcompound);

	public void readFromNBT(NBTTagCompound nbttagcompound);

	public String getSaveDir();

	public String getSaveFile();
}