package ariamis.blocks;

import ariamis.Ariamis;
import codechicken.lib.render.BlockRenderer;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
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
    public int getRenderBlockPass(){
        return 1;
    }
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void onBlockHarvested(World p_149681_1_, int p_149681_2_, int p_149681_3_, int p_149681_4_, int p_149681_5_, EntityPlayer p_149681_6_) {
        super.onBlockHarvested(p_149681_1_, p_149681_2_, p_149681_3_, p_149681_4_, p_149681_5_, p_149681_6_);
    }

    @Override
    public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity en) {
        super.onEntityCollidedWithBlock(w, x, y, z, en);
        if(en instanceof EntityPlayer ) {
            EntityPlayer player = (EntityPlayer) en;
            boolean a = player.getActivePotionEffect(Potion.moveSpeed) != null;
            if (!a) {
                player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20, 0));
                w.playSoundEffect(x, y, z, Ariamis.MODID + ":fog", 1f, 1f);
            }
        }
    }
}
