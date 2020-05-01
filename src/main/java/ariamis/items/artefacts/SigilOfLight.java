package ariamis.items.artefacts;

import ariamis.Ariamis;
import ariamis.items.ItemRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class SigilOfLight extends Item {
    int cooldown;
    long lastClick;
    public SigilOfLight(){
        setTextureName(Ariamis.MODID+":light_wand");
        setUnlocalizedName(Ariamis.MODID+".light_wand");
        setCreativeTab(Ariamis.creativeTab);
        GameRegistry.registerItem(this,Ariamis.MODID+".light_wand" );
        lastClick=0;
        cooldown=10*20;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer caster) {
        if(world.getWorldTime() - cooldown < lastClick) return item;
        MovingObjectPosition rayTrace = rayTrace(4, world, caster, false);

        if (rayTrace != null && rayTrace.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {

            int blockHitX = rayTrace.blockX;
            int blockHitY = rayTrace.blockY;
            int blockHitZ = rayTrace.blockZ;
            int blockHitSide = rayTrace.sideHit;

            switch (blockHitSide) {
                case 0:
                    blockHitY--;
                    break;
                case 1:
                    blockHitY++;
                    break;
                case 2:
                    blockHitZ--;
                    break;
                case 3:
                    blockHitZ++;
                    break;
                case 4:
                    blockHitX--;
                    break;
                case 5:
                    blockHitX++;
                    break;
            }

            if (world.isAirBlock(blockHitX, blockHitY, blockHitZ)) {

                if (!world.isRemote) {
                    world.setBlock(blockHitX, blockHitY, blockHitZ, ItemRegistry.magicLight);
                    if (world.getTileEntity(blockHitX, blockHitY, blockHitZ) instanceof TileEntityTimer) {
                        ((TileEntityTimer) world.getTileEntity(blockHitX, blockHitY, blockHitZ)).setLifetime( (600));
                    }
                }

                caster.swingItem();
                world.playSoundAtEntity(caster, "wizardry:aura", 1.0f, 1.0f);

            }
        } else {
            int x = (int) (Math.floor(caster.posX) + caster.getLookVec().xCoord * 4);
            int y = (int) (Math.floor(caster.posY) + caster.eyeHeight + caster.getLookVec().yCoord * 4);
            int z = (int) (Math.floor(caster.posZ) + caster.getLookVec().zCoord * 4);

            if (world.isAirBlock(x, y, z)) {
                //world.playSound(x, y, z, "sound.ambient.cave.cave", 1.0f, 1.5f, false);
                if (!world.isRemote) {
                    world.setBlock(x, y, z, ItemRegistry.magicLight);
                    if (world.getTileEntity(x, y, z) instanceof TileEntityTimer) {
                        ((TileEntityTimer) world.getTileEntity(x, y, z)).setLifetime((int) (600 ));
                    }
                }
                caster.swingItem();
                world.playSoundAtEntity(caster, "wizardry:aura", 1.0f, 1.0f);

            }
        }
        return super.onItemRightClick(item, world, caster);
    }

    public static MovingObjectPosition rayTrace(double range, World world, EntityLivingBase entity, boolean hitLiquids) {
        Vec3 vec3 = Vec3.createVectorHelper(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
        Vec3 vec31 = entity.getLookVec();
        Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);
        return world.rayTraceBlocks(vec3, vec32, hitLiquids);
    }
}
