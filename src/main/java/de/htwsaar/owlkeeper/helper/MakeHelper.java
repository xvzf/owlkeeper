package de.htwsaar.owlkeeper.helper;

import de.htwsaar.owlkeeper.storage.local.config.ConfigurationManager;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class MakeHelper {

    private String[] recipes;

    public MakeHelper(String[] recipes) {
        this.recipes = recipes;
    }

    public static void main(String[] args) throws IOException {
        new MakeHelper(args).run();
    }

    public void run() throws IOException {
        for (String recipe :
                recipes) {


        ConfigurationManager cm = ConfigurationManager.getConfigManager();
        String executable = cm.getConfig("db").getProperty("clientcommand");
        String sqlfile = cm.getConfig("sqlfiles").getProperty(recipe);
        sqlfile = Resource.getSQLResourcePath(sqlfile);
        System.out.println(sqlfile);
        System.out.println(executable);
        Process p;

        p = new ProcessBuilder(executable.split(" ")).redirectInput(new File(sqlfile)).start();

        Scanner s = new Scanner(p.getInputStream()).useDelimiter("\\Z");
        try {
            System.out.println(s.next());
        }catch (NoSuchElementException e){
            System.out.println("No Output ¯\\_(ツ)_/¯");
        }
    }}
}

