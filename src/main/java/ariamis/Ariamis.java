package ariamis;
import ariamis.items.ItemRegistry;
import ariamis.net.*;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mod(modid = Ariamis.MODID, version = Ariamis.VERSION)
public class Ariamis{
    public static final String MODID = "ariamis";
    public static final String VERSION = "0.1";
    public static SimpleNetworkWrapper network;

    @SidedProxy(modId=MODID, clientSide = "ariamis.ClientProxy", serverSide = "ariamis.CommonProxy")
    public static CommonProxy proxy;


    @Mod.Instance(value = MODID)
    public static Ariamis instance;

    @EventHandler
    public void pre_init(FMLPreInitializationEvent event) {

    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        network.registerMessage(PacketTileEntityDataHandler.class, PacketTileEntityData.class, 3, Side.CLIENT);//used for grindstone
        network.registerMessage(PacketTileEntityClientEventHandler.class, PacketTileEntityClientEvent.class, 5, Side.SERVER);

        //network.registerMessage(PacketExtendedEntityPropertiesDataHandler.class, PacketExtendedEntityPropertiesData.class, 0, Side.CLIENT);
        // network.registerMessage(PacketEntityDataHandler.class, PacketEntityData.class, 1, Side.CLIENT);
        //network.registerMessage(PacketGuiActionHandler.class, PacketGuiAction.class, 4, Side.SERVER); // one of this packet types is needed
        ItemRegistry.initYL();
    }
    @EventHandler
    public void post_init(FMLPostInitializationEvent event) {
        ItemRegistry.recipes();
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
