package de.htwsaar.owlkeeper.ui.controllers.partials;

import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class NavigationProjectsController extends Controller{

    @FXML
    private VBox root;

    public void setContent(UiApp app){
        this.root.getChildren().clear();
        for (int i = 0; i < 4; i++) {
            root.getChildren().add(this.getProject(app));
        }
    }

    private Text getProject(UiApp app){
        Text t = new Text("HTW-0021");
        t.getStyleClass().add("navigation-item__text");
        return t;
    }

}
