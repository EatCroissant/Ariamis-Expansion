package ariamis.net;

import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import io.netty.channel.Channel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by detro on 02.04.2020.
 */
public class IvNetworkHelperServer
{
    public static <UTileEntity extends TileEntity & PartialUpdateHandler> void sendTileEntityUpdatePacket(UTileEntity tileEntity, String context, SimpleNetworkWrapper network, EntityPlayer player, Object... params)
    {
        if (!(player instanceof EntityPlayerMP))
            throw new UnsupportedOperationException();

        network.sendTo(PacketTileEntityData.packetEntityData(tileEntity, context, params), (EntityPlayerMP) player);
    }

    public static <UTileEntity extends TileEntity & PartialUpdateHandler> void sendTileEntityUpdatePacket(UTileEntity tileEntity, String context, SimpleNetworkWrapper network, Object... params)
    {
        sendToPlayersWatchingChunk(tileEntity.getWorldObj(), tileEntity.xCoord / 16, tileEntity.zCoord / 16, network, PacketTileEntityData.packetEntityData(tileEntity, context, params));
    }

    public static void sendToPlayersWatchingChunk(World world, int chunkX, int chunkZ, SimpleNetworkWrapper network, IMessage message)
    {
        List<EntityPlayerMP> playersWatching = getPlayersWatchingChunk(world, chunkX, chunkZ);

        for (EntityPlayerMP playerMP : playersWatching)
        {
            network.sendTo(message, playerMP);
        }
    }

    public static void sendToPlayersWatchingChunk(World world, int chunkX, int chunkZ, Channel channel, Object message)
    {
        List<EntityPlayerMP> playersWatching = getPlayersWatchingChunk(world, chunkX, chunkZ);

        for (EntityPlayerMP playerMP : playersWatching)
        {
            sendToPlayer(channel, playerMP, message);
        }
    }

    public static void sendToPlayersWatchingChunk(World world, int chunkX, int chunkZ, Packet packet)
    {
        List<EntityPlayerMP> playersWatching = getPlayersWatchingChunk(world, chunkX, chunkZ);

        for (EntityPlayerMP playerMP : playersWatching)
        {
            playerMP.playerNetServerHandler.sendPacket(packet);
        }
    }

    public static void sendToPlayer(Channel channel, EntityPlayerMP playerMP, Object message)
    {
        channel.attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channel.attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(playerMP);
        channel.writeAndFlush(message);
    }

    public static List<EntityPlayerMP> getPlayersWatchingChunk(World world, int chunkX, int chunkZ)
    {
        if (world.isRemote || !(world instanceof WorldServer))
        {
            return Collections.emptyList();
        }

        ArrayList<EntityPlayerMP> playersWatching = new ArrayList<EntityPlayerMP>();

        WorldServer server = (WorldServer) world;
        PlayerManager playerManager = server.getPlayerManager();

        List<EntityPlayerMP> players = server.playerEntities;
        for (EntityPlayerMP player : players)
        {
            if (playerManager.isPlayerWatchingChunk(player, chunkX, chunkZ))
            {
                playersWatching.add(player);
            }
        }

        return playersWatching;
    }

    public static void sendEEPUpdatePacketToPlayer(Entity entity, String eepKey, String context, SimpleNetworkWrapper network, EntityPlayer player, Object... params)
    {
        if (!(player instanceof EntityPlayerMP))
            throw new UnsupportedOperationException();

        network.sendTo(PacketExtendedEntityPropertiesData.packetEntityData(entity, eepKey, context, params), (EntityPlayerMP) player);
    }

    public static void sendEEPUpdatePacket(Entity entity, String eepKey, String context, SimpleNetworkWrapper network, Object... params)
    {
        if (entity.worldObj.isRemote)
            throw new UnsupportedOperationException();

        for (EntityPlayer player : ((WorldServer) entity.worldObj).getEntityTracker().getTrackingPlayers(entity))
            sendEEPUpdatePacketToPlayer(entity, eepKey, context, network, player, params);

        if (entity instanceof EntityPlayer) // Players don't 'track' themselves
            sendEEPUpdatePacketToPlayer(entity, eepKey, context, network, (EntityPlayer) entity, params);
    }
}