package de.htwsaar.owlkeeper.ui.controllers.partials;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class TopBarController{

    @FXML
    public Text title;

    public void setTitle(String title){
        this.title.setText(title);
    }
}
