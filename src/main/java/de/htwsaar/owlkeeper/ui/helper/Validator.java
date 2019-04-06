package de.htwsaar.owlkeeper.ui.helper;

import javafx.scene.Node;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * Helper Class used to validate GUI-Forms
 */
public class Validator {
    private static final String STYLE_FORM_ERROR = "form-error";

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

}
