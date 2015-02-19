package Henry.Bessuille.Minecraft.blocks;

import Henry.Bessuille.Minecraft.Lib.Constants;
import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.world.IBlockAccess;

/**
 * Created by mbessuille on 14-12-29.
 */
public class CementBlock extends Block
{
    private String name = "cement";
    private boolean bFlammable = false;
    public CementBlock( boolean bIsFlammable )
    {
        super(Material.iron);
        this.setCreativeTab(CreativeTabs.tabBlock );
        bFlammable = bIsFlammable;

        // Customize it.
        // Obsidian has hardness 50, resistance of 6000 ??
        // Iron has hardness 5, resistance 30.
        // Stone has hardness 1.5, resistance 30
        setHardness( (float)50.0 );
        // Set resistance after Hardness, as setHardness also sets resistance to 5*hardness
        // resistance of 750 is incredibly strong.  Huge amounts of TNT will not destroy this
        setResistance( (float)750.0 );

        // Textures (cement.png in assets/henrysuper/textures/blocks folder)
        setBlockName(Constants.MODID + "_" + name );
        setBlockTextureName(Constants.MODID + ":" + name );

        // Register the new block with the game
        GameRegistry.registerBlock( this, name );
    }

    // This is silly.  I don't think a regular block made of iron can catch fire!  So even without overriding this
    // method, this block would never catch fire.
    // (Note that we can't prevent fire from being placed on this block - but the cement block will not burn or spread)
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face)
    {
        // Could combine these into something like return (bFlammable && ... )
        if( bFlammable )
            return( super.isFlammable( world, x, y, z, face ));
        else
            return( false );
    }
}