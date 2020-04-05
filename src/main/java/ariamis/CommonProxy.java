package ariamis;

import ariamis.items.ItemRegistry;
import minefantasy.mf2.api.knowledge.InformationPage;

/**
 * Created by detro on 04.04.2020.
 */

public class CommonProxy {
    public void registerPages(){

        InformationPage.getInfoPage("infoPage.construction").getInfoList().add(ItemRegistry.flags);
        InformationPage.getInfoPage("infoPage.artisanry").getInfoList().add(ItemRegistry.repairing);

    }
}