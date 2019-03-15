package de.htwsaar.owlkeeper.ui.controllers;

import de.htwsaar.owlkeeper.ui.UiApp;

public abstract class Controller{

    private UiApp uiApp;

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
     * Main entry point for the Controller
     */
    public void init(){};
}
