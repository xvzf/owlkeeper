package de.htwsaar.owlkeeper.ui.state;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.model.DeveloperModel;

import java.util.HashMap;

public class MyTaskList extends BaseState{

    private Developer currentUser;

    public void handleQuery(HashMap <String, Object> query){
        super.handleQuery(query);
        this.currentUser = new DeveloperModel(1).getContainer(); // @todo make current user dynamic
    }

    @Override
    public HashMap<String, Object> collectState(){
        HashMap<String, Object> output = super.collectState();
        output.put("currentUser", this.currentUser);
        return output;
    }
}
