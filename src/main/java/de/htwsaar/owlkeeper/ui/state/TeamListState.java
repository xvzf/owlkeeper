package de.htwsaar.owlkeeper.ui.state;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Team;
import de.htwsaar.owlkeeper.storage.model.DeveloperModel;
import de.htwsaar.owlkeeper.storage.model.TeamModel;

import java.util.HashMap;
import java.util.List;

public class TeamListState extends BaseState {
    private List<Team> teams;
    private List<Developer> developers;

    @Override
    public void handleQuery(HashMap<String, Object> query) {
        super.handleQuery(query);
        this.teams = TeamModel.getTeams();
        this.developers = DeveloperModel.getDevelopers();
    }

    @Override
    public HashMap<String, Object> collectState() {
        HashMap<String, Object> state = super.collectState();
        state.put("teams", this.teams);
        state.put("developers", this.developers);
        return state;
    }
}
