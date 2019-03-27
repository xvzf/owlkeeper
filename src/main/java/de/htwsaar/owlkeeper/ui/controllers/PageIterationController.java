package de.htwsaar.owlkeeper.ui.controllers;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.model.ProjectModel;
import de.htwsaar.owlkeeper.storage.model.ProjectStageModel;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.partials.*;
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
            stage = stages.get(0);
            tasks = tasksMap.get(stage);
        }

//        state.forEach((key, Object) -> {
//            System.out.println(key);
//            System.out.println(Object + "\n\n");
//        });


        // Meta
        this.navigationController.setContent(app, (String[]) state.get("pages"));
        this.projectsController.setContent(app, (HashMap<Long, Project>) state.get("projects"));

        // Main
        this.topbarController.setTitle(project.getName());
//         this.iterationController.initialize();
        this.tasksController.setContent(app, tasks, focus);
    }
}
