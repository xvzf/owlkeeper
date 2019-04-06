package de.htwsaar.owlkeeper.ui.controllers.partials;

import java.util.List;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.Controller;
import de.htwsaar.owlkeeper.ui.helper.CommonNodes;
import de.htwsaar.owlkeeper.ui.state.TaskListState;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;

public class IterationBarController extends Controller {
    private static final String IMG_ARROW_LEFT = "/images/arrow-left.png";
    private static final String IMG_ARROW_RIGHT = "/images/arrow-right.png";
    private static final String STYLE_ITERATION_BAR_COL = "iteration-bar__column";
    private static final String STYLE_H2 = "h2";

    @FXML
    private HBox left;
    @FXML
    private HBox center;
    @FXML
    private HBox right;

    public void initialize(UiApp app, Project project, ProjectStage stage, List<ProjectStage> stageList) {
        left.getChildren().clear();
        center.getChildren().clear();
        right.getChildren().clear();

        if (stage == null) {
            return;
        }

        int index = stageList.indexOf(stage);

		if (index > 0) {
            ProjectStage leftStage = stageList.get(index - 1);
            left.getStyleClass().add(STYLE_ITERATION_BAR_COL);
            left.setAlignment(Pos.CENTER_LEFT);
            HBox leftInner = new HBox();
            leftInner.setAlignment(Pos.CENTER_LEFT);
            leftInner.getChildren().add(CommonNodes.Image(IMG_ARROW_LEFT, 20, 20));
            Text leftText = new Text(leftStage.getName());
            leftText.getStyleClass().add(STYLE_H2);
            leftInner.getChildren().add(leftText);
            leftInner.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> app.route("page-iteration",
                    TaskListState.getQueryMap(project.getId(), leftStage.getId(), null, false)));
            left.getChildren().add(leftInner);
        }

        center.setAlignment(Pos.CENTER);
        Text centerText = new Text(stage.getName());
        centerText.getStyleClass().add(STYLE_H2);
        center.getChildren().add(centerText);

        if (stageList.size() >= index + 2) {
            ProjectStage rightStage = stageList.get(index + 1);
            right.getStyleClass().add(STYLE_ITERATION_BAR_COL);
            right.setAlignment(Pos.CENTER_RIGHT);
            HBox rightInner = new HBox();
            rightInner.setAlignment(Pos.CENTER_RIGHT);
            Text rightText = new Text(rightStage.getName());
            rightText.getStyleClass().add(STYLE_H2);
            rightInner.getChildren().add(rightText);
            rightInner.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                app.route("page-iteration",
                        TaskListState.getQueryMap(project.getId(), rightStage.getId(), null, false));
            });
            rightInner.getChildren().add(CommonNodes.Image(IMG_ARROW_RIGHT, 20, 20));
            right.getChildren().add(rightInner);
        }

        HBox.setHgrow(center, Priority.ALWAYS);
    }
}
