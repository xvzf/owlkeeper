package de.htwsaar.owlkeeper.storage.local.config;

import de.htwsaar.owlkeeper.helper.Resource;
import de.htwsaar.owlkeeper.helper.exceptions.ConfigurationException;
import de.htwsaar.owlkeeper.helper.exceptions.ResourceNotFoundException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;

public class ConfigHelper {
    private static Logger logger = LogManager.getLogger(ConfigHelper.class);

    /**
     * Encodes a configuration as a loadable string
     *
     * @param config the configuration, loaded into a string
     * @return a decoded string which can be used to load a config on another machine.
     */
    public static String encodeConfig(String config) {
        return Base64.getUrlEncoder().encodeToString(config.getBytes());
    }

    /**
     * Reads a base64 String and returns the encoded configuration.
     *
     * @param base64String A encoded Base64 string created by {@link #encodeConfig(String)} which represents an entire config file.
     * @return A Configuration Manager handling the given config saved to the default config location.
     * @throws ConfigurationException if an exception occurred creating or writing to the config file.
     */
    public static ConfigurationManager getConfigManagerFromString(String base64String) {
        return getConfigManagerFromString(base64String, ConfigurationManager.DEFAULT_CONFIG, Resource.class);
    }

    /**
     * Reads a base64 String and returns the encoded configuration.
     *
     * @param base64String A encoded Base64 string created by {@link #encodeConfig(String)} which represents an entire config file.
     * @param configFile   The path to the config file relative to the resources folder.
     * @param clazz        The class providing the path to load the config file.
     * @return A Configuration Manager handling the given config
     * @throws ConfigurationException if an exception occurred creating or writing to the config file.
     */
    public static ConfigurationManager getConfigManagerFromString(String base64String, String configFile,
            Class<?> clazz) {
        String absolutePath;
        byte[] config = Base64.getUrlDecoder().decode(base64String);
        try {
            absolutePath = Resource.resourceToAbsolutePath(clazz, configFile);
        } catch (ResourceNotFoundException e) {
            logger.warn("Given config location could not be found. Using default config location", e);
            absolutePath = Resource.resourceToAbsolutePath(ConfigurationManager.DEFAULT_CONFIG);
        }

        // See if the config file already exists. If not we need to create it to avoid exceptions.
        File f = new File(absolutePath);
        try {
            if (f.createNewFile()) {
                logger.info("Created program config file in " + absolutePath);
            }
        } catch (IOException e) {
            ConfigurationException ex = new ConfigurationException(
                    "Error creating new config file. - " + e.getMessage());
            logger.error(ex);
            throw ex;
        }
        // Simply write the String to a file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(f, false))) {
            String writeString = IOUtils.toString(config, "UTF-8");
            //System.out.println(writeString);
            writer.write(writeString);
        } catch (IOException e) {
            ConfigurationException ex = new ConfigurationException(
                    "Error creating new config file. - " + e.getMessage());
            logger.error(ex);
            throw ex;
        }
        // Finally just use already present parse to load a config manager.
        return ConfigurationManager.getConfigManager(clazz, configFile);
    }
}
