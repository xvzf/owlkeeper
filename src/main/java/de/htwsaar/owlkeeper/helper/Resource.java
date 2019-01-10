
package de.htwsaar.owlkeeper.helper;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

/**
 * Resource
 */
public class Resource {
    public static String getClassResourceAsString(Object o, String name) throws ResourceNotFoundException {
        InputStream inputStream = o.getClass().getResourceAsStream(name);

        if(inputStream == null) {
            throw new ResourceNotFoundException();
        }

        try {
            return IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        } catch (IOException ie) {
            throw new ResourceNotFoundException();
        }
    }
}