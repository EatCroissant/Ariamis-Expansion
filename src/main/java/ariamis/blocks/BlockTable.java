package ariamis.blocks;

import ariamis.Ariamis;
import ariamis.net.GuiHandler;
import ariamis.tile.EntitySarcofag;
import ariamis.tile.TileEntityBlockTable;
import ariamis.tile.render.RenderBlockTable;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by detro on 14.04.2020.
 */
public class BlockTable extends BlockContainer {


    public BlockTable() {
        super(Material.wood);
        setBlockName("alchemy_table");
        setBlockTextureName(Ariamis.MODID + ":alchemy_table");
        setCreativeTab(Ariamis.creativeTab);
//        GameRegistry.registerTileEntityWithAlternatives(TileEntityBlockTable.class, "alchTable", Ariamis.MODID +"alchemy_table");
        GameRegistry.registerBlock(this, Ariamis.MODID + ".alchemy_table");

        GameRegistry.registerTileEntity(TileEntityBlockTable.class, "tileBlockTable");
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockTable.class, new RenderBlockTable());
    }

    public int getProgress() {
        return 0;
    }


    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        TileEntityBlockTable te =  new TileEntityBlockTable();
        te.setWorldObj(world);
        return te;
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float anglex, float angley, float anglez) {
        if (world.isRemote) return true;
        else {
            TileEntityBlockTable te = (TileEntityBlockTable)world.getTileEntity(x, y, z);
            te.onMasterNull();
            TileEntityBlockTable master = te.getMaster();
            if (!player.isSneaking()) {
                if(master!=null)
                    player.openGui(Ariamis.instance, GuiHandler.GuiIDs.Ariamis.ordinal(), world, master.xCoord, master.yCoord, master.zCoord);
            }
            return true;
        }
    }


    public IInventory func_149951_m(World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(x, y, z);
        IInventory object = te instanceof TileEntityBlockTable ? ((TileEntityBlockTable) te).getMaster():null;
        if (!(object == null))
            return object;
        return null;
    }


    public void breakBlock(World w, int x, int y, int z, net.minecraft.block.Block block, int p_149749_6_) {
        TileEntityBlockTable te = (TileEntityBlockTable)w.getTileEntity(x, y, z);
        TileEntityBlockTable master = te.getMaster();
        if (master != null) {
            for (int i1 = 0; i1 < master.getSizeInventory(); ++i1) {
                ItemStack itemstack = master.getStackInSlot(i1);
                if (itemstack != null) {
                    if(master.random==null) master.random = new Random(w.getWorldTime());
                    else master.random.setSeed(w.getWorldTime());
                    float f = master.random.nextFloat() * 0.8F + 0.1F;
                    float f1 = master.random.nextFloat() * 0.8F + 0.1F;
                    EntityItem entityitem;
                    for (float f2 = master.random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; w.spawnEntityInWorld(entityitem)) {
                        int j1 = master.random.nextInt(21) + 10;
                        if (j1 > itemstack.stackSize) j1 = itemstack.stackSize;
                        itemstack.stackSize -= j1;
                        entityitem = new EntityItem(w, (double)((float)x + f), (double)((float)y + f1), (double)((float)z + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
                        float f3 = 0.05F;
                        entityitem.motionX = (double)((float)master.random.nextGaussian() * f3);
                        entityitem.motionY = (double)((float)master.random.nextGaussian() * f3 + 0.2F);
                        entityitem.motionZ = (double)((float)master.random.nextGaussian() * f3);
                        if (itemstack.hasTagCompound()) entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                    }
                }
            }
            w.func_147453_f(x, y, z, block);
        }
        super.breakBlock(w, x, y, z, block, p_149749_6_);
    }


    public void onNeighborBlockChange(World w, int x, int y, int z, Block block) {
        super.onNeighborBlockChange(w, x, y, z, block);
        TileEntityBlockTable te = (TileEntityBlockTable)w.getTileEntity(x, y, z);
        if(!func_149952_n(w, x, y, z)){
            te.onTEDestroyed();
        }
    }

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



}
