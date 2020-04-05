package ariamis.net;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by detro on 02.04.2020.
 */
public class PacketGuiAction implements IMessage {
    private String context;
    private ByteBuf payload;

    public PacketGuiAction() {
    }

    public PacketGuiAction(String context, ByteBuf payload) {
        this.context = context;
        this.payload = payload;
    }

    public static PacketGuiAction packetGuiAction(String context, Number... args) {
        ByteBuf payload = Unpooled.buffer();
        Number[] arr$ = args;
        int len$ = args.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            Number num = arr$[i$];
            IvPacketHelper.writeNumber(payload, num);
        }

        return new PacketGuiAction(context, payload);
    }

    public String getContext() {
        return this.context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public ByteBuf getPayload() {
        return this.payload;
    }

    public void setPayload(ByteBuf payload) {
        this.payload = payload;
    }

    public void fromBytes(ByteBuf buf) {
        this.context = ByteBufUtils.readUTF8String(buf);
        this.payload = IvPacketHelper.readByteBuffer(buf);
    }

    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, this.context);
        IvPacketHelper.writeByteBuffer(buf, this.payload);
    }

    public interface ActionHandler {
        void handleAction(String var1, ByteBuf var2);
    }
}