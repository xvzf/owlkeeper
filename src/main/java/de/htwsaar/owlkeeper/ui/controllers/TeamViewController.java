package de.htwsaar.owlkeeper.ui.controllers;

import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.partials.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class TeamViewController extends Controller{

    @FXML
    public Parent topbar;

    @FXML
    public TopBarController topbarController;


    @FXML
    public Parent navigation;

    @FXML
    public NavigationMainController navigationController;


    @FXML
    public Parent projects;

    @FXML
    public NavigationProjectsController projectsController;


    @FXML
    public Parent teams;

    @FXML
    public TeamController teamsController;


    public void init(){

        UiApp app = this.getApp();

        // Meta
        this.navigationController.setContent(app);
        this.projectsController.setContent(app);

        // Main
        this.topbarController.setTitle("Hallo Welt");
        this.teamsController.setContent();
    }
}