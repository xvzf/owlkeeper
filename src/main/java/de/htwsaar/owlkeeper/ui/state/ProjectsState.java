package de.htwsaar.owlkeeper.ui.state;

import java.util.HashMap;

public class ProjectsState extends BaseState{
    public void handleQuery(HashMap<String, Object> query){
        super.handleQuery(query);
    }

    @Override
    public HashMap<String, Object> collectState(){
        HashMap<String, Object> output = super.collectState();
        System.out.println(output);
        return output;
    }
}
