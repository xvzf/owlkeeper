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

public class NavigationProjectsController extends Controller {
    private static final String PROJECTS = "Projects";
    private static final String STYLE_NAV_ITEM_TEXT = "navigation-item__text";

    @FXML
    private VBox root;

    @FXML
    private VBox main;

    public void setContent(UiApp app, HashMap<Long, Project> projects) {
        main.getChildren().clear();
        this.root.getChildren().clear();

        // Add headline
        Text headline = new Text(PROJECTS);
        headline.getStyleClass().add(STYLE_NAV_ITEM_TEXT);
        headline.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            app.route("projects", new HashMap<>());
        });
        main.getChildren().add(headline);

        // Fill Projects
        projects.forEach((id, project) -> {
            root.getChildren().add(this.getProject(app, project));
        });
    }

    private Text getProject(UiApp app, Project p) {
        Text t = new Text(p.getName());
        t.getStyleClass().add(STYLE_NAV_ITEM_TEXT);
        t.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            app.route("page-iteration", TaskListState.getQueryMap(p.getId(), -1, null, false));
        });
        return t;
    }

}
