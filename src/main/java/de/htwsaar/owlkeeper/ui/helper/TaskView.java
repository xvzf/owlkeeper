package de.htwsaar.owlkeeper.ui.helper;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Collection of helpers for the Task-View
 */
public final class TaskView{

    private TaskView(){}

    /**
     * Builds the Task-Sidebar
     * @todo 28.02.2019 allow fill by dynamic task object
     * @return the full sidebar Node
     */
    public static ScrollPane buildSidebar(){

        // Sidebar Pane
        ScrollPane sidebar = new ScrollPane();
        sidebar.setFitToHeight(true);
        sidebar.setPrefWidth(450);
        sidebar.setPrefWidth(750);


        // Sidebar Box
        VBox content = new VBox();
        content.setPrefHeight(0);
        content.setPrefWidth(450);
        content.getStyleClass().add("sidebar");
        sidebar.setContent(content);


        // Tags
        HBox tags = new HBox();
        tags.setPrefHeight(0);
        tags.setPrefWidth(0);
        tags.getStyleClass().add("sidebar__tags");
        content.getChildren().add(tags);


        // Individual Tags
        tags.getChildren().add(CommonNodes.Tag("blocked", "#E14B4B"));


        // Title
        Text title = new Text("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy");
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
        meta.getChildren().add(CommonNodes.Date("23.07.2019"));

        // Team
        HBox team = new HBox();
        team.setPrefWidth(20000);
        team.setPrefHeight(100);
        team.setAlignment(Pos.CENTER_RIGHT);
        team.getStyleClass().add("sidebar__team");
        meta.getChildren().add(team);

        for (int i = 0; i < 3; i++){
            team.getChildren().add(CommonNodes.Image("/images/users.png", 30, 150));
        }

        // Description
        Text description = new Text("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.");
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

        for (int i = 0; i < 2; i++){
            HBox comment = new HBox();
            comment.getStyleClass().add("comments__item");
            comments.getChildren().add(comment);

            comment.getChildren().add(CommonNodes.Image("/images/users.png", 30, 150));

            Text commentText = new Text("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.");
            commentText.setWrappingWidth(350);
            comment.getChildren().add(commentText);
        }

        // TextArea
        TextArea input = new TextArea();
        input.getStyleClass().add("comments__input");
        input.setPromptText("write a comment...");
        comments.getChildren().add(input);

        // Button
        Button button = new Button();
        button.setText("send");
        button.getStyleClass().add("button");
        button.getStyleClass().add("button--small");
        comments.getChildren().add(button);


        return sidebar;
    }

    /**
     * Builds the Task-Listing
     * @todo 28.02.2019 allow fill by dynamic task object
     * @return the full listing Node
     */
    public static HBox getTaskNode(){
        HBox task = new HBox();
        task.setAlignment(Pos.CENTER_LEFT);
        task.getStyleClass().add("task-listing");

        // Status icon
        task.getChildren().add(CommonNodes.Image("/images/check-square.png", 30, 150));

        // Title
        task.getChildren().add(new Text("HTW-0021"));

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
        for (int i = 0; i < 3; i++){
            team.getChildren().add(CommonNodes.Image("/images/users.png", 30, 150));
        }

        // Tags
        HBox tags = new HBox();
        tags.setAlignment(Pos.CENTER);
        team.setPrefWidth(280);
        tags.getStyleClass().add("task-listing__tags");
        meta.getChildren().add(tags);
        for (int i = 0; i < 2; i++){
            tags.getChildren().add(CommonNodes.Tag("blocked", "#5A4BE1"));
        }


        // Date
        meta.getChildren().add(CommonNodes.Date("23.07.2019"));

        return task;
    }
}
