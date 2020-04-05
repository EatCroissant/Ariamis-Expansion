package ariamis.net;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by detro on 02.04.2020.
 */
public class PacketTileEntityClientEventHandler implements IMessageHandler<PacketTileEntityClientEvent, IMessage> {
    @Override
    public IMessage onMessage(PacketTileEntityClientEvent message, MessageContext ctx) {
        World world = MinecraftServer.getServer().worldServerForDimension(message.getDimension());
        TileEntity entity = world.getTileEntity(message.getX(), message.getY(), message.getZ());

        if (entity != null)
            ((ClientEventHandler) entity).onClientEvent(message.getPayload(), message.getContext(), ctx.getServerHandler().playerEntity);

        return null;
    }
}