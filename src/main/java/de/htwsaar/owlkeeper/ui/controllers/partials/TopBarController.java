package de.htwsaar.owlkeeper.ui.controllers.partials;

import de.htwsaar.owlkeeper.ui.controllers.Controller;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class TopBarController extends Controller {

    @FXML
    public Text title;

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
