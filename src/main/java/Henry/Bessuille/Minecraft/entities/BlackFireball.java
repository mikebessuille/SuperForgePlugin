package Henry.Bessuille.Minecraft.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Created by mbessuille on 16-01-15.
 */
public class BlackFireball extends EntityLargeFireball
{
    float fDamage = 9.0F;  // Default Large Fireball uses 6.0F for damage to entities

    public BlackFireball(World world, EntityLivingBase player, double a, double b, double c)
    {
        super(world, player, a, b, c);

        // TODO:  Change this fireball appearance, power, etc.
        setSize( 3.0F, 3.0F ); // Size of the fireball itself?
        setExplosionSize( 8 );  // Default in LargeFireball is 1.
    }

    @Override
    protected void onImpact(MovingObjectPosition pos)
    {
        if (!this.worldObj.isRemote)
        {
            Entity entity = pos.entityHit;
            if( pos.entityHit != null )
            {
                if (entity instanceof EntityLiving)
                {
                    ((EntityLiving) entity).addPotionEffect(new PotionEffect(Potion.wither.id, 200));
                    entity.attackEntityFrom(DamageSource.causeFireballDamage(this, this.shootingEntity), this.fDamage );

                }
            }

            this.worldObj.newExplosion( (Entity)null,
                                        this.posX, this.posY, this.posZ,
                                        (float)this.field_92057_e, true,
                                        this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
            this.setDead();
        }

        /* Don't call super at all, so we can control the explosion from this method.
        // Call Superclass after, since it sets dead to this entity.
        super.onImpact(pos);
        */
    }

    protected void setExplosionSize( int nSize )
    {
        if (nSize > 0)
        {
            this.field_92057_e = nSize; // Default is 1... not sure what this is, but I believe it's the explosion power.

        }
    }
}
