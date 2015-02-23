package Henry.Bessuille.Minecraft.items;

import Henry.Bessuille.Minecraft.SuperForgePlugin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.Item;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.block.*;
import net.minecraft.init.Blocks;

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
    public boolean onItemUse( ItemStack tool, EntityPlayer player, World world,
                              int x, int y, int z,
                              int face, float block_x, float block_y, float block_z )
    {

        if( !world.isRemote )
        {
            if( face != 1)
            {
                // TODO:  Something more interesting if clicking the side of a block
                // item was not clicked on the top of a block
                if (!player.canPlayerEdit(x, y, z, face, tool))
                {
                    return false;
                }
                world.setBlock( x, y, z, Blocks.dirt );
            }
            else
            {
                // Face is Top of block
                // TODO:  Do something more interesting if clicking on a block
                EntityChicken chicken = new EntityChicken( world );
                chicken.setPosition( x + 0.5, y + 1, z + 0.5 );
                world.spawnEntityInWorld( chicken );
            }
            return true;
        }
        return false;
    }


    // onItemRightClick seems to be called only when the item is clicked into the air (not on a block)
    @Override
    public ItemStack onItemRightClick( ItemStack stack, World world, EntityPlayer player )
    {
        if( !world.isRemote )
        {
            // SuperForgePlugin.getLogger().info( "WAND: RIGHT CLICK");
            // TODO:  Why does this also get called when I'm clicking on a block?
            // TODO:  spawn a new type of black fireball

            Vec3 look = player.getLookVec();
            EntityLargeFireball fireball = new EntityLargeFireball(world, player, 0, 0, 0);
            fireball.setPosition(
                    player.posX + look.xCoord * 2,
                    player.posY + look.yCoord * 2,
                    player.posZ + look.zCoord * 2);
            fireball.accelerationX = look.xCoord * 0.4;
            fireball.accelerationY = look.yCoord * 0.4;
            fireball.accelerationZ = look.zCoord * 0.4;
            world.spawnEntityInWorld(fireball);
        }
        return( stack );
    }

    @Override
    public boolean onLeftClickEntity( ItemStack tool, EntityPlayer player, Entity entity)
    {
        // When this item is used to attack an entity, also light it on fire and throw it away from the player
        if( !entity.isImmuneToFire() && !entity.isBurning())
        {
            entity.setFire( 10 );
            Vec3 look = player.getLookVec();
            entity.setVelocity( look.xCoord * 3, look.yCoord * 3, 2.0 );
        }

        // if we return true, the entity won't be attacked
        return false;
    }

}