
package de.htwsaar.owlkeeper.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Resource helper class
 */
public class Resource {
    private static Logger logger = LogManager.getLogger(Resource.class);

    /**
     * Loads a resource into a string
     *
     * @param o Current object
     * @param path Path to the resource
     * @return String representation of the resource
     * @throws ResourceNotFoundException Resource could not be accessed
     */
    public static String getResourceAsString(Object o, String path) throws ResourceNotFoundException {
        InputStream inputStream = o.getClass().getResourceAsStream(path);

        if(inputStream == null) {
            logger.error("Could not open resource @ " + path);
            throw new ResourceNotFoundException();
        }

        try {
            return IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        } catch (IOException ie) {
            logger.error("Could not read resource @ " + path, ie);
            throw new ResourceNotFoundException();
        }
    }
}