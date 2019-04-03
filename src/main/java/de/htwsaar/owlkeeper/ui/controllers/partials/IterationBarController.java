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

	@FXML
	private HBox left;
	@FXML
	private HBox center;
	@FXML
	private HBox right;

	public void clear() {

	}

	public void initialize(UiApp app, Project project, ProjectStage stage, List<ProjectStage> stageList) {
		left.getChildren().clear();
		center.getChildren().clear();
		right.getChildren().clear();

		if (stage == null) {
			return;
		}

		int index = stageList.indexOf(stage);
		Pos currentPos = Pos.CENTER_LEFT;

		if (index > 0) {
			ProjectStage leftStage = stageList.get(index - 1);
			left.getStyleClass().add("iteration-bar__column");
			left.setAlignment(Pos.CENTER_LEFT);
			left.getChildren().add(CommonNodes.Image("/images/arrow-left.png", 30, 150));
			Text leftText = new Text(leftStage.getName());
			leftText.getStyleClass().add("h2");
			left.getChildren().add(leftText);
			left.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> app.route("page-iteration",
					TaskListState.getQueryMap(project.getId(), leftStage.getId(), null, false), true));
		}

		currentPos = Pos.CENTER;
		center.setAlignment(currentPos);
		Text centerText = new Text(stage.getName());
		centerText.getStyleClass().add("h2");
		center.getChildren().add(centerText);

		if (stageList.size() >= index + 2) {
			ProjectStage rightStage = stageList.get(index + 1);
			right.getStyleClass().add("iteration-bar__column");
			right.setAlignment(Pos.CENTER_RIGHT);
			Text rightText = new Text(rightStage.getName());
			rightText.getStyleClass().add("h2");
			right.getChildren().add(rightText);
			right.getChildren().add(CommonNodes.Image("/images/arrow-right.png", 30, 150));
			right.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> app.route("page-iteration",
					TaskListState.getQueryMap(project.getId(), rightStage.getId(), null, false), true));
		}

		HBox.setHgrow(center, Priority.ALWAYS);

	}
}
