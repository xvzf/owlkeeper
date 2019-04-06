package de.htwsaar.owlkeeper.ui.helper;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.entity.TaskComment;
import de.htwsaar.owlkeeper.storage.model.ProjectStageModel;
import de.htwsaar.owlkeeper.storage.model.TaskCommentModel;
import de.htwsaar.owlkeeper.storage.model.TaskModel;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.state.TaskListState;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
        sidebar.setMinWidth(450);
        sidebar.setHbarPolicy(ScrollBarPolicy.NEVER);

        // Sidebar Box
        VBox content = new VBox();
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

        Validator validator = new Validator();

        ScrollPane sidebar = buildSidebarWrapper();
        VBox content = (VBox) sidebar.getContent();

        Text headline = new Text("Task anlegen oder bearbeiten");
        headline.getStyleClass().add("h2");
        content.getChildren().add(headline);

        VBox nameBox = new VBox();
        nameBox.getStyleClass().add("form-item");
        nameBox.getChildren().add(new Text("Projekt Name:"));
        TextField name = new TextField();
        nameBox.getChildren().add(name);
        content.getChildren().add(nameBox);
        name.setText(taskEntity.getName());

        VBox descriptionBox = new VBox();
        descriptionBox.getStyleClass().add("form-item");
        descriptionBox.getChildren().add(new Text("Projekt description:"));
        TextArea description = new TextArea();
        description.setMaxWidth(400);
        description.setWrapText(true);
        descriptionBox.getChildren().add(description);
        content.getChildren().add(descriptionBox);
        description.setText(taskEntity.getDescription());

        VBox dateBox = new VBox();
        dateBox.getStyleClass().add("form-item");
        dateBox.getChildren().add(new Text("Deadline:"));
        DatePicker deadline = new DatePicker(new Timestamp(System.currentTimeMillis()).toLocalDateTime().toLocalDate());
        dateBox.getChildren().add(deadline);
        content.getChildren().add(dateBox);
        if (taskEntity.getDeadline() != null) {
            deadline.setValue(taskEntity.getDeadline().toLocalDateTime().toLocalDate());
        }

        VBox submitBox = new VBox();
        Button submit = new Button("save task");
        submitBox.getChildren().add(submit);
        content.getChildren().add(submitBox);

        //@todo add team value
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (validator.execute()) {
                taskEntity.setName(name.getText());
                taskEntity.setDescription(description.getText());
                taskEntity.setDeadline(Timestamp.valueOf(deadline.getValue().atStartOfDay()));
                taskEntity.setTeam(1);
                new TaskModel(taskEntity).save();
                long stage = taskEntity.getProjectStage();
                long project = new ProjectStageModel(stage).getContainer().getProject();
                app.route("page-iteration", TaskListState.getQueryMap(project, stage, null, false), true);
            }
            validator.reset();
        });

        validator.addRule(new Validator.Rule(name, Validator::TextNotEmpty, "Project name can't be empty."));
        validator.addRule(new Validator.Rule(description, Validator::TextNotEmpty, "Project description can't be empty."));
        validator.addRule(new Validator.Rule(deadline, node -> ((DatePicker) node).getValue() != null, "Deadline needs to be defined"));
        submitBox.getChildren().add(validator.getMessageField());

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
//        HBox tags = new HBox();
//        tags.getStyleClass().add("sidebar__tags");
//        content.getChildren().add(tags);

        // Individual Tags
//        tags.getChildren().add(CommonNodes.Tag("blocked", "#E14B4B"));

        // Title
        Text title = new Text(taskEntity.getName());
        title.getStyleClass().add("sidebar__title");
        title.setWrappingWidth(400);
        content.getChildren().add(title);

        // Date & Team -- Wrapper
        HBox meta = new HBox();
        meta.getStyleClass().add("sidebar__meta");
        meta.setAlignment(Pos.CENTER_LEFT);
        content.getChildren().add(meta);

        // Date-icon
        meta.getChildren().add(CommonNodes.Image("/images/calendar.png", 22, 22));

        // Date-Text
        meta.getChildren().add(CommonNodes.Date(taskEntity.getDeadline()));

        // Team
        HBox team = new HBox();
        team.setAlignment(Pos.CENTER_RIGHT);
        team.getStyleClass().add("sidebar__team");
        meta.getChildren().add(team);
        meta.setHgrow(team, Priority.ALWAYS);

        for (int i = 0; i < 3; i++) {
            team.getChildren().add(CommonNodes.Image("/images/user.png", 25, 25));
        }

        // Description
        Text description = new Text(taskEntity.getDescription());
        description.setWrappingWidth(400);
        content.getChildren().add(description);

        Button editButton = new Button("bearbeiten");
        editButton.getStyleClass().addAll("button", "button--small", "button--secondary");
        content.getChildren().add(editButton);
        editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            long stage = taskEntity.getProjectStage();
            long project = new ProjectStageModel(stage).getContainer().getProject();
            app.route("page-iteration", TaskListState.getQueryMap(project, stage, null, false, taskEntity));
        });

        // HairLine (hr)
        content.getChildren().add(CommonNodes.Hr(375, true));

        // Comments
        Validator validator = new Validator();
        VBox comments = new VBox();
        comments.getStyleClass().add("comments");
        content.getChildren().add(comments);

        List<TaskComment> tasksComments = new TaskModel(taskEntity).getComments();
        for (TaskComment commentEntity : tasksComments) {
            HBox comment = new HBox();
            comment.getStyleClass().add("comments__item");
            comment.getChildren().add(CommonNodes.Image("/images/user.png", 25, 25));
            Text commentText = new Text(commentEntity.getContent());
            commentText.setWrappingWidth(350);
            comment.getChildren().add(commentText);
            comments.getChildren().add(comment);
        }

        // TextArea
        TextArea input = new TextArea();
        input.setMaxWidth(375);
        input.setWrapText(true);
        input.getStyleClass().add("comments__input");
        input.setPromptText("write a comment...");
        comments.getChildren().add(input);

        // Button
        VBox submitBox = new VBox();
        Button button = new Button();
        button.setText("send");
        button.getStyleClass().addAll("button", "button--small");
        submitBox.getChildren().add(button);
        comments.getChildren().add(submitBox);
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (validator.execute()) {
                long id = DeveloperManager.getCurrentDeveloper().getContainer().getId();
                TaskCommentModel comment = new TaskCommentModel(input.getText(), id, taskEntity.getId());
                comment.save();
                long stage = taskEntity.getProjectStage();
                long project = new ProjectStageModel(stage).getContainer().getProject();
                app.route("page-iteration", TaskListState.getQueryMap(project, stage, taskEntity, false), true);
            }
            validator.reset();
        });

        // Validations
        validator.addRule(new Validator.Rule(input, Validator::TextNotEmpty, "The new comment can't be empty."));
        submitBox.getChildren().add(validator.getMessageField());

        return sidebar;
    }

    /**
     * Builds the Task-Listing
     *
     * @return the full listing Node
     * @todo 27.03.2019 finish dynamic content
     */
    public static HBox getTaskNode(UiApp app, Task taskEntity){
        HBox task = new HBox();
        task.setAlignment(Pos.CENTER_LEFT);
        task.getStyleClass().add("task-listing");

        // Status icon
        ImageView image = CommonNodes.Image("/images/check-square.png", 26, 25);
        image.getStyleClass().add("task-list__icon");
        if (taskEntity.getFulfilled() != null) {
            image.setImage(new Image("/images/check-square-checked.png"));
        } else {
            image.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> image.setImage(new Image("/images/check-square-checked.png")));
            image.addEventHandler(MouseEvent.MOUSE_EXITED, event -> image.setImage(new Image("/images/check-square.png")));
        }
        image.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (taskEntity.getFulfilled() == null){
                taskEntity.setFulfilled(new Timestamp(System.currentTimeMillis()));
            }
            else{
                taskEntity.setFulfilled(null);
            }
            TaskModel model = new TaskModel(taskEntity);
            model.save();
            long stage = taskEntity.getProjectStage();
            long project = new ProjectStageModel(stage).getContainer().getProject();
            app.route("page-iteration", TaskListState.getQueryMap(project, stage, null, false), true);
        });
        task.getChildren().add(image);

        // Title
        Text title = new Text(taskEntity.getName());
        title.getStyleClass().add("task-list__title");
        task.getChildren().add(title);

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
            team.getChildren().add(CommonNodes.Image("/images/user.png", 25, 25));
        }

        // Tags
//        HBox tags = new HBox();
//        tags.setAlignment(Pos.CENTER);
//        team.setPrefWidth(280);
//        tags.getStyleClass().add("task-listing__tags");
//        meta.getChildren().add(tags);
//        for (int i = 0; i < 2; i++) {
//            tags.getChildren().add(CommonNodes.Tag("blocked", "#5A4BE1"));
//        }

        // Date
        Text date = CommonNodes.Date(taskEntity.getDeadline());
        meta.getChildren().add(date);

        return task;
    }
}
