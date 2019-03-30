package de.htwsaar.owlkeeper.ui.controllers.partials;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.model.ProjectStageModel;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.Controller;
import de.htwsaar.owlkeeper.ui.helper.CommonNodes;
import de.htwsaar.owlkeeper.ui.state.TaskListState;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;


import javax.swing.text.Position;
import java.util.List;

public class IterationBarController extends Controller{

    public HBox iterationBar;

    public void clear(){

    }

    public void initialize(UiApp app, Project project, ProjectStage stage, List<ProjectStage> stageList){
        this.iterationBar.getChildren().clear();

        if (stage == null) {
            return;
        }

        int index = stageList.indexOf(stage);
        Pos currentPos = Pos.CENTER_LEFT;

        if (index > 0) {
            ProjectStage leftStage = stageList.get(index - 1);
            HBox left = new HBox();
            left.getStyleClass().add("iteration-bar__column");
            left.setAlignment(Pos.CENTER_LEFT);
            left.getChildren().add(CommonNodes.Image("/images/arrow-right.png", 30, 150));
            Text leftText = new Text(leftStage.getName());
            leftText.getStyleClass().add("h2");
            left.getChildren().add(leftText);
            this.iterationBar.getChildren().add(left);
            left.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> app.route("page-iteration", TaskListState.getQueryMap(project.getId(), leftStage.getId(), null, false)));

            currentPos = Pos.CENTER;
        }

        HBox center = new HBox();
        center.setAlignment(currentPos);
        center.getStyleClass().add("iteration-bar__column");
        Text centerText = new Text(stage.getName());
        centerText.getStyleClass().add("h2");
        center.getChildren().add(centerText);
        this.iterationBar.getChildren().add(center);
        center.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> app.route("page-iteration", TaskListState.getQueryMap(project.getId(), stage.getId(), null, false)));

        if (stageList.size() >= index + 2) {
            ProjectStage rightStage = stageList.get(index + 1);
            HBox right = new HBox();
            right.getStyleClass().add("iteration-bar__column");
            right.setAlignment(Pos.CENTER_RIGHT);
            Text rightText = new Text(rightStage.getName());
            rightText.getStyleClass().add("h2");
            right.getChildren().add(rightText);
            right.getChildren().add(CommonNodes.Image("/images/arrow-right.png", 30, 150));
            this.iterationBar.getChildren().add(right);
            right.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> app.route("page-iteration", TaskListState.getQueryMap(project.getId(), rightStage.getId(), null, false)));
        }

        this.iterationBar.setHgrow(center, Priority.ALWAYS);

    }
}
