package de.htwsaar.owlkeeper.ui.pages;

import java.util.HashMap;

public class MyTasks extends Page{

    public MyTasks(long currentUser){
        super("page");
        HashMap<String, Object> query = new HashMap<>();
        query.put("user", currentUser);
        this.setQuery(query);
    }

    @Override
    public String getName(){
        return "My Tasks";
    }
}
