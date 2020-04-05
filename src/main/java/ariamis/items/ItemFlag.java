package ariamis.items;

import ariamis.Ariamis;
import ariamis.entity.EntityFlag;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by detro on 03.04.2020.
 */
public class ItemFlag extends Item
{
    public int flagSize;
    public String namePrefix;

    private IIcon poleIcon;
    private IIcon clothIcon;

    public ItemFlag(int flagSize, String namePrefix)
    {
        super();
        setHasSubtypes(true);
        setMaxDamage(0);

        this.flagSize = flagSize;
        this.namePrefix = namePrefix;

        if(namePrefix.equalsIgnoreCase("small")) {
            setUnlocalizedName("flagSmall");
            setTextureName(Ariamis.MODID + ":flag_small");

        }else{
            setUnlocalizedName("flagLarge");
            setTextureName(Ariamis.MODID + ":flag_large");
        }
        setCreativeTab(Ariamis.creativeTab);
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.getBlock(par4, par5, par6) == Blocks.snow_layer)
        {
            par5--;
        }

        if (par7 != 1)
        {
            return false;
        }

        par5++;

        EntityFlag entityflag = new EntityFlag(par3World);
        entityflag.setPosition(par4, par5, par6);
        entityflag.setColor(par1ItemStack.getItemDamage());
        entityflag.setSize(flagSize);

        if (entityflag.canStayAtPosition())
        {
            if (!par3World.isRemote)
            {
                par3World.spawnEntityInWorld(entityflag);
            }
            par1ItemStack.stackSize--;
        }
        return true;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return super.getUnlocalizedName(itemStack) + ".dye" + itemStack.getItemDamage();
    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        return 2;
    }

    @Override
    public IIcon getIconFromDamageForRenderPass(int damage, int pass)
    {
        return pass == 0 ? poleIcon : clothIcon;
    }

    @Override
    public int getColorFromItemStack(ItemStack par1ItemStack, int pass)
    {
        return pass == 1 ? ItemDye.field_150922_c[par1ItemStack.getItemDamage() % ItemDye.field_150922_c.length] : 0xffffffff;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister)
    {
        poleIcon = iconRegister.registerIcon(getIconString() + "_" + "pole");
        clothIcon = iconRegister.registerIcon(getIconString() + "_" + "cloth");
    }

    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 16; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
