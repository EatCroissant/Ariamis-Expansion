package ariamis.net.proxy;

import ariamis.items.ItemRegistry;
import minefantasy.mf2.api.knowledge.InformationPage;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

/**
 * Created by detro on 04.04.2020.
 */

public class CommonProxy {
    public void registerPages(){

        InformationPage.getInfoPage("infoPage.construction").getInfoList().add(ItemRegistry.flags);
        InformationPage.getInfoPage("infoPage.artisanry").getInfoList().add(ItemRegistry.repairing);

    }
    public static World getClientWorld()
    {
        return null;
    }
}