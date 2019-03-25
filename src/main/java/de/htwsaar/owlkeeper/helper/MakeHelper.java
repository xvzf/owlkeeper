package de.htwsaar.owlkeeper.helper;

import de.htwsaar.owlkeeper.storage.local.config.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class MakeHelper {

    private static Logger logger = LogManager.getLogger(MakeHelper.class);

    private String[] recipes;

    public MakeHelper(String[] recipes) {
        this.recipes = recipes;
    }

    public static void main(String[] args) throws IOException {
        new MakeHelper(args).run();
    }

    public void run() throws IOException {
        ConfigurationManager cm = ConfigurationManager.getConfigManager();
        String executable = cm.getConfig("db").getProperty("clientcommand");
        logger.info("SQL client: " + executable);
        logger.info("Executing SQL files: [" + String.join(", ", recipes) +"]");
        for (String recipe : recipes) {
            String sqlfile = cm.getConfig("sqlfiles").getProperty(recipe);
            sqlfile = Resource.getSQLResourcePath(sqlfile);
            logger.info("SQL file: " + sqlfile);

            Process p = new ProcessBuilder(executable.split(" ")).redirectInput(new File(sqlfile)).start();

            Scanner s = new Scanner(p.getInputStream()).useDelimiter("\\Z");
            try {
                logger.info("Output: " + s.next());
            } catch (NoSuchElementException e) {
                logger.info("Output: Empty ¯\\_(ツ)_/¯");
            }
        }
    }
}

