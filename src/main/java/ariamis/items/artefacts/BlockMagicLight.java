package ariamis.items.artefacts;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockMagicLight extends BlockContainer {

    public BlockMagicLight(Material par2Material) {
        super(par2Material);
        this.setLightLevel(1.0f);
        this.setBlockBounds(0, 0, 0, 0, 0, 0);
        setBlockName("magicLight");
        setBlockTextureName("minecraft:beacon");
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityMagicLight(600);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(){
        return false;
    }
}
