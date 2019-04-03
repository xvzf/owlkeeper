package de.htwsaar.owlkeeper.ui.helper;

import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.entity.TaskComment;
import de.htwsaar.owlkeeper.storage.model.ProjectStageModel;
import de.htwsaar.owlkeeper.storage.model.TaskCommentModel;
import de.htwsaar.owlkeeper.storage.model.TaskModel;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.state.TaskListState;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Collection of helpers for the Task-View
 */
public final class TaskView{

    private TaskView(){
    }

    /**
     * Builds the wrapper pane for the sidebar
     *
     * @return scrollpane sidebar
     */
    private static ScrollPane buildSidebarWrapper(){
        // Sidebar Pane
        ScrollPane sidebar = new ScrollPane();
        sidebar.setFitToHeight(true);
        sidebar.setPrefWidth(450);


        // Sidebar Box
        VBox content = new VBox();
        content.setPrefHeight(0);
        content.setPrefWidth(450);
        content.getStyleClass().add("sidebar");
        sidebar.setContent(content);
        return sidebar;
    }

    /**
     * Builds the new-task sidebar
     *
     * @param taskEntity new task entity
     * @return scrollpane sidebar
     */
    public static ScrollPane buildNewTaskSidebar(Task taskEntity, UiApp app){
        ScrollPane sidebar = buildSidebarWrapper();
        VBox content = (VBox) sidebar.getContent();

        Text headline = new Text("Neuer Task anlegen");
        headline.getStyleClass().add("h2");
        content.getChildren().add(headline);

        TextField name = new TextField();
        name.setPromptText("Projekt Name");
        content.getChildren().add(name);

        TextArea description = new TextArea();
        description.setPromptText("Projekt Beschreibung");
        content.getChildren().add(description);

        DatePicker deadline = new DatePicker();
        content.getChildren().add(deadline);

        Button submit = new Button("Task anlegen");
        content.getChildren().add(submit);

        //@todo add team value
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            taskEntity.setName(name.getText());
            taskEntity.setDescription(description.getText());
            taskEntity.setDeadline(Timestamp.valueOf(deadline.getValue().atStartOfDay()));
            taskEntity.setTeam(1);
            new TaskModel(taskEntity).save();
            long stage = taskEntity.getProjectStage();
            long project = new ProjectStageModel(stage).getContainer().getProject();
            app.route("page-iteration", TaskListState.getQueryMap(project, stage, null, false));
        });


        return sidebar;
    }

    /**
     * Builds the Task-Sidebar
     *
     * @return the full sidebar Node
     */
    public static ScrollPane buildSidebar(Task taskEntity, UiApp app){
        ScrollPane sidebar = buildSidebarWrapper();
        VBox content = (VBox) sidebar.getContent();

        // Tags
        HBox tags = new HBox();
        tags.setPrefHeight(0);
        tags.setPrefWidth(0);
        tags.getStyleClass().add("sidebar__tags");
        content.getChildren().add(tags);


        // Individual Tags
        tags.getChildren().add(CommonNodes.Tag("blocked", "#E14B4B"));


        // Title
        Text title = new Text(taskEntity.getName());
        title.getStyleClass().add("sidebar__title");
        title.setWrappingWidth(400);
        content.getChildren().add(title);


        // Date & Team -- Wrapper
        HBox meta = new HBox();
        meta.setPrefHeight(0);
        meta.setPrefWidth(200);
        meta.getStyleClass().add("sidebar__meta");
        meta.setAlignment(Pos.CENTER_LEFT);
        content.getChildren().add(meta);

        // Date-icon
        meta.getChildren().add(CommonNodes.Image("/images/calendar.png", 30, 150));

        // Date-Text
        meta.getChildren().add(CommonNodes.Date(taskEntity.getDeadline()));

        // Team
        HBox team = new HBox();
        team.setPrefWidth(20000);
        team.setPrefHeight(100);
        team.setAlignment(Pos.CENTER_RIGHT);
        team.getStyleClass().add("sidebar__team");
        meta.getChildren().add(team);

        for (int i = 0; i < 3; i++) {
            team.getChildren().add(CommonNodes.Image("/images/users.png", 30, 150));
        }

        // Description
        Text description = new Text(taskEntity.getDescription());
        description.setWrappingWidth(400);
        content.getChildren().add(description);

        // HairLine (hr)
        content.getChildren().add(CommonNodes.Hr(400, true));

        // Comments
        VBox comments = new VBox();
        comments.setPrefWidth(400);
        comments.setPrefHeight(275);
        comments.getStyleClass().add("comments");
        content.getChildren().add(comments);


        List<TaskComment> tasksComments = new TaskModel(taskEntity).getComments();
        for (TaskComment commentEntity : tasksComments) {
            HBox comment = new HBox();
            comment.getStyleClass().add("comments__item");
            comments.getChildren().add(comment);

            comment.getChildren().add(CommonNodes.Image("/images/users.png", 30, 150));

            Text commentText = new Text(commentEntity.getContent());
            commentText.setWrappingWidth(350);
            comment.getChildren().add(commentText);
        }

        // TextArea
        TextArea input = new TextArea();
        input.getStyleClass().add("comments__input");
        input.setWrapText(true);
        input.setPromptText("write a comment...");
        comments.getChildren().add(input);

        // Button
        Button button = new Button();
        button.setText("send");
        button.getStyleClass().add("button");
        button.getStyleClass().add("button--small");
        comments.getChildren().add(button);

        // @todo make current user dynamic
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            TaskCommentModel comment = new TaskCommentModel(input.getText(), 1, taskEntity.getId());
            comment.save();
            long stage = taskEntity.getProjectStage();
            long project = new ProjectStageModel(stage).getContainer().getProject();
            app.route("page-iteration", TaskListState.getQueryMap(project, stage, taskEntity, false));
        });


        return sidebar;
    }

    /**
     * Builds the Task-Listing
     *
     * @return the full listing Node
     * @todo 27.03.2019 finish dynamic content
     */
    public static HBox getTaskNode(Task taskEntity){
        HBox task = new HBox();
        task.setAlignment(Pos.CENTER_LEFT);
        task.getStyleClass().add("task-listing");

        // Status icon
        task.getChildren().add(CommonNodes.Image("/images/check-square.png", 30, 150));

        // Title
        task.getChildren().add(new Text(taskEntity.getName()));

        // Meta
        HBox meta = new HBox();
        meta.setAlignment(Pos.CENTER_RIGHT);
        meta.setPrefWidth(20000);
        task.getChildren().add(meta);


        // Team
        HBox team = new HBox();
        team.setAlignment(Pos.CENTER);
        team.setPrefWidth(180);
        team.getStyleClass().add("task-listing__assigned");
        meta.getChildren().add(team);
        for (int i = 0; i < 3; i++) {
            team.getChildren().add(CommonNodes.Image("/images/users.png", 30, 150));
        }

        // Tags
        HBox tags = new HBox();
        tags.setAlignment(Pos.CENTER);
        team.setPrefWidth(280);
        tags.getStyleClass().add("task-listing__tags");
        meta.getChildren().add(tags);
        for (int i = 0; i < 2; i++) {
            tags.getChildren().add(CommonNodes.Tag("blocked", "#5A4BE1"));
        }

        // Date
        Text date = CommonNodes.Date(taskEntity.getDeadline());
        meta.getChildren().add(date);

        return task;
    }
}
