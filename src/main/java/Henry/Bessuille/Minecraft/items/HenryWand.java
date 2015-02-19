package Henry.Bessuille.Minecraft.items;

import Henry.Bessuille.Minecraft.SuperForgePlugin;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by mbessuille on 15-02-16.
 */
public class HenryWand extends Item
{
    public HenryWand()
    {
        super();
        setUnlocalizedName("henry_wand");
        setCreativeTab(CreativeTabs.tabCombat);
        setTextureName("henrysuper:wand");
        setMaxStackSize(1);
    }


    // onItemUse seems to be called when the item is clicked on a block
    @Override
    public boolean onItemUse( ItemStack stack, EntityPlayer player, World world,
                              int x, int y, int z,
                              int face, float block_x, float block_y, float block_z )
    {
        if( face != 1)
        {
            // item was not clicked on the top of a block, so do nothing
            return false;
        }

        if( !world.isRemote )
        {
            EntityChicken chicken = new EntityChicken( world );
            chicken.setPosition( x + 0.5, y + 1, z + 0.5 );
            world.spawnEntityInWorld( chicken );
        }
        return true;
    }


    // onItemRightClick seems to be called only when the item is clicked into the air (not on a block)
    @Override
    public ItemStack onItemRightClick( ItemStack stack, World world, EntityPlayer player )
    {
        // SuperForgePlugin.getLogger().info( "WAND: RIGHT CLICK");
        // TODO: spawn a Large Fireball!
        // TODO:  spawn a new type of black fireball
        return( stack );
    }
}