package de.htwsaar.owlkeeper.local.config;

import de.htwsaar.owlkeeper.helper.Resource;
import de.htwsaar.owlkeeper.storage.local.config.ConfigHelper;
import de.htwsaar.owlkeeper.storage.local.config.ConfigurationManager;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Base64;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

;


public class ConfigHelperTest {

    @BeforeAll
    static void setup() {
        ConfigurationManager.reset();
    }

    @AfterEach
    void teardown() {
        ConfigurationManager.reset();
    }

    @Test
    void testConfigEncode() {
        String rawData = "[a section]\n url=http://my favorite site.test/?asds\n user=admin\n password  = secret\n test=true";
        String encoded = ConfigHelper.encodeConfig(rawData);
        String actual = "";
        try {
            actual = IOUtils.toString(Base64.getDecoder().decode(encoded), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            fail("Unexpected IO error running tests.");
        }
        assertEquals(rawData, actual);
    }

    @Test
    void testConfigDecodeAndWriteSections() {
        String rawData = "[a section]\n url=http://my favorite site.test/?asds\n user=admin\n password  = secret\n test=true\n [section2]\n key=value";
        String encoded = ConfigHelper.encodeConfig(rawData);
        ConfigurationManager man = ConfigHelper
                .getConfigManagerFromString(encoded, "/configManagerTest/configBase64Test.properties", this.getClass());
        System.out.println(man.getConfig("a section"));
        assertTrue(man.listSections().contains("a section") && man.listSections().contains("section2"));
        assertEquals(2, man.listSections().size());
    }

    @Test
    void testConfigDecodeAndWriteContents() {
        String rawData = "[a section]\n url=http://my favorite site.test/?asds\n user=admin\n password  = secret\n test=true\n [section2]\n key=value";
        String encoded = ConfigHelper.encodeConfig(rawData);
        ConfigurationManager man = ConfigHelper
                .getConfigManagerFromString(encoded, "/configManagerTest/configBase64Test.properties", this.getClass());
        Properties expected = Resource.getProperties(this, "/configManagerTest/configBase64Test.properties");

        expected.remove("[a");
        expected.remove("[section2]");
        Properties actual = man.getConfig("a section");
        actual.putAll(man.getConfig("section2"));

        assertEquals(expected, actual);
    }
}
