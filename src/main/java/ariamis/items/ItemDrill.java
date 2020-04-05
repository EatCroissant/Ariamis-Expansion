package ariamis.items;

import ariamis.Ariamis;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;


public class ItemDrill extends ItemPickaxe{
    public static Material[] validMaterials = {Material.anvil,Material.clay,Material.glass,Material.grass,Material.ground,Material.ice,Material.iron,Material.packedIce,Material.piston,Material.rock,Material.sand, Material.snow};

    public ItemDrill() {
        super(ToolMaterial.EMERALD);
        this.setMaxDamage(3200);
        this.setUnlocalizedName(Ariamis.MODID+"bronze_drill");
        this.setTextureName(Ariamis.MODID+":drill");
    }


    /*TOOL STUFF*/
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase player)
    {
        return true;
    }
    public boolean isEffective(Material mat) {
        for(Material m : validMaterials)
            if(m == mat)
                return true;
        return false;
    }

    public boolean onBlockDestroyed(ItemStack stack, World world, Block blockID, int x, int y, int z, EntityLivingBase entityLivingBase) {
        if ((double)blockID.getBlockHardness(world, x, y, z) != 0.0D) {
            stack.setStackDisplayName("durability "+stack.getItemDamage());
            stack.damageItem(1, entityLivingBase);
        }
        return true;
    }

    public boolean canBreakExtraBlock(World world, Block block, int x, int y, int z, int meta, EntityPlayer player, ItemStack drill, boolean inWorld)
    {
        if(block.canHarvestBlock(player, meta) && isEffective(block.getMaterial()) && drill.getItemDamage() !=getMaxDamage()-1 ) {

            return true;
        }
        return false;
    }
    @Override
    public boolean onBlockStartBreak(ItemStack stack, int ix, int iy, int iz, EntityPlayer player)
    {
        World world = player.worldObj;
        if(player.isSneaking() || world.isRemote || !(player instanceof EntityPlayerMP))
            return false;
        MovingObjectPosition mop = this.getMovingObjectPositionFromPlayer(world, player, true);
        int side = mop.sideHit;
        int diameter = 3;
        int depth = 1;

        Block b = world.getBlock(ix, iy, iz);
        float maxHardness = 1;
        if (b!=null&&!b.isAir(world, ix, iy, iz))
            maxHardness = b.getPlayerRelativeBlockHardness(player, world, ix, iy, iz)*0.8F;
        if (maxHardness<0)
            maxHardness = 0;
        int startX=ix;
        int startY=iy;
        int startZ=iz;
        if(diameter%2==0)//even numbers
        {
            float hx = (float)mop.hitVec.xCoord-ix;
            float hy = (float)mop.hitVec.yCoord-iy;
            float hz = (float)mop.hitVec.zCoord-iz;
            if((side<2&&hx<.5)||(side<4&&hx<.5))
                startX-= diameter/2;
            if(side>1&&hy<.5)
                startY-= diameter/2;
            if((side<2&&hz<.5)||(side>3&&hz<.5))
                startZ-= diameter/2;
        }
        else//odd numbers
        {
            startX-=(side==4||side==5?0: diameter/2);
            startY-=(side==0||side==1?0: diameter/2);
            startZ-=(side==2||side==3?0: diameter/2);
        }

        for(int dd=0; dd<depth; dd++)
            for(int dw=0; dw<diameter; dw++)
                for(int dh=0; dh<diameter; dh++)
                {
                    int x = startX+ (side==4||side==5?dd: dw);
                    int y = startY+ (side==0||side==1?dd: dh);
                    int z = startZ+ (side==0||side==1?dh: side==4||side==5?dw: dd);

                    if(x==ix&&y==iy&&z==iz)
                        continue;
                    if(!world.blockExists(x, y, z))
                        continue;
                    Block block = world.getBlock(x, y, z);
                    int meta = world.getBlockMetadata(x, y, z);
                    float h = block.getPlayerRelativeBlockHardness(player, world, x, y, z);
                    if(block != null && !block.isAir(world, x, y, z) && h>maxHardness) {
                        if(!this.canBreakExtraBlock(world, block, x, y, z, meta, player, stack, true))
                            continue;

                        BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, ((EntityPlayerMP)player).theItemInWorldManager.getGameType(), (EntityPlayerMP) player, x,y,z);
                        if(event.isCanceled())
                            continue;


                        if(player.capabilities.isCreativeMode) {
                            block.onBlockHarvested(world, x, y, z, meta, player);
                            if (block.removedByPlayer(world, player, x, y, z, false))
                                block.onBlockDestroyedByPlayer(world, x, y, z, meta);
                        }
                        else {
                            block.onBlockHarvested(world, x,y,z, meta, player);
                            if(block.removedByPlayer(world, player, x,y,z, true)) {

                                block.onBlockDestroyedByPlayer( world, x,y,z, meta);
                                block.harvestBlock(world, player, x,y,z, meta);
                                block.dropXpOnBlockBreak(world, x,y,z, event.getExpToDrop());
                                if(stack.stackSize==0 || stack==null || stack.getMaxDamage() == stack.getItemDamage()){
                                    ItemStack is= new ItemStack(ItemRegistry.bronse_drill_broken);
                                    player.inventory.consumeInventoryItem(player.getCurrentEquippedItem().getItem());
                                    player.inventory.addItemStackToInventory(is);
                                    return false;
                                }
                            }
                            player.getCurrentEquippedItem().func_150999_a(world, block, x, y, z, player);
                        }
                        world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
                        ((EntityPlayerMP)player).playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
                    }
                }
        return false;
    }


}