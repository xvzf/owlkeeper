package de.htwsaar.owlkeeper.ui;

import de.htwsaar.owlkeeper.ui.state.State;
import de.htwsaar.owlkeeper.ui.controllers.Controller;
import javafx.fxml.FXMLLoader;

/**
 * Interface to define a new Scene in the main application GUI
 */
public abstract class UiScene{
    private UiApp uiApp;
    private State state;
    private Controller controller;

    /**
     * Inits the scene state
     */
    public UiScene(){
        this.state = this.initState();
        this.state.handleQuery(null);
    }

    public abstract String getName();

    public abstract ViewApplication.SceneBuilder getBuilder();

    public void prepareFxml(FXMLLoader loader){
        Controller c = loader.getController();
        this.controller = c;
        if (c != null) {
            c.setApp(this.getApp());
            c.setUiScene(this);
            c.init();
        }
    }

    /**
     * Sets the Application UI Reference
     *
     * @param app Main UiApp reference
     */
    public void setApp(UiApp app){
        this.uiApp = app;
    }

    /**
     * Gets the Application UI Reference
     *
     * @return Main UiApp reference
     */
    public UiApp getApp(){
        return this.uiApp;
    }

    /**
     * Returns the UIScenes current state Object
     *
     * @return result of the State collectState method
     */
    public State getState(){
        return this.state;
    }

    /**
     * Returns the fxml attached controller for this
     * UiScene
     * @return the controller created by the fxml syntax
     */
    public Controller getController(){
        return this.controller;
    }

    /**
     * Inits the default scene state
     * as null
     *
     * @return State instance
     */
    public State initState(){
        return new State(){
            @Override
            public void handleQuery(Object o){
            }

            @Override
            public Object collectState(){
                return null;
            }
        };
    }
}