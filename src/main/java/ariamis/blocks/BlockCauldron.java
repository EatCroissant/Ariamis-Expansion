package ariamis.blocks;

import ariamis.Ariamis;
import ariamis.net.GuiHandler;
import ariamis.tile.TileEntityBlockTable;
import ariamis.tile.render.RenderBlockTable;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCauldron extends BlockContainer {

    public BlockCauldron() {
        super(Material.wood);
        setBlockName("alchemy.cauldron");
        setBlockTextureName(Ariamis.MODID + ":alchemy.cauldron");
        setCreativeTab(Ariamis.creativeTab);
        GameRegistry.registerBlock(this, Ariamis.MODID + ".alchemy.cauldron");

        //GameRegistry.registerTileEntity(TileEntityBlockTable.class, "tileBlockCauldron");
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockTable.class, new RenderBlockTable());
    }
    public int meta=0;
    public int getProgress(){
        return 0;
    }


    @Override
    public boolean renderAsNormalBlock(){
        return false;
    }
    @Override
    public int getRenderType(){
        return -1;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }
    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityBlockTable();
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float anglex, float angley, float anglez) {
        if (world.isRemote) return true;
        else {
            if(player.isUsingItem() && (player.getItemInUse() !=null  && player.getItemInUse().getItem() == Items.flint_and_steel))
                return false;
            return true;
        }
    }

    public IInventory func_149951_m(World world, int x, int y, int z) {
        Object object = (TileEntityBlockTable)world.getTileEntity(x, y, z);
        if (object == null) return null;
        else {
            return (IInventory)object;
        }
    }


    //furnace methods
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase e, ItemStack item) {
        int l = MathHelper.floor_double((double)(e.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
            w.setBlockMetadataWithNotify(x, y, z, 2, 2);
        if (l == 1)
            w.setBlockMetadataWithNotify(x, y, z, 5, 2);
        if (l == 2)
            w.setBlockMetadataWithNotify(x, y, z, 3, 2);
        if (l == 3)
            w.setBlockMetadataWithNotify(x, y, z, 4, 2);

        if (item.hasDisplayName())
            ((TileEntityFurnace)w.getTileEntity(x, y, z)).func_145951_a(item.getDisplayName());

    }


}
