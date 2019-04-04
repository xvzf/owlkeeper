package de.htwsaar.owlkeeper.ui.controllers.partials;

import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.model.ProjectStageModel;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.helper.TaskView;
import de.htwsaar.owlkeeper.ui.state.TaskListState;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class MyTasksController extends SidebarController<Task> {

    @FXML
    public VBox tasks;

    public void setContent(UiApp app, List<Task> tasks, Task sidebar) {
        this.removeSidebar();
        if (sidebar != null) {
            this.addSidebar(sidebar, app);
        }
        this.tasks.getChildren().clear();
        if (tasks != null) {
            // @todo dynamic sections maybe
            Task task;
            for(int i = 0; i < tasks.size(); i++) {
                task = tasks.get(i);
                if (task.getFulfilled() == null) {
                    this.tasks.getChildren().add(this.getTaskList(app, "Open tasks", tasks));
                }
            }
            for(int i = 0; i < tasks.size(); i++) {
                task = tasks.get(i);
                if (task.getFulfilled() != null) {
                    this.tasks.getChildren().add(this.getTaskList(app, "Closed tasks", tasks));
                }
            }
        }
    }

    /**
     * Builds the full task-list UI
     *
     * @return the full task-list Node
     */
    private VBox getTaskList(UiApp app, String titleText, List<Task> taskList) {
        VBox taskListing = new VBox();
        taskListing.getStyleClass().add("task-list");

        Text title = new Text(titleText);
        title.getStyleClass().add("h2");
        taskListing.getChildren().add(title);

        VBox tasks = new VBox();
        tasks.getStyleClass().add("task-list__items");
        taskListing.getChildren().add(tasks);

        taskList.forEach(task -> tasks.getChildren().add(this.getTask(app, task)));
        return taskListing;
    }

    /**
     * Builds an individual task for the ui
     *
     * @return the task Node
     */
    private HBox getTask(UiApp app, Task taskEntity) {
        HBox task = TaskView.getTaskNode(taskEntity);
        task.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            long stage = taskEntity.getProjectStage();
            long project = new ProjectStageModel(stage).getContainer().getProject();
            Task focus = (Task) this.getUiScene().getState().getQuery().get("focus");
            if (focus == null || focus.getId() != taskEntity.getId()) {
                app.route("page-iteration", TaskListState.getQueryMap(project, stage, taskEntity, false));
            } else {
                app.route("page-iteration", TaskListState.getQueryMap(project, stage, null, false));
            }
        });
        return task;
    }

    @Override
    ScrollPane buildSidebar(Task task, UiApp app) {
        return TaskView.buildSidebar(task, app);
    }
}
