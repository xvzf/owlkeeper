package de.htwsaar.owlkeeper.ui;

import de.htwsaar.owlkeeper.ui.scenes.*;

/**
 * Main class to stage the individual Application Scenes
 */
public class Ui{

    /**
     * UI Constructor
     * Here all Scenes are stages to the UiApp class and the GUI is launched
     */
    Ui(){
        UiApp.stageScene(new InstallConnect());
        UiApp.stageScene(new InstallDB());
        UiApp.stageScene(new InstallLogin());
        UiApp.stageScene(new InstallSelect());
        UiApp.stageScene(new InstallSuccess());
        UiApp.stageScene(new Login());
        UiApp.stageScene(new Page());
        UiApp.stageScene(new PageIteration());
        UiApp.stageScene(new PageTeam());

        UiApp.STARTING_SCENE = "install-select";
        UiApp.startUi();
    }

    /**
     * Temporary entry point to boot only the ui
     *
     * @param args CLI args
     */
    public static void main(String[] args){
        new Ui();
    }
}
