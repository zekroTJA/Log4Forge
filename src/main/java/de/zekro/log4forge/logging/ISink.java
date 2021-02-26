package de.zekro.log4forge.logging;

import net.minecraftforge.common.config.Configuration;

import java.util.List;

/**
 * Sink specifies a generic log sink which takes an
 * array of entries, which are passed by the EventHandler
 * from the event stash.
 *
 * The sink formats the entries and stores them to a given
 * storage medium.
 */
public interface ISink {
    /**
     * Sets the maximum amount of stored entries.
     * @param max maximum amount of stored entries.
     */
    void setMaxEntries(long max);

    /**
     * Retrieves the Configuration instance, sets
     * the configuration keys and values for that
     * sink and and configures the sink instance
     * depending on the retrieved config values.
     * @param config Configuration instance
     */
    void getConfig(Configuration config);

    /**
     * Whether or not the sink is enabled.
     * @return enabled state
     */
    boolean isEnabled();

    /**
     * Parses the passed stash of entries and appends
     * them to the desired storage medium respecting
     * the configured maximum amount of stored entries.
     *
     * @param entries entries stash
     * @throws Exception exceptions while storing
     */
    void append(List<Entry> entries) throws Exception;
}
