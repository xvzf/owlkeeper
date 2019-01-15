package de.htwsaar.owlkeeper.storage.local.config;

import de.htwsaar.owlkeeper.helper.constants.DatabaseConstants.DATABASE_CONFIGS;

/**
 * Interface which any class that wants to use the configuration system should implement.
 */
public interface Configurable {
    /**
     * Loads from storage or creates a new configuration object.
     * @return A Config object loaded from persistent storage or created if it didn't exist.
     */
    //static Config loadConfiguration();

    /**
     * Returns the current configuration object.
     *
     * @return The currently loaded config object.
     */
    Config getConfiguration();

    /**
     * Edits the configuration entry for the given key to be the new value
     *
     * @param key   the entry to change
     * @param value the value it should now be
     * @throws ConfigurationException when an invalid key is provided.
     */
    void editConfiguration(DATABASE_CONFIGS key, final String value);
}
