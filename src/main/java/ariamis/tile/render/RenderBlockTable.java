package ariamis.tile.render;

import ariamis.Ariamis;
import ariamis.tile.TileEntityBlockTable;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * Created by detro on 14.04.2020.
 */
public class RenderBlockTable extends TileEntitySpecialRenderer {
    ResourceLocation texture;
    ResourceLocation objModelLocation;
    ResourceLocation objSingleModelLocation;
    IModelCustom model;
    IModelCustom model_block;


    public RenderBlockTable() {
        texture = new ResourceLocation(Ariamis.MODID, "textures/mod/Magic_table.png");
        objModelLocation = new ResourceLocation(Ariamis.MODID, "models/magic_table.obj");
        objSingleModelLocation = new ResourceLocation(Ariamis.MODID, "models/magic_table_block.obj");
        model = AdvancedModelLoader.loadModel(objModelLocation);
        model_block = AdvancedModelLoader.loadModel(objSingleModelLocation);

    }

    @Override
    public void renderTileEntityAt(TileEntity te, double posX, double posY, double posZ, float timeSinceLastTick) {
        TileEntityBlockTable te2 = (TileEntityBlockTable) te;
        bindTexture(texture);
        if(!te2.isMaster() && te2.master==null) {
            GL11.glPushMatrix();
            GL11.glTranslated(posX + 0.5, posY + 0.5, posZ + 0.5);
            GL11.glScalef(1, 1, 1);
            GL11.glPushMatrix();
            model_block.renderAll();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
        else if(te2.isMaster()){
            GL11.glPushMatrix();
            GL11.glTranslated(posX + 0.5, posY + 0.5, posZ + 0.5);
            float angle=0;
            switch (te2.rotation){
                case 0: angle=0;break;
                case 1: angle=0;break;
                case 2: angle=180;break;
                case 3: angle=-90;break;
                case 4: angle =90;break;
            }
            GL11.glRotatef(angle,0,1,0);
            GL11.glScalef(1, 1, 1);
            GL11.glPushMatrix();
            model.renderAll();
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }
}