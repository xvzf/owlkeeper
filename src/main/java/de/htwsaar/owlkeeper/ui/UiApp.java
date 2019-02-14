package de.htwsaar.owlkeeper.ui;

import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Main Application instance for the applications gui
 * this gets instanced through the static launch() method
 * callable through UiApp.startUi()
 */
public class UiApp extends ViewApplication{

    private static ArrayList<UiScene> scenes = new ArrayList<>();

    /**
     * Adds a new Scene to the application
     *
     * @param scene new Scene instance to register in the applications GUI
     */
    public static void stageScene(UiScene scene){
        scenes.add(scene);
    }

    /**
     * Main starting point for the GUI
     * Here all scenes are finally registered and defined
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception The main entry scene named "default" must be defined before the GUI is launched
     */
    @Override
    void boot(Stage primaryStage) throws Exception{
        UiApp.scenes.forEach(scene -> {
            try {
                this.addScene(scene.getName(), scene.getBuilder());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        if (!this.hasScene(this.getCurrentSceneName())) {
            throw new Exception("Scene with the name '"+this.getCurrentSceneName()+"' must be defined");
        }
        this.switchScene(this.getCurrentSceneName());
        primaryStage.show();
    }

    /**
     * Static API to boot the GUI
     */
    public static void startUi(){
        launch();
    }
}