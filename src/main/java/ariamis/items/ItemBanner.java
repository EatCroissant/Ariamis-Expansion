package ariamis.items;

import ariamis.Ariamis;
import ariamis.tile.EntityBanner;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by detro on 03.04.2020.
 */
public class ItemBanner extends ItemHangingEntity
{
    public int bannerSize;
    public String namePrefix;

    private IIcon poleIcon;
    private IIcon clothIcon;

    public ItemBanner(int bannerSize, String namePrefix)
    {
        super(EntityBanner.class);
        setHasSubtypes(true);
        setMaxDamage(0);

        this.bannerSize = bannerSize;
        this.namePrefix = namePrefix;


        if(namePrefix.equalsIgnoreCase("small")) {
            setUnlocalizedName("bannerSmall");
            setTextureName(Ariamis.MODID + ":banner_small");
        }
         else{
            setUnlocalizedName("bannerLarge");
            setTextureName(Ariamis.MODID + ":banner_large");
        }
        setCreativeTab(Ariamis.creativeTab);

    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 == 0 || par7 == 1)
        {
            return false;
        }
        else
        {
            if (bannerSize == 2)
                par5 -= 2; //Fix for banners

            int var11 = Direction.facingToDirection[par7];
            EntityHanging var12 = this.createHangingEntity(par3World, par4, par5, par6, var11, par1ItemStack.getItemDamage());

            if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
            {
                return false;
            }
            else
            {
                if (var12 != null && var12.onValidSurface())
                {
                    if (!par3World.isRemote)
                    {
                        par3World.spawnEntityInWorld(var12);
                    }

                    --par1ItemStack.stackSize;
                }

                return true;
            }
        }
    }

    private EntityHanging createHangingEntity(World par1World, int par2, int par3, int par4, int par5, int damage)
    {
        return new EntityBanner(par1World, par2, par3, par4, par5, bannerSize, damage);
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
