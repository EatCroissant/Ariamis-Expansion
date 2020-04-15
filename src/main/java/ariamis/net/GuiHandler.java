package ariamis.net;

import ariamis.Ariamis;
import ariamis.tile.TableContainer;
import ariamis.tile.TileEntityBlockTable;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by detro on 15.04.2020.
 */
public class GuiHandler implements IGuiHandler {
    public enum GuiIDs{
        Ariamis;
    }
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (GuiIDs.values()[ID]){
            case Ariamis:
                return new TableContainer((TileEntityBlockTable)world.getTileEntity(x,y,z),player.inventory);

        }
        throw new IllegalArgumentException("Wrong id");
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (GuiIDs.values()[ID]){
            case Ariamis:
                return null;

        }
        throw new IllegalArgumentException("Wrong id");
    }
}
