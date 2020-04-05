package ariamis.net;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.inventory.Container;
import net.minecraft.network.NetHandlerPlayServer;

/**
 * Created by detro on 02.04.2020.
 */
public class PacketGuiActionHandler implements IMessageHandler<PacketGuiAction, IMessage>
{
    @Override
    public IMessage onMessage(PacketGuiAction message, MessageContext ctx)
    {
        NetHandlerPlayServer netHandler = ctx.getServerHandler();

        Container container = netHandler.playerEntity.openContainer;
        if (container instanceof PacketGuiAction.ActionHandler)
        {
            ((PacketGuiAction.ActionHandler) container).handleAction(message.getContext(), message.getPayload());
        }

        return null;
    }
}