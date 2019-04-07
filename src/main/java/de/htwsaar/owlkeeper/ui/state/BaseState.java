package de.htwsaar.owlkeeper.ui.state;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.model.ProjectModel;
import de.htwsaar.owlkeeper.ui.pages.MyTasks;
import de.htwsaar.owlkeeper.ui.pages.Page;
import de.htwsaar.owlkeeper.ui.pages.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BaseState extends State {

    /**
     * This variable is incremented every time
     * a task is updated to prevent missing rerenders
     */
    public static int QUERY_COUNT = 0;

    private List<Page> pages;
    private HashMap<Long, Project> projects;

    /**
     * Returns a project by its id
     *
     * @param id the projects id
     * @return the full project entity
     */
    Project getProject(Long id) {
        return this.projects.get(id);
    }

    @Override
    public void handleQuery(HashMap<String, Object> query) {
        this.query = mergeQueries(this.getDefaultQuery(), query);
        this.pages = this.getNavigationList();
        this.projects = new HashMap<>();
        List<Project> projects = ProjectModel.getProjects();
        projects.forEach(project -> this.projects.put(project.getId(), project));
    }

    /**
     * Builds a List of navigation items
     */
    private List<Page> getNavigationList() {
        List<Page> list = new ArrayList<>();
        list.add(new MyTasks());
        list.add(new Team());
        return list;
    }

    @Override
    public HashMap<String, Object> collectState() {
        HashMap<String, Object> output = new HashMap<>();
        output.put("pages", this.pages);
        output.put("projects", this.projects);
        output.put("_i", QUERY_COUNT);
        return output;
    }

    @Override
    public HashMap<String, Object> getDefaultQuery() {
        return new HashMap<>();
    }
}
