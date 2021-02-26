package de.zekro.log4forge;

import de.zekro.log4forge.config.Config;
import de.zekro.log4forge.logging.EventLogger;
import de.zekro.log4forge.logging.ISink;
import de.zekro.log4forge.logging.sinks.JsonFileSink;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.commons.lang3.ClassUtils;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Mod(
        modid = Log4Forge.MODID,
        name = Log4Forge.NAME,
        version = Log4Forge.VERSION,
        serverSideOnly = true,
        acceptableRemoteVersions = "*"
)
public class Log4Forge {
    public static final String MODID = "log4forge";
    public static final String NAME = "Log4Forge";
    public static final String VERSION = "1.0";

    private static Logger logger;

    private static Config config;
    private static EventLogger eventLogger;

    private static final List<ISink> sinks = Arrays.asList(new JsonFileSink());

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();

        config = new Config(event);
        Configuration cfg = config.init();

        eventLogger = new EventLogger(
                config.getMaxEntries(),
                config.getSavePeriodSeconds() * 1000);

        sinks.forEach(s -> {
            s.getConfig(cfg);
            eventLogger.addSink(s);
        });

        cfg.save();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) { }

    public static Logger getLogger() {
        return logger;
    }

    public static EventLogger getEventLogger() {
        return eventLogger;
    }
}
