package de.htwsaar.owlkeeper.ui.state;

import java.util.HashMap;

/**
 * Scene state management
 */
public interface State{

    /**
     * Handles an incoming state change request
     *
     * @param query the request object
     */
    void handleQuery(HashMap<String, Object> query);

    /**
     * Compiles the output state into one object
     *
     * @return the combined output state
     */
    HashMap<String, Object> collectState();

    /**
     * Returns a the default query for this state object
     * @return default state object
     */
    HashMap<String, Object> getDefaultQuery();
}
