package common.item;

import client.tileentity.TileUniversalGenerator;
import common.Contents;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFlintAndSteelTier extends Item{

	public ItemFlintAndSteelTier()
    {
		this.setUnlocalizedName("sg.flintAndSteelt");
        this.maxStackSize = 1;
        this.setMaxDamage(64);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 == 0){
            --par5;
        }
        if (par7 == 1){
            ++par5;
        }
        if (par7 == 2){
            --par6;
        }
        if (par7 == 3){
            ++par6;
        }
        if (par7 == 4){
            --par4;
        }
        if (par7 == 5){
            ++par4;
        }
        if (!entityplayer.canPlayerEdit(par4, par5, par6, par7, itemstack)){
            return false;
        }else{
            if (world.isAirBlock(par4, par5, par6)){
            	world.playSoundEffect((double)par4 + 0.5D, (double)par5 + 0.5D, (double)par6 + 0.5D, "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F);
            	//world.setBlock(par4, par5, par6, Blocks.fire);
            	if(world.getBlock(par4, par5, par6) == Contents.universalGenerator && entityplayer.isSneaking() && !world.isRemote){
            		TileUniversalGenerator tile = (TileUniversalGenerator)world.getTileEntity(par4, par5, par6);
            		tile.burnTime += 1000;
            		itemstack.damageItem(1, entityplayer);
            	}
            }
            return true;
        }
    }
}
