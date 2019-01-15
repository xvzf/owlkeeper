package de.htwsaar.owlkeeper.storage.local.config;

import de.htwsaar.owlkeeper.helper.constants.DatabaseConstants.DATABASE_CONFIGS;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleConfigTest {
    @Test
    void testConfigCreated() {
        ConfigLoader.loadDatabaseConfiguration();
    }

    @Test
    void testPreferencesToProperties() {
        DatabaseConnectionConfig dat = ConfigLoader.loadDatabaseConfiguration();
        Properties p = dat.connectionProperties();
        assertTrue(p.containsKey("use_ssl") && p.containsKey("user") && p.containsKey("password"));
        assertFalse(p.containsKey("url"));
    }

    @Test
    void testConfigLoadAndThenEdit() {
        DatabaseConnectionConfig dat = ConfigLoader.loadDatabaseConfiguration();

        assertEquals("owlkeeper", dat.getUser());
        ConfigEditor.editDatabaseConfiguration(DATABASE_CONFIGS.USER, "testvalue22");
        assertEquals("testvalue22", dat.getUser());
        ConfigEditor.editDatabaseConfiguration(DATABASE_CONFIGS.USER, "owlkeeper");
        assertEquals("owlkeeper", dat.getUser());
    }
}
