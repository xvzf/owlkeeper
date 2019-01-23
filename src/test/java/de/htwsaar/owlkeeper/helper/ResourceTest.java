package de.htwsaar.owlkeeper.helper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
                Resource.getSQLResource("bootstrap.sql").trim().length() == 0
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