package de.htwsaar.owlkeeper.ui.state;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.model.ProjectModel;
import de.htwsaar.owlkeeper.ui.pages.MyTasks;
import de.htwsaar.owlkeeper.ui.pages.Page;
import de.htwsaar.owlkeeper.ui.pages.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseState implements State{

    private List<Page> pages;
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
    public void handleQuery(HashMap <String, Object> query){
        this.query = mergeQueries(this.getDefaultQuery(), query);
        this.pages = this.getNavigationList();
        this.projects = new HashMap<>();
        List<Project> projects = ProjectModel.getProjects();
        projects.forEach(project -> this.projects.put(project.getId(), project));
    }

    /**
     * Builds a List of navigation items
     */
    private List<Page> getNavigationList(){
        List<Page> list = new ArrayList<>();
        list.add(new MyTasks());
        list.add(new Team());
        return list;
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
