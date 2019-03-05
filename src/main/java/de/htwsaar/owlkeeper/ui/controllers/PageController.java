package de.htwsaar.owlkeeper.ui.controllers;

import de.htwsaar.owlkeeper.ui.controllers.partials.MyTasksController;
import de.htwsaar.owlkeeper.ui.controllers.partials.NavigationMainController;
import de.htwsaar.owlkeeper.ui.controllers.partials.NavigationProjectsController;
import de.htwsaar.owlkeeper.ui.controllers.partials.TopBarController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class PageController extends Controller{

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
    public Parent tasks;

    @FXML
    public MyTasksController tasksController;


    public void init(){

        // Meta
        this.navigationController.setContent();
        this.projectsController.setContent();

        // Main
        this.topbarController.setTitle("Hallo Welt");
        this.tasksController.setContent();
    }
}
