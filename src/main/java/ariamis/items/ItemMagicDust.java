package ariamis.items;

import ariamis.Ariamis;
import net.minecraft.client.resources.I18n;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.bukkit.material.Wool;

import java.util.*;

/**
 * Created by detro on 16.04.2020.
 */
public class ItemMagicDust extends Item {
    /*
    *   Metadata description:
    *   1 - Regeneration
    *   2 - Speed
    *   3 - Fire Resistance
    *   4 - Instant Health
    *   5 - Night Vision
    *   6 - Strength
    *   7 - Water Breathing
    *   8 - Invisibility
    *   9 - Poison
    *   10 - Weakness
    *   11 - Slowness
    *   12 - Instant Damage
    *   13 - Wither
    *
    */
    enum potion {
        regeneration(0,"e"),
        speed(1,"b"),
        fire_resistance(2,"c"),
        instant_health(3,"4"),
        night_vision(4,"d"),
        strength(5,"g"),
        water_breathing(6,"1"),
        invisibility(7,"7"),
        poison(8,"2"),
        weakness(9,"8"),
        slowness(10,"0"),
        instant_damage(11,"5"),
        wither(12,"0");
        private final int id;
        private final String color;
        potion(int id, String color) {
            this.id=id;
            this.color = color;
        }

        public String getColor() {
            return "\u00A7"+color;
        }

        public int getId() {
            return id;
        }
    }

    public ItemMagicDust() {
        setUnlocalizedName("magic_dust");
        setTextureName(Ariamis.MODID + ":magic_dust");
        setCreativeTab(Ariamis.creativeTab);
        GameRegistry.registerItem(this, Ariamis.MODID + ".magic_dust");
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    potion[] getEffectsFromMeta(int meta) {
        char[] c = Integer.toBinaryString(meta).toCharArray();
        EnumSet<potion> p = EnumSet.noneOf(potion.class);
        for (int i = 0; i < c.length  ; i++)
            if (c[i] == '1') p.add(potion.values()[c.length-i-1]);
        potion[] ordinals2 = new potion[p.size()];
        int index = 0;
        for (potion e : p)
            ordinals2[index++] = e;
        return ordinals2;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack i, EntityPlayer p, List l, boolean p2) {
        super.addInformation(i, p, l, p2);
        l.add(I18n.format("item.magic_dust.desc"));
        if (GuiScreen.isShiftKeyDown()) {
            potion[] effects = getEffectsFromMeta(i.getItemDamage());
            StringBuilder list= new StringBuilder();
            for (int j = 0; j < effects.length; j++) {
                list.append(effects[j].getColor())
                        .append(I18n.format("item.magic_dust."+( effects[j].getId() + 1)));
                if(j+1<effects.length) list.append("\u00A7r, ");
            }
            l.add(list.toString());
        } else {
            l.add("Press shift for more info");
        }
    }

    @Override
    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        potion[]  p =  getEffectsFromMeta(p_77648_1_.getItemDamage());

        System.out.println( p.length );
        return super.onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);

    }


    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item it, CreativeTabs ct, List l) {
        int s = 1;
        l.add(new ItemStack(it, 1, 0x01111));
        for (int i = 0; i < 13; i++)
            l.add(new ItemStack(it, 1, 1 << i));
    }

};
