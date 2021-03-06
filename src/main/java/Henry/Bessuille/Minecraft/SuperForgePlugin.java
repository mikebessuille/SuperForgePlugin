package Henry.Bessuille.Minecraft;

import Henry.Bessuille.Minecraft.Lib.Constants;
import Henry.Bessuille.Minecraft.blocks.ModBlocks;
import Henry.Bessuille.Minecraft.items.ModItems;
import Henry.Bessuille.Minecraft.commands.ExplodeCommand;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

import org.apache.logging.log4j.Logger;

/**
 * Created by mbessuille on 14-12-08.
 */
@Mod(modid = Constants.MODID, name = Constants.MODNAME, version = Constants.VERSION)
public class SuperForgePlugin
{
    static Logger logger;

    // Pre-Init: Adding blocks, items, worldgen, ...
    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event )
    {
        logger = event.getModLog();

        ModBlocks.initBlocks();
        ModItems.initItems();
    }

    // Init is for adding TileEntities, events, renderers
    @Mod.EventHandler
    public void init(FMLInitializationEvent event )
    {
        // System.out.println("Henry is awesome");
        logger.info("Henry SuperForgePlugin");

    }


    // postInit is for addons for other mods.
    @Mod.EventHandler
    public void postInit( FMLPostInitializationEvent event )
    {

    }

    @Mod.EventHandler
    public void serverLoad( FMLServerStartingEvent event )
    {
        event.registerServerCommand( new ExplodeCommand() );
    }

    public static Logger getLogger()
    {
        return logger;
    }
}
