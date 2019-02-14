package de.htwsaar.owlkeeper.ui.scenes;

import de.htwsaar.owlkeeper.ui.UiScene;
import de.htwsaar.owlkeeper.ui.ViewApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class InstallSelect implements UiScene{
    @Override
    public String getName(){
        return "install-select";
    }

    @Override
    public ViewApplication.SceneBuilder getBuilder(){
        return application -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/install-select.fxml"));
            Parent root = loader.load();
            return new Scene(root, 1000, 800);
        };
    }
}