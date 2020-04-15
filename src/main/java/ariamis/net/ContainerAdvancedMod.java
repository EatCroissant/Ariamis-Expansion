package ariamis.net;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerDispenser;

/**
 * Created by detro on 15.04.2020.
 */
public class ContainerAdvancedMod extends Container{
    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_) {
        return false;
    }
}
