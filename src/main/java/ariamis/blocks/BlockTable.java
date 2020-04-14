package ariamis.blocks;
import ariamis.Ariamis;
import ariamis.entity.render.RenderBlockTable;
import ariamis.entity.render.TileEntityBlockTable;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
}