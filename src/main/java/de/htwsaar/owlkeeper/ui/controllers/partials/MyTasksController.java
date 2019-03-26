package de.htwsaar.owlkeeper.ui.controllers.partials;

import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.helper.TaskView;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class MyTasksController extends SidebarController{

    @FXML
    public VBox tasks;

    private ScrollPane sidebar;

    public void setContent(UiApp app, boolean sidebar){
        if (sidebar){
            this.addSidebar();
        }
        else{
            this.removeSidebar();
        }
        this.tasks.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            this.tasks.getChildren().add(this.getTaskList(app));
        }
    }

    /**
     * Builds the full task-list UI
     *
     * @return the full task-list Node
     * @todo 28.02.2019 fill with dynamic content
     */
    private VBox getTaskList(UiApp app){
        VBox taskList = new VBox();
        taskList.getStyleClass().add("task-list");

        Text title = new Text("Open Tasks:");
        title.getStyleClass().add("h2");
        taskList.getChildren().add(title);

        VBox tasks = new VBox();
        tasks.getStyleClass().add("task-list__items");
        taskList.getChildren().add(tasks);

        for (int i = 0; i < 5; i++) {
            tasks.getChildren().add(this.getTask());
        }

        return taskList;
    }

    /**
     * Builds an individual task for the ui
     *
     * @return the task Node
     * @ TODO: 28.02.2019 fill with dynamic content
     */
    private HBox getTask(){
        return TaskView.getTaskNode();
    }

    @Override
    ScrollPane buildSidebar(){
        return TaskView.buildSidebar();
    }
}
