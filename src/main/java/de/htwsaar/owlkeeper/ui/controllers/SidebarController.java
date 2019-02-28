package de.htwsaar.owlkeeper.ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

abstract class SidebarController{

    @FXML
    public HBox root;

    private ScrollPane sidebar;

    /**
     * Removes the Sidebar from this Task-View
     */
    protected void removeSidebar(){
        if (this.sidebar != null) {
            this.root.getChildren().removeAll(this.sidebar);
        }
    }

    /**
     * Adds the Sidebar to the Task-View
     * @todo 28.02.2019 make sidebar dynamic
     */
    protected void addSidebar(){
        this.removeSidebar();
        ScrollPane sidebar = this.buildSidebar();
        this.root.getChildren().add(sidebar);
        this.sidebar = sidebar;
    }

    abstract ScrollPane buildSidebar();
}
