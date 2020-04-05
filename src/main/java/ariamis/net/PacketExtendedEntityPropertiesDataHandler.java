package ariamis.net;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Created by detro on 02.04.2020.
 */
public class PacketExtendedEntityPropertiesDataHandler implements IMessageHandler<PacketExtendedEntityPropertiesData, IMessage> {
    @Override
    public IMessage onMessage(PacketExtendedEntityPropertiesData message, MessageContext ctx) {
        World world = AriamisClient.getClientWorld();
        Entity entity = world.getEntityByID(message.getEntityID());
        if (entity != null) {
            IExtendedEntityProperties eep = entity.getExtendedProperties(message.getEepKey());
            if (eep != null)
                ((PartialUpdateHandler) eep).readUpdateData(message.getPayload(), message.getContext());
        }
        return null;
    }
}

