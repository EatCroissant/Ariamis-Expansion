package ariamis.gui;

import ariamis.Ariamis;
import ariamis.tile.TableContainer;
import ariamis.tile.TileEntityBlockTable;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Created by detro on 15.04.2020.
 */
public class TableGui extends GuiContainer{
    private static final ResourceLocation gui_texture = new ResourceLocation(Ariamis.MODID, "/textures/mod/Magic_table.png");
    public TileEntityBlockTable tebt;

    public TableGui(InventoryPlayer ip, TileEntityBlockTable tebt ){
        super(new TableContainer( tebt, ip));
        this.tebt = tebt;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int a, int b){
        String s= this.tebt.hasCustomInventoryName() ? this.tebt.getInventoryName(): I18n.format(this.tebt.getInventoryName());
        this. fontRendererObj.drawString(s, this.xSize/2 - this.fontRendererObj.getStringWidth(s)/2, 6,4210752 );

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1f,1f,1f,1f);
        this.mc.getTextureManager().bindTexture(gui_texture);
        int k = (this.width - this.xSize)/2;
        int j = (this.height - this.ySize)/2;
        this.drawTexturedModalRect(k,j,0,0,this.xSize,this.ySize);
    }
}
