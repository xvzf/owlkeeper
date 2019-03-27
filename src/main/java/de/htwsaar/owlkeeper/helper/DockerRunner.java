package de.htwsaar.owlkeeper.helper;

import de.htwsaar.owlkeeper.storage.local.config.ConfigurationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Execute docker-compose files
 */
public class DockerRunner {

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
     * @throws IOException
     */
    public void run() throws IOException {
        ConfigurationManager cm = ConfigurationManager.getConfigManager();
        String executable = cm.getConfig("docker").getProperty("executable");
        logger.info("Docker: " + executable);
        logger.info("Executing: " + argument);
        String ymlfile = Resource.resourceToAbsolutePath(DockerRunner.class, "/docker-compose.yml");
        String cmd = executable + ymlfile + " " + argument;
        ProcessRunner.run(cmd, null);
    }
}
