package Henry.Bessuille.Minecraft.blocks;

import net.minecraft.block.Block;

/**
 * Created by mbessuille on 14-12-29.
 */
public final class ModBlocks
{
    private static Block cementBlock;
    private static Block blastproofGlassBlock;

    public static void initBlocks()
    {
        cementBlock = new CementBlock( false ); // false for not flammable.
        blastproofGlassBlock = new BlastproofGlassBlock();
    }
}
