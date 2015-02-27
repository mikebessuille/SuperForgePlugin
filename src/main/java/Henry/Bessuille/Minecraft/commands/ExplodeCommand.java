package Henry.Bessuille.Minecraft.commands;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraft.util.Vec3;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityLargeFireball;


/**
 * Created by mbessuille on 15-02-13.
 */
public class ExplodeCommand implements ICommand
{
    private List aliases;

    public ExplodeCommand()
    {
        new String("Puke");     // Keep this here to ensure that KW Analysis is running for this module

        this.aliases = new ArrayList();
        this.aliases.add("explode");
        this.aliases.add("exp");
    }

    @Override
    public String getCommandName()
    {
        return "explode";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender)
    {
        return "explode";
    }

    @Override
    public List getCommandAliases()
    {
        return this.aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] astring)
    {

        EntityPlayer player;
        World world;

        if(sender instanceof EntityPlayer)
        {
            player = (EntityPlayer)sender;
            world = player.getEntityWorld();

            Vec3 look = player.getLookVec();
            EntityLargeFireball fireball = new EntityLargeFireball(world, player, 0, 0, 0);
            fireball.setPosition(
                    player.posX + look.xCoord * 2,
                    player.posY + look.yCoord * 2,
                    player.posZ + look.zCoord * 2);
            fireball.accelerationX = look.xCoord * 0.1;
            fireball.accelerationY = look.yCoord * 0.1;
            fireball.accelerationZ = look.zCoord * 0.1;
            world.spawnEntityInWorld( fireball );

            return;
        }
        else
        {
            sender.addChatMessage(new ChatComponentText("Only players can explode things!"));
            return;
        }

        /*
        if(astring.length == 0)
        {
            icommandsender.sendChatToPlayer("Invalid arguments");
            return;
        }
        */
    }


    @Override
    public boolean canCommandSenderUseCommand(ICommandSender icommandsender)
    {
        if(icommandsender instanceof EntityPlayer)
            return true;
        else
            return false;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender icommandsender,
                                        String[] astring)
    {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i)
    {
        return false;
    }

    @Override
    public int compareTo(Object o)
    {
        return 0;
    }
}
