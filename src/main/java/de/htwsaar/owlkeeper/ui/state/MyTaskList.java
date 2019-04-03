package de.htwsaar.owlkeeper.ui.state;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.model.DeveloperModel;

import java.util.HashMap;
import java.util.List;

public class MyTaskList extends BaseState {

    private Developer currentUser;
    private List<Task> tasks;

    public void handleQuery(HashMap<String, Object> query) {
        super.handleQuery(query);
        this.currentUser = new DeveloperModel(1).getContainer(); // @todo make current user dynamic
        this.tasks = new DeveloperModel(currentUser).getTasks();
    }

    @Override
    public HashMap<String, Object> collectState() {
        HashMap<String, Object> output = super.collectState();
        output.put("currentUser", this.currentUser);
        output.put("tasks", this.tasks);
        return output;
    }
}
