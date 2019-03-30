package de.htwsaar.owlkeeper.ui.controllers;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.model.ProjectModel;
import de.htwsaar.owlkeeper.storage.model.ProjectStageModel;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.partials.*;
import de.htwsaar.owlkeeper.ui.pages.Page;
import javafx.fxml.FXML;
import javafx.scene.Parent;

import java.util.HashMap;
import java.util.List;

public class PageIterationController extends Controller{

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


    public void boot(HashMap<String, Object> state){

        UiApp app = this.getApp();
        List<ProjectStage> stages = (List<ProjectStage>) state.get("stages");
        Project project = (Project) state.get("project");
        HashMap<ProjectStage, List<Task>> tasksMap = (HashMap<ProjectStage, List<Task>>) state.get("tasks");

        ProjectStage stage = null;
        List<Task> tasks = null;
        Task focus = (Task) state.get("focus");

        if (!stages.isEmpty()) {
            if (state.get("stage") == null) {
                stage = stages.get(0);
            } else {
                for (ProjectStage checkStage : stages) {
                    if (checkStage.getId() == (long) state.get("stage")) {
                        stage = checkStage;
                        break;
                    }
                }
            }
            tasks = tasksMap.get(stage);
        }

        // Meta
        this.navigationController.setContent(app, (List<Page>) state.get("pages"));
        this.projectsController.setContent(app, (HashMap<Long, Project>) state.get("projects"));

        // Main
        this.topbarController.setTitle(project.getName());
        this.iterationController.initialize(app, project, stage, stages);
        this.tasksController.setContent(app, tasks, focus);
    }
}
