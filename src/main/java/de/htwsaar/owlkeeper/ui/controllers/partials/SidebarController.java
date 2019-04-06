package de.htwsaar.owlkeeper.ui.controllers.partials;

import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

abstract class SidebarController<T> extends Controller {

    @FXML
    public HBox root;

    private ScrollPane sidebar;

    /**
     * Removes the Sidebar from this Task-View
     */
    void removeSidebar() {
        if (this.sidebar != null) {
            this.root.getChildren().removeAll(this.sidebar);
        }
    }

    /**
     * Adds the Sidebar to the Task-View
     */
    void addSidebar(T item, UiApp app) {
        this.removeSidebar();
        ScrollPane sidebar = this.buildSidebar(item, app);
        this.root.getChildren().add(sidebar);
        this.sidebar = sidebar;
    }

    abstract ScrollPane buildSidebar(T item, UiApp app);
}
