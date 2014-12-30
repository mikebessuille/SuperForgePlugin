package Henry.Bessuille.Minecraft.blocks;

import Henry.Bessuille.Minecraft.Lib.Constants;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by mbessuille on 14-12-29.
 * BulletProof Glass must be based off BlockGlass for it to render correctly.  Putting a glass (partially transparent)
 * texture on a normal Block causes you to be able to see through the bottom of the block and through other blocks
 * into the earth!
 */
public class BlastproofGlassBlock extends BlockGlass
{
    private String name = "blastproofGlass";
    public BlastproofGlassBlock()
    {
        super( Material.rock, false ); // False for whether faces should be rendered when adjacent to a block other than this type.
        this.setCreativeTab(CreativeTabs.tabBlock );

        // Customize it.
        // Obsidian has hardness 50, resistance of 6000 ??
        // Iron has hardness 5, resistance 30.
        // Stone has hardness 1.5, resistance 30
        setHardness( (float)10.0 );
        // Set resistance after Hardness, as setHardness also sets resistance to 5*hardness
        setResistance( (float)100.0 );

        // This is how much light is reduced when passing through each block.
        // fully opaque blocks are 255.  Glass is 0.
        setLightOpacity( 35 );

        // Textures (cement.png in assets/henrysuper/textures/blocks folder)
        this.setBlockName(Constants.MODID + "_" + name );
        setBlockTextureName(Constants.MODID + ":" + name );

        // Register the new block with the game
        GameRegistry.registerBlock(this, name);
    }
}