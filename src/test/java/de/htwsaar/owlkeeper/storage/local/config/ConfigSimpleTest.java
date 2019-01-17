package de.htwsaar.owlkeeper.storage.local.config;

import org.junit.jupiter.api.Test;

import java.util.Properties;


public class ConfigSimpleTest {

    @Test
    void test1() {
        ConfigurationManager man = ConfigurationManager.getConfigManager();
        Properties db = man.getConfig("db");
        Properties ui = man.getConfig("user_interface");
        Properties login = man.getConfig("login");

        System.out.println(db.keySet() + " -- " + db.values());
        System.out.println(ui.keySet() + " -- " + ui.values());
        System.out.println(login.keySet() + " -- " + login.values());
    }
}
