package ariamis.net;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;

/**
 * Created by detro on 02.04.2020.
 */
public class PacketEntityData implements IMessage
{
    private int entityID;
    private String context;
    private ByteBuf payload;

    public PacketEntityData()
    {
    }

    public PacketEntityData(int entityID, String context, ByteBuf payload)
    {
        this.entityID = entityID;
        this.context = context;
        this.payload = payload;
    }

    public static <UEntity extends Entity & PartialUpdateHandler> PacketEntityData packetEntityData(UEntity entity, String context, Object... params)
    {
        ByteBuf buf = Unpooled.buffer();
        entity.writeUpdateData(buf, context, params);
        return new PacketEntityData(entity.getEntityId(), context, buf);
    }

    public int getEntityID()
    {
        return entityID;
    }

    public void setEntityID(int entityID)
    {
        this.entityID = entityID;
    }

    public String getContext()
    {
        return context;
    }

    public void setContext(String context)
    {
        this.context = context;
    }

    public ByteBuf getPayload()
    {
        return payload;
    }

    public void setPayload(ByteBuf payload)
    {
        this.payload = payload;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        entityID = buf.readInt();
        context = ByteBufUtils.readUTF8String(buf);
        payload = IvPacketHelper.readByteBuffer(buf);
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(entityID);
        ByteBufUtils.writeUTF8String(buf, context);
        IvPacketHelper.writeByteBuffer(buf, payload);
    }

}
