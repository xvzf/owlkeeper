package de.htwsaar.owlkeeper.ui.controllers.partials;

import java.util.List;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Team;
import de.htwsaar.owlkeeper.storage.model.DeveloperModel;
import de.htwsaar.owlkeeper.storage.model.TeamModel;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.helper.CommonNodes;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TeamController extends SidebarController<Object> {
    private static final String LEADER = "Leader:";
    private static final String MEMBERS = "Members:";
    private static final String IMG_USERS = "/images/users.png";
    private static final String STYLE_H2 = "h2";
    private static final String STYLE_TEAM = "team";
    private static final String STYLE_TEAM_UNITS = "team__units";
    private static final String STYLE_TEAM_UNIT = "team-unit";
    private static final String STYLE_TEAM_UNIT_MEMBER = "team-unit__member";

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
        team.getStyleClass().add(STYLE_TEAM);

        Text title = new Text(teamEntity.getName());
        title.getStyleClass().add(STYLE_H2);
        team.getChildren().add(title);

        HBox units = new HBox();
        units.getStyleClass().add(STYLE_TEAM_UNITS);
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
        Developer leadDev = new DeveloperModel(teamEntity.getLeader()).getContainer();
        
        VBox unit = new VBox();
        unit.getStyleClass().add(STYLE_TEAM_UNIT);
        unit.getChildren().add(new Text(LEADER));

        HBox leader = new HBox();
        leader.getStyleClass().add(STYLE_TEAM_UNIT_MEMBER);
        leader.getChildren().add(CommonNodes.Image(IMG_USERS, 30, 30));
        leader.getChildren().add(new Text(leadDev.getName()));
        unit.getChildren().add(leader);

        unit.getChildren().add(new Text(MEMBERS));

        for (Developer dev : developers) {
            HBox member = new HBox();
            member.getStyleClass().add(STYLE_TEAM_UNIT_MEMBER);
            member.getChildren().add(CommonNodes.Image(IMG_USERS, 30, 30));
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
