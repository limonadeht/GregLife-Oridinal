package common.item;

import java.util.List;

import com.google.common.base.Strings;

import client.tileentity.TileUniversalGenerator;
import common.Contents;
import common.block.BlockTankStorage;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.IFluidContainerItem;
import util.Utils;

public class ItemTankStorage extends ItemBlock implements IFluidContainerItem{

	public ItemTankStorage(Block block){
		super(block);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean bool){
		BlockTankStorage generator = (BlockTankStorage)this.field_150939_a;

		FluidTank fakeTank = readTank(itemStack);
		FluidStack fluidStack = fakeTank.getFluid();

		list.add("I am Fluid Tanks!");
		list.add("Max Storage: " + generator.tankMaxStorage + " mb");
		if(fluidStack != null && fluidStack.amount > 0){
			float percent = Math.max(100.0f / fakeTank.getCapacity() * fluidStack.amount / 16, 1);
			list.add(EnumChatFormatting.GREEN + fluidStack.getLocalizedName() + " " + String.format("%d mB (%.0f%%)", fluidStack.amount, percent));
		}else{
			list.add(EnumChatFormatting.GREEN + "Not contain a fluids.");
		}
	}

	@SuppressWarnings("unused")
	private static String getFluidName(FluidStack fluidStack) {
		final Fluid fluid = fluidStack.getFluid();
		String localizedName = fluid.getLocalizedName(fluidStack);
		if (!Strings.isNullOrEmpty(localizedName) && !localizedName.equals(fluid.getUnlocalizedName())) {
			return fluid.getRarity(fluidStack).rarityColor.toString() + localizedName;
		} else {
			return EnumChatFormatting.OBFUSCATED + "LOLNOPE" + EnumChatFormatting.RESET;
		}
	}

	public static ItemStack createFilledTank(Fluid fluid) {
		final int tankCapacity = TileUniversalGenerator.getTankCapacity();
		FluidStack stack = FluidRegistry.getFluidStack(fluid.getName(), tankCapacity);
		if (stack == null) return null;

		FluidTank tank = new FluidTank(tankCapacity);
		tank.setFluid(stack);

		ItemStack item = new ItemStack(Contents.tankStorage);
		saveTank(item, tank);
		return item;
	}

	private static FluidTank readTank(ItemStack stack) {
		FluidTank tank = new FluidTank(TileUniversalGenerator.getTankCapacity());

		final NBTTagCompound itemTag = stack.getTagCompound();
		if (itemTag != null && itemTag.hasKey("tank")) {
			tank.readFromNBT(itemTag.getCompoundTag("tank"));
			return tank;
		}

		return tank;
	}

	private static void saveTank(ItemStack container, FluidTank tank) {
		if (tank.getFluidAmount() > 0) {
			NBTTagCompound itemTag = Utils.getItemTag(container);

			NBTTagCompound tankTag = new NBTTagCompound();
			tank.writeToNBT(tankTag);
			itemTag.setTag("tank", tankTag);
		} else {
			container.stackTagCompound = null;
		}
	}

	/*IFluidContainerItem*/
	@Override
	public FluidStack getFluid(ItemStack container){
		FluidTank tank = readTank(container);
		if (tank == null) return null;
		FluidStack result = tank.getFluid();
		if (result != null) result.amount *= container.stackSize;
		return result;
	}

	@Override
	public int getCapacity(ItemStack container){
		FluidTank tank = readTank(container);
		return tank != null? tank.getCapacity() * container.stackSize : 0;
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill){
		if (resource == null) return 0;

		FluidTank tank = readTank(container);
		if (tank == null) return 0;

		final int count = container.stackSize;
		if (count == 0) return 0;

		final int amountPerTank = resource.amount / count;
		if (amountPerTank == 0) return 0;

		FluidStack resourcePerTank = resource.copy();
		resourcePerTank.amount = amountPerTank;

		int filledPerTank = tank.fill(resourcePerTank, doFill);
		if (doFill) saveTank(container, tank);
		return filledPerTank * count;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain){
		if (maxDrain <= 0) return null;

		FluidTank tank = readTank(container);
		if (tank == null) return null;

		final int count = container.stackSize;
		if (count == 0) return null;

		final int amountPerTank = maxDrain / count;
		if (amountPerTank == 0) return null;

		FluidStack drained = tank.drain(amountPerTank, doDrain);
		if (doDrain) saveTank(container, tank);

		if (drained != null) drained.amount *= count;

		return drained;
	}
}
