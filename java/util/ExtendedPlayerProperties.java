package util;

import common.SimplyGenerators;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;

public class ExtendedPlayerProperties implements IExtendedEntityProperties{

	/* MOD固有の文字列。EntityPlayerに登録時に使用。
	   MOD内で複数のIExtendedEntityPropertiesを使う場合は、別の文字列をそれぞれ割り当てること。*/
	public final static String EXT_PROP_NAME = SimplyGenerators.MOD_ID;

	private int sampleInt = 0;
	private double sampleDouble = 0.0D;
	private boolean sampleBoolean = false;
	private String sampleString = "";
	private ItemStack sampleItemStack = new ItemStack(Items.apple);
	private ItemStack[] sampleItemStacks = new ItemStack[10];
	private NBTTagCompound nbtTag = new NBTTagCompound();

	/*EntityPlayerにIExtendedEntityPropertiesを登録。登録文字列はMOD固有のものを割り当てること*/
	public static void register(EntityPlayer player) {
		player.registerExtendedProperties(EXT_PROP_NAME, new ExtendedPlayerProperties());
	}
	/*IExtendedEntityPropertiesをEntityPlayerインスタンスから取得する*/
	public static ExtendedPlayerProperties get(EntityPlayer player) {
		return (ExtendedPlayerProperties)player.getExtendedProperties(EXT_PROP_NAME);
	}


	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("sampleInt", this.sampleInt);
		nbt.setDouble("sampleDouble", this.sampleDouble);
		nbt.setBoolean("sampleBoolean", this.sampleBoolean);
		nbt.setString("sampleString", this.sampleString);
		nbt.setTag("sampleTag", this.nbtTag);
		//ItemStackの保存
		NBTTagCompound itemNBT = new NBTTagCompound();
		this.sampleItemStack.writeToNBT(itemNBT);
		nbt.setTag("sampleItemStack", itemNBT);
		//ItemStackの配列の保存
		NBTTagList itemsTagList = new NBTTagList();
		for (int i = 0; i < this.sampleItemStacks.length; ++i)
		{
			if (this.sampleItemStacks[i] != null)
			{
				NBTTagCompound var4 = new NBTTagCompound();
				var4.setByte("Slot", (byte)i);
				this.sampleItemStacks[i].writeToNBT(var4);
				itemsTagList.appendTag(var4);
			}
		}
		nbt.setTag("Items", itemsTagList);

		compound.setTag(EXT_PROP_NAME, nbt);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound nbt = (NBTTagCompound)compound.getTag(EXT_PROP_NAME);
		this.sampleInt = nbt.getInteger("sampleInt");
		this.sampleDouble = nbt.getDouble("sampleDouble");
		this.sampleBoolean = nbt.getBoolean("sampleBoolean");
		this.sampleString = nbt.getString("sampleString");
		this.nbtTag = nbt.getCompoundTag("sampleTag");
		//ItemStackの読み込み
		this.sampleItemStack = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("sampleItemStack"));
		//ItemStackの配列の読み込み
		NBTTagList itemsTagList = nbt.getTagList("Items", Constants.NBT.TAG_COMPOUND);
		this.sampleItemStacks = new ItemStack[10];
		for (int i = 0; i < itemsTagList.tagCount(); ++i)
		{
			NBTTagCompound var4 = itemsTagList.getCompoundTagAt(i);
			int slot = var4.getByte("Slot") & 255;

			if (slot >= 0 && slot < this.sampleItemStacks.length)
			{
				this.sampleItemStacks[slot] = ItemStack.loadItemStackFromNBT(var4);
			}
		}
	}

	@Override
	/*初期化メソッド。今のところ使う必要はない。*/
	public void init(Entity entity, World world) {}

	/*以降、各変数のGetterおよびSetter。
	 * 使い方としては、EntityPlayerのインスタンスが取得できるメソッド内で、
	 * ExtendedPlayerProperties.get(playerインスタンス).setSampleInt(sample)
	 * と呼び出す。*/

	public int getSampleInt() {
		return sampleInt;
	}

	public void setSampleInt(int sampleInt) {
		this.sampleInt = sampleInt;
	}

	public double getSampleDouble() {
		return sampleDouble;
	}

	public void setSampleDouble(double sampleDouble) {
		this.sampleDouble = sampleDouble;
	}

	public boolean isSampleBoolean() {
		return sampleBoolean;
	}

	public void setSampleBoolean(boolean sampleBoolean) {
		this.sampleBoolean = sampleBoolean;
	}

	public String getSampleString() {
		return sampleString;
	}

	public void setSampleString(String sampleString) {
		this.sampleString = sampleString;
	}

	public ItemStack getSampleItemStack() {
		return sampleItemStack;
	}

	public void setSampleItemStack(ItemStack sampleItemStack) {
		this.sampleItemStack = sampleItemStack;
	}

	public ItemStack[] getSampleItemStacks() {
		return sampleItemStacks;
	}

	public void setSampleItemStacks(ItemStack[] sampleItemStacks) {
		this.sampleItemStacks = sampleItemStacks;
	}

	public NBTTagCompound getSampleNBTTagCompound() {
		return nbtTag;
	}

	public void setSampleNBTTagCompound(NBTTagCompound sampleNBTTagCompound) {
		this.nbtTag = sampleNBTTagCompound;
	}
}
