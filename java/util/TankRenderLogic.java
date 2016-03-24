package util;

import client.tileentity.TileTankStorage;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class TankRenderLogic implements ITankRenderFluidData{

	private TileTankStorage tank;

	@Override
	public boolean shouldRenderFluidWall(ForgeDirection side) {
		switch (side) {
		case DOWN:
		case UP:
		case EAST:
		case WEST:
		case NORTH:
		case SOUTH: {
		}
		default:
			return true;
		}
	}

	@Override
	public boolean hasFluid() {
		return tank.getTank().getFluidAmount() > 0;
	}

	@Override
	public FluidStack getFluid() {
		return tank.getTank().getFluid();
	}

	@SuppressWarnings("static-access")
	@Override
	public float getCenterFluidLevel(float time) {
		final float raw = (float)tank.getTank().getFluidAmount() / tank.getTankCapacity();
		return raw;
	}

	@Override
	public float getCornerFluidLevel(Diagonal diagonal, float time) {
		return 5.0F;
	}
}