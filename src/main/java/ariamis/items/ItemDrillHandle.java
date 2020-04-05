package ariamis.items;

import ariamis.Ariamis;
import net.minecraft.item.Item;


public class ItemDrillHandle extends Item{
    public ItemDrillHandle() {
        this.setUnlocalizedName("drill_handle");
        this.setTextureName(Ariamis.MODID+":drill_handle");
        this.maxStackSize=1;
    }

}