package ariamis.items;

import ariamis.Ariamis;
import net.minecraft.item.Item;


public class ItemDrillBroken extends Item{
    public ItemDrillBroken() {
        this.setUnlocalizedName("broken_drill");
        this.setTextureName(Ariamis.MODID+":drill_broken");
        this.maxStackSize=1;
        System.out.println(""+this.toString());
    }

}