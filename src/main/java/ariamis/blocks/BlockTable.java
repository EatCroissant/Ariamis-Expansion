package ariamis.blocks;
import ariamis.Ariamis;
import ariamis.net.GuiHandler;
import ariamis.tile.EntitySarcofag;
import ariamis.tile.TileEntityBlockTable;
import ariamis.tile.render.RenderBlockTable;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;

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
            if(!player.isSneaking())
                player.openGui(Ariamis.instance, GuiHandler.GuiIDs.Ariamis.ordinal(), world, x,y,z);

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

}
