package ariamis.entity.render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;

/**
 * Created by detro on 07.04.2020.
 */

@SideOnly(Side.CLIENT)
public class ModelLargeSarcofag  extends ModelSarcofag {
    public ModelLargeSarcofag() {
        this.chestLid = (new ModelRenderer(this, 0, 0)).setTextureSize(128, 64);
        this.chestLid.addBox(0.0F, -5.0F, -14.0F, 30, 5, 14, 0.0F);
        this.chestLid.rotationPointX = 1.0F;
        this.chestLid.rotationPointY = 7.0F;
        this.chestLid.rotationPointZ = 15.0F;
        this.chestBelow = (new ModelRenderer(this, 0, 19)).setTextureSize(128, 64);
        this.chestBelow.addBox(0.0F, 0.0F, 0.0F, 30, 10, 14, 0.0F);
        this.chestBelow.rotationPointX = 1.0F;
        this.chestBelow.rotationPointY = 6.0F;
        this.chestBelow.rotationPointZ = 1.0F;
    }
}

