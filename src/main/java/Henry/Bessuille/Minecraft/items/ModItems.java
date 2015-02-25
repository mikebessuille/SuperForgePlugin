package Henry.Bessuille.Minecraft.items;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;


import java.util.List;


/**
 * Created by mbessuille on 15-02-19.
 */
public class ModItems
{
    public static void initItems()
    {
        Item referenceItem = new HenryWand();
        GameRegistry.registerItem( referenceItem, referenceItem.getUnlocalizedName() );
    }

    // Utility class with extended distance.
    // TODO:  Dead code; delete this since getTarget() works!
    protected static MovingObjectPosition getPositionLongDistance(World world, EntityPlayer player, boolean p_77621_3_)
    {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double)f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double)f + (double)(world.isRemote ? player.getEyeHeight() - player.getDefaultEyeHeight() : player.getEyeHeight());
        // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double)f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * 0.017453292F - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
        float f5 = -MathHelper.cos(-f1 * 0.017453292F);
        float f6 = MathHelper.sin(-f1 * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 300.0D;
        Vec3 vec31 = vec3.addVector((double)f7 * d3, (double)f6 * d3, (double)f8 * d3);
        return world.func_147447_a(vec3, vec31, p_77621_3_, !p_77621_3_, false);
    }


    // Utility function found on web for getting a target at a distance
    protected static EntityLivingBase getTarget(World world, EntityPlayer player, double distance)
    {
        final float par1 = 1.0F; // partialtick param, something to do with current vs. previous locations?
        final double EYE_HEIGHT = 1.62;  // this is a guess... need to confirm it

        Entity pointedEntity;
        double d0 = distance;
        MovingObjectPosition omo = player.rayTrace(d0, par1);
        double d1 = d0;
        Vec3 startPos = player.getPosition(par1);
        // Account for difference between server and client.
        if (!world.isRemote) startPos = startPos.addVector(0, EYE_HEIGHT, 0);
        Vec3 lookPos = player.getLook(par1);
        Vec3 vec32 = startPos.addVector(lookPos.xCoord * d0, lookPos.yCoord * d0, lookPos.zCoord * d0);
        pointedEntity = null;
        Vec3 vec33 = null;
        float f1 = 1.0F;
        List list = world.getEntitiesWithinAABBExcludingEntity( player,
                                                                player.boundingBox.addCoord( lookPos.xCoord * d0,
                                                                                             lookPos.yCoord * d0,
                                                                                             lookPos.zCoord * d0).expand((double)f1,
                                                                                             (double)f1, (double)f1));
        double d2 = d1;

        for (int i = 0; i < list.size(); ++i)
        {
            Entity entity = (Entity)list.get(i);

            if (entity.canBeCollidedWith())
            {
                float f2 = entity.getCollisionBorderSize();
                AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
                MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(startPos, vec32);

                if (axisalignedbb.isVecInside(startPos))
                {
                    if (0.0D < d2 || d2 == 0.0D)
                    {
                        pointedEntity = entity;
                        vec33 = movingobjectposition == null ? startPos : movingobjectposition.hitVec;
                        d2 = 0.0D;
                    }
                }
                else if (movingobjectposition != null)
                {
                    double d3 = startPos.distanceTo(movingobjectposition.hitVec);

                    if (d3 < d2 || d2 == 0.0D)
                    {
                        if (entity == player.ridingEntity && !entity.canRiderInteract())
                        {
                            if (d2 == 0.0D)
                            {
                                pointedEntity = entity;
                                vec33 = movingobjectposition.hitVec;
                            }
                        }
                        else
                        {
                            pointedEntity = entity;
                            vec33 = movingobjectposition.hitVec;
                            d2 = d3;
                        }
                    }
                }
            }
        }
        if (pointedEntity != null && (d2 < d1 || omo == null))
        {
            omo = new MovingObjectPosition(pointedEntity, vec33);

            if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame)
            {
                // MRB: Not sure what this does or if it's necessary...
                // MRB: I moved the mc= line to here from the top, as I'm not using mc anywhere else.
                Minecraft mc = Minecraft.getMinecraft();
                mc.pointedEntity = pointedEntity;
            }
        }
        if (omo != null)
        {
            if (omo.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY)
            {
                if(omo.entityHit instanceof EntityLivingBase)
                {

                    return (EntityLivingBase)omo.entityHit;
                }
            }
        }
        return null;
    }


    static protected Block findBlockUnderEntity(Entity parEntity)
    {
        int blockX = MathHelper.floor_double(parEntity.posX);
        int blockY = MathHelper.floor_double(parEntity.boundingBox.minY)-1;
        int blockZ = MathHelper.floor_double(parEntity.posZ);
        return parEntity.worldObj.getBlock(blockX, blockY, blockZ);
    }
}
