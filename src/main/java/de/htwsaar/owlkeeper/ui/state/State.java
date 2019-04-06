package de.htwsaar.owlkeeper.ui.state;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Scene state management
 */
public abstract class State {

    HashMap<String, Object> query;

    /**
     * Handles an incoming state change request
     *
     * @param query the request object
     */
    public abstract void handleQuery(HashMap<String, Object> query);

    /**
     * Compiles the output state into one object
     *
     * @return the combined output state
     */
    public abstract HashMap<String, Object> collectState();

    /**
     * Returns a the default query for this state object
     * 
     * @return default state object
     */
    public abstract HashMap<String, Object> getDefaultQuery();

    /**
     * Returns the current state query
     * 
     * @return state query
     */
    public HashMap<String, Object> getQuery() {
        return this.query;
    }

    /**
     * Merges two queries into one new one
     *
     * @param defaultQuery defaulty query object
     * @param query given query object
     * @return merged query object
     */
    public static HashMap<String, Object> mergeQueries(HashMap<String, Object> defaultQuery,
            HashMap<String, Object> query) {
        HashMap<String, Object> newQuery = new HashMap<String, Object>();
        defaultQuery.forEach(newQuery::put);
        query.forEach(newQuery::put);
        return newQuery;
    }

    /**
     * Compares two queries and returns true if both queries are identical in value
     *
     * @param a query a
     * @param b query b
     * @return true if the queries are identical
     */
    public static boolean compareQueries(HashMap<String, Object> a, HashMap<String, Object> b) {
        if (a == null || b == null || a.size() != b.size()) {
            return false;
        }
        AtomicBoolean same = new AtomicBoolean(true);
        a.forEach((key, object) -> {
            if (object == null) {
                if (b.get(key) != null) {
                    same.set(false);
                }
            } else if (b.get(key) == null || !object.toString().equals(b.get(key).toString())) {
                same.set(false);
            }
        });
        return same.get();
    }
}
