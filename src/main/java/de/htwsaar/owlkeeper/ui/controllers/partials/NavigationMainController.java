package de.htwsaar.owlkeeper.ui.controllers.partials;


import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.Controller;
import de.htwsaar.owlkeeper.ui.helper.CommonNodes;
import de.htwsaar.owlkeeper.ui.pages.Page;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.List;

public class NavigationMainController extends Controller{

    @FXML
    private VBox root;

    public void setContent(UiApp app, List<Page> pages){
        this.root.getChildren().clear();
        for (Page page : pages) {
            switch (page.getName()) {
            case "Team":
                this.root.getChildren().add(this.buildItem(app, page, "/images/users.png", false));
                break;
            default:
                this.root.getChildren().add(this.buildItem(app, page, "/images/home.png", false));
                break;
            }
        }
    }

    private HBox buildItem(UiApp app, Page page, String icon, boolean active){

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
        Text t = new Text(page.getName());
        t.getStyleClass().add("navigation-item__text");
        box.getChildren().add(t);

        box.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            app.route(page.getTemplate(), page.getQuery());
        });

        return box;
    }

}
