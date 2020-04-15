package ariamis.tile;

import codechicken.nei.recipe.BrewingOverlayHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.BrewerInventory;

import java.util.List;

/**
 * Created by detro on 07.04.2020.
 */

public class TableContainer extends Container
{
    private IInventory tableInventory;

    public TableContainer(IInventory ii) {
        this.tableInventory = ii;
        this.addSlotToContainer(new Slot(ii, 1, 62, 40));
        this.addSlotToContainer(new Slot(ii, 2, 70, 48));
        this.addSlotToContainer(new Slot(ii, 3, 62, 48));
        this.addSlotToContainer(new Slot(ii, 4, 70, 40));

        for(int i=0;i<9;i++) {
            int j=i%3;
            int k=i/3;
            this.addSlotToContainer(new Slot(ii, 5+i, 46-8*j, 48+k));

        }

        ii.openInventory();
    }

    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    public IInventory getTableInventory() {
        return tableInventory;
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer player, int slot_number) {
        ItemStack itemstack = null;

        return itemstack;
    }


    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
    }

}
