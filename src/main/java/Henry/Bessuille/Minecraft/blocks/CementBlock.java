package Henry.Bessuille.Minecraft.blocks;

import Henry.Bessuille.Minecraft.Lib.Constants;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * Created by mbessuille on 14-12-29.
 */
public class CementBlock extends Block
{
    private String name = "cement";
    public CementBlock()
    {
        super(Material.rock);
        this.setCreativeTab(CreativeTabs.tabBlock );
        this.setBlockName(Constants.MODID + "_" + name );
        setBlockTextureName(Constants.MODID + ":" + name );

        // Register the new block with the game
        GameRegistry.registerBlock( this, name );
    }
}
