package examplemod;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import minefantasy.mf2.api.MineFantasyAPI;
import minefantasy.mf2.api.crafting.anvil.IAnvilRecipe;
import minefantasy.mf2.api.crafting.carpenter.ICarpenterRecipe;
import minefantasy.mf2.api.knowledge.InformationBase;
import minefantasy.mf2.api.knowledge.InformationPage;
import minefantasy.mf2.api.knowledge.ResearchArtefacts;
import minefantasy.mf2.api.rpg.SkillList;
import minefantasy.mf2.item.list.ComponentListMF;
import minefantasy.mf2.knowledge.KnowledgeListMF;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "examplemod";
    public static final String VERSION = "1.0";
    public static Item bronse_drill;
    public static Item drill_handle;
    public static Item bronse_drill_broken;
    ICarpenterRecipe drill_handleR;
    IAnvilRecipe bronse_drill_from_brokenR;
    IAnvilRecipe bronse_drillR;

    public static InformationPage drills;
    public static  ArrayList<ICarpenterRecipe> drill_recipe;
    InformationBase drill_info;

    @EventHandler
    public void pre_init(FMLPreInitializationEvent event) {
        System.out.println("Registering items: "+ MODID);
        registerItemsNames();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("Registering recipes:  "+ MODID);
        registerItemRecipes();
    }
    @EventHandler
    public void post_init(FMLPostInitializationEvent event) {
        System.out.println("Registering items "+ MODID);

        //drills = new InformationPage("infoPage.dirll_info", SkillList.construction).registerInfoPage();
        drill_info = (new InformationBase("dirll_info", 10, -1, 1, new ItemStack(bronse_drill), KnowledgeListMF.tungsten)).registerStat().setPage(KnowledgeListMF.engineering).addSkill(SkillList.engineering, 30).addSkill(SkillList.artisanry, 30);
//        drill_info.addPages(new EntryPageText("knowledge.tungsten.1"),
//                new EntryPageText("knowledge.tungsten.2"), new EntryPageText("knowledge.tungsten.3"),
//                new EntryPageCrucible(KnowledgeListMF.wolframiteR));
        ResearchArtefacts.addArtefact(Items.diamond, drill_info);
        InformationPage.getInfoPage("infoPage.engineering").getInfoList().add(drill_info);


        Item hunk = ComponentListMF.metalHunk;
        drill_handleR = MineFantasyAPI.addCarpenterToolRecipe(SkillList.engineering, new ItemStack(drill_handle),
                "drill_info", "hammer", "hammer", 2, 10,  new Object[]{
                "    ", "TXRF","DSAQ","TXRF",  'T', ComponentListMF.leather_strip, 'X',ComponentListMF.plate_huge ,
                'R',ComponentListMF.rivet, 'F',ComponentListMF.bolt, 'D',ComponentListMF.iron_frame,'S',
                ComponentListMF.bronze_gears,'A', ComponentListMF.tungsten_gears,'Q', ComponentListMF.cogwork_shaft});
        bronse_drill_from_brokenR = MineFantasyAPI.addAnvilRecipe(SkillList.engineering, new ItemStack(bronse_drill),"drill_info",false,"hammer",2, 2, 10,  new Object[]{
                        " T    ","TXT   ","XRX   ","RCR   ", 'X', ComponentListMF.bar("Tungsten"),'T', ComponentListMF.diamond_shards,'R', ComponentListMF.bar("Tin"),'C', drill_handle});
        bronse_drillR = MineFantasyAPI.addAnvilRecipe(SkillList.engineering, new ItemStack(bronse_drill),"drill_info",false,"hammer",2, 2, 10,  new Object[]{
                " T    ","TXT   ","XRX   ","RCR   ", 'X', ComponentListMF.bar("Tungsten").getItem(),'T', ComponentListMF.diamond_shards,'R', ComponentListMF.bar("Tin").getItem(),'C', bronse_drill_broken});


        //drill_recipe = new ArrayList<ICarpenterRecipe>();
    }


    public void registerItemsNames(){
        bronse_drill = new ItemDrill();
        bronse_drill.setCreativeTab(creativeTab);
        drill_handle = new ItemDrillHandle();
        drill_handle.setCreativeTab(creativeTab);
        bronse_drill_broken = new ItemDrillBroken();
        bronse_drill_broken.setCreativeTab(creativeTab);

        GameRegistry.registerItem(bronse_drill, "drill");
        GameRegistry.registerItem(drill_handle, "drill_handle");
        GameRegistry.registerItem(bronse_drill_broken,"drill_broken");
    }

    public void registerItemRecipes(){
        GameRegistry.addRecipe(new ItemStack(bronse_drill, 1), new Object[]{"###", "#X#", "###", ('X'), Blocks.gravel, ('#'), Items.iron_ingot});
    }

    public static CreativeTabs creativeTab = new CreativeTabs(MODID)
    {
        @Override
        public Item getTabIconItem()
        {
            return null;
        }
        @Override
        public ItemStack getIconItemStack()
        {
            return new ItemStack(Blocks.dirt,1,1);
        }
    };
}
