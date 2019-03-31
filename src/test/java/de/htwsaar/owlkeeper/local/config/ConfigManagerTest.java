package de.htwsaar.owlkeeper.local.config;


import de.htwsaar.owlkeeper.helper.Resource;
import de.htwsaar.owlkeeper.helper.exceptions.ConfigurationException;
import de.htwsaar.owlkeeper.storage.local.config.ConfigurationManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigManagerTest {

    @BeforeAll
    static void setup() {
        ConfigurationManager.reset();
    }

    @AfterEach
    void teardown() {
        ConfigurationManager.reset();
    }

    private ConfigurationManager getManager(String path) {
        return ConfigurationManager.getConfigManager(this.getClass(), path);
    }

    @Test
    void testConfigLoadSections() {
        ConfigurationManager man = getManager("/configManagerTest/configLoadTest.properties");
        HashSet<String> expected = new HashSet<>();
        expected.add("db");
        expected.add("other");
        expected.add("some_section");

        assertTrue(man.listSections().containsAll(expected));
    }

    @Test
    void testConfigLoadPairs() {
        ConfigurationManager man = getManager("/configManagerTest/configLoadTest2.properties");
        Properties expected = Resource.getProperties(this, "/configManagerTest/configLoadTest2.properties");
        expected.remove("[test]"); // Get rid of the sections
        expected.remove("[test2]");
        Properties actual = man.getConfig("test");
        actual.putAll(man.getConfig("test2"));

        assertEquals(expected, actual);
    }

    @Test
    void testConfigWhitespace() {
        ConfigurationManager man = getManager("/configManagerTest/configWhiteSpaceTest.properties");
        assertTrue(man.listSections().contains("section") && man.listSections().contains("section2")); // All sections there?
        assertTrue(man.getConfig("section").size() == 1 && man.getConfig("section2").size() == 1); // No extra pairs are loaded?
        assertTrue(man.getConfig("section").getProperty("key").equals("value1")); // No Whitespace even if theres whitespace between the equls?

    }

    @Test
    void testWhitespaceInValue() { // All whitespace inside should be preserved
        ConfigurationManager man = getManager("/configManagerTest/configWhiteSpaceTest2.properties");
        assertEquals("something that has a whitespace.", man.getConfig("section1").getProperty("valueWhitespace"));
    }

    @Test
    void testWhitespaceInKey() {
        ConfigurationManager man = getManager("/configManagerTest/configWhiteSpaceTest2.properties");
        assertTrue(man.getConfig("section2").stringPropertyNames().contains("key space"));
    }

    @Test
    void testWhitespaceInSection() {
        ConfigurationManager man = getManager("/configManagerTest/configWhiteSpaceTest2.properties");
        List<String> expected = new ArrayList<>();
        expected.add("section1");
        expected.add("section2");
        expected.add("section whitespace");
        assertTrue(man.listSections().containsAll(expected));
        assertTrue(man.listSections().size() == 3);
    }

    @Test
    void testNoKeysInSection() { // What happens if theres just a section tag, nothing else?
        ConfigurationManager man = getManager("/configManagerTest/configEdgecaseTest.properties");
        List<String> expected = new ArrayList<>();
        expected.add("section");
        expected.add("section2");
        expected.add("section3");
        assertTrue(man.listSections().containsAll(expected));
        assertTrue(man.listSections().size() == 3);
        assertTrue(man.getConfig("section2").isEmpty() && man.getConfig("section3").isEmpty());
    }

    @Test
    void testEqualsInValue() {
        ConfigurationManager man = getManager("/configManagerTest/configEdgecaseTest.properties");
        assertEquals("value = something", man.getConfig("section").getProperty("key"));
    }

    @Test
    void testSyntaxNoSection() {
        assertThrows(ConfigurationException.class, () -> getManager("/configManagerTest/configNoSection.properties"));
    }

    @Test
    void testSyntaxKeyAndEmptyValue() {
        assertThrows(ConfigurationException.class, () -> getManager("/configManagerTest/configBadSyntaxTest.properties"));
    }

    @Test
    void testSyntaxNoKey() {
        assertThrows(ConfigurationException.class, () -> getManager("/configManagerTest/configBadSyntaxTest2.properties"));

    }

    @Test
    void testSyntaxNoEquals() {
        assertThrows(ConfigurationException.class, () -> getManager("/configManagerTest/configBadSyntaxTest3.properties"));

    }

    @Test
    void testSyntaxBadSectionDeclaration() {
        assertThrows(ConfigurationException.class, () -> getManager("/configManagerTest/configBadSyntaxTest4.properties"));
    }
}
