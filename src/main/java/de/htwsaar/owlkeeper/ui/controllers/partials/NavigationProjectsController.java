package de.htwsaar.owlkeeper.ui.controllers.partials;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class NavigationProjectsController{

    @FXML
    private VBox root;

    public void setContent(){
        this.root.getChildren().clear();
        for (int i = 0; i < 4; i++) {
            root.getChildren().add(this.getProject());
        }
    }

    private Text getProject(){
        Text t = new Text("HTW-0021");
        t.getStyleClass().add("navigation-item__text");
        return t;
    }

}
