package ariamis.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PhantomTETable extends TileEntity {
    private int x,z;
    public PhantomTETable(int meta) {

        this.x = 0;
        this.z = 0;
        this.x = x+1;
        switch (meta) {
            case 0:
                this.x++;
                break;
            case 1:
                this.z++;
                break;
            case 2:
                this.x--;
                break;
            case 3:
                this.z--;
                break;

            default:
                System.out.println("Illegal phantomTE state! ");
        }
    }


    public TileEntityBlockTable getMainTE(World world,int x,int y,int z){
        return (TileEntityBlockTable)world.getTileEntity(x+this.x,y,z+this.z);
    }

}
