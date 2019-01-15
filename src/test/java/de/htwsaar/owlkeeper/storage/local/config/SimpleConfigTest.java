package de.htwsaar.owlkeeper.storage.local.config;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Properties;

public class SimpleConfigTest {
    @Test
    void testConfigCreated() {
        ConfigLoader.loadDatabaseConnection();
    }

    @Test
    void testPreferencesToProperties() {
        DatabaseConnectionConfig dat = ConfigLoader.loadDatabaseConnection();
        Properties p = dat.connectionProperties();
        assertTrue(p.containsKey("use_ssl") && p.containsKey("user") && p.containsKey("password"));
        assertFalse(p.containsKey("url"));
    }
}
