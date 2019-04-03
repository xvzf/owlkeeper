package de.htwsaar.owlkeeper.helper;

import de.htwsaar.owlkeeper.helper.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResourceTest {

    @Test
    void getResourceAsString() throws ResourceNotFoundException {
        // Check if we can load an arbitrary file
        assertFalse(
                Resource.getResourceAsString(new Resource(), "/log4j2.xml").trim().length() == 0
        );
    }

    @Test
    void getResourceAsStringException() {
        // Check if an exception is thrown correctly if file not exists
        assertThrows(ResourceNotFoundException.class, () -> {
            Resource.getResourceAsString(new Resource(), "//srslynotexisting"); // Unsupported file name in all OS
        });
    }

    @Test
    void getSQLResource() throws ResourceNotFoundException {
        assertFalse(
                Resource.getSQLResource("bootstrap/tables.sql").trim().length() == 0
        );
    }

    @Test
    void getSQLResourceException() {
        // Check if an exception is thrown correctly if file not exists
        assertThrows(ResourceNotFoundException.class, () -> {
            Resource.getSQLResource("//afssafasfsafsafsakhfsafksajh"); // Unsupported file name in all OS
        });
    }
}
