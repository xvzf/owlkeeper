
package de.htwsaar.owlkeeper.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Resource helper class
 */
public class Resource {
    private static Logger logger = LogManager.getLogger(Resource.class);

    private static final String SQL_COMMENT = "(.*)--.*$";
    private static final String SQL_PGSQL = "^ *\\\\.*$";
    private static final String SQL_TRIM = " +";

    /**
     * Loads a resource into a string
     *
     * @param o    Current object
     * @param path Path to the resource
     * @return String representation of the resource
     * @throws ResourceNotFoundException Resource could not be accessed
     */
    public static String getResourceAsString(Object o, String path) throws ResourceNotFoundException {
        InputStream inputStream = o.getClass().getResourceAsStream(path);

        if (inputStream == null) {
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

    /**
     * Loads an SQL file from
     *
     * @param name SQL File name (relative to resources/sqls/)
     * @return SQL which can be passed to the DB
     * @throws ResourceNotFoundException SQL-File could not be accessed
     */
    public static String getSQLResource(String name) throws ResourceNotFoundException {
        String commentedSql = Resource.getResourceAsString(new Resource(), "/sqls/" + name);

        Optional<String> parsedSQL = Arrays.stream(commentedSql.split("\n"))
                .map(e -> {
                    String processed = e.replaceAll(Resource.SQL_COMMENT, "$1")
                            .replaceAll(Resource.SQL_PGSQL, "");

                    return processed;
                })
                .reduce((pre, curr) -> pre + " " + curr)
                .map(e -> e.replaceAll(Resource.SQL_TRIM, " ").trim());

        if (!parsedSQL.isPresent()) {
            logger.error("Could not parse SQL-File " + name);
            throw new ResourceNotFoundException();
        }

        return parsedSQL.get();
    }

}