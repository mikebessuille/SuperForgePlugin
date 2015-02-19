package Henry.Bessuille.Minecraft.items;

import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;

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
}
