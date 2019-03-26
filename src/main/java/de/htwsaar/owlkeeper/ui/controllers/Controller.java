package de.htwsaar.owlkeeper.ui.controllers;

import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.UiScene;

public abstract class Controller{

    private UiApp uiApp;
    private UiScene uiScene;

    /**
     * Sets the Application UI Reference
     *
     * @param app Main UiApp reference
     */
    public void setApp(UiApp app){
        this.uiApp = app;
    }

    /**
     * Sets the Application UIScene Reference
     *
     * @param scene Main UiScene scene
     */
    public void setUiScene(UiScene scene){
        this.uiScene = scene;
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
     * Gets the Application UI Reference
     *
     * @return Main UiScene reference
     */
    public UiScene getUiScene(){
        return this.uiScene;
    }

    /**
     * Main entry point for the Controller
     */
    public void init(){};
}
