package de.htwsaar.owlkeeper.ui.state;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.model.ProjectModel;
import de.htwsaar.owlkeeper.storage.model.ProjectStageModel;

import java.util.HashMap;
import java.util.List;

public class TaskListState extends BaseState {

    private Project project;
    private List<ProjectStage> stages;
    private HashMap<ProjectStage, List<Task>> tasks;

    @Override
    public void handleQuery(HashMap<String, Object> query) {
        super.handleQuery(query);
        Long pId = (Long) this.query.get("project");
        this.project = this.getProject(pId);
        this.stages = new ProjectModel(this.project).getStages();
        this.tasks = new HashMap<>();
        this.stages
                .forEach(projectStage -> this.tasks.put(projectStage, new ProjectStageModel(projectStage).getTasks()));
    }

    @Override
    public HashMap<String, Object> collectState() {
        HashMap<String, Object> state = (HashMap<String, Object>) super.collectState();
        state.put("stages", this.stages);
        state.put("project", this.project);
        state.put("tasks", this.tasks);
        state.put("stage", ((long) this.query.get("stage") >= 0) ? this.query.get("stage") : null);
        state.put("focus", this.query.get("focus"));
        state.put("newtask", ((this.query.get("newtask")) != null) && (boolean) this.query.get("newtask"));
        state.put("edittask", this.query.get("edittask"));
        return state;
    }

    @Override
    public HashMap<String, Object> getDefaultQuery() {
        return getQueryMap(1, -1, null, false);
    }

    /**
     * Helper to build the TaskListState query object
     *
     * @param project project id
     * @param stage stage id
     * @param focus task id to open in the sidebar
     * @param newtask true if a new task should be added
     * @param edittask taskobject to edit
     * @return TaskListState query object
     */
    public static HashMap<String, Object> getQueryMap(long project, long stage, Task focus, boolean newtask,
            Task edittask) {
        HashMap<String, Object> query = new HashMap<>();
        query.put("project", project);
        query.put("stage", stage);
        query.put("focus", focus);
        query.put("newtask", newtask);
        query.put("edittask", edittask);
        query.put("_i", QUERY_COUNT);
        return query;
    }

    /**
     * Helper to build the TaskList State query object
     *
     * @param project project id
     * @param stage stage id
     * @param focus task id to open in the sidebar
     * @param newtask true if a new task should be added
     * @return TaskListState query object
     */
    public static HashMap<String, Object> getQueryMap(long project, long stage, Task focus, boolean newtask) {
        return getQueryMap(project, stage, focus, newtask, null);
    }
}
