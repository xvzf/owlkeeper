package de.htwsaar.owlkeeper.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * Main class to stage the individual Application Scenes
 */
public class Ui{

    /**
     * UI Constructor
     * Here all Scenes are stages to the UiApp class and the GUI is launched
     */
    Ui(){
        // UiApp.stageScene(new Lorem());
        // ...
        UiApp.startUi();
    }

    /**
     * Temporary entry point to boot only the ui
     * @param args CLI args
     */
    public static void main(String[] args){
        new Ui();
    }
}
