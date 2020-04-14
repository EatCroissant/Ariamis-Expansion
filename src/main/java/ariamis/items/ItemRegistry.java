package ariamis.items;

import ariamis.blocks.BlockGrindstone;
import ariamis.blocks.BlockSarcofag;
import ariamis.blocks.BlockTable;
import ariamis.blocks.FogBlock;
import ariamis.tile.EntityBanner;
import ariamis.tile.EntityFlag;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import ariamis.Ariamis;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.crafting.carpenter.ICarpenterRecipe;
import minefantasy.mf2.api.knowledge.InformationBase;
import minefantasy.mf2.api.knowledge.InformationPage;
import minefantasy.mf2.api.knowledge.ResearchArtefacts;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.audio.SoundRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static net.minecraft.init.Items.dye;

/**
 * Created by detro on 02.04.2020.
 */
public class ItemRegistry {
    public static Item bronse_drill;
    public static Item drill_handle;
    public static Item bronse_drill_broken;
    public static Item grindstoneStone;
    public static Block grindstone;
    public static Block sarcofag;
    public static Block fog;
    public static Block alchemy_table;

    public static Item flagSmall;
    public static Item flagLarge;
    public static Item bannerSmall;
    public static Item bannerLarge;


    static void registerTools(){
        bronse_drill = new ItemDrill();
        drill_handle = new ItemDrillHandle();
        bronse_drill_broken = new ItemDrillBroken();
        bronse_drill.setCreativeTab(Ariamis.creativeTab);
        drill_handle.setCreativeTab(Ariamis.creativeTab);
        bronse_drill_broken.setCreativeTab(Ariamis.creativeTab);


        GameRegistry.registerItem(bronse_drill, "drill");
        GameRegistry.registerItem(drill_handle, "drill_handle");
        GameRegistry.registerItem(bronse_drill_broken,"drill_broken");
    }



    static ICarpenterRecipe drill_handleR;
    static IAnvilRecipe bronse_drill_from_brokenR;
    static IAnvilRecipe bronse_drillR;



    public static ICarpenterRecipe grindstoneStone_r;
    public static ICarpenterRecipe grindstonetable_r;
    public static ICarpenterRecipe banner_small_r;
    public static ICarpenterRecipe banner_large_r;
    public static ICarpenterRecipe flag_small_r;
    public static ICarpenterRecipe flag_large_r;


    public static InformationBase drill_info;
    public static InformationBase flags;
    public static InformationBase repairing;

    static void registerToolRecipes(){
        drill_info = (new InformationBase("dirll_info", 10, -1, 1, new ItemStack(bronse_drill), KnowledgeListMF.tungsten)).registerStat().setPage(KnowledgeListMF.engineering).addSkill(SkillList.engineering, 30).addSkill(SkillList.artisanry, 30);
        ResearchArtefacts.addArtefact(Items.diamond, drill_info);
        InformationPage.getInfoPage("infoPage.engineering").getInfoList().add(drill_info);

        drill_handleR = MineFantasyAPI.addCarpenterToolRecipe(SkillList.engineering, new ItemStack(drill_handle),
                "drill_info", "hammer", "hammer", 2, 10,  new Object[]{
                        "    ", "TXRF","DSAQ","TXRF",  'T', ComponentListMF.leather_strip, 'X',ComponentListMF.plate_huge ,
                        'R',ComponentListMF.rivet, 'F',ComponentListMF.bolt, 'D',ComponentListMF.iron_frame,'S',
                        ComponentListMF.bronze_gears,'A', ComponentListMF.tungsten_gears,'Q', ComponentListMF.cogwork_shaft});
        bronse_drill_from_brokenR = MineFantasyAPI.addAnvilRecipe(SkillList.engineering, new ItemStack(bronse_drill),"drill_info",false,"hammer",2, 2, 10,  new Object[]{
                " T    ","TXT   ","XRX   ","RCR   ", 'X', ComponentListMF.bar("Tungsten"),'T', ComponentListMF.diamond_shards,'R', ComponentListMF.bar("Tin"),'C', drill_handle});
        bronse_drillR = MineFantasyAPI.addAnvilRecipe(SkillList.engineering, new ItemStack(bronse_drill),"drill_info",false,"hammer",2, 2, 10,  new Object[]{
                " T    ","TXT   ","XRX   ","RCR   ", 'X', ComponentListMF.bar("Tungsten").getItem(),'T', ComponentListMF.diamond_shards,'R', ComponentListMF.bar("Tin").getItem(),'C', bronse_drill_broken});
    }

    public static void initAriamisItems(){
        alchemy_table = new BlockTable();
        fog = new FogBlock();
    }

    public static void initYL(){
        grindstone = new BlockGrindstone(Material.wood);
        sarcofag = new BlockSarcofag();
        grindstoneStone = new ItemGrindstoneStone();
        EntityRegistry.registerModEntity(EntityFlag.class, "ygcFlag", 0, Ariamis.instance, 160, Integer.MAX_VALUE, false);
        EntityRegistry.registerModEntity(EntityBanner.class, "ygcBanner", 1, Ariamis.instance, 160, Integer.MAX_VALUE, false);




        flagSmall = (new ItemFlag(0, "small"));
        flagLarge = (new ItemFlag(2, "large"));
        bannerSmall = (new ItemBanner(0, "small"));
        bannerLarge = (new ItemBanner(2, "large"));
        GameRegistry.registerItem(flagSmall, "flagSmall", Ariamis.MODID);
        GameRegistry.registerItem(flagLarge, "flagLarge", Ariamis.MODID);
        GameRegistry.registerItem(bannerSmall, "bannerSmall", Ariamis.MODID);
        GameRegistry.registerItem(bannerLarge, "bannerLarge", Ariamis.MODID);
        for (int i = 1; i < 16; i++){
            addShapelessRecipe(new ItemStack(bannerSmall, 1, i), new ItemStack(bannerSmall, 1), new ItemStack(dye, 1, i));
            addShapelessRecipe(new ItemStack(bannerLarge, 1, i), new ItemStack(bannerLarge, 1), new ItemStack(dye, 1, i));
            addShapelessRecipe(new ItemStack(flagSmall, 1, i), new ItemStack(flagSmall, 1), new ItemStack(dye, 1, i));
            addShapelessRecipe(new ItemStack(flagLarge, 1, i), new ItemStack(flagLarge, 1), new ItemStack(dye, 1, i));
        }
    }
    public static void recipes(){
        flags = (new InformationBase("flags", 1, -2, 0, bannerLarge,  (InformationBase)null)).registerStat().setPage(KnowledgeListMF.construction).setUnlocked();
        repairing = (new InformationBase("repairing", 8, 2, 0, grindstoneStone,  (InformationBase)null)).registerStat().setPage(KnowledgeListMF.artisanry).setUnlocked();

        grindstoneStone_r = MineFantasyAPI.addCarpenterToolRecipe(SkillList.construction, new ItemStack(grindstoneStone), "repairing", "hammer", "hammer", 0, 10,  new Object[]{" X  ", "X X "," X  ","    ",  'X', Blocks.sandstone});
        grindstonetable_r = MineFantasyAPI.addCarpenterToolRecipe(SkillList.construction, new ItemStack(grindstone), "repairing", "hammer", "hammer", 0, 10,  new Object[]{"PVPV", "SSSS","WPW ","W W ", 'W', Blocks.log, 'S', Items.stick, 'V', ComponentListMF.vine, 'P', ComponentListMF.plank });


        flag_small_r = MineFantasyAPI.addCarpenterToolRecipe(SkillList.construction, flagSmall, "flags", "hammer",  1,      new Object[]{"PWW ", "PWW ","P   ","P   ", 'W', new ItemStack(Blocks.wool, 1,0), 'P', ComponentListMF.plank });
        flag_large_r = MineFantasyAPI.addCarpenterToolRecipe(SkillList.construction, flagLarge, "flags", "hammer", 1,       new Object[]{"PWWW", "PWWW","PWWW","P   ", 'W', new ItemStack(Blocks.wool, 1,0), 'P', ComponentListMF.plank });
        banner_small_r = MineFantasyAPI.addCarpenterToolRecipe(SkillList.construction, bannerSmall, "flags", "hammer", 1,   new Object[]{"PP  ", "WW  ","WW  ","    ", 'W', new ItemStack(Blocks.wool, 1,0), 'P', ComponentListMF.plank });
        banner_large_r = MineFantasyAPI.addCarpenterToolRecipe(SkillList.construction,bannerLarge, "flags", "hammer", 1,    new Object[]{"PPP ", "WWW ","WWW ","WWW ", 'W', new ItemStack(Blocks.wool, 1,0), 'P', ComponentListMF.plank });

        Ariamis.proxy.registerPages();

    }
    private static void addShapelessRecipe(ItemStack output, Object... params) {
        GameRegistry.addRecipe(new ShapelessOreRecipe(output, params));
    }



}
