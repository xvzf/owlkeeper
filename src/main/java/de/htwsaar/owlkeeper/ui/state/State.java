package de.htwsaar.owlkeeper.ui.state;

/**
 * Scene state management
 */
public interface State{

    /**
     * Handles an incoming state change request
     *
     * @param o the request object
     */
    public void handleQuery(Object o);

    /**
     * Compiles the output state into one object
     *
     * @return the combined output state
     */
    public Object collectState();
}
