package de.htwsaar.owlkeeper.ui.controllers;

import de.htwsaar.owlkeeper.ui.controllers.partials.*;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class PageIterationController{

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
    public Parent iteration;

    @FXML
    public IterationBarController iterationController;


    @FXML
    public Parent tasks;

    @FXML
    public MyTasksController tasksController;


    @FXML
    public void initialize(){

        // Meta
        this.navigationController.setContent();
        this.projectsController.setContent();

        // Main
        this.topbarController.setTitle("Hallo Welt");
        // this.iterationController.initialize();
        this.tasksController.setContent();
    }
}
