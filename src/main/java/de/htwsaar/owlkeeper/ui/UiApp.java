package de.htwsaar.owlkeeper.ui;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main Application instance for the applications gui
 * this gets instanced through the static launch() method
 * callable through UiApp.startUi()
 */
public class UiApp extends ViewApplication{

    private static HashMap<String, UiScene> scenes = new HashMap<>();

    /**
     * Adds a new Scene to the application
     *
     * @param scene new Scene instance to register in the applications GUI
     */
    public static void stageScene(UiScene scene){
        scenes.put(scene.getName(), scene);
    }

    /**
     * Routes the application to the given scene using the given data object as route-information
     *
     * @param key  name of the new scene
     * @param data data object passed to the scenes state
     */
    public void route(String key, HashMap<String, Object> data){
        UiScene scene = scenes.get(key);
        scene.getState().handleQuery(data);
        scene.getController().boot(scene.getState().collectState());
        this.switchScene(key);
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
        UiApp.scenes.forEach((name, scene) -> {
            try {
                scene.setApp(this);
                this.addScene(name, scene.getBuilder());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        if (!this.hasScene(this.getCurrentSceneName())) {
            throw new Exception("Scene with the name '" + this.getCurrentSceneName() + "' must be defined");
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
