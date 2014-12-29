package Henry.Bessuille.Minecraft;

import Henry.Bessuille.Minecraft.Lib.Constants;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by mbessuille on 14-12-08.
 */
@Mod(modid = Constants.MODID, name = Constants.MODNAME, version = Constants.VERSION)
public class SuperForgePlugin
{
    // Pre-Init: Adding blocks, items, worldgen, ...
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event )
    {


    }

    // Init is for adding TileEntities, events, renderers
    @Mod.EventHandler
    public void init(FMLInitializationEvent event )
    {
        System.out.println("Henry is awesome");
    }


    // postInit is for addons for other mods.
    @Mod.EventHandler
    public void postInit( FMLPostInitializationEvent event )
    {

    }
}
