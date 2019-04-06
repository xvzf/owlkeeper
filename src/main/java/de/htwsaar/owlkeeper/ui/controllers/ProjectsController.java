package de.htwsaar.owlkeeper.ui.controllers;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.partials.NavigationMainController;
import de.htwsaar.owlkeeper.ui.controllers.partials.NavigationProjectsController;
import de.htwsaar.owlkeeper.ui.controllers.partials.ProjectsListingController;
import de.htwsaar.owlkeeper.ui.controllers.partials.TopBarController;
import de.htwsaar.owlkeeper.ui.pages.Page;
import javafx.fxml.FXML;
import javafx.scene.Parent;

import java.util.HashMap;
import java.util.List;

public class ProjectsController extends Controller {
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
    public Parent projectList;

    @FXML
    public ProjectsListingController projectListController;

    public void boot(HashMap<String, Object> state) {
        UiApp app = this.getApp();

        // Meta
        this.navigationController.setContent(app, (List<Page>) state.get("pages"));
        this.projectsController.setContent(app, (HashMap<Long, Project>) state.get("projects"));

        // Main
        this.topbarController.setTitle("Projects");
        this.projectListController.setContent(app, (HashMap<Long, Project>) state.get("projects"));
    }
}
