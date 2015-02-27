package Henry.Bessuille.Minecraft.items;

import Henry.Bessuille.Minecraft.SuperForgePlugin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.item.Item;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
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
    // TODO:  This may be only one instance for ALL items of this type; Look up "NBT"
    private boolean bItemInUse = false;

    public HenryWand()
    {
        super();
        setUnlocalizedName("henry_wand");
        setCreativeTab(CreativeTabs.tabCombat);
        setTextureName("henrysuper:wand");
        setMaxStackSize(1);
    }


    // onItemUse seems to be called when the item is right-clicked on a block nearby
    @Override
    public boolean onItemUse( ItemStack tool, EntityPlayer player, World world,
                              int x, int y, int z,
                              int face, float block_x, float block_y, float block_z )
    {

        if( !world.isRemote )
        {
            bItemInUse = true;
            // if we don't set the item in use, then onPlayerStoppedUsing won't get called
            player.setItemInUse(tool, getMaxItemUseDuration(tool));
            // SuperForgePlugin.getLogger().info( "[SERVER] WAND: onItemUse()");

            if( face != 1)
            {
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
                EntityChicken chicken = new EntityChicken( world );
                chicken.setPosition( x + 0.5, y + 1, z + 0.5 );
                world.spawnEntityInWorld( chicken );
            }
            return true;
        }
        return false;
    }


    // onItemRightClick is called whenever the item is right-clicked (whether it's on a block, or not!)
    @Override
    public ItemStack onItemRightClick( ItemStack stack, World world, EntityPlayer player )
    {
        // Don't attack with lightning if this is also a "use" event (close to the player!)
        // in that case: onItemUse gets called, then onItemRightClick called on the server only.
        if( bItemInUse == true )
        {
            bItemInUse = false;
            return( super.onItemRightClick( stack, world, player ));
        }

        EntityLivingBase entityTarget = ModItems.getTarget( world, player, 200.0D );
        if( entityTarget != null )
        {
            // hit an entity, so spawn Lightning (want to do this on both server and client)

            /*
            if( !world.isRemote)
            {
                SuperForgePlugin.getLogger().info( "[SERVER] WAND: Lightning");
            }
            else SuperForgePlugin.getLogger().info("[CLIENT] WAND: Lightning");
            */

            EntityLightningBolt bolt = new EntityLightningBolt( world, entityTarget.posX, entityTarget.posY, entityTarget.posZ );
            world.spawnEntityInWorld( bolt );
        }
        else
        {
            if (!world.isRemote)  // Only spawn most entities on the server, not both client and server!
            {
                // This checks whether we right-clicked on air instead of a block
                // Base version of this method only checks out to 5 blocks; otherwise its return is NULL
                MovingObjectPosition pos = getMovingObjectPositionFromPlayer(world, player, true);
                // Long distance version never returns ENTITY
                // MovingObjectPosition pos = ModItems.getPositionLongDistance( world, player, true );

                if (pos == null || pos.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)
                {
                    // if we didn't right-click on a block

                    // SuperForgePlugin.getLogger().info( "WAND: RIGHT CLICK");
                    // TODO:  spawn a new type of black fireball

                    Vec3 look = player.getLookVec();
                    EntityLargeFireball fireball = new EntityLargeFireball(world, player, 0, 0, 0);
                    fireball.setPosition(
                            player.posX + look.xCoord * 2,
                            player.posY + look.yCoord * 2,
                            player.posZ + look.zCoord * 2);
                    // Not sure why, but setVelocity doesn't do the same thing, and there's no
                    // setacceleration method
                    fireball.accelerationX = look.xCoord * 0.6;
                    fireball.accelerationY = look.yCoord * 0.6;
                    fireball.accelerationZ = look.zCoord * 0.6;
                    world.spawnEntityInWorld(fireball);
                }
                else
                {
                    // Clicked on nearby block.  This is handled in onItemUse()
                }
            }
        }
        // return( stack );
        return( super.onItemRightClick( stack, world, player ));
    }


    @Override
    public boolean onLeftClickEntity( ItemStack tool, EntityPlayer player, Entity entity)
    {
        // When this item is used to attack an entity, also light it on fire and throw it away from the player
        // Consider overriding the hitEntity() method instead??
        if( !entity.isImmuneToFire() && !entity.isBurning())
        {
            entity.setFire( 10 );
            Vec3 look = player.getLookVec();
            // the direction the player is looking.  x, z are map coords, and y is height.
            // This makes the entity always jump up a little when hit.
            entity.setVelocity( look.xCoord, 1.0, look.zCoord );
        }

        // if we return true, the entity won't be attacked
        // return false;
        return super.onLeftClickEntity( tool, player, entity );
    }


    // This only gets called if the player right-clicks on an entity that is close to him
    // Need to use onItemRightClick to check if the user is looking at an entity (long distance)
    // TODO: Make this do something different if clicking close, versus how it's handled in onItemRightClick()
    @Override
    public boolean itemInteractionForEntity( ItemStack stack, EntityPlayer player, EntityLivingBase entity )
    {
        World world = player.worldObj;
        // Do this on both client and server, because we must spawn lightning on both
        // (Most entities are only spawned on the server)

        // Vec3 loc = entity.getPosition();   // loc.xCoord, loc.yCoord, loc.zCoord
        // Block target = findBlockUnderEntity( entity );
        int x = MathHelper.floor_double(entity.posX);
        int y = MathHelper.floor_double(entity.boundingBox.minY)-1;  // this would get the block BELOW the entity
        // int y = MathHelper.floor_double(entity.posY);
        int z = MathHelper.floor_double(entity.posZ);
        // if( world.canLightningStrikeAt( x, y, z )) // This fails if it's not raining!
        // TODO:  Try this without the check for sky; does this work underground?
        if( world.canBlockSeeTheSky( x, y+1, z ))
        {
            EntityLightningBolt bolt = new EntityLightningBolt( world, x, y, z );
            world.spawnEntityInWorld( bolt );
        }
        return true;
        // return( super.itemInteractionForEntity( stack, entity );
    }


    /**
     * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
     * MRB:  This is never called!  And I'm not sure why!!!! :(
     */
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int itemUseCount )
    {
        bItemInUse = false;
        SuperForgePlugin.getLogger().info("WAND: Stopped using");
        super.onPlayerStoppedUsing( stack, world, player, itemUseCount );
    }

    /**
     * How long it takes to use or consume an item.   MRB:  if this is not overridden,
     * the default is 0 so onPlayerStoppedUsing never gets called.
     */
    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        int nMyUnusedInteger;

        // return 72000;  // from ItemBow
        return 10000;
    }
}