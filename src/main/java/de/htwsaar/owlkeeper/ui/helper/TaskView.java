package de.htwsaar.owlkeeper.ui.helper;

import java.sql.Timestamp;
import java.util.List;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.entity.TaskComment;
import de.htwsaar.owlkeeper.storage.entity.Team;
import de.htwsaar.owlkeeper.storage.model.*;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.state.TaskListState;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Collection of helpers for the Task-View
 */
public final class TaskView {
    private static final String EDIT = "Edit";
    private static final String NEW_TASK = "Edit task";
    private static final String TASK_DEADLINE = "Task deadline";
    private static final String TASK_DESC = "Task description";
    private static final String TASK_NAME = "Task name";
    private static final String SAVE_TASK = "Save";
    private static final String SUBMIT = "Submit";
    private static final String WRITE_COMMENT = "Write a comment…";
    private static final String IMG_CALENDAR = "/images/calendar.png";
    private static final String IMG_CHECK_SQUARE_UNCHECKED = "/images/check-square-unchecked.png";
    private static final String IMG_CHECK_SQUARE_UNCHECKED_HIGHLIGHT = "/images/check-square-unchecked-highlight.png";
    private static final String IMG_CHECK_SQUARE_CHECKED = "/images/check-square-checked.png";
    private static final String IMG_CHECK_SQUARE_CHECKED_HIGHLIGHT = "/images/check-square-checked-highlight.png";
    private static final String IMG_USER = "/images/user.png";
    private static final String MSG_DEADLINE_NOT_DEFINED = "Deadline needs to be defined";
    private static final String MSG_COMMENT_EMPTY = "Comments can't be empty.";
    private static final String MSG_PROJECT_NAME_EMPTY = "Project name can't be empty.";
    private static final String MSG_PROJECT_DESC_EMPTY = "Project description can't be empty.";
    private static final String STYLE_BUTTON = "button";
    private static final String STYLE_BUTTON_SMALL = "button--small";
    private static final String STYLE_BUTTON_SECONDARY = "button--secondary";
    private static final String STYLE_COMMENTS = "comments";
    private static final String STYLE_COMMENTS_INPUT = "comments__input";
    private static final String STYLE_COMMENTS_ITEM = "comments__item";
    private static final String STYLE_H2 = "h2";
    private static final String STYLE_SIDEBAR = "sidebar";
    private static final String STYLE_SIDEBAR_META = "sidebar__meta";
    private static final String STYLE_SIDEBAR_TEAM = "sidebar__team";
    private static final String STYLE_SIDEBAR_TITLE = "sidebar__title";
    private static final String STYLE_TASK_LISTING = "task-listing";
    private static final String STYLE_TASK_LIST_ICON = "task-list__icon";
    private static final String STYLE_TASK_LIST_TITLE = "task-list__title";
    private static final String STYLE_TASK_LISTING_ASSIGNED = "task-listing__assigned";
    private static final String STYLE_FORM_ITEM = "form-item";

    private TaskView() {
    }

    /**
     * Builds the wrapper pane for the sidebar
     *
     * @return scrollpane sidebar
     */
    private static ScrollPane buildSidebarWrapper() {
        // Sidebar Pane
        ScrollPane sidebar = new ScrollPane();
        sidebar.setFitToHeight(true);
        sidebar.setMinWidth(600);
        sidebar.setHbarPolicy(ScrollBarPolicy.NEVER);

        // Sidebar Box
        VBox content = new VBox();
        content.getStyleClass().add(STYLE_SIDEBAR);
        sidebar.setContent(content);
        return sidebar;
    }

    /**
     * Builds the new-task sidebar
     *
     * @param taskEntity new task entity
     * @return scrollpane sidebar
     */
    public static ScrollPane buildNewTaskSidebar(Task taskEntity, UiApp app) {

        Validator validator = new Validator();

        ScrollPane sidebar = buildSidebarWrapper();
        VBox content = (VBox) sidebar.getContent();

        Text headline = new Text(NEW_TASK);
        headline.getStyleClass().add(STYLE_H2);
        content.getChildren().add(headline);

        VBox nameBox = new VBox();
        nameBox.getStyleClass().add(STYLE_FORM_ITEM);
        nameBox.getChildren().add(new Text(TASK_NAME));
        TextField name = new TextField();
        nameBox.getChildren().add(name);
        content.getChildren().add(nameBox);
        name.setText(taskEntity.getName());

        VBox descriptionBox = new VBox();
        descriptionBox.getStyleClass().add(STYLE_FORM_ITEM);
        descriptionBox.getChildren().add(new Text(TASK_DESC));
        TextArea description = new TextArea();
        description.setMaxWidth(550);
        description.setWrapText(true);
        descriptionBox.getChildren().add(description);
        content.getChildren().add(descriptionBox);
        description.setText(taskEntity.getDescription());

        VBox dateBox = new VBox();
        dateBox.getStyleClass().add(STYLE_FORM_ITEM);
        dateBox.getChildren().add(new Text(TASK_DEADLINE));
        DatePicker deadline = new DatePicker(new Timestamp(System.currentTimeMillis()).toLocalDateTime().toLocalDate());
        dateBox.getChildren().add(deadline);
        content.getChildren().add(dateBox);
        description.setText(taskEntity.getDescription());

        if (taskEntity.getDeadline() != null) {
            deadline.setValue(taskEntity.getDeadline().toLocalDateTime().toLocalDate());
        }

        VBox teamBox = new VBox();
        teamBox.getStyleClass().add(STYLE_FORM_ITEM);
        teamBox.getChildren().add(new Text("Team"));
        ChoiceBox<CommonNodes.EntityWrapper<Team>> team = CommonNodes.ChoiceBox(TeamModel.getTeams(), t -> t.getName());
        teamBox.getChildren().add(team);
        content.getChildren().add(teamBox);

        long currentTeam = taskEntity.getTeam();
        if (currentTeam != 0) {
            team.getItems().forEach((e) -> {
                if (e.getItem().getId() == currentTeam) {
                    team.getSelectionModel().select(e);
                }
            });
        }

        VBox submitBox = new VBox();
        Button submit = new Button(SAVE_TASK);
        submitBox.getChildren().add(submit);
        content.getChildren().add(submitBox);

        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (validator.execute()) {
                deadline.setValue(deadline.getConverter().fromString(deadline.getEditor().getText()));
                taskEntity.setName(name.getText());
                taskEntity.setDescription(description.getText());
                taskEntity.setDeadline(Timestamp.valueOf(deadline.getValue().atStartOfDay()));
                taskEntity.setTeam(team.getValue().getItem().getId());
                new TaskModel(taskEntity).save();
                long stage = taskEntity.getProjectStage();
                long project = new ProjectStageModel(stage).getContainer().getProject();
                app.route("page-iteration", TaskListState.getQueryMap(project, stage, taskEntity, false), true);
            }
            validator.reset();
        });

        validator.addRule(new Validator.Rule(name, Validator::TextNotEmpty, MSG_PROJECT_NAME_EMPTY));
        validator.addRule(new Validator.Rule(description, Validator::TextNotEmpty, MSG_PROJECT_DESC_EMPTY));
        validator.addRule(
                new Validator.Rule(deadline, node -> ((DatePicker) node).getValue() != null, MSG_DEADLINE_NOT_DEFINED));
        submitBox.getChildren().add(validator.getMessageField());

        return sidebar;
    }

    /**
     * Builds the Task-Sidebar
     *
     * @return the full sidebar Node
     */
    public static ScrollPane buildSidebar(Task taskEntity, UiApp app) {
        ScrollPane sidebar = buildSidebarWrapper();
        VBox content = (VBox) sidebar.getContent();

        // Title
        Text title = new Text(taskEntity.getName());
        title.getStyleClass().add(STYLE_SIDEBAR_TITLE);
        title.setWrappingWidth(550);
        content.getChildren().add(title);

        // Date & Team -- Wrapper
        HBox meta = new HBox();
        meta.getStyleClass().add(STYLE_SIDEBAR_META);
        meta.setAlignment(Pos.CENTER_LEFT);
        content.getChildren().add(meta);

        // Date-icon
        meta.getChildren().add(CommonNodes.Image(IMG_CALENDAR, 22, 22));

        // Date-Text
        meta.getChildren().add(CommonNodes.Date(taskEntity.getDeadline()));

        // Team
        HBox team = new HBox();
        team.setAlignment(Pos.CENTER_RIGHT);
        team.getStyleClass().add(STYLE_SIDEBAR_TEAM);
        meta.getChildren().add(team);
        HBox.setHgrow(team, Priority.ALWAYS);

        for (Developer dev : new TeamModel(taskEntity.getTeam()).getDevelopers()) {
            ImageView img = CommonNodes.Image(IMG_USER, 25, 25);
            Tooltip t = new Tooltip(dev.getName() + " <" + dev.getEmail() + ">");
            Tooltip.install(img, t);
            team.getChildren().add(img);
        }

        // Description
        Text description = new Text(taskEntity.getDescription());
        description.setWrappingWidth(550);
        content.getChildren().add(description);

        Button editButton = new Button(EDIT);
        editButton.getStyleClass().addAll(STYLE_BUTTON, STYLE_BUTTON_SMALL, STYLE_BUTTON_SECONDARY);
        content.getChildren().add(editButton);
        editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            long stage = taskEntity.getProjectStage();
            long project = new ProjectStageModel(stage).getContainer().getProject();
            app.route("page-iteration", TaskListState.getQueryMap(project, stage, null, false, taskEntity));
        });

        // HairLine (hr)
        content.getChildren().add(CommonNodes.Hr(525, true));

        // Comments
        Validator validator = new Validator();
        VBox comments = new VBox();
        comments.getStyleClass().add(STYLE_COMMENTS);
        content.getChildren().add(comments);

        List<TaskComment> tasksComments = new TaskModel(taskEntity).getComments();
        for (TaskComment commentEntity : tasksComments) {
            HBox comment = new HBox();
            comment.getStyleClass().add(STYLE_COMMENTS_ITEM);
            ImageView img = CommonNodes.Image(IMG_USER, 25, 25);
            Developer dev = new DeveloperModel(commentEntity.getDeveloper()).getContainer();
            Tooltip t = new Tooltip(dev.getName() + " <" + dev.getEmail() + ">");
            Tooltip.install(img, t);
            comment.getChildren().add(img);
            Text commentText = new Text(commentEntity.getContent());
            commentText.setWrappingWidth(500);
            comment.getChildren().add(commentText);
            comments.getChildren().add(comment);
        }

        // TextArea
        TextArea input = new TextArea();
        input.setMaxWidth(525);
        input.setWrapText(true);
        input.getStyleClass().add(STYLE_COMMENTS_INPUT);
        input.setPromptText(WRITE_COMMENT);
        comments.getChildren().add(input);

        // Button
        VBox submitBox = new VBox();
        Button button = new Button();
        button.setText(SUBMIT);
        button.getStyleClass().addAll(STYLE_BUTTON, STYLE_BUTTON_SMALL);
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
        validator.addRule(new Validator.Rule(input, Validator::TextNotEmpty, MSG_COMMENT_EMPTY));
        submitBox.getChildren().add(validator.getMessageField());

        return sidebar;
    }

    /**
     * Builds the Task-Listing
     *
     * @return the full listing Node
     */
    public static HBox getTaskNode(UiApp app, Task taskEntity, String query) {
        HBox task = new HBox();
        task.setAlignment(Pos.CENTER_LEFT);
        task.getStyleClass().add(STYLE_TASK_LISTING);

        // Status icon
        ImageView image = CommonNodes.Image(IMG_CHECK_SQUARE_UNCHECKED, 26, 25);
        image.getStyleClass().add(STYLE_TASK_LIST_ICON);
        if (taskEntity.getFulfilled() != null) {
            image.setImage(new Image(IMG_CHECK_SQUARE_CHECKED));
            image.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> image.setImage(new Image(IMG_CHECK_SQUARE_CHECKED_HIGHLIGHT)));
            image.addEventHandler(MouseEvent.MOUSE_EXITED, event -> image.setImage(new Image(IMG_CHECK_SQUARE_CHECKED)));
        } else {
            image.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> image.setImage(new Image(IMG_CHECK_SQUARE_UNCHECKED_HIGHLIGHT)));
            image.addEventHandler(MouseEvent.MOUSE_EXITED, event -> image.setImage(new Image(IMG_CHECK_SQUARE_UNCHECKED)));
        }
        image.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (taskEntity.getFulfilled() == null) {
                taskEntity.setFulfilled(new Timestamp(System.currentTimeMillis()));
            } else {
                taskEntity.setFulfilled(null);
            }
            TaskModel model = new TaskModel(taskEntity);
            model.save();
            long stage = taskEntity.getProjectStage();
            long project = new ProjectStageModel(stage).getContainer().getProject();
            app.route(query, TaskListState.getQueryMap(project, stage, null, false), true);
        });
        task.getChildren().add(image);

        // Title
        Text title = new Text(taskEntity.getName());
        title.getStyleClass().add(STYLE_TASK_LIST_TITLE);
        task.getChildren().add(title);

        // Meta
        HBox meta = new HBox();
        meta.setAlignment(Pos.CENTER_RIGHT);
        meta.setPrefWidth(20000);
        task.getChildren().add(meta);

        // Team
        TeamModel teamModel = new TeamModel(taskEntity.getTeam());
        HBox team = new HBox();
        team.setAlignment(Pos.CENTER);
        team.setPrefWidth(180);
        team.getStyleClass().add(STYLE_TASK_LISTING_ASSIGNED);
        meta.getChildren().add(team);
        for (Developer dev : teamModel.getDevelopers()) {
            ImageView img = CommonNodes.Image(IMG_USER, 25, 25);
            Tooltip t = new Tooltip(dev.getName() + " <" + dev.getEmail() + ">");
            Tooltip.install(img, t);
            team.getChildren().add(img);
        }

        // Date
        Text date = CommonNodes.Date(taskEntity.getDeadline());
        meta.getChildren().add(date);

        return task;
    }
}
