package de.htwsaar.owlkeeper.ui;

import java.util.HashMap;

import de.htwsaar.owlkeeper.ui.state.BaseState;
import de.htwsaar.owlkeeper.ui.state.State;
import javafx.stage.Stage;

/**
 * Main Application instance for the applications gui this gets instanced
 * through the static launch() method callable through UiApp.startUi()
 */
public class UiApp extends ViewApplication {

    private static HashMap<String, UiScene> scenes = new HashMap<>();

    /**
     * Adds a new Scene to the application
     *
     * @param scene new Scene instance to register in the applications GUI
     */
    public static void stageScene(UiScene scene) {
        scenes.put(scene.getName(), scene);
    }

    /**
     * Routes the application to the given scene using the given data object as
     * route-information
     *
     * @param key name of the new scene
     * @param data data object passed to the scenes state
     * @param force forces redrawing of the given scene
     */
    public void route(String key, HashMap<String, Object> data, boolean force) {
        UiScene scene = scenes.get(key);
        State state = scene.getState();
        // only handle the new query if the target scene does not have this query
        // prepared already
        if (force || !BaseState.compareQueries(state.getQuery(), data)) {
            scene.getState().handleQuery(data);
            scene.getController().boot(scene.getState().collectState());
        }
        this.switchScene(key);
    }

    /**
     * Routes the application to the given scene using the given data object as
     * route-information
     *
     * @param key name of the new scene
     * @param data data object passed to the scenes state
     */
    public void route(String key, HashMap<String, Object> data) {
        this.route(key, data, false);
    }

    /**
     * Main starting point for the GUI Here all scenes are finally registered and
     * defined
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception The main entry scene named "default" must be defined before
     *             the GUI is launched
     */
    @Override
    void boot(Stage primaryStage) throws Exception {
        this.loadScenes();
        if (!this.hasScene(this.getCurrentSceneName())) {
            throw new Exception("Scene with the name '" + this.getCurrentSceneName() + "' must be defined");
        }
        this.switchScene(this.getCurrentSceneName());
        primaryStage.setMinHeight(650);
        primaryStage.setMinWidth(950);
        primaryStage.setHeight(700);
        primaryStage.setWidth(1000);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**
     * Loads all scenes which have not been
     * loaded yet  making them available
     * with the route method
     */
    public void loadScenes(){
        UiApp.scenes.forEach((name, scene) -> {
            try {
                if (!this.hasScene(name)){
                    scene.setApp(this);
                    this.addScene(name, scene.getBuilder());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Static API to boot the GUI
     */
    public static void startUi() {
        launch();
    }
}
