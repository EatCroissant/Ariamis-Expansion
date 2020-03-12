package examplemod;

import net.minecraft.item.Item;


public class ItemDrillHandle extends Item{
    public ItemDrillHandle() {
        this.setUnlocalizedName("drill_handle");
        this.setTextureName(ExampleMod.MODID+":drill_handle");
        this.maxStackSize=1;
    }

}