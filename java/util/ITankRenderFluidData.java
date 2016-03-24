package util;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public interface ITankRenderFluidData {
	public FluidStack getFluid();

	public boolean hasFluid();

	public boolean shouldRenderFluidWall(ForgeDirection side);

	public float getCornerFluidLevel(Diagonal diagonal, float time);

	public float getCenterFluidLevel(float time);
}