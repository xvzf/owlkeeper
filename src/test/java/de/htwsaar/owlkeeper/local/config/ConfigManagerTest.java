package de.htwsaar.owlkeeper.local.config;


import de.htwsaar.owlkeeper.helper.Resource;
import de.htwsaar.owlkeeper.storage.local.config.ConfigurationManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConfigManagerTest {

    @AfterEach
    void teardown() {
        ConfigurationManager.reset();
    }

    private ConfigurationManager getManager(String path) {
        return ConfigurationManager.getConfigManager(this.getClass(), path);
    }

    @Test
    void testConfigLoad() {
        ConfigurationManager man = getManager("/configLoadTest.properties");
        HashSet<String> expected = new HashSet<>();
        expected.add("db");
        expected.add("other");
        expected.add("some_section");
        assertTrue(man.listSections().containsAll(expected));
    }

    @Test
    void testConfigLoad2() {
        ConfigurationManager man = getManager("/configLoadTest2.properties");
        Properties expected = Resource.getProperties(this, "/configLoadTest2.properties");
        expected.remove("[test]"); // Get rid of the section
        expected.remove("[test2]");
        assertTrue(() -> {
            expected.forEach((k, v) -> {

            });
            return true;
        });
    }

    @Test
    void testConfigSyntaxWhitespace() {
        ConfigurationManager man = getManager("/configWhiteSpaceTest.properties");

    }
}
