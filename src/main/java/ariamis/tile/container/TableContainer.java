package ariamis.tile.container;
import ariamis.tile.TileEntityBlockTable;
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

    public TableContainer( IInventory iip,TileEntityBlockTable ii) {
        this.tableInventory = ii;
        this.addSlotToContainer(new Slot(ii, 0, 80, 8)); // slot burning
        this.addSlotToContainer(new Slot(ii, 1, 98+18+18, 26+18+18)); // slot water

        this.addSlotToContainer(new Slot(ii, 2, 98, 26)); // input slots x4
        this.addSlotToContainer(new Slot(ii, 3, 98+18, 26));
        this.addSlotToContainer(new Slot(ii, 4, 98+18, 26+18));
        this.addSlotToContainer(new Slot(ii, 5, 98, 26+18));

        for(int i=0;i<9;i++) {
            int j=i%3;
            int k=i/3;
            this.addSlotToContainer(new Slot(ii, 6+i, 16+18*j, 17+18*k));
        }

        playerInventory( iip,0, 0);

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
        return null;
    }


    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
    }

}
