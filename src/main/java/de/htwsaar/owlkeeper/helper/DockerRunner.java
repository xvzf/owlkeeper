package de.htwsaar.owlkeeper.helper;

import de.htwsaar.owlkeeper.storage.local.config.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Execute docker-compose files
 */
public class DockerRunner {

    private static final String PROPERTY_SECTION = "docker";
    private static final String PROPERTY_KEY = "executable";
    private static final String YML_FILE_NAME = "/docker-compose.yml";

    private static final String LOGGER_TEXT_EXECUTABLE = "Docker: ";
    private static final String LOGGER_TEXT_ARGUMENTS = "Executing: ";

    private static final String EXECUTABLE_ARGUMENT_DELIMITER = " ";

    private static Logger logger = LogManager.getLogger(SQLFileRunner.class);

    private String argument;

    /**
     * Constructor
     *
     * @param argument the arguments for docker
     */
    public DockerRunner(String argument) {
        this.argument = argument;
    }

    /**
     * Execute it
     *
     * @throws IOException when executable can't be found
     */
    public void run() throws IOException {
        ConfigurationManager cm = ConfigurationManager.getConfigManager();
        String executable = cm.getConfig(PROPERTY_SECTION).getProperty(PROPERTY_KEY);
        logger.info(LOGGER_TEXT_EXECUTABLE + executable);
        logger.info(LOGGER_TEXT_ARGUMENTS + argument);
        String ymlfile = Resource.resourceToAbsolutePath(DockerRunner.class, YML_FILE_NAME);
        String cmd = executable + ymlfile + EXECUTABLE_ARGUMENT_DELIMITER + argument;
        ProcessRunner.run(cmd, null);
    }
}
