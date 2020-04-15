package ariamis.tile;

import ariamis.blocks.BlockSarcofag;
import ariamis.blocks.BlockTable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import java.util.Iterator;
import java.util.List;

/**
 * Created by detro on 14.04.2020.
 */
public class TileEntityBlockTable extends TileEntity implements IInventory {
    public float rotation = 0;
    public float scale = 0;
    private ItemStack[] inventory = new ItemStack[36];
    private int ticksSinceSync;
    private int cachedChestType;
    private String customName;
    public int numPlayersUsing;

    @Override
    public void updateEntity(){
        if (worldObj.isRemote) scale = 0.5f;
        super.updateEntity();
        ++this.ticksSinceSync;
        float f;
        if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0) {
            this.numPlayersUsing = 0;
            f = 5.0F;
            List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double)((float)this.xCoord - f), (double)((float)this.yCoord - f), (double)((float)this.zCoord - f), (double)((float)(this.xCoord + 1) + f), (double)((float)(this.yCoord + 1) + f), (double)((float)(this.zCoord + 1) + f)));
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer)iterator.next();
                if (entityplayer.openContainer instanceof TableContainer) {
                    IInventory iinventory = ((TableContainer)entityplayer.openContainer).getTableInventory();
                    if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).isPartOfLargeChest(this)) ++this.numPlayersUsing;
                }
            }
        }



    }

    public void closeInventory() {
        if (this.getBlockType() instanceof BlockTable) {
            --this.numPlayersUsing;
            this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
        }
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {return true;}

    @Override
    public int getSizeInventory() {return 14;}

    public ItemStack getStackInSlot(int slot)
    {
        return this.inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int number) {
        if (this.inventory[slot] != null) {
            ItemStack itemstack;
            if (this.inventory[slot].stackSize <= number) {
                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                this.markDirty();
                return itemstack;
            }
            else {
                itemstack = this.inventory[slot].splitStack(number);
                if (this.inventory[slot].stackSize == 0)
                    this.inventory[slot] = null;
                this.markDirty();
                return itemstack;
            }
        }
        else return null;
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.inventory[slot] != null) {
            ItemStack itemstack = this.inventory[slot];
            this.inventory[slot] = null;
            return itemstack;
        }
        else return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.inventory[slot] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) stack.stackSize = this.getInventoryStackLimit();
        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.customName : "container.chest";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.customName != null && this.customName.length() > 0;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq( this.xCoord + 0.5D,  this.yCoord + 0.5D,  this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {
        if (this.numPlayersUsing < 0) this.numPlayersUsing = 0;
        ++this.numPlayersUsing;
        this.worldObj.addBlockEvent(this.xCoord, this.yCoord, this.zCoord, this.getBlockType(), 1, this.numPlayersUsing);
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord, this.zCoord, this.getBlockType());
        this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord - 1, this.zCoord, this.getBlockType());
    }
}
