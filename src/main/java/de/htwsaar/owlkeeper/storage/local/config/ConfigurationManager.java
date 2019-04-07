package de.htwsaar.owlkeeper.storage.local.config;

import de.htwsaar.owlkeeper.helper.Resource;
import de.htwsaar.owlkeeper.helper.exceptions.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

/**
 * The configuration manager provides a central hub for loading, retrieving and writing config files.
 * Changes to the configuration can be made simply by calling the respective methods. <br>
 * Syntax of the config file should be as follows:
 * <table>
 * <tr>
 * <th>[section1]</th>
 * </tr>
 * <tr>
 * <td align="right">key1</td>
 * <td> = </td>
 * <td>value1</td>
 * </tr>
 * <tr>
 * <td align="right">key2</td>
 * <td> = </td>
 * <td>value2</td>
 * </tr>
 * <tr>
 * <th>[section2]</th>
 * </tr>
 * <tr>
 * <td align="right">key3</td>
 * <td> = </td>
 * <td>value3</td>
 * </tr>
 * </table>
 * Keys within the same section must be unique but different section can have the same keys. Arbitrary amounts of whitespace are allowed too but will be overwritten.
 */
public class ConfigurationManager {
    public static  String DEFAULT_CONFIG = "/owlkeeper.properties";
    private static ConfigurationManager manager;
    private static Logger logger = LogManager.getLogger(ConfigurationManager.class);
    private final String path; // path to the loaded config file.
    private final HashMap<String, Properties> availableProperties;

    /**
     * Creates a Configuration Manager which loads to config from the given config.
     *
     * @param path the relative path to the config in the resource folder.
     */
    private ConfigurationManager(String path) {
        this(ConfigurationManager.class, path);
    }

    private ConfigurationManager(Class<?> clazz, String path) {
        availableProperties = new HashMap<>();

        try {
            this.path = Resource.resourceToAbsolutePath(clazz, path);
            new ConfigFileParser().parse();
        } catch (IOException e) {
            logger.error(e);
            throw new ConfigurationException(e.getMessage());
        }
    }

    /**
     * Retrieves an active configuration manager with default config file location.
     *
     * @return The active configuration manager.
     */
    public static ConfigurationManager getConfigManager() {
        if (manager == null) {
            manager = new ConfigurationManager(DEFAULT_CONFIG);
        }
        return manager;
    }

    /**
     * Gets a ConfigurationManager
     */
    public static ConfigurationManager getConfigManager(Class<?> clazz, String path) {
        if (manager == null) manager = new ConfigurationManager(clazz, path);

        return manager;
    }

    /**
     * Retrieves the Properties for the given section.
     *
     * @param section The section which should be retrieved.
     * @return The Properties object containing the configuration of this section.
     */
    public Properties getConfig(String section) {
        return availableProperties.get(section);
    }

    /**
     * Adds a new section to the properties.
     *
     * @param section the name of the section to add.
     */
    public void addSection(String section) {
        availableProperties.putIfAbsent(section, new Properties());
    }

    /**
     * Deletes a section.
     *
     * @param section the section to delete.
     */
    public void deleteSection(String section) {
        availableProperties.remove(section);
    }

    /**
     * Retrieves a set of available configuration sections.
     *
     * @return A set containing all available configs.
     */
    public Set<String> listSections() {
        return availableProperties.keySet();
    }

    /**
     * Resets the Config Manager for the case if a different one shall be loaded.
     */
    public static void reset() {
        manager = null;
    }

    /**
     * Changes the existing configuration by editing existing key - value pairs or adding new ones.
     *
     * @param section the section to edit
     * @param key     the key whose value should be edited.
     * @param value   the value to change or add
     * @throws ConfigurationException if the given section does not exist
     */
    public void changeConfig(String section, String key, String value) {
        if (!sectionExists(section)) {
            throw new ConfigurationException("Configuration section [" + section + "] does not exist in file " + path);
        }
        Properties p = getConfig(section);
        p.setProperty(key, value);

        writeConfig();
    }

    private boolean sectionExists(String section) {
        return listSections().contains(section);
    }

    /**
     * Writes the config to the config file.
     */
    private void writeConfig() {
        Stream<String> writeStream = availableProperties.entrySet().parallelStream().flatMap(property -> Stream
                .of("[" + property.getKey() + "]\n" + property.getValue().entrySet().parallelStream()
                        .flatMap(entry -> Stream.of(entry.getKey() + "=" + entry.getValue() + "\n"))
                        .reduce("", (str1, str2) -> str1 + str2)));
        // Some explanation: The availableProperties are converted into a Set of entries. These each have a key (the section)
        // and a properties. These are in turn another map of String to String, thus turned into another set of entries, where each
        // entry is flattened, turned into a stream and all of them are reduced to a string which is concatenated with the section.

        try (BufferedWriter bf = new BufferedWriter(new FileWriter(path))) {
            writeStream.forEach(s -> {
                try {
                    bf.write(s);
                } catch (IOException e) {
                    logger.error(e);
                    throw new ConfigurationException("Unable to write to file.");
                }
            });
        } catch (IOException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    /**
     * A config file parser that is capable of reading and loading a file.
     */
    private class ConfigFileParser {

        private void parse() throws IOException, ConfigurationException {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            String currentlyActiveConfigSection = ""; // The config section which is currently being read.
            int readSectionCounter = 0;

            // Read the file line by line
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing whitespace
                if (line.isEmpty()) { // Ignore empty lines
                    continue;
                }
                if (line.startsWith("[")) { // Found a new config section
                    if (!line.endsWith("]")) { // config sections must end with ]
                        ConfigurationException e = new ConfigurationException(
                                "An error occurred reading the configuration at the line \" " + line + "\". Missing \"]\"");
                        logger.error(e);
                        br.close();
                        throw e;
                    }
                    currentlyActiveConfigSection = line.substring(1, line.length() - 1).trim();  // Remove [] at beginning and end
                    if (currentlyActiveConfigSection.isEmpty()) {
                        ConfigurationException e = new ConfigurationException(
                                "An error occurred reading the configuration at the line \" " + line + "\". Empty section name");
                        logger.error(e);
                        br.close();
                        throw e;
                    }
                    addSection(currentlyActiveConfigSection);
                    readSectionCounter++;

                } else { // Line isn't a section line. Assume it's a key value line
                    // Split the line at the = sign, left is the key, right the value.
                    String[] entries = line.split("=");
                    String value;
                    String key;
                    if (readSectionCounter == 0 || currentlyActiveConfigSection.isEmpty()) {
                        ConfigurationException e = new ConfigurationException(
                                "The configuration file started without a section tag.");
                        logger.error(e);
                        br.close();
                        throw e;
                    }

                    if (entries.length < 2) {
                        ConfigurationException e = new ConfigurationException(
                                "An error occurred reading the configuration at the line \"" + line + "\". Invalid Syntax");
                        logger.error(e);
                        br.close();
                        throw e;
                    }

                    // In case there's a = sign in the value, it unfortunately gets split. Lets put it back in
                    value = String.join("=", Arrays.copyOfRange(entries, 1, entries.length));

                    // Remove leading and trailing whitespaces
                    value = value.trim();
                    key = entries[0].trim();

                    if (key.isEmpty()) {
                        ConfigurationException e = new ConfigurationException(
                                "An error occurred reading the configuration at the line \"" + line + "\". Invalid Syntax, empty key");
                        logger.error(e);
                        br.close();
                        throw e;
                    }
                    getConfig(currentlyActiveConfigSection).setProperty(key, value);
                }
            }
            br.close();
        }
    }
}
