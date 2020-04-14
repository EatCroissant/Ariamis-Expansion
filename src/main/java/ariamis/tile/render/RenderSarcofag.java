package ariamis.tile.render;

import ariamis.tile.EntitySarcofag;
import ariamis.tile.model.ModelLargeSarcofag;
import ariamis.tile.model.ModelSarcofag;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Created by detro on 06.04.2020.
 */
public class RenderSarcofag extends TileEntitySpecialRenderer {
    private static final ResourceLocation nodrmal_double = new ResourceLocation("ariamis","textures/entity/sarcofag_large.png");
    private static final ResourceLocation normal = new ResourceLocation("ariamis","textures/entity/sarcofag.png");
    private ModelSarcofag chestModel = new ModelSarcofag();
    private ModelSarcofag largeModel = new ModelLargeSarcofag();
    public void renderTileEntityAt(EntitySarcofag chest, double x, double y, double z, float angle) {
        int i;
        if (!chest.hasWorldObj()) i = 0;
        else {
            Block block = chest.getBlockType();
            i = chest.getBlockMetadata();

            if (block instanceof BlockChest && i == 0) {
                try {
                    ((BlockChest)block).func_149954_e(chest.getWorldObj(), chest.xCoord, chest.yCoord, chest.zCoord);
                }
                catch (ClassCastException e) {
                    FMLLog.severe("Attempted to render a chest at %d,  %d, %d that was not a chest", chest.xCoord, chest.yCoord, chest.zCoord);
                }
                i = chest.getBlockMetadata();
            }
            chest.checkForAdjacentChests();
        }

        if (chest.adjacentChestZNeg == null && chest.adjacentChestXNeg == null) {
            ModelSarcofag modelchest;
            if (chest.adjacentChestXPos == null && chest.adjacentChestZPos == null) {
                modelchest = this.chestModel;
                this.bindTexture(normal);
            } else {
                modelchest = this.largeModel;
                this.bindTexture(nodrmal_double);
            }

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float)x, (float)y + 1.0F, (float)z + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            short short1 = 0;

            switch (i){
                case 2:
                    short1 = 180;
                    if(chest.adjacentChestXPos != null) GL11.glTranslatef(1.0F, 0.0F, 0.0F);
                    break;
                case 3:
                    short1 = 0;
                    break;
                case 4:
                    short1 = 90;
                    break;
                case 5:
                    short1 = -90;
                    if(chest.adjacentChestZPos != null) GL11.glTranslatef(0.0F, 0.0F, -1.0F);
            }

            GL11.glRotatef((float)short1, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            float f1 = chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * angle;
            float f2;

            if (chest.adjacentChestZNeg != null) {
                f2 = chest.adjacentChestZNeg.prevLidAngle + (chest.adjacentChestZNeg.lidAngle - chest.adjacentChestZNeg.prevLidAngle) * angle;
                if (f2 > f1) {
                    f1 = f2;
                }
            }

            if (chest.adjacentChestXNeg != null) {
                f2 = chest.adjacentChestXNeg.prevLidAngle + (chest.adjacentChestXNeg.lidAngle - chest.adjacentChestXNeg.prevLidAngle) * angle;
                if (f2 > f1) {
                    f1 = f2;
                }
            }

            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            modelchest.chestLid.rotateAngleX = -(f1 * (float)Math.PI / 2.0F);
            modelchest.renderAll();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        this.renderTileEntityAt((EntitySarcofag)p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}
