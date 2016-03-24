package common.entity;

import common.Contents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import util.ItemNBTHelper;

public class EntityRailGun extends EntityThrowable{

	private EntityPlayer player;

	private double damage = (double)ItemNBTHelper.getCompound(new ItemStack(Contents.soulariumCannon)).getInteger("DAMAGE");

	public EntityRailGun(World world){
		super(world);
	}

	/*public EntityRailGun(World worlds, EntityLivingBase entity){
        super(worlds, entity);
    }

	@SideOnly(Side.CLIENT)
    public EntityRailGun(World world, double f2, double f3, double f4)
    {
        super(world, f2, f3, f4);
    }*/

	protected void onImpact(MovingObjectPosition moving)
	{
		if(moving.entityHit != null){
			moving.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
		}

		for(int i = 0; i < 32; ++i){
			this.worldObj.spawnParticle("portal", this.posX, this.posY + this.rand.nextDouble() * 2.0D, this.posZ, this.rand.nextGaussian(), 0.0D, this.rand.nextGaussian());
		}

		if(!this.worldObj.isRemote){
			if(this.getThrower() != null && this.getThrower() instanceof EntityPlayerMP){
				if(moving.entityHit == null){
					this.attackEntityFrom(DamageSource.fall, (float)damage);
				}
			}

			this.setDead();
		}
	}

	public void writeEntityToNBT(NBTTagCompound nbt){
        nbt.setDouble("DAMAGE", this.damage);
    }

	public void readEntityFromNBT(NBTTagCompound nbt){
        if(nbt.hasKey("DAMAGE", 99)){
            this.damage = nbt.getDouble("damage");
        }
    }

	public void setDamage(double damage)
    {
        this.damage = damage;
    }

    public double getDamage()
    {
        return this.damage;
    }
}
