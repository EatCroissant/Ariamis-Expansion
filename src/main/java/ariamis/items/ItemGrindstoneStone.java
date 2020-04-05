package ariamis.items;

import ariamis.Ariamis;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * Created by detro on 03.04.2020.
 */
public class ItemGrindstoneStone extends Item
{
    public ItemGrindstoneStone()
    {
        super();
        this.setCreativeTab(Ariamis.creativeTab);
        setUnlocalizedName("grindstoneStone");;
        setTextureName(Ariamis.MODID  + ":grindstoneStone");
        setMaxStackSize(1);
        GameRegistry.registerItem(this, "grindstoneStone", Ariamis.MODID);
    }
}