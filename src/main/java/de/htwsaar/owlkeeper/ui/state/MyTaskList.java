package de.htwsaar.owlkeeper.ui.state;

import java.util.HashMap;
import java.util.List;

import de.htwsaar.owlkeeper.helper.DeveloperManager;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Task;

public class MyTaskList extends BaseState {

    private Developer currentUser;
    private List<Task> tasks;

    public void handleQuery(HashMap<String, Object> query) {
        super.handleQuery(query);
        this.currentUser = DeveloperManager.getCurrentDeveloper().getContainer();
        this.tasks = DeveloperManager.getCurrentDeveloper().getTasks();
    }

    @Override
    public HashMap<String, Object> collectState() {
        HashMap<String, Object> output = super.collectState();
        output.put("currentUser", this.currentUser);
        output.put("tasks", this.tasks);
        return output;
    }
}
