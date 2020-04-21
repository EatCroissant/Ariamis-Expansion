package ariamis.items.artefacts;

import ariamis.Ariamis;
import ariamis.items.ItemRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class SigilOfSixthSeens extends Item {
    int cooldown;
    long lastClick;
    public SigilOfSixthSeens() {
        setTextureName(Ariamis.MODID+":eye_wand");
        setUnlocalizedName(Ariamis.MODID+".eye_wand");
        setCreativeTab(Ariamis.creativeTab);
        GameRegistry.registerItem(this,Ariamis.MODID+".eye_wand" );
        lastClick=0;
        cooldown=10*20;
    }


    @Override
    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int xx, float xy, float xz, float angle) {

        // Cannot be cast when it has already been cast
        if(!world.isRemote && world.getWorldTime() - cooldown > lastClick){
            player.addPotionEffect(new PotionEffect(Ariamis.sixthSense.id, 40, 2, true));
            lastClick = world.getWorldTime();
            return true;
        }
        return false;
    }
}
