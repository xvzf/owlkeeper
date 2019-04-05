package de.htwsaar.owlkeeper.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Helper-class to run processes
 */
public class ProcessRunner {
    private static Logger logger = LogManager.getLogger(ProcessRunner.class);

    private static final String CMD_ARGUMENT_DELIMITER = " ";
    private static final String REGEX_END_OF_INPUT = "\\Z";
    private static final String LOGGER_OUTPUT_TEXT = "Output: ";
    private static final String LOGGER_OUTPUT_EMPTY_STREAM = "Empty ¯\\_(ツ)_/¯";

    /**
     * Run process
     * Output goes to the logger
     *
     * @param cmd           The entire commandline with all arguments. Accepts no shell features like pipes
     * @param redirectinput null, if not input shall be redirected, otherwise a file being fed to the process stdin
     * @throws IOException when cmd executable can't be found
     */
    public static void run(String cmd, File redirectinput) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(cmd.split(CMD_ARGUMENT_DELIMITER));
        if (redirectinput != null) {
            pb.redirectInput(redirectinput);
        }

        Process p = pb.start();
        try (Scanner s = new Scanner(p.getInputStream()).useDelimiter(REGEX_END_OF_INPUT)) {
            logger.info(LOGGER_OUTPUT_TEXT + s.next());
        } catch (NoSuchElementException e) {
            logger.info(LOGGER_OUTPUT_TEXT + LOGGER_OUTPUT_EMPTY_STREAM);
        }
    }
}
