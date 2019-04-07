package de.htwsaar.owlkeeper.ui.controllers.partials;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.helper.TaskView;
import de.htwsaar.owlkeeper.ui.state.TaskListState;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class ProjectTasksController extends MyTasksController {

    private static final String NEW_TASK = "New task";
    private static final String STYLE_BUTTON = "button";
    private static final String STYLE_BUTTON_SMALL = "button--small";

    private boolean newTask;
    private Task editTask;

    public void setContent(UiApp app, List<Task> tasks, Project project, ProjectStage stage, Task sidebar,
            boolean newTask, Task editTask) {
        this.newTask = newTask;
        this.editTask = editTask;
        super.setContent(app, tasks, sidebar);

        if (this.editTask != null) {
            this.addSidebar(this.editTask, app);
        } else if (this.newTask) {
            Task emptyTask = new Task();
            emptyTask.setProjectStage(stage.getId());
            this.addSidebar(emptyTask, app);
        }

        Button btn = new Button(NEW_TASK);
        btn.getStyleClass().addAll(STYLE_BUTTON, STYLE_BUTTON_SMALL);
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            app.route("page-iteration", TaskListState.getQueryMap(project.getId(), stage.getId(), null, true), true);
        });
        this.tasks.getChildren().add(btn);
    }

    @Override
    ScrollPane buildSidebar(Task task, UiApp app) {
        if (this.newTask || this.editTask != null) {
            return TaskView.buildNewTaskSidebar(task, app);
        }
        return TaskView.buildSidebar(task, app);
    }

    /**
     * Returns the target-page for the task-checkbox
     * @return page-name string
     */
    String getRedirectTarget(){
        return "page-iteration";
    }
}
