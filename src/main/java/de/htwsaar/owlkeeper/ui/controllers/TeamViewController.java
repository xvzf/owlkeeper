package de.htwsaar.owlkeeper.ui.controllers;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.Team;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.partials.*;
import de.htwsaar.owlkeeper.ui.pages.Page;
import javafx.fxml.FXML;
import javafx.scene.Parent;

import java.util.HashMap;
import java.util.List;

public class TeamViewController extends Controller {

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

    public void boot(HashMap<String, Object> state) {
        UiApp app = this.getApp();
        this.teamsController.setApp(app);

        // Meta
        this.navigationController.setContent(app, (List<Page>) state.get("pages"));
        this.projectsController.setContent(app, (HashMap<Long, Project>) state.get("projects"));

        // Main
        this.topbarController.setTitle("Team");
        this.teamsController.setContent((List<Team>) state.get("teams"), (List<Developer>) state.get("developers"));
    }
}