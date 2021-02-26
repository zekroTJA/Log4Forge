package de.zekro.log4forge.config;

import de.zekro.log4forge.Log4Forge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

/**
 * Main config wrapper.
 */
public class Config {

    private final File mainConfig;

    private long maxEntries = 10_000;
    private long savePeriodSeconds = 5 * 60;

    public Config(FMLPreInitializationEvent event) {
        File mainConfigLocation = new File(event.getModConfigurationDirectory(), Log4Forge.MODID);
        mainConfig = new File(mainConfigLocation.getPath(), Log4Forge.MODID + ".cfg");
    }

    /**
     * Initializes the main configuration and
     * returns the inner Configuration instance.
     * @return inner configuration instance
     */
    public Configuration init() {
        Configuration cfg = new Configuration(this.mainConfig);

        String category;

        category = "Main";
        cfg.addCustomCategoryComment(category, "Main configuration");
        this.maxEntries = cfg.getInt(
                "maxEntries", category, (int) maxEntries, 0, Integer.MAX_VALUE,
                "Maximum amount of entries that shall be stored in the log. 0 means no limit.");
        this.savePeriodSeconds = cfg.getInt(
                "savePeriodSeconds", category, (int) savePeriodSeconds, 5, Integer.MAX_VALUE,
                "The time period in seconds in which the log is physically stored.");

        return cfg;
    }

    public long getMaxEntries() {
        return maxEntries;
    }

    public long getSavePeriodSeconds() {
        return savePeriodSeconds;
    }
}
