package ariamis.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by detro on 07.04.2020.
 */

public class ContainerSarcofag extends Container
{
    private IInventory lowerChestInventory;
    private int numRows;

    public ContainerSarcofag(IInventory ii1, IInventory ii2) {
        this.lowerChestInventory = ii2;
        this.numRows = ii2.getSizeInventory() / 9;
        ii2.openInventory();
        int i = (this.numRows - 4) * 18, j, k;
        for (k = 0; k < 9; ++k) {
            for (j = 0; j < this.numRows; ++j) this.addSlotToContainer(new Slot(ii2, k + j * 9, 8 + k * 18, 18 + j * 18));
            for (j = 0; j < 3; ++j)this.addSlotToContainer(new Slot(ii1, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            this.addSlotToContainer(new Slot(ii1, j, 8 + k * 18, 161 + i));
        }
    }

    public boolean canInteractWith(EntityPlayer player) {
        return this.lowerChestInventory.isUseableByPlayer(player);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer player, int slot_number) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slot_number);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (slot_number < this.numRows * 9)
                if (!this.mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true))
                    return null;
            else if (!this.mergeItemStack(itemstack1, 0, this.numRows * 9, false))
                return null;
            if (itemstack1.stackSize == 0)
                slot.putStack((ItemStack)null);
            else
                slot.onSlotChanged();
        }
        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        this.lowerChestInventory.closeInventory();
    }

    /**
     * Return this chest container's lower chest inventory.
     */
    public IInventory getLowerChestInventory()
    {
        return this.lowerChestInventory;
    }
}
