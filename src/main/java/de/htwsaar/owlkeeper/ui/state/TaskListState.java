package de.htwsaar.owlkeeper.ui.state;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.model.ProjectModel;
import de.htwsaar.owlkeeper.storage.model.ProjectStageModel;

import java.util.HashMap;
import java.util.List;

public class TaskListState extends BaseState implements State{

    private Project project;
    private List<ProjectStage> stages;
    private HashMap<ProjectStage, List<Task>> tasks;

    @Override
    public void handleQuery(HashMap<String, Object> query){
        super.handleQuery(query);
        Long pId = (Long) this.query.get("project");
        this.project = this.getProject(pId);
        this.stages = new ProjectModel(this.project).getStages();
        this.tasks = new HashMap<>();
        this.stages.forEach(projectStage -> this.tasks.put(projectStage, new ProjectStageModel(projectStage).getTasks()));

    }

    @Override
    public HashMap<String, Object> collectState(){
        HashMap<String, Object> state = (HashMap) super.collectState();
        state.put("stages", this.stages);
        state.put("project", this.project);
        state.put("tasks", this.tasks);
        state.put("stage", this.query.get("stage"));
        state.put("focus", this.query.get("focus"));
        return state;
    }

    @Override
    public HashMap<String, Object> getDefaultQuery(){
        return getQueryMap(1, null, null);
    }

    /**
     * Helper to build the TaskListState query object
     * @param project project id
     * @param stage stage id
     * @param focus task id to open in the sidebar
     * @return TaskListState query object
     */
    public static HashMap<String, Object> getQueryMap(long project, Object stage, Task focus){
        HashMap<String, Object> query = new HashMap<>();
        query.put("project", project);
        query.put("stage", stage);
        query.put("focus", focus);
        return query;
    }
}
