package de.htwsaar.owlkeeper.ui;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.ui.scenes.InstallConnect;
import de.htwsaar.owlkeeper.ui.scenes.InstallDB;
import de.htwsaar.owlkeeper.ui.scenes.InstallLogin;
import de.htwsaar.owlkeeper.ui.scenes.InstallSelect;
import de.htwsaar.owlkeeper.ui.scenes.InstallSuccess;
import de.htwsaar.owlkeeper.ui.scenes.Login;
import de.htwsaar.owlkeeper.ui.scenes.Page;
import de.htwsaar.owlkeeper.ui.scenes.PageIteration;
import de.htwsaar.owlkeeper.ui.scenes.PageTeam;

/**
 * Main class to stage the individual Application Scenes
 */
public class Ui {

    /**
     * UI Constructor Here all Scenes are stages to the UiApp class and the GUI is
     * launched
     */
    Ui() {
        // @todo remove absolute positions
        // @todo fix issue with resized images
        UiApp.stageScene(new InstallConnect());
        UiApp.stageScene(new InstallDB());
        UiApp.stageScene(new InstallLogin());
        UiApp.stageScene(new InstallSelect());
        UiApp.stageScene(new InstallSuccess());
        UiApp.stageScene(new Login());
        UiApp.stageScene(new Page());
        UiApp.stageScene(new PageIteration());
        UiApp.stageScene(new PageTeam());

        // Change this to test individual scenes
        UiApp.STARTING_SCENE = "page";
        UiApp.TITLE = "Owlkeeper";
        UiApp.startUi();
    }

    /**
     * Temporary entry point to boot only the ui
     *
     * @param args CLI args
     */
    public static void main(String[] args) {
        DeveloperManager.loginDeveloper("devel1@owlkeeper.de");
        new Ui();
    }
}
