package de.htwsaar.owlkeeper.ui.helper;

import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.dao.DeveloperDao;
import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Helper Class used to validate GUI-Forms
 */
public class Validator {
    private static final String STYLE_FORM_ERROR = "form-error";
    /** Thanks to: http://emailregex.com/    */
    private static final Pattern EMAIL = Pattern
            .compile ("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

    /**
     * Validation rule
     */
    public static class Rule {
        private Node node;
        private Predicate<Node> predicate;
        private String message;

        /**
         * construcotr
         *
         * @param node JavaFx to apply the rule to
         * @param predicate predicate which needs to return true for the rule to pass
         * @param message error message if the predicate returns false
         */
        public Rule(Node node, Predicate<Node> predicate, String message) {
            this.message = message;
            this.node = node;
            this.predicate = predicate;
        }

        /**
         * Executes the rule
         *
         * @return returns the predicates return value
         */
        private boolean apply() {
            return this.predicate.test(this.node);
        }
    }

    private ArrayList<Rule> rules;
    private ArrayList<String> messages;
    private VBox messageField;

    /**
     * Constructor
     */
    public Validator() {
        this.messageField = buildMessageField();
        this.rules = new ArrayList<>();
        this.reset();
    }

    /**
     * Resets the Validator fields
     */
    public void reset() {
        this.messages = new ArrayList<>();
    }

    /**
     * Adds a new rule to this validator
     *
     * @param rule rule instance
     */
    public void addRule(Rule rule) {
        this.rules.add(rule);
    }

    /**
     * Executes the validation
     *
     * @return true if validation was successful
     */
    public boolean execute() {
        boolean success = true;
        this.getMessageField().getChildren().clear();
        for (Rule rule : this.rules) {
            boolean check = rule.apply();
            success = success && check;
            if (!check) {
                this.messages.add(rule.message);
            }
        }
        for (String message : this.messages) {
            this.getMessageField().getChildren().add(new Text(message));
        }
        return success;
    }

    /**
     * Returns the error messages as a ArrayList
     *
     * @return error messages
     */
    public ArrayList<String> getMessages() {
        return this.messages;
    }

    /**
     * Returns a VBox field, where the error messages are rendered after a failed
     * valdiation
     *
     * @return vbox node object
     */
    public VBox getMessageField() {
        return this.messageField;
    }

    /**
     * Builds a default validation message field
     *
     * @return javafx node object
     */
    private static VBox buildMessageField() {
        VBox box = new VBox();
        box.getStyleClass().add(STYLE_FORM_ERROR);
        return box;
    }

    /**
     * Checks if the given TextInput is not empty
     *
     * @param node object to check
     * @return returns true if the input is not empty
     */
    public static boolean TextNotEmpty(Node node) {
        String text = ((TextInputControl) node).getText();
        return text != null && !text.trim().isEmpty();
    }

    public static boolean checkEmailSyntax(Node node) {
        String text = ((TextInputControl) node).getText();
        return EMAIL.matcher(text).matches();
    }

    public static boolean checkEmailExists(Node node) {
        String text = ((TextInputControl) node).getText();
        return DBConnection.getJdbi().withExtension(DeveloperDao.class, DeveloperDao::getDevelopers).stream()
                .noneMatch(dev -> dev.getEmail().equals(text));
    }

}
