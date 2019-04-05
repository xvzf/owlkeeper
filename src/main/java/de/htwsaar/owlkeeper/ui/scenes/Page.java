package de.htwsaar.owlkeeper.ui.scenes;

import de.htwsaar.owlkeeper.ui.UiScene;
import de.htwsaar.owlkeeper.ui.ViewApplication;
import de.htwsaar.owlkeeper.ui.state.MyTaskList;
import de.htwsaar.owlkeeper.ui.state.State;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Page extends UiScene {
    @Override
    public String getName() {
        return "page";
    }

    @Override
    public ViewApplication.SceneBuilder getBuilder() {
        return application -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/page.fxml"));
            Parent root = loader.load();
            this.prepareFxml(loader);
            return new Scene(root, 1000, 800);
        };
    }

    public State initState() {
        return new MyTaskList();
    }
}
