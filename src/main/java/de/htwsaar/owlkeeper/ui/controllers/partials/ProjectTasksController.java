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

public class ProjectTasksController extends MyTasksController{

    private boolean newTask;

    public void setContent(UiApp app, List<Task> tasks, Project project, ProjectStage stage, Task sidebar, boolean newTask){
        this.newTask = newTask;
        super.setContent(app, tasks, sidebar);

        if (this.newTask && sidebar == null) {
            Task emptyTask = new Task();
            emptyTask.setProjectStage(stage.getId());
            this.addSidebar(emptyTask, app);
        }


        Button btn = new Button("Neuer Task hinzufügen");
        btn.getStyleClass().addAll("button", "button--small");
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> app.route("page-iteration", TaskListState.getQueryMap(project.getId(), stage.getId(), null, true)));
        this.tasks.getChildren().add(btn);
    }

    @Override
    ScrollPane buildSidebar(Task task, UiApp app){
        if (this.newTask) {
            return TaskView.buildNewTaskSidebar(task, app);
        }
        return TaskView.buildSidebar(task, app);
    }
}
