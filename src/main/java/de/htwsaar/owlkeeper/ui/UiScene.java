package de.htwsaar.owlkeeper.ui;

import de.htwsaar.owlkeeper.ui.controllers.Controller;
import javafx.fxml.FXMLLoader;

/**
 * Interface to define a new Scene in the main application GUI
 */
public abstract class UiScene{
    private UiApp uiApp;

    public abstract String getName();

    public abstract ViewApplication.SceneBuilder getBuilder();

    public void prepareFxml(FXMLLoader loader){
        Controller c = loader.getController();
        if (c != null) {
            c.setApp(this.getApp());
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
}