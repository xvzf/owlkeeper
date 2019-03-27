package de.htwsaar.owlkeeper.ui.state;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.model.ProjectModel;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class BaseState implements State{

    private String[] pages;
    private HashMap<Long, Project> projects;
    HashMap<String, Object> query;

    /**
     * Returns a project by its id
     *
     * @param id the projects id
     * @return the full project entity
     */
    Project getProject(Long id){
        return this.projects.get(id);
    }

    /**
     * Merges two queries into one new one
     * @param defaultQuery defaulty query object
     * @param query given query object
     * @return merged query object
     */
    private static HashMap<String, Object> mergeQueries(HashMap<String, Object> defaultQuery, HashMap<String, Object> query){
        HashMap<String, Object> newQuery = new HashMap<String, Object>();
        defaultQuery.forEach(newQuery::put);
        query.forEach(newQuery::put);
        return newQuery;
    }

    @Override
    public void handleQuery(HashMap<String, Object> query){
        this.query = mergeQueries(this.getDefaultQuery(), query);
        this.pages = new String[]{"Seite xyz", "ABC hallo ", "Lorem Ipsum"};
        this.projects = new HashMap<>();
        List<Project> projects = ProjectModel.getProjects();
        projects.forEach(project -> this.projects.put(project.getId(), project));
    }

    @Override
    public HashMap<String, Object> collectState(){
        HashMap<String, Object> output = new HashMap<>();
        output.put("pages", this.pages);
        output.put("projects", this.projects);
        return output;
    }

    @Override
    public HashMap<String, Object> getDefaultQuery(){
        return new HashMap<>();
    }
}
