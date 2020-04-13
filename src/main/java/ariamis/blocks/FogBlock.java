package ariamis.blocks;

import ariamis.Ariamis;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
public class FogBlock extends Block {

    public FogBlock() {
        super(Material.web);
        setBlockName("Fog");
        setBlockTextureName(Ariamis.MODID+":fog");

        setLightOpacity(3);
        setBlockUnbreakable();
        setCreativeTab(Ariamis.creativeTab);
        GameRegistry.registerBlock(this, "Fog");
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z) {
        return null;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

}
