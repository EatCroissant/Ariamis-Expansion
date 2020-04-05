package ariamis;

import ariamis.entity.*;
import ariamis.items.ItemRegistry;
import ariamis.tile.TileEntityGrindstone;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import minefantasy.mf2.api.knowledge.InformationPage;
import minefantasy.mf2.api.knowledge.client.EntryPageRecipeCarpenter;
import minefantasy.mf2.api.knowledge.client.EntryPageText;
import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;

/**
 * Created by detro on 04.04.2020.
 */
public final class ClientProxy extends CommonProxy {
    public void registerPages(){
        ItemRegistry.flags .addPages(new EntryPageText("knowledge.flags.text.1"), new EntryPageRecipeCarpenter(ItemRegistry.flag_small_r));
        ItemRegistry.flags .addPages(new EntryPageRecipeCarpenter(ItemRegistry.flag_large_r), new EntryPageRecipeCarpenter(ItemRegistry.banner_small_r));
        ItemRegistry.flags .addPages(new EntryPageRecipeCarpenter(ItemRegistry.banner_large_r));

        ItemRegistry.repairing.addPages(new EntryPageText("knowledge.repairing.text.1"), new EntryPageRecipeCarpenter(ItemRegistry.grindstonetable_r));
        ItemRegistry.repairing.addPages(new EntryPageRecipeCarpenter(ItemRegistry.grindstoneStone_r));

        InformationPage.getInfoPage("infoPage.construction").getInfoList().add(ItemRegistry.flags);
        InformationPage.getInfoPage("infoPage.artisanry").getInfoList().add(ItemRegistry.repairing);

        RenderingRegistry.registerEntityRenderingHandler(EntityFlag.class, new RenderFlag());
        RenderingRegistry.registerEntityRenderingHandler(EntityBanner.class, new RenderBanner());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrindstone.class, new TileEntityRendererGrindstone());
        RenderingRegistry.registerEntityRenderingHandler(EntityFlag.class, new RenderFlag());
        RenderingRegistry.registerEntityRenderingHandler(EntityBanner.class, new RenderBanner());
    }
}
