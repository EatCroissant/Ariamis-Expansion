package ariamis.net;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by detro on 02.04.2020.
 */
public class AriamisClient {
        @SideOnly(Side.CLIENT)
        public static EntityPlayer getClientPlayer()
        {
            return Minecraft.getMinecraft().thePlayer;
        }

        @SideOnly(Side.CLIENT)
        public static World getClientWorld()
        {
            return Minecraft.getMinecraft().theWorld;
        }


}
