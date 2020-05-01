package ariamis.items.artefacts;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMagicLight extends TileEntityTimer {

    public int[] randomiser;
    public int[] randomiser2;

    public TileEntityMagicLight(){
        super();
    }

    public TileEntityMagicLight(int maxTimer){
        super(maxTimer);
        randomiser = new int[30];
        randomiser[0] = -1;
        randomiser2 = new int[30];
        randomiser2[0] = -1;
    }

    public void updateEntity(){

        if(randomiser[0] == -1){
            for(int i=0; i<randomiser.length; i++){
                randomiser[i] = this.worldObj.rand.nextInt(10);
            }
        }
        if(randomiser2[0] == -1){
            for(int i=0; i<randomiser2.length; i++){
                randomiser2[i] = this.worldObj.rand.nextInt(10);
            }
        }
        super.updateEntity();
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        randomiser = tagCompound.getIntArray("randomiser");
        randomiser2 = tagCompound.getIntArray("randomiser2");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setIntArray("randomiser", randomiser);
        tagCompound.setIntArray("randomiser2", randomiser2);
    }

}
