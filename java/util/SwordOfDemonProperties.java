package util;

import common.SimplyGenerators;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class SwordOfDemonProperties implements IExtendedEntityProperties{

    public static final String EXT_PROP_NAME = SimplyGenerators.MOD_ID;
    protected EntityPlayer player = null;
    private ItemStack stack = null;
    private ChunkCoordinates chunkCoord = null;
    private NBTTagCompound nbtTag = new NBTTagCompound();
    private boolean forceCoord = false;
    private int dimension = 0;
	protected int exp, maxExp, level, maxLevel;

    public NBTTagCompound getWearableData()
    {
        return nbtTag;
    }

    public SwordOfDemonProperties(EntityPlayer player)
    {
        this.player = player;
    }

    public NBTTagCompound getData()
    {
        NBTTagCompound data = new NBTTagCompound();
        saveNBTData(data);

        return data;
    }

    public static void register(EntityPlayer player)
    {
        player.registerExtendedProperties(EXT_PROP_NAME, new SwordOfDemonProperties(player));
    }

    public static SwordOfDemonProperties get(EntityPlayer player)
    {
        return (SwordOfDemonProperties) player.getExtendedProperties(EXT_PROP_NAME);
    }

    /**
     * Called when the entity that this class is attached to is saved.
     * Any custom entity data  that needs saving should be saved here.
     *
     * @param compound The compound to save to.
     */
    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
    	if(stack != null) compound.setTag("wearable", stack.writeToNBT(new NBTTagCompound()));
    	compound.setInteger("Exp", this.exp);
    	compound.setInteger("Max Exp", this.maxExp);
    	compound.setInteger("Level", level);
    	compound.setInteger("Max Level", this.maxLevel);
    	//compound.setBoolean("forceCampFire",forceCoord);
    }

    /**
     * Called when the entity that this class is attached to is loaded.
     * In order to hook into this, you will need to subscribe to the EntityConstructing event.
     * Otherwise, you will need to initialize manually.
     *
     * @param compound The compound to load from.
     */
    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        if(compound!=null)
        {
            setWearable( compound.hasKey("wearable") ? ItemStack.loadItemStackFromNBT(compound.getCompoundTag("wearable")) : null);
            //setCampFire( new ChunkCoordinates(compound.getInteger("campFireX"), compound.getInteger("campFireY"), compound.getInteger("campFireZ")));
            //dimension = compound.getInteger("compFireDim");
            //forceCoord = compound.getBoolean("forceCampfire");

        	compound.getInteger("Exp");
        	compound.getInteger("Max Exp");
        	compound.getInteger("Level");
        	compound.getInteger("Max Level");
        }
    }

    /**
     * Used to initialize the extended properties with the entity that this is attached to, as well
     * as the world object.
     * Called automatically if you register with the EntityConstructing event.
     * May be called multiple times if the extended properties is moved over to a new entity.
     * Such as when a player switches dimension {Minecraft re-creates the player entity}
     *
     * @param entity The entity that this extended properties is attached to
     * @param world  The world in which the entity exists
     */
    @Override
    public void init(Entity entity, World world)
    {
        this.player = (EntityPlayer)entity;
    }

    public void setWearable(ItemStack bp)
    {
        stack = bp;
    }


    public ItemStack getWearable()
    {
        return stack != null ? stack : null ;
    }

    public void setCampFire(ChunkCoordinates cf)
    {
        chunkCoord = cf;
    }

    public boolean hasWearable()
    {
        return stack != null;
    }

    public ChunkCoordinates getCampFire()
    {
        return chunkCoord;
    }

    public EntityPlayer getPlayer()
    {
        return player;
    }

    public void setDimension(int dimension)
    {
        this.dimension = dimension;
    }

    public int getDimension()
    {
        return dimension;
    }

    public boolean isForcedCampFire()
    {
        return forceCoord;
    }

    public void setForceCampFire(boolean forceCampFire)
    {
        this.forceCoord = forceCampFire;
    }

    /*//Scary names for methods because why not
    public void executeWearableUpdateProtocol()
    {
        if(Utils.notNullAndInstanceOf(stack.getItem(), IBackWearableItem.class))
        {
            ((IBackWearableItem)stack.getItem()).onEquippedUpdate(player.getEntityWorld(), player, stack);
        }
    }

    public void executeWearableDeathProtocol()
    {
        if (Utils.notNullAndInstanceOf(stack.getItem(), IBackWearableItem.class))
        {
            ((IBackWearableItem) stack.getItem()).onPlayerDeath(player.getEntityWorld(), player, stack);
        }
    }

    public void executeWearableEquipProtocol()
    {
        if (Utils.notNullAndInstanceOf(stack.getItem(), IBackWearableItem.class))
        {
            ((IBackWearableItem) stack.getItem()).onEquipped(player.getEntityWorld(), player, stack);
        }
    }

    public void executeWearableUnequipProtocol()
    {
        if (Utils.notNullAndInstanceOf(stack.getItem() , IBackWearableItem.class))
        {
            ((IBackWearableItem) stack.getItem()).onUnequipped(player.getEntityWorld(), player, stack);
        }
    }*/
}
