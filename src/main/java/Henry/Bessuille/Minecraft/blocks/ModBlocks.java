package Henry.Bessuille.Minecraft.blocks;

import net.minecraft.block.Block;

/**
 * Created by mbessuille on 14-12-29.
 */
public final class ModBlocks
{
    public static Block cementBlock;

    public static void initBlocks()
    {
        cementBlock = new CementBlock();
    }
}
