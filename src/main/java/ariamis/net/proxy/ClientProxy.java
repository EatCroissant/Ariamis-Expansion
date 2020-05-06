package ariamis.net.proxy;

import ariamis.tile.*;
import ariamis.tile.render.RenderBanner;
import ariamis.tile.render.RenderFlag;
import ariamis.tile.render.RenderSarcofag;
import ariamis.items.ItemRegistry;
import ariamis.tile.render.TileEntityRendererGrindstone;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import minefantasy.mf2.api.crafting.carpenter.ICarpenterRecipe;
import minefantasy.mf2.api.knowledge.InformationPage;
import minefantasy.mf2.api.knowledge.client.EntryPage;
import minefantasy.mf2.api.knowledge.client.EntryPageRecipeCarpenter;
import minefantasy.mf2.api.knowledge.client.EntryPageText;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

/**
 * Created by detro on 04.04.2020.
 */
public final class ClientProxy extends CommonProxy {
    public void registerPages(){
        ItemRegistry.flags.addPages(new EntryPage[]{new EntryPageText("knowledge.flags.text.1"), new EntryPageRecipeCarpenter(new ICarpenterRecipe[]{ItemRegistry.flag_small_r})});
        ItemRegistry.flags.addPages(new EntryPage[]{new EntryPageRecipeCarpenter(new ICarpenterRecipe[]{ItemRegistry.flag_large_r}), new EntryPageRecipeCarpenter(new ICarpenterRecipe[]{ItemRegistry.banner_small_r})});
        ItemRegistry.flags.addPages(new EntryPage[]{new EntryPageRecipeCarpenter(new ICarpenterRecipe[]{ItemRegistry.banner_large_r})});
        ItemRegistry.repairing.addPages(new EntryPage[]{new EntryPageText("knowledge.repairing.text.1"), new EntryPageRecipeCarpenter(new ICarpenterRecipe[]{ItemRegistry.grindstonetable_r})});
        ItemRegistry.repairing.addPages(new EntryPage[]{new EntryPageRecipeCarpenter(new ICarpenterRecipe[]{ItemRegistry.grindstoneStone_r})});
        InformationPage.getInfoPage("infoPage.construction").getInfoList().add(ItemRegistry.flags);
        InformationPage.getInfoPage("infoPage.artisanry").getInfoList().add(ItemRegistry.repairing);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrindstone.class, new TileEntityRendererGrindstone());
        RenderingRegistry.registerEntityRenderingHandler(EntityFlag.class, new RenderFlag());
        RenderingRegistry.registerEntityRenderingHandler(EntityBanner.class, new RenderBanner());
        ClientRegistry.bindTileEntitySpecialRenderer(EntitySarcofag.class, new RenderSarcofag());

    }
    public static World getClientWorld()
    {
        return Minecraft.getMinecraft().theWorld;
    }
}
