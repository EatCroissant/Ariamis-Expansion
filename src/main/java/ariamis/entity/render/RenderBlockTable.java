package ariamis.entity.render;

import ariamis.Ariamis;
import ariamis.tile.TileEntityGrindstone;
import net.minecraft.client.model.ModelBase;
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
    IModelCustom model;


    public RenderBlockTable() {
        texture = new ResourceLocation(Ariamis.MODID, "textures/mod/Magic_table.png");
        objModelLocation = new ResourceLocation(Ariamis.MODID, "models/magic_table.obj");
        model = AdvancedModelLoader.loadModel(objModelLocation);

    }

    @Override
    public void renderTileEntityAt(TileEntity te, double posX, double posY, double posZ, float timeSinceLastTick) {
        TileEntityBlockTable te2 = (TileEntityBlockTable) te;
        float rotation = te2.rotation + (timeSinceLastTick / 2F);

        bindTexture(texture);

        GL11.glPushMatrix();
        GL11.glTranslated(posX + 0.5, posY + 0.5, posZ + 0.5);
        GL11.glScalef(1, 1, 1);
        GL11.glPushMatrix();
        GL11.glRotatef(rotation, 0F, 1F, 0.5F);
        model.renderAll();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}