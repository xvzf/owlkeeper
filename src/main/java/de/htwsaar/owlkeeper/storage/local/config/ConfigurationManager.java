package de.htwsaar.owlkeeper.storage.local.config;

import de.htwsaar.owlkeeper.helper.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 */
public class ConfigurationManager {
    private static ConfigurationManager manager;
    private static Logger logger = LogManager.getLogger(Resource.class);
    private final HashMap<String, Properties> availableProperties;


    private ConfigurationManager() {
        availableProperties = new HashMap<>();
        try {
            new ConfigFilePaser("/home/mark/GitHub Studium/owlkeeper/src/main/resources/owlkeeper.properties").parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the currently active configuration manager.
     *
     * @return The active configuration manager.
     */
    public static ConfigurationManager getConfigManager() {
        if (manager == null) {
            manager = new ConfigurationManager();
        }
        return manager;
    }

    /**
     * Retrieves the Properties for the given section.
     *
     * @param section The section which should be retrieved.
     * @return The Properties object containing the configuration of this section.
     */
    public Properties getConfig(String section) {
        availableProperties.putIfAbsent(section, new Properties());
        return availableProperties.get(section);
    }

    private class ConfigFilePaser {
        private final String path; // path to the loaded config file.

        public ConfigFilePaser(String path) {
            this.path = path;
        }

        private void parse() throws IOException, ConfigurationException {
            BufferedReader br = Resource.loadFile(path);
            String line;
            String currentlyActiveConfigSection = ""; // The config section which is currently being read.

            // Read the file line by line
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing whitespace
                if (line.isEmpty()) {
                    // Skip
                } else if (line.startsWith("[")) { // Found a new config section
                    System.out.println("Found one " + line);
                    try {
                        currentlyActiveConfigSection = line.substring(line.indexOf("[") + 1, line.indexOf("]"));
                    } catch (IndexOutOfBoundsException ioe) {
                        ConfigurationException e = new ConfigurationException("An error occurred reading the configuration at the line \" " + line + "\". It doesn't seem to adhere to the syntax.");
                        logger.error(e);
                        throw e;
                    }


                    if (currentlyActiveConfigSection.isEmpty()) { // TODO More sophisticated checks for correct syntax.
                        ConfigurationException e = new ConfigurationException("An error occurred reading the configuration at the line \" " + line + "\".");
                        logger.error(e);
                        throw e;
                    }

                } else {
//                    System.out.println("Found entry: " + line);
                    line = line.replace(" ", ""); // Remove all whitespace.
                    String[] entries = line.trim().split("="); // Split the line at the = sign, left is the key, right the value.
                    if (entries.length < 2) {
                        ConfigurationException e = new ConfigurationException("An error occurred reading the configuration at the line \" " + line + "\".");
                        logger.error(e);
                        throw e;
                    }
                    //System.out.println(currentlyActiveConfigSection + " - "+ entries[0] + "::" + entries[1]);
                    getConfig(currentlyActiveConfigSection).setProperty(entries[0], entries[1]);
                }


            }
            br.close();
        }


    }
}
