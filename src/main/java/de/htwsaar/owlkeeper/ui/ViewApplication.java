package de.htwsaar.owlkeeper.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Extension of the JavaFx Application object to extend the functionality of it
 * while inside the GUI
 */
abstract public class ViewApplication extends Application {

    public static String TITLE = "title";
    public static String STARTING_SCENE = "default";

    /**
     * Interface used to define build processes for the individual scenes
     */
    public interface SceneBuilder {
        Scene build(ViewApplication application) throws Exception;
    }

    private HashMap<String, Scene> scenes = new HashMap<>();
    private Stage stage = null;
    private String currentScene = ViewApplication.STARTING_SCENE;

    /**
     * Main entry point for JavaFX application and starting point for the GUI
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        primaryStage.setTitle(TITLE);
        this.boot(primaryStage);
    }

    /**
     * Returns the HashMap containing all currently defined scenes
     *
     * @return HashMap of all defined Scenes with their registered name
     */
    public HashMap<String, Scene> getScenes() {
        return this.scenes;
    }

    /**
     * Returns the current primary stage
     *
     * @return The primary application stage
     */
    public Stage getStage() {
        return this.stage;
    }

    /**
     * Switches the scene to the one defined by the given key
     *
     * @param key name of the new scene
     */
    public void switchScene(String key) {
        boolean resize = false;
        Stage stage = this.getStage();

        double x, y, width, height;
        x = y = width = height = 0;
        if (stage.getScene() != null) {
            x = stage.getX();
            y = stage.getY();
            width = stage.getWidth();
            height = stage.getHeight();
            resize = true;
        }
        stage.setScene(this.scenes.get(key));
        if (resize) {
            stage.setX(x);
            stage.setY(y);
            stage.setWidth(width);
            stage.setHeight(height);
        }
        this.currentScene = key;
    }

    /**
     * Adds a new scene to the GUI
     *
     * @param key the scenes name
     * @param builder the scenes build process
     */
    public void addScene(String key, SceneBuilder builder) throws Exception {
        this.scenes.put(key, builder.build(this));
    }

    /**
     * Checks if the given scene is currently registered
     *
     * @param key name of the scene
     * @return true if a scene with the given name is registered
     */
    public boolean hasScene(String key) {
        return this.scenes.get(key) != null;
    }

    /**
     * Main starting point for the GUI Here all scenes are finally registered and
     * defined
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if something goes wrong
     */
    abstract void boot(Stage primaryStage) throws Exception;

    /**
     * Returns the current Scene name
     *
     * @return current scene name
     */
    public String getCurrentSceneName() {
        return this.currentScene;
    }

}
