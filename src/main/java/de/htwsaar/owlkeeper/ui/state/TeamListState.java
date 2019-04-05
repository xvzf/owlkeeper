package de.htwsaar.owlkeeper.ui.state;

import de.htwsaar.owlkeeper.storage.entity.Team;
import de.htwsaar.owlkeeper.storage.model.TeamModel;

import java.util.HashMap;
import java.util.List;

public class TeamListState extends BaseState {
    private List<Team> teams;

    @Override
    public void handleQuery(HashMap<String, Object> query) {
        super.handleQuery(query);
        this.teams = TeamModel.getTeams();
    }

    @Override
    public HashMap<String, Object> collectState() {
        HashMap<String, Object> state = (HashMap<String, Object>) super.collectState();
        state.put("teams", this.teams);
        return state;
    }
}
