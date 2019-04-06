package de.htwsaar.owlkeeper.ui.state;

import java.util.HashMap;

public final class EmptyState extends State{
    @Override
    public void handleQuery(HashMap<String, Object> query){}

    @Override
    public HashMap<String, Object> collectState(){
        return this.getDefaultQuery();
    }

    @Override
    public HashMap<String, Object> getDefaultQuery(){
        return new HashMap<>();
    }
}
