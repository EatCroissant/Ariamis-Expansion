package ariamis.entity.render;

import net.minecraft.tileentity.TileEntity;

/**
 * Created by detro on 14.04.2020.
 */
public class TileEntityBlockTable extends TileEntity {
    public float rotation = 0;
    /* Scale */
    public float scale = 0;

    @Override
    public void updateEntity(){
        /* Increments 0.5  This can be changed */
        if (worldObj.isRemote) rotation += 0.5f;
        /* Whatever you want your scale to be */
        if (worldObj.isRemote) scale = 0.5f;
    }
}
