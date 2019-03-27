package de.htwsaar.owlkeeper.ui.controllers.partials;


import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.Controller;
import de.htwsaar.owlkeeper.ui.helper.CommonNodes;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class NavigationMainController extends Controller{

    @FXML
    private VBox root;

    public void setContent(UiApp app, String[] pages){
        this.root.getChildren().clear();
        for (String s : pages) {
            this.root.getChildren().add(this.buildItem(app, s, "/images/home.png", false));
        }
    }

    private HBox buildItem(UiApp app, String text, String icon, boolean active){

        // Define Box
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);
        box.prefHeight(0);
        box.prefWidth(250);
        box.getStyleClass().add("navigation-item");
        if (active) {
            box.getStyleClass().add("navigation-item--active");
        }

        // Add Icon;
        box.getChildren().add(CommonNodes.Image(icon, 30, 30));

        // Add Text
        Text t = new Text(text);
        t.getStyleClass().add("navigation-item__text");
        box.getChildren().add(t);

        box.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            app.route("page", null);
        });

        return box;
    }

}
