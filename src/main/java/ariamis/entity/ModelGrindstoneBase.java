package ariamis.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Created by detro on 03.04.2020.
 */
public class ModelGrindstoneBase extends ModelBase
{
    //fields
    ModelRenderer basebackright;
    ModelRenderer basebackleft;
    ModelRenderer basefrontleft;
    ModelRenderer basfrontright;
    ModelRenderer bottombase;
    ModelRenderer beamleft;
    ModelRenderer beamright;
    ModelRenderer beamfront;
    ModelRenderer beamback;
    ModelRenderer thingy1;
    ModelRenderer thingy2;
    ModelRenderer thingy3;
    ModelRenderer thingy4;
    ModelRenderer stonestick;
    ModelRenderer crank1;
    ModelRenderer crank2;

    public ModelGrindstoneBase()
    {
        textureWidth = 128;
        textureHeight = 64;

        basebackright = new ModelRenderer(this, 0, 0);
        basebackright.addBox(-7F, 0F, 4F, 3, 12, 3);
        basebackright.setRotationPoint(0F, 13F, 0F);
        basebackright.setTextureSize(64, 32);
        basebackright.mirror = true;
        setRotation(basebackright, 0.1487144F, 0F, 0F);
        basebackleft = new ModelRenderer(this, 0, 16);
        basebackleft.addBox(2F, 0F, 4F, 3, 12, 3);
        basebackleft.setRotationPoint(0F, 13F, 0F);
        basebackleft.setTextureSize(64, 32);
        basebackleft.mirror = true;
        setRotation(basebackleft, 0.1487144F, 0F, 0F);
        basefrontleft = new ModelRenderer(this, 0, 32);
        basefrontleft.addBox(2F, 0F, -7F, 3, 12, 3);
        basefrontleft.setRotationPoint(0F, 13F, 0F);
        basefrontleft.setTextureSize(64, 32);
        basefrontleft.mirror = true;
        setRotation(basefrontleft, -0.1487195F, 0F, 0F);
        basfrontright = new ModelRenderer(this, 0, 48);
        basfrontright.addBox(-7F, 0F, -7F, 3, 12, 3);
        basfrontright.setRotationPoint(0F, 13F, 0F);
        basfrontright.setTextureSize(64, 32);
        basfrontright.mirror = true;
        setRotation(basfrontright, -0.1487195F, 0F, 0F);
        bottombase = new ModelRenderer(this, 20, 0);
        bottombase.addBox(-5F, 8F, -6.5F, 8, 1, 13);
        bottombase.setRotationPoint(0F, 13F, 0F);
        bottombase.setTextureSize(64, 32);
        bottombase.mirror = true;
        setRotation(bottombase, 0F, 0F, 0F);
        beamleft = new ModelRenderer(this, 20, 15);
        beamleft.addBox(3F, 1F, -5.5F, 1, 2, 11);
        beamleft.setRotationPoint(0F, 13F, 0F);
        beamleft.setTextureSize(64, 32);
        beamleft.mirror = true;
        setRotation(beamleft, 0F, 0F, 0F);
        beamright = new ModelRenderer(this, 45, 15);
        beamright.addBox(-6F, 1F, -5.5F, 1, 2, 11);
        beamright.setRotationPoint(0F, 13F, 0F);
        beamright.setTextureSize(64, 32);
        beamright.mirror = true;
        setRotation(beamright, 0F, 0F, 0F);
        beamfront = new ModelRenderer(this, 20, 29);
        beamfront.addBox(-4F, 5F, -7F, 6, 2, 1);
        beamfront.setRotationPoint(0F, 13F, 0F);
        beamfront.setTextureSize(64, 32);
        beamfront.mirror = true;
        setRotation(beamfront, 0F, 0F, 0F);
        beamback = new ModelRenderer(this, 35, 29);
        beamback.addBox(-4F, 5F, 6F, 6, 2, 1);
        beamback.setRotationPoint(0F, 13F, 0F);
        beamback.setTextureSize(64, 32);
        beamback.mirror = true;
        setRotation(beamback, 0F, 0F, 0F);
        thingy1 = new ModelRenderer(this, 80, 0);
        thingy1.addBox(3F, 0F, 0.5F, 1, 1, 4);
        thingy1.setRotationPoint(0F, 13F, 0F);
        thingy1.setTextureSize(64, 32);
        thingy1.mirror = true;
        setRotation(thingy1, 0F, 0F, 0F);
        thingy2 = new ModelRenderer(this, 91, 0);
        thingy2.addBox(3F, 0F, -4.5F, 1, 1, 4);
        thingy2.setRotationPoint(0F, 13F, 0F);
        thingy2.setTextureSize(64, 32);
        thingy2.mirror = true;
        setRotation(thingy2, 0F, 0F, 0F);
        thingy3 = new ModelRenderer(this, 80, 6);
        thingy3.addBox(-6F, 0F, 0.5F, 1, 1, 4);
        thingy3.setRotationPoint(0F, 13F, 0F);
        thingy3.setTextureSize(64, 32);
        thingy3.mirror = true;
        setRotation(thingy3, 0F, 0F, 0F);
        thingy4 = new ModelRenderer(this, 91, 6);
        thingy4.addBox(-6F, 0F, -4.5F, 1, 1, 4);
        thingy4.setRotationPoint(0F, 13F, 0F);
        thingy4.setTextureSize(64, 32);
        thingy4.mirror = true;
        setRotation(thingy4, 0F, 0F, 0F);
        stonestick = new ModelRenderer(this, 80, 12);
        stonestick.addBox(-8F, -0.5F, -0.5F, 14, 1, 1);
        stonestick.setRotationPoint(0F, 13.5F, 0F);
        stonestick.setTextureSize(64, 32);
        stonestick.mirror = true;
        setRotation(stonestick, 0F, 0F, 0F);
        crank1 = new ModelRenderer(this, 80, 15);
        crank1.addBox(6F, -0.5F, -0.5F, 1, 1, 5);
        crank1.setRotationPoint(0F, 13.5F, 0F);
        crank1.setTextureSize(64, 32);
        crank1.mirror = true;
        setRotation(crank1, 0F, 0F, 0F);
        crank2 = new ModelRenderer(this, 93, 15);
        crank2.addBox(7F, -0.5F, 3.5F, 2, 1, 1);
        crank2.setRotationPoint(0F, 13.5F, 0F);
        crank2.setTextureSize(64, 32);
        crank2.mirror = true;
        setRotation(crank2, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        basebackright.render(f5);
        basebackleft.render(f5);
        basefrontleft.render(f5);
        basfrontright.render(f5);
        bottombase.render(f5);
        beamleft.render(f5);
        beamright.render(f5);
        beamfront.render(f5);
        beamback.render(f5);
        thingy1.render(f5);
        thingy2.render(f5);
        thingy3.render(f5);
        thingy4.render(f5);
        stonestick.render(f5);
        crank1.render(f5);
        crank2.render(f5);
    }

    @Override
    public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);

        crank1.rotateAngleX = par7Entity.rotationYaw;
        crank2.rotateAngleX = par7Entity.rotationYaw;
        stonestick.rotateAngleX = par7Entity.rotationYaw;
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}