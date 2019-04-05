package de.htwsaar.owlkeeper.ui.pages;

import java.util.HashMap;

/**
 * Wrapper for a Generic Navigation Page
 */
public abstract class Page {
    private String template;
    private HashMap<String, Object> query;

    /**
     * Constructor
     * 
     * @param template template string
     * @param query route query object
     */
    public Page(String template, HashMap<String, Object> query) {
        this.template = template;
        this.setQuery(query);
    }

    /**
     * Constructor
     * 
     * @param template
     */
    public Page(String template) {
        this(template, new HashMap<>());
    }

    /**
     * Sets the query data
     * 
     * @param query route query object
     */
    void setQuery(HashMap<String, Object> query) {
        this.query = query;
    }

    /**
     * Returns the template name
     * 
     * @return template string
     */
    public String getTemplate() {
        return this.template;
    }

    /**
     * Returns the route query object
     * 
     * @return route query object
     */
    public HashMap<String, Object> getQuery() {
        return this.query;
    }

    /**
     * Returns the Pages title string
     * 
     * @return page-title
     */
    public abstract String getName();
}
