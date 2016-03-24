package nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class NBTSaveManager
{
	private ConcurrentMap<String, INBTSaveObject> instanceMap = new ConcurrentHashMap<String, INBTSaveObject>();
	INBTSaveObject currentObject;
	@SuppressWarnings("rawtypes")
	Class objectClass;
	String save_dir;
	String save_file;

	GuiScreen prevGui;
	String lastWorldName;
	World prevWorld;

	public NBTSaveManager(INBTSaveObject nbtsaveobject)
	{
		if (nbtsaveobject == null)
		{
			throw new RuntimeException("Instance is null!!");
		}

		this.currentObject = nbtsaveobject;
		this.objectClass = nbtsaveobject.getClass();
		this.save_dir = nbtsaveobject.getSaveDir();

		this.save_file = nbtsaveobject.getSaveFile();
	}

	public INBTSaveObject getObject(World world)
	{
		if (this.currentObject == null || this.checkWorldChanged(world))
		{
			String worldName = this.getWorldName(world);

			if (this.instanceMap.containsKey(worldName))
			{
				this.instanceMap.replace(this.lastWorldName, this.currentObject);
				this.currentObject = (INBTSaveObject)this.instanceMap.get(worldName);
			}
			else
			{
				try {
					INBTSaveObject instance = (INBTSaveObject)this.objectClass.newInstance();
					this.instanceMap.put(worldName, (this.currentObject = instance));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return this.currentObject;
	}

	public void update(Minecraft mc, GuiScreen guiScreen)
	{
		World world = mc.theWorld;

		if (this.prevWorld != world)
		{
			this.prevWorld = world;

			if (this.prevWorld != null)
			{
				// World����������Ƃ�
				this.onWorldChanged(mc);
			}
		}

		if (guiScreen == this.prevGui)
		{
			return;
		}

		this.prevGui = guiScreen;

		if (guiScreen instanceof GuiIngameMenu)
		{
			try
			{
				this.save(mc);
			}
			catch (IOException ioexception)
			{
				ioexception.printStackTrace();
			}
		}
	}

	private void onWorldChanged(Minecraft mc)
	{
		World world = mc.theWorld;

		this.getObject(world);
		this.lastWorldName = this.getWorldName(world);

		try {
			this.load(mc);
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
	}

	public void load(Minecraft mc) throws IOException
	{
		File saveDir = this.getSaveDir(mc);

		if (!saveDir.exists())
		{
			saveDir.mkdirs();
			return;
		}

		File saveFile = this.getSaveFile(mc);

		if (saveFile.exists())
		{
			NBTTagCompound nbt = CompressedStreamTools.readCompressed(new BufferedInputStream(new  FileInputStream(saveFile)));
			this.getObject(mc.theWorld).readFromNBT(nbt);
		}
	}

	public void save(Minecraft mc) throws IOException
	{
		NBTTagCompound nbt = new NBTTagCompound();
		this.getObject(mc.theWorld).writeToNBT(nbt);

		File saveDir = this.getSaveDir(mc);

		if (!saveDir.exists())
		{
			saveDir.mkdirs();
		}

		File saveFile = this.getSaveFile(mc);

		if (!saveFile.exists())
		{
			if (saveFile.createNewFile())
			{
				CompressedStreamTools.writeCompressed(nbt, new BufferedOutputStream(new FileOutputStream(saveFile)));
			}
		}
		else
		{
			CompressedStreamTools.writeCompressed(nbt, new BufferedOutputStream(new FileOutputStream(saveFile)));
		}
	}

	public boolean checkWorldChanged(World world)
	{
		return !this.getWorldName(world).equals(this.lastWorldName);
	}

	public File getSaveDir(Minecraft mc)
	{
		return (new File(new File(mc.mcDataDir, "saves"), this.getWorldName(mc.theWorld) + this.save_dir));
	}

	public File getSaveFile(Minecraft mc)
	{
		return (new File(this.getSaveDir(mc).toString(), this.save_file));
	}

	public String getWorldName(World world)
	{
		return world.getSaveHandler().getWorldDirectoryName();
	}
}