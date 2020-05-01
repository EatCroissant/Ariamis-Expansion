package ariamis.items.artefacts;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityTimer extends TileEntity {

    public int timer = 0;
    public int maxTimer;

    public TileEntityTimer() {

    }

    public TileEntityTimer(int maxTimer) {
        this.maxTimer = maxTimer;
    }

    public void updateEntity() {
        timer++;
        if (!this.worldObj.isRemote) {
        }
        if (timer > maxTimer && !this.worldObj.isRemote) {
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
        }

    }

    public void setLifetime(int lifetime) {
        this.maxTimer = lifetime;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        timer = tagCompound.getInteger("timer");
        maxTimer = tagCompound.getInteger("maxTimer");
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("timer", timer);
        tagCompound.setInteger("maxTimer", maxTimer);
    }

    @Override
    public Packet getDescriptionPacket() {
        //S35PacketUpdateTileEntity packet = (S35PacketUpdateTileEntity) super.getDescriptionPacket();
        NBTTagCompound tag = new NBTTagCompound();
        writeToNBT(tag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        //super.onDataPacket(net, pkt);
        NBTTagCompound tag = pkt.func_148857_g();
        readFromNBT(tag);
    }

}