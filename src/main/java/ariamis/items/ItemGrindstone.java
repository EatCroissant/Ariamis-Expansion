package ariamis.items;

import ariamis.Ariamis;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * Created by detro on 03.04.2020.
 */
public class ItemGrindstone extends ItemBlock
{
    public ItemGrindstone(Block block)
    {
        super(block);
        maxStackSize = 16;

        setTextureName(Ariamis.MODID+':' + this.getUnlocalizedName());
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int x, int y, int z, int blockSide, float par8, float par9, float par10)
    {
        Block prevBlock = par3World.getBlock(x, y, z);
        int prevMeta = par3World.getBlockMetadata(x, y, z);

        if (prevBlock == Blocks.snow_layer && prevMeta < 1)
        {
            blockSide = 1;
        }
        else if (prevBlock != Blocks.vine && prevBlock != Blocks.tallgrass && prevBlock != Blocks.deadbush && !prevBlock.isReplaceable(par3World, x, y, z))
        {
            if (blockSide == 0)
                --y;
            if (blockSide == 1)
                ++y;
            if (blockSide == 2)
                --z;
            if (blockSide == 3)
                ++z;
            if (blockSide == 4)
                --x;
            if (blockSide == 5)
                ++x;
        }

        Block block = ItemRegistry.grindstone;

        if (!block.canPlaceBlockAt(par3World, x, y, z))
        {
            return false;
        }

        int i1 = MathHelper.floor_double((par2EntityPlayer.rotationYaw * 4F) / 360F + 0.5D) & 3;

        par3World.setBlock(x, y, z, block, i1, 3);

        par1ItemStack.stackSize--;

        return true;
    }
}