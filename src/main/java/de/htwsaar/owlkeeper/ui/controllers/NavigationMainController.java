package de.htwsaar.owlkeeper.ui.controllers;


import de.htwsaar.owlkeeper.ui.helper.CommonNodes;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class NavigationMainController{

    @FXML
    private VBox root;

    public void initialize() {
        for (int i = 0; i < 4; i++){
            this.root.getChildren().add(this.buildItem("Hallo Welt " + i, "/images/home.png", i==0));
        }
    }

    private HBox buildItem(String text, String icon, boolean active){
        // Define Box
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);
        box.prefHeight(0);
        box.prefWidth(250);
        box.getStyleClass().add("navigation-item");
        if (active){
            box.getStyleClass().add("navigation-item--active");
        }

        // Add Icon;
        box.getChildren().add(CommonNodes.Image(icon, 30,30));

        // Add Text
        Text t = new Text(text);
        t.getStyleClass().add("navigation-item__text");
        box.getChildren().add(t);
        return box;
    }

}
