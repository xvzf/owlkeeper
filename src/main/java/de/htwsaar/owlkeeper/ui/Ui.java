package de.htwsaar.owlkeeper.ui;

import de.htwsaar.owlkeeper.storage.local.config.ConfigurationManager;
import de.htwsaar.owlkeeper.ui.scenes.*;

/**
 * Main class to stage the individual Application Scenes
 */
public class Ui {

    /**
     * UI Constructor Here all Scenes are stages to the UiApp class and the GUI is
     * launched
     */
    Ui() {

        /*
            Initialize with only the login view
            ---
            the full ui gets initialized after the user
            is logged in

            @see de.htwsaar.owlkeeper.ui.controllers.LoginController
        */
        UiApp.stageScene(new Login());

        // Change this to test individual scenes
        UiApp.STARTING_SCENE = "login";
        UiApp.TITLE = "Owlkeeper";
        UiApp.startUi();
    }



    /**
     * Temporary entry point to boot only the ui
     *
     * @param args CLI args
     */
    public static void main(String[] args) {
        if(args.length >0){
            ConfigurationManager.DEFAULT_CONFIG = args[0];
        }
        new Ui();
    }
}
