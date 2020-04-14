package ariamis.blocks;

import ariamis.Ariamis;
import ariamis.tile.EntitySarcofag;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import java.util.Random;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;

/**
 * Created by detro on 06.04.2020.
 */
public class BlockSarcofag extends BlockContainer {
        private final Random random = new Random();
        public BlockSarcofag() {
            super(Material.wood);
            setHardness(1.5F);
            setStepSound(Block.soundTypeWood);
            setBlockName("sarcofag");
            setBlockTextureName(Ariamis.MODID + ":sarcofag");
            this.setCreativeTab(Ariamis.creativeTab);
            this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
            GameRegistry.registerTileEntityWithAlternatives(EntitySarcofag.class, "Sarcofag", "sarcofag");
            GameRegistry.registerBlock(this, "sarcofag");
        }

        public boolean isOpaqueCube()
        {
            return false;
        }
        public boolean renderAsNormalBlock()
        {
            return false;
        }
        public int getRenderType()
        {
            return 22;
        }

        /**
         * Updates the blocks bounds based on its current state. Args: world, x, y, z
         */
        public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, int x, int y , int z) {
            float x1=(iBlockAccess.getBlock(x - 1, y, z) == this)?0:0.0625F, y1=0.0F, z1=iBlockAccess.getBlock(x, y, z - 1) == this?0:0.0625F, x2=(iBlockAccess.getBlock(x + 1, y, z) == this)?1.0F:0.9375F,y2=0.875F,z2=(iBlockAccess.getBlock(x, y, z + 1) == this)?1.0F:0.9375F;
            this.setBlockBounds(x1, y1, z1, x2, y2, z2);
        }

        /**
         * Called whenever the block is added into the world. Args: world, x, y, z
         */
        public void onBlockAdded(World w, int x, int y, int z) {
            super.onBlockAdded(w, x, y, z);
            this.func_149954_e(w, x, y, z);
            net.minecraft.block.Block block = w.getBlock(x, y, z - 1);
            net.minecraft.block.Block block1 = w.getBlock(x, y, z + 1);
            net.minecraft.block.Block block2 = w.getBlock(x - 1, y, z);
            net.minecraft.block.Block block3 = w.getBlock(x + 1, y, z);
            if (block == this)
                this.func_149954_e(w, x, y, z - 1);
            if (block1 == this)
                this.func_149954_e(w, x, y, z + 1);
            if (block2 == this)
                this.func_149954_e(w, x - 1, y, z);
            if (block3 == this)
                this.func_149954_e(w, x + 1, y, z);
        }

        /**
         * Called when the block is placed in the world.
         */
        public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase player, ItemStack item) {
            Block block = w.getBlock(x, y, z - 1);
            Block block1 = w.getBlock(x, y, z + 1);
            net.minecraft.block.Block block2 = w.getBlock(x - 1, y, z);
            net.minecraft.block.Block block3 = w.getBlock(x + 1, y, z);
            byte b0 = 0;
            int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            switch (l) {
                case 0:
                    b0 = 2;
                    break;
                case 1:
                    b0 = 5;
                    break;
                case 2:
                    b0 = 3;
                    break;
                case 3:
                    b0 = 4;
                    break;
            }

            if (block != this && block1 != this && block2 != this && block3 != this)
                w.setBlockMetadataWithNotify(x, y, z, b0, 3);
            else {
                if ((block == this || block1 == this) && (b0 == 4 || b0 == 5)) {
                    if (block == this) w.setBlockMetadataWithNotify(x, y, z - 1, b0, 3);
                    else w.setBlockMetadataWithNotify(x, y, z + 1, b0, 3);
                    w.setBlockMetadataWithNotify(x, y, z, b0, 3);
                }
                if ((block2 == this || block3 == this) && (b0 == 2 || b0 == 3)) {
                    if (block2 == this) w.setBlockMetadataWithNotify(x - 1, y, z, b0, 3);
                    else w.setBlockMetadataWithNotify(x + 1, y, z, b0, 3);
                    w.setBlockMetadataWithNotify(x, y, z, b0, 3);
                }
            }

            if (item.hasDisplayName()) ((EntitySarcofag) w.getTileEntity(x, y, z)).func_145976_a(item.getDisplayName());
        }

        public void func_149954_e(World w, int x, int y, int z) {
            if (!w.isRemote) {
                Block block = w.getBlock(x, y, z - 1);
                Block block1 = w.getBlock(x, y, z + 1);
                Block block2 = w.getBlock(x - 1, y, z);
                Block block3 = w.getBlock(x + 1, y, z);
                boolean flag = true;
                int l;
                Block block4;
                int i1;
                Block block5;
                boolean flag1;
                byte b0;
                int j1;
                if (block != this && block1 != this) {
                    if (block2 != this && block3 != this) {
                        b0 = 3;
                        if (block.func_149730_j() && !block1.func_149730_j()) b0 = 3;
                        
                        if (block1.func_149730_j() && !block.func_149730_j()) b0 = 2;
                        
                        if (block2.func_149730_j() && !block3.func_149730_j()) b0 = 5;

                        if (block3.func_149730_j() && !block2.func_149730_j()) b0 = 4;
                    }
                    else
                    {
                        l = block2 == this ? x - 1 : x + 1;
                        block4 = w.getBlock(l, y, z - 1);
                        i1 = block2 == this ? x - 1 : x + 1;
                        block5 = w.getBlock(i1, y, z + 1);
                        b0 = 3;
                        flag1 = true;

                        if (block2 == this) j1 = w.getBlockMetadata(x - 1, y, z);
                        else j1 = w.getBlockMetadata(x + 1, y, z);
                        
                        if (j1 == 2) b0 = 2;
                        
                        if ((block.func_149730_j() || block4.func_149730_j()) && !block1.func_149730_j() && !block5.func_149730_j()) b0 = 3;
                        
                        if ((block1.func_149730_j() || block5.func_149730_j()) && !block.func_149730_j() && !block4.func_149730_j()) b0 = 2;
                    }
                } else {
                    l = block == this ? z - 1 : z + 1;
                    block4 = w.getBlock(x - 1, y, l);
                    i1 = block == this ? z - 1 : z + 1;
                    block5 = w.getBlock(x + 1, y, i1);
                    b0 = 5;
                    flag1 = true;

                    if (block == this) j1 = w.getBlockMetadata(x, y, z - 1);
                    
                    else j1 = w.getBlockMetadata(x, y, z + 1);
                    
                    if (j1 == 4) b0 = 4;
                    
                    if ((block2.func_149730_j() || block4.func_149730_j()) && !block3.func_149730_j() && !block5.func_149730_j()) b0 = 5;
                    

                    if ((block3.func_149730_j() || block5.func_149730_j()) && !block2.func_149730_j() && !block4.func_149730_j()) b0 = 4;
                    
                }
                w.setBlockMetadataWithNotify(x, y, z, b0, 3);
            }
        }

        /**
         * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
         */
        public boolean canPlaceBlockAt(World w, int x, int y, int z) {
            int l = 0;
            if (w.getBlock(x - 1, y, z) == this) ++l;
            if (w.getBlock(x + 1, y, z) == this) ++l;
            if (w.getBlock(x, y, z - 1) == this) ++l;
            if (w.getBlock(x, y, z + 1) == this) ++l;

            return l <= 1 && (!this.func_149952_n(w, x - 1, y, z) && (!this.func_149952_n(w, x + 1, y, z) && (!this.func_149952_n(w, x, y, z - 1) && !this.func_149952_n(w, x, y, z + 1))));
        }

        private boolean func_149952_n(World w, int x, int y, int z) {
            return w.getBlock(x, y, z) == this && (w.getBlock(x - 1, y, z) == this || (w.getBlock(x + 1, y, z) == this || (w.getBlock(x, y, z - 1) == this || w.getBlock(x, y, z + 1) == this)));
        }

        /**
         * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
         * their own) Args: x, y, z, neighbor Block
         */
        public void onNeighborBlockChange(World w, int x, int y, int z, Block block) {
            super.onNeighborBlockChange(w, x, y, z, block);
            EntitySarcofag tileentitychest = (EntitySarcofag)w.getTileEntity(x, y, z);
            if (tileentitychest != null)
            tileentitychest.updateContainingBlockInfo();
            
        }

        public void breakBlock(World w, int x, int y, int z, net.minecraft.block.Block block, int p_149749_6_) {
            EntitySarcofag tileentitychest = (EntitySarcofag)w.getTileEntity(x, y, z);
            if (tileentitychest != null) {
                for (int i1 = 0; i1 < tileentitychest.getSizeInventory(); ++i1) {
                    ItemStack itemstack = tileentitychest.getStackInSlot(i1);

                    if (itemstack != null) {
                        float f = this.random.nextFloat() * 0.8F + 0.1F;
                        float f1 = this.random.nextFloat() * 0.8F + 0.1F;
                        EntityItem entityitem;

                        for (float f2 = this.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; w.spawnEntityInWorld(entityitem)) {
                            int j1 = this.random.nextInt(21) + 10;

                            if (j1 > itemstack.stackSize)
                                j1 = itemstack.stackSize;


                            itemstack.stackSize -= j1;
                            entityitem = new EntityItem(w, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                            float f3 = 0.05F;
                            entityitem.motionX = (double)((float)this.random.nextGaussian() * f3);
                            entityitem.motionY = (double)((float)this.random.nextGaussian() * f3 + 0.2F);
                            entityitem.motionZ = (double)((float)this.random.nextGaussian() * f3);

                            if (itemstack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());

                        }
                    }
                }
                w.func_147453_f(x, y, z, block);
            }
            super.breakBlock(w, x, y, z, block, p_149749_6_);
        }

        /**
         * Called upon block activation (right click on the block.)
         */
        public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float anglex, float angley, float anglez) {
            if (world.isRemote) return true;
            else {
                IInventory iinventory = this.func_149951_m(world, x, y, z);
                if (iinventory != null)
                    player.displayGUIChest(iinventory);
                return true;
            }
        }

        public IInventory func_149951_m(World world, int x, int y, int z) {
            Object object = (EntitySarcofag)world.getTileEntity(x, y, z);
            if (object == null || (world.isSideSolid(x, y + 1, z, DOWN))|| (world.getBlock(x - 1, y, z) == this && (world.isSideSolid(x - 1, y + 1, z, DOWN) || func_149953_o(world, x - 1, y, z)))|| (world.getBlock(x + 1, y, z) == this && (world.isSideSolid(x + 1, y + 1, z, DOWN) || func_149953_o(world, x + 1, y, z)))|| (world.getBlock(x, y, z - 1) == this && (world.isSideSolid(x, y + 1, z - 1, DOWN) || func_149953_o(world, x, y, z - 1)))|| (world.getBlock(x, y, z + 1) == this && (world.isSideSolid(x, y + 1, z + 1, DOWN) || func_149953_o(world, x, y, z + 1)))) return null;
             else {
                if (world.getBlock(x - 1, y, z) == this) object = new InventoryLargeChest("container.sarcafagDouble", (EntitySarcofag)world.getTileEntity(x - 1, y, z), (IInventory)object);
                if (world.getBlock(x + 1, y, z) == this) object = new InventoryLargeChest("container.sarcafagDouble", (IInventory)object, (EntitySarcofag)world.getTileEntity(x + 1, y, z));
                if (world.getBlock(x, y, z - 1) == this) object = new InventoryLargeChest("container.sarcafagDouble", (EntitySarcofag)world.getTileEntity(x, y, z - 1), (IInventory)object);
                if (world.getBlock(x, y, z + 1) == this) object = new InventoryLargeChest("container.sarcafagDouble", (IInventory)object, (EntitySarcofag)world.getTileEntity(x, y, z + 1));
                return (IInventory)object;
            }
        }

        /**
         * Returns a new instance of a block's tile entity class. Called on placing the block.
         */
        public TileEntity createNewTileEntity(World w, int p_149915_2_) {
            return new EntitySarcofag();
        }

        /**
         * Can this block provide power. Only wire currently seems to have this change based on its state.
         */
        public boolean canProvidePower()
        {
            return false;
        }

        public int isProvidingWeakPower(IBlockAccess p_149709_1_, int p_149709_2_, int p_149709_3_, int p_149709_4_, int p_149709_5_)
        {
            if (!this.canProvidePower()) return 0;

            else {
                int i1 = ((EntitySarcofag)p_149709_1_.getTileEntity(p_149709_2_, p_149709_3_, p_149709_4_)).numPlayersUsing;
                return MathHelper.clamp_int(i1, 0, 15);
            }
        }

        public int isProvidingStrongPower(IBlockAccess p_149748_1_, int p_149748_2_, int p_149748_3_, int p_149748_4_, int p_149748_5_)
        {
            return p_149748_5_ == 1 ? this.isProvidingWeakPower(p_149748_1_, p_149748_2_, p_149748_3_, p_149748_4_, p_149748_5_) : 0;
        }
        //isOcelotSittingOnTop
        private static boolean func_149953_o(World w, int x, int y, int z) {
            return false;
        }

        /**
         * If this returns true, then comparators facing away from this block will use the value from
         * getComparatorInputOverride instead of the actual redstone signal strength.
         */
        public boolean hasComparatorInputOverride()
        {
            return true;
        }

        /**
         * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
         * strength when this block inputs to a comparator.
         */
        public int getComparatorInputOverride(World w, int x, int y, int z, int s) {
            return Container.calcRedstoneFromInventory(this.func_149951_m(w, x, y, z));
        }

        @SideOnly(Side.CLIENT)
        public void registerBlockIcons(IIconRegister p_149651_1_) {
            this.blockIcon = p_149651_1_.registerIcon("planks_oak");
        }

}
