package de.htwsaar.owlkeeper.helper;

import de.htwsaar.owlkeeper.helper.exceptions.ResourceNotFoundException;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

/**
 * Resource helper class
 */
public class Resource {
    private static final String SQL_COMMENT = "(.*)--.*$";
    private static final String SQL_PGSQL = "^ *\\\\.*$";
    private static final String SQL_TRIM = " +";
    private static Logger logger = LogManager.getLogger(Resource.class);

    private static final String FOLDER_SQL_FILES = "/sqls/";

    /**
     * Creates an input stream from a resource
     *
     * @param o    Current object
     * @param path Path to the resource
     * @return InputStream
     */
    private static InputStream getResourceAsStream(Object o, String path) {
        if (o == null) {
            return null;
        }
        return o.getClass().getResourceAsStream(path);
    }

    /**
     * Returns the absolute path of a resource
     *
     * @param resourcePath The relative path to the resource in the resources folder.
     * @return The absolute file system path of the resource.
     */
    public static String resourceToAbsolutePath(String resourcePath) {
        return Resource.class.getResource(resourcePath).getPath().replace("%20", " ");
    }

    /**
     * Returns the absolute path of a resource
     * @param clazz
     * @param resourcePath
     * @return
     * @throws ResourceNotFoundException
     */
    public static String resourceToAbsolutePath(Class<?> clazz, String resourcePath) throws ResourceNotFoundException {
        URL resource = clazz.getResource(resourcePath);
        if (resource == null) { // Didnt find the resource.
            throw new ResourceNotFoundException("Resource " + resourcePath + " does not exist.");
        } else {
            return resource.getPath().replace("%20", " ");
        }
    }

    /**
     * Loads a resource into a string
     *
     * @param o    Current object
     * @param path Path to the resource
     * @return String representation of the resource
     * @throws ResourceNotFoundException Resource could not be accessed
     */
    public static String getResourceAsString(Object o, String path) throws ResourceNotFoundException {
        InputStream inputStream = getResourceAsStream(o, path);

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
        String commentedSql = Resource.getResourceAsString(new Resource(), FOLDER_SQL_FILES + name);

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

    /**
     * Retrieves the absolute path to a sql file in the sql-folder
     *
     * @param name name of the sql file
     * @return the absolute path
     * @throws ResourceNotFoundException when the file could not be found
     */
    public static String getSQLResourcePath(String name) throws ResourceNotFoundException {
        return Resource.resourceToAbsolutePath(FOLDER_SQL_FILES + name);
    }

    /**
     * Read properties
     *
     * @param name Filename
     * @return Properties
     */
    public static Properties getProperties(String name) {
        return getProperties(new Resource(), name);
    }

    /**
     * Read properties
     *
     * @param name Filename
     * @return Properties
     */
    public static Properties getProperties(Object o, String name) {
        InputStream inputStream = getResourceAsStream(o, name);

        Properties toReturn = new Properties();

        try {
            toReturn.load(inputStream);
        } catch (IOException ie) {
            logger.error(ie);
        }

        return toReturn;
    }

}
