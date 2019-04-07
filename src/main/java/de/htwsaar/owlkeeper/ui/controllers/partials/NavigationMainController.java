package de.htwsaar.owlkeeper.ui.controllers.partials;

import java.util.List;

import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.Controller;
import de.htwsaar.owlkeeper.ui.pages.Page;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class NavigationMainController extends Controller {
    private static final String STYLE_NAV_ITEM = "navigation-item";
    private static final String STYLE_NAV_ITEM_ACTIVE = "navigation-item--active";
    private static final String STYLE_NAV_ITEM_TEXT = "navigation-item__text";

    @FXML
    private VBox root;

    public void setContent(UiApp app, List<Page> pages) {
        this.root.getChildren().clear();
        for (Page page : pages) {
            this.root.getChildren().add(this.buildItem(app, page, false));
        }
    }

    private HBox buildItem(UiApp app, Page page, boolean active) {

        // Define Box
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);
        box.getStyleClass().add(STYLE_NAV_ITEM);
        if (active) {
            box.getStyleClass().add(STYLE_NAV_ITEM_ACTIVE);
        }

        // Add Icon;
        box.getChildren().add(page.getIcon());

        // Add Text
        Text t = new Text(page.getName());
        t.getStyleClass().add(STYLE_NAV_ITEM_TEXT);
        box.getChildren().add(t);

        box.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            app.route(page.getTemplate(), page.getQuery(), page.getForce());
        });

        return box;
    }

}
