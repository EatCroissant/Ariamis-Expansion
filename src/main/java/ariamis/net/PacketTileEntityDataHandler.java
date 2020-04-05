package ariamis.net;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by detro on 02.04.2020.
 */
public class PacketTileEntityDataHandler implements IMessageHandler<PacketTileEntityData, IMessage>
{
    @Override
    public IMessage onMessage(PacketTileEntityData message, MessageContext ctx)
    {
        World world = AriamisClient.getClientWorld();
        TileEntity entity = world.getTileEntity(message.getX(), message.getY(), message.getZ());

        if (entity != null)
            ((PartialUpdateHandler) entity).readUpdateData(message.getPayload(), message.getContext());

        return null;
    }
}