package de.htwsaar.owlkeeper.ui.controllers.partials;

import java.util.List;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Team;
import de.htwsaar.owlkeeper.storage.model.TeamModel;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.helper.CommonNodes;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TeamController extends SidebarController<Object> {

    @FXML
    public HBox team;

    public void setContent(List<Team> teams) {
        this.team.getChildren().clear();
        // this.addSidebar();
        for (Team team : teams) {
            this.team.getChildren().add(this.buildTeam(team));
        }
    }

    /**
     * Builds the Team View Node
     * 
     * @return Team View VBox Node Object
     */
    private VBox buildTeam(Team teamEntity) {
        VBox team = new VBox();
        team.getStyleClass().add("team");

        Text title = new Text(teamEntity.getName());
        title.getStyleClass().add("h2");
        team.getChildren().add(title);

        HBox units = new HBox();
        units.getStyleClass().add("team__units");
        team.getChildren().add(units);

        units.getChildren().add(this.buildUnit(teamEntity));

        return team;
    }

    /**
     * Builds the Team Unite Node
     * 
     * @return Team Unit VBox Node Object
     */
    private VBox buildUnit(Team teamEntity) {
        List<Developer> developers = new TeamModel(teamEntity).getDevelopers();
        VBox unit = new VBox();
        unit.getStyleClass().add("team-unit");
        new Text("Leader: " + teamEntity.getLeader());
        unit.getChildren().add(new Text("Leader:"));

        HBox leader = new HBox();
        leader.getStyleClass().add("team-unit__member");
        leader.getChildren().add(CommonNodes.Image("/images/users.png", 30, 30));
        // TODO: get leader of team
        leader.getChildren().add(new Text(developers.get(0).getName()));
        unit.getChildren().add(leader);

        unit.getChildren().add(new Text("Members:"));

        for (Developer dev : developers) {
            HBox member = new HBox();
            member.getStyleClass().add("team-unit__member");
            member.getChildren().add(CommonNodes.Image("/images/users.png", 30, 30));
            member.getChildren().add(new Text(dev.getName()));
            unit.getChildren().add(member);
        }

        return unit;
    }

    @Override
    ScrollPane buildSidebar(Object o, UiApp app) {
        return new ScrollPane();
    }
}
