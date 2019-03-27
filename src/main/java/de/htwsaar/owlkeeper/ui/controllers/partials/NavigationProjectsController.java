package de.htwsaar.owlkeeper.ui.controllers.partials;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.Controller;
import de.htwsaar.owlkeeper.ui.state.TaskListState;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.HashMap;

public class NavigationProjectsController extends Controller{

    @FXML
    private VBox root;

    public void setContent(UiApp app, HashMap<Long, Project> projects){
        this.root.getChildren().clear();
        projects.forEach((id, project) -> {
            root.getChildren().add(this.getProject(app, project));
        });
    }

    private Text getProject(UiApp app, Project p){
        Text t = new Text(p.getName());
        t.getStyleClass().add("navigation-item__text");
        t.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            app.route("page-iteration", TaskListState.getQueryMap(p.getId(), null, null));
        });
        return t;
    }

}
