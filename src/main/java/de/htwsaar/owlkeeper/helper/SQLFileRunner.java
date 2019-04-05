package de.htwsaar.owlkeeper.helper;

import de.htwsaar.owlkeeper.storage.local.config.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Class for executing SQL files
 */
public class SQLFileRunner {

    private static Logger logger = LogManager.getLogger(SQLFileRunner.class);

    private static final String PROPERTY_SECTION_CLIENT = "db";
    private static final String PROPERTY_KEY_CLIENT = "sqlclient";
    private static final String PROPERTY_SECTION_SQL_FILE = "sqlfiles";

    private static final String LOGGER_TEXT_CLIENT = "SQL client: ";
    private static final String LOGGER_FORMAT_FILES = "Executing SQL files: [%s]";
    private static final String LOGGER_FILE_DELIMITER = ", ";
    private static final String LOGGER_TEXT_FILE = "SQL file: ";

    private String[] recipes;

    /**
     * Constructor
     *
     * @param recipes all recipes, defined in owlkeeper.properties section sql files, that should be executed
     */
    public SQLFileRunner(String[] recipes) {
        this.recipes = recipes;
    }

    /**
     * Executes all recipes
     *
     * @throws IOException when a recipe could not be found
     */
    public void run() throws IOException {
        ConfigurationManager cm = ConfigurationManager.getConfigManager();
        String executable = cm.getConfig(PROPERTY_SECTION_CLIENT).getProperty(PROPERTY_KEY_CLIENT);
        logger.info(LOGGER_TEXT_CLIENT + executable);
        logger.info(String.format(LOGGER_FORMAT_FILES, String.join(LOGGER_FILE_DELIMITER, recipes)));
        for (String recipe : recipes) {
            String sqlfile = cm.getConfig(PROPERTY_SECTION_SQL_FILE).getProperty(recipe);
            sqlfile = Resource.getSQLResourcePath(sqlfile);
            logger.info(LOGGER_TEXT_FILE + sqlfile);
            ProcessRunner.run(executable, new File(sqlfile));
        }
    }
}

