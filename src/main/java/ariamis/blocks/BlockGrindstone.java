package ariamis.blocks;

import ariamis.Ariamis;
import ariamis.items.ItemGrindstone;
import ariamis.tile.TileEntityGrindstone;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by detro on 02.04.2020.
 */
public class BlockGrindstone extends Block
{
    public BlockGrindstone(Material m)
    {
        super(m);
        setHardness(1.5F);
        setStepSound(Block.soundTypeWood);
        setBlockName("grindstone");
        setBlockTextureName(Ariamis.MODID + ":grindstoneBase");
        setCreativeTab(Ariamis.creativeTab);
        GameRegistry.registerBlock(this, ItemGrindstone.class, "grindstone");
        GameRegistry.registerTileEntityWithAlternatives(TileEntityGrindstone.class, "ygcGrindstone", "grindstone");

    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        TileEntity tileEntity = par1World.getTileEntity(par2, par3, par4);

        if (tileEntity instanceof TileEntityGrindstone)
        {
            TileEntityGrindstone tileEntityGrindstone = (TileEntityGrindstone) tileEntity;

            if (par5EntityPlayer.getHeldItem() != null)
            {
                if (!tileEntityGrindstone.tryApplyingItem(par5EntityPlayer.getHeldItem(), par5EntityPlayer))
                    tileEntityGrindstone.tryRepairingItem(par5EntityPlayer.getHeldItem(), par5EntityPlayer);
            }
            else
                tileEntityGrindstone.increaseGrindstoneRotation();

            return true;
        }

        return false;
    }

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World var1, int i)
    {
        return new TileEntityGrindstone();
    }
}
