package ariamis.tile.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created by detro on 03.04.2020.
 */
public class ModelGrindstoneStone extends ModelBase
{
    //fields
    ModelRenderer stone1;
    ModelRenderer stone2;
    ModelRenderer stone3;

    public ModelGrindstoneStone()
    {
        textureWidth = 128;
        textureHeight = 64;

        stone1 = new ModelRenderer(this, 0, 0);
        stone1.addBox(-3F, -4.5F, -6F, 4, 8, 12);
        stone1.setRotationPoint(0F, 13.5F, 0F);
        stone1.setTextureSize(128, 64);
        stone1.mirror = true;
        setRotation(stone1, 0F, 0F, 0F);
        stone2 = new ModelRenderer(this, 0, 34);
        stone2.addBox(-3F, -6.5F, -4F, 4, 2, 8);
        stone2.setRotationPoint(0F, 13.5F, 0F);
        stone2.setTextureSize(128, 64);
        stone2.mirror = true;
        setRotation(stone2, 0F, 0F, 0F);
        stone3 = new ModelRenderer(this, 0, 23);
        stone3.addBox(-3F, 3.5F, -4F, 4, 2, 8);
        stone3.setRotationPoint(0F, 13.5F, 0F);
        stone3.setTextureSize(128, 64);
        stone3.mirror = true;
        setRotation(stone3, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        stone1.render(f5);
        stone2.render(f5);
        stone3.render(f5);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);

        stone1.rotateAngleX = par7Entity.rotationYaw;
        stone2.rotateAngleX = par7Entity.rotationYaw;
        stone3.rotateAngleX = par7Entity.rotationYaw;
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}