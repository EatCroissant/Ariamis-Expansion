package ariamis.tile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by detro on 07.04.2020.
 */

public class TableContainer extends Container {
    private TileEntityBlockTable tableInventory;
    private IInventory playerInv;

    public TableContainer(TileEntityBlockTable ii, IInventory iip) {
        this.playerInv = iip;
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

        playerInventory(iip, 0, 0);

    }

    protected void playerInventory(IInventory iip, int x, int y){
        for (int i = 0; i < 3; ++i)
            for (int j = 0; j < 9; ++j)
                this.addSlotToContainer(new Slot(iip, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
        for (int i = 0; i < 9; ++i)
            this.addSlotToContainer(new Slot(iip, i, 8 + i * 18, 142));
    }

    public boolean canInteractWith(EntityPlayer player) {
        return tableInventory.isUseableByPlayer(player);
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
