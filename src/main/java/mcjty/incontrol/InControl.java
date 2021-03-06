package mcjty.incontrol;


import mcjty.incontrol.cache.StructureCache;
import mcjty.incontrol.proxy.CommonProxy;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import org.apache.logging.log4j.Logger;

@Mod(modid = InControl.MODID, name = InControl.MODNAME,
        dependencies =
                "required-after:compatlayer@[" + InControl.COMPATLAYER_VER + ",);" +
                "after:Forge@[" + InControl.MIN_FORGE10_VER + ",);" +
                "after:forge@[" + InControl.MIN_FORGE11_VER + ",)",
        version = InControl.VERSION,
        acceptableRemoteVersions = "*",
        acceptedMinecraftVersions = "[1.10,1.12)")
public class InControl {

    public static final String MODID = "incontrol";
    public static final String MODNAME = "InControl";
    public static final String VERSION = "3.6.3";
    public static final String MIN_FORGE10_VER = "12.18.1.2082";
    public static final String MIN_FORGE11_VER = "13.19.0.2176";
    public static final String COMPATLAYER_VER = "0.2.5";

    @SidedProxy(clientSide = "mcjty.incontrol.proxy.ClientProxy", serverSide = "mcjty.incontrol.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static InControl instance;

    public static Logger logger;

    public static boolean lostcities = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
        lostcities = Loader.isModLoaded("lostcities");
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    @Mod.EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(new CmdReload());
        event.registerServerCommand(new CmdDebug());
        event.registerServerCommand(new CmdLoadSpawn());
        event.registerServerCommand(new CmdLoadPotentialSpawn());
        event.registerServerCommand(new CmdLoadSummonAid());
        event.registerServerCommand(new CmdLoadLoot());
        event.registerServerCommand(new CmdShowMobs());
        event.registerServerCommand(new CmdKillMobs());
    }

    @Mod.EventHandler
    public void serverStopped(FMLServerStoppedEvent event) {
        StructureCache.CACHE.clean();
    }
}
