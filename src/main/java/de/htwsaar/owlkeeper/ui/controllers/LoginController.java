package de.htwsaar.owlkeeper.ui.controllers;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.model.DeveloperModel;
import de.htwsaar.owlkeeper.ui.scenes.*;
import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.helper.CommonNodes;
import de.htwsaar.owlkeeper.ui.helper.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class LoginController extends Controller{

    private static final String VALIDATION_ERROR = "Your Login Credentials are incorrect.\n Please check your inputs.";
    private static final String EMAIL_ADRESS = "Email adress:";
    private static final String PASSWORD = "Password:";
    private static final String LOGIN = "Login";
    private static final String STYLE_FORM = "form";
    private static final String STYLE_LOGIN_PANEL = "login-panel";
    private static final String STYLE_FORM_ITEM = "form-item";
    private static final String IMG_LOGO = "/images/logo.png";


    @FXML
    private VBox root;

    /**
     * Builds the Login Form
     *
     * @param state page state object
     */
    public void boot(HashMap<String, Object> state){
        this.root.getChildren().add(CommonNodes.Image(IMG_LOGO, 300, 200));
        VBox panel = new VBox();
        panel.getStyleClass().addAll(STYLE_FORM, STYLE_LOGIN_PANEL);
        root.getChildren().add(panel);

        Validator validator = new Validator();
        panel.getChildren().add(validator.getMessageField());


        // E-Mail field
        VBox emailBox = new VBox();
        emailBox.getStyleClass().add(STYLE_FORM_ITEM);
        emailBox.getChildren().add(new Label(EMAIL_ADRESS));
        TextField email = new TextField();
        emailBox.getChildren().add(email);
        panel.getChildren().add(emailBox);

        // Password field
        VBox passwordBox = new VBox();
        passwordBox.getStyleClass().add(STYLE_FORM_ITEM);
        passwordBox.getChildren().add(new Label(PASSWORD));
        PasswordField password = new PasswordField();
        passwordBox.getChildren().add(password);
        panel.getChildren().add(passwordBox);

        // Login Button
        Button submit = new Button(LOGIN);
        panel.getChildren().add(submit);
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            validator.execute();
            validator.reset();
        });

        // Validation
        validator.addRule(new Validator.Rule(panel, node -> {
            // Some input is empty
            if (!Validator.TextNotEmpty(email) || !Validator.TextNotEmpty(password)) {
                return false;
            }
            // Try
            if (DeveloperManager.checkLogin(email.getText(), password.getText())) {
                loginUser(this.getApp(), email.getText());
                return true;
            }
            return false;
        }, VALIDATION_ERROR));


        // Prefill Credentials
        // TODO: 06.04.2019 remove if needed
        email.setText("devel1@owlkeeper.de");
        password.setText("password-1");
    }

    /**
     * Executes the user login and loads the full OwlKeeper UI
     *
     * @param email developer email adress
     */
    private static void loginUser(UiApp app, String email){
        // Login user
        DeveloperManager.loginDeveloper(email);

        // Load full UI
        // TODO: 06.04.2019 add credentials check for each of these views
        UiApp.stageScene(new Page());
        UiApp.stageScene(new PageIteration());
        UiApp.stageScene(new PageTeam());
        UiApp.stageScene(new Projects());
        app.loadScenes();

        // Redirect into the application
        app.route("page", new HashMap<>());
    }
}
