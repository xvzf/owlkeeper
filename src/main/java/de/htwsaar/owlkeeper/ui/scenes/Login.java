package de.htwsaar.owlkeeper.ui.scenes;

import de.htwsaar.owlkeeper.ui.UiScene;
import de.htwsaar.owlkeeper.ui.ViewApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.security.*;

public class Login extends UiScene{

    @Override
    public String getName(){
        return "login";
    }

    @Override
    public ViewApplication.SceneBuilder getBuilder(){
        return application -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            this.prepareFxml(loader);
            return new Scene(root, 1000, 800);
        };
    }

    private String getHash(string pw) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
        } catch (java.security.NoSuchAlgorithmException e) {
            return "Nicht gefunden!";   //TODO Something better than that
        }
        byte[] pwBytes = pw.getBytes("UTF-8");
        byte[] hash = md.digest(pwBytes);
        return hash.toString();
    }
}
