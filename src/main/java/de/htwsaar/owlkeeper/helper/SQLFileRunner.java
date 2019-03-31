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

    private String[] recipes;

    /**
     * Constructor
     *
     * @param recipes all recipes, defined in owlkeeper.properties section sqlfiles, that should be executed
     */
    public SQLFileRunner(String[] recipes) {
        this.recipes = recipes;
    }

    /**
     * Executes
     *
     * @throws IOException
     */
    public void run() throws IOException {
        ConfigurationManager cm = ConfigurationManager.getConfigManager();
        String executable = cm.getConfig("db").getProperty("sqlclient");
        logger.info("SQL client: " + executable);
        logger.info("Executing SQL files: [" + String.join(", ", recipes) + "]");
        for (String recipe : recipes) {
            String sqlfile = cm.getConfig("sqlfiles").getProperty(recipe);
            sqlfile = Resource.getSQLResourcePath(sqlfile);
            logger.info("SQL file: " + sqlfile);
            ProcessRunner.run(executable, new File(sqlfile));
        }
    }
}

