package de.htwsaar.owlkeeper.ui.controllers.partials;

import de.htwsaar.owlkeeper.ui.helper.CommonNodes;
import de.htwsaar.owlkeeper.ui.helper.TaskView;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TeamController extends SidebarController{

    @FXML
    public VBox team;

    public void setContent(){
        // this.addSidebar();
        for (int i = 0; i < 3; i++){
            this.team.getChildren().add(this.buildTeam());
        }
    }

    /**
     * Builds the Team View Node
     * @todo 01.03.2019 make fillable with dynamic content
     * @return Team View VBox Node Object
     */
    private VBox buildTeam(){
        VBox team = new VBox();
        team.getStyleClass().add("team");

        Text title = new Text("Team A");
        title.getStyleClass().add("h2");
        team.getChildren().add(title);

        Text description = new Text("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum.");
        description.setWrappingWidth(650);
        team.getChildren().add(description);

        HBox units = new HBox();
        units.getStyleClass().add("team__units");
        team.getChildren().add(units);
        for (int i = 0; i < 3; i++) {
            units.getChildren().add(this.buildUnit());
        }

        return team;
    }

    /**
     * Builds the Team Unite Node
     * @todo 01.03.2019 make fillable with dynamic content
     * @return Team Unit VBox Node Object
     */
    private VBox buildUnit(){
        VBox unit = new VBox();
        unit.getStyleClass().add("team-unit");

        Text title = new Text("UX/UI Design:");
        unit.getChildren().add(title);

        for (int i = 0; i < 5; i++) {
            HBox member = new HBox();
            member.getStyleClass().add("team-unit__member");
            unit.getChildren().add(member);

            member.getChildren().add(CommonNodes.Image("/images/users.png", 30, 30));
            member.getChildren().add(new Text("Jon Doe Lorem Ipsum"));
        }

        return unit;
    }

    @Override
    ScrollPane buildSidebar(){
        return TaskView.buildSidebar();
    }
}
