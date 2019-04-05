package de.htwsaar.owlkeeper.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Helperclass to run processes
 */
public class ProcessRunner {
    private static Logger logger = LogManager.getLogger(ProcessRunner.class);

    /**
     * Run process
     * Output goes to the logger
     *
     * @param cmd           The entire commandline with all arguments. Accepts no shell features like pipes
     * @param redirectinput null, if not input shall be redirected, otherwise a file being fed to the process stdin
     * @throws IOException
     */
    public static void run(String cmd, File redirectinput) throws IOException {
        ProcessBuilder pb = new ProcessBuilder(cmd.split(" "));
        if (redirectinput != null) {
            pb.redirectInput(redirectinput);
        }

        Process p = pb.start();
        try (Scanner s = new Scanner(p.getInputStream()).useDelimiter("\\Z")) {
            logger.info("Output: " + s.next());
        } catch (NoSuchElementException e) {
            logger.info("Output: Empty ¯\\_(ツ)_/¯");
        }
    }
}
