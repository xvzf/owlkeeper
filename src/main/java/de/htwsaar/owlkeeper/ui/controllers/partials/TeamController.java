package de.htwsaar.owlkeeper.ui.controllers.partials;

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

import java.util.List;

public class TeamController extends SidebarController<Object>{

    @FXML
    public VBox team;

    public void setContent(List<Team> teams){
        this.team.getChildren().clear();
        // this.addSidebar();
        for (Team team: teams){
            this.team.getChildren().add(this.buildTeam(team));
        }
    }

    /**
     * Builds the Team View Node
     * @return Team View VBox Node Object
     */
    private VBox buildTeam(Team teamEntity){
        new TeamModel(teamEntity);

        VBox team = new VBox();
        team.getStyleClass().add("team");

        Text title = new Text(teamEntity.getName());
        title.getStyleClass().add("h2");
        team.getChildren().add(title);

        Text description = new Text("teamEntity.description"); //@todo description field is missing
        description.setWrappingWidth(650);
        team.getChildren().add(description);

        HBox units = new HBox();
        units.getStyleClass().add("team__units");
        team.getChildren().add(units);
        // @todo missing team units
        for (int i = 0; i < 3; i++) {
            units.getChildren().add(this.buildUnit(teamEntity));
        }

        return team;
    }

    /**
     * Builds the Team Unite Node
     * @return Team Unit VBox Node Object
     */
    private VBox buildUnit(Team teamEntity){
        // @todo get developers for this teamEntity
        List<Developer> developers = DeveloperModel.getDevelopers();

        VBox unit = new VBox();
        unit.getStyleClass().add("team-unit");

        Text title = new Text("UX/UI Design:");
        unit.getChildren().add(title);

        for (Developer dev : developers) {
            HBox member = new HBox();
            member.getStyleClass().add("team-unit__member");
            unit.getChildren().add(member);
            member.getChildren().add(CommonNodes.Image("/images/users.png", 30, 30));
            member.getChildren().add(new Text(dev.getName()));
        }

        return unit;
    }

    @Override
    ScrollPane buildSidebar(Object o, UiApp app){
        return new ScrollPane();
    }
}
