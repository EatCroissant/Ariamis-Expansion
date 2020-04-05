package ariamis.net;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

/**
 * Created by detro on 02.04.2020.
 */
public class PacketEntityDataHandler implements IMessageHandler<PacketEntityData, IMessage>
{
    @Override
    public IMessage onMessage(PacketEntityData message, MessageContext ctx)
    {
        World world = AriamisClient.getClientWorld();
        Entity entity = world.getEntityByID(message.getEntityID());

        if (entity != null)
            ((PartialUpdateHandler) entity).readUpdateData(message.getPayload(), message.getContext());

        return null;
    }
}
