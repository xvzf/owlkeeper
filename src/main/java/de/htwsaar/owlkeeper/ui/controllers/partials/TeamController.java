package de.htwsaar.owlkeeper.ui.controllers.partials;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Team;
import de.htwsaar.owlkeeper.storage.model.DeveloperModel;
import de.htwsaar.owlkeeper.storage.model.TeamModel;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.Controller;
import de.htwsaar.owlkeeper.ui.helper.CommonNodes;
import de.htwsaar.owlkeeper.ui.helper.Validator;
import de.htwsaar.owlkeeper.ui.helper.DataCheckbox;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TeamController extends Controller{
    private static final String LEADER = "Leader:";
    private static final String MEMBERS = "Members:";
    private static final String DEVELOPERS = "Teamless developers";
    private static final String IMG_USER = "/images/user.png";
    private static final String STYLE_WRAPPER = "team__wrapper";
    private static final String STYLE_H2 = "h2";
    private static final String STYLE_DEV_LISTING = "developer-listing";
    private static final String STYLE_FORM = "project-form";
    private static final String STYLE_FORM_ITEM = "project-form__item";
    private static final String STYLE_TEAM = "team";
    private static final String STYLE_TEAM_UNITS = "team__units";
    private static final String STYLE_TEAM_UNIT = "team-unit";
    private static final String STYLE_TEAM_UNIT_MEMBER = "team-unit__member";
    private static final String STYLE_TEAM_UNIT_LEADER = "team-unit__member--leader";

    @FXML
    public VBox team;

    public void setContent(List<Team> teams, List<Developer> developers){
        this.team.getChildren().clear();

        // Teams and Developers
        HBox wrapper = new HBox();
        wrapper.getStyleClass().add(STYLE_WRAPPER);
        for (Team team : teams) {
            wrapper.getChildren().add(this.buildTeam(team));
        }
        wrapper.getChildren().add(this.buildUserList(developers));
        this.team.getChildren().add(wrapper);

        this.team.getChildren().add(CommonNodes.Hr(600, true));


        VBox forms = new VBox();
        forms.getStyleClass().add("project__stage-wrapper");
        forms.getChildren().add(this.buildNewDevForm(this.getApp(), teams));
        forms.getChildren().add(CommonNodes.Hr(600, true));
        forms.getChildren().add(this.buildNewTeamForm(this.getApp(), developers));
        forms.getChildren().add(CommonNodes.Hr(600, true));
        forms.getChildren().add(this.buildDevTeamForms(this.getApp(), developers, teams));
        this.team.getChildren().add(forms);
    }

    /**
     * Builds a list of all developers
     *
     * @param developers list of developers
     * @return Dev View VBox Node Object
     */
    private VBox buildUserList(List<Developer> developers){
        int count = 0;
        VBox box = new VBox();

        Text title = new Text(DEVELOPERS);
        title.getStyleClass().add(STYLE_H2);

        VBox listing = new VBox();
        listing.getStyleClass().add(STYLE_DEV_LISTING);
        for (Developer dev : developers) {
            if (new DeveloperModel(dev).getTeams().size() == 0) {
                listing.getChildren().add(this.getDeveloperBox(dev));
                count++;
            }
        }

        // Only render content if at least one developer does not have a team
        if (count > 0) {
            box.getChildren().add(title);
            box.getChildren().add(listing);
        }

        return box;
    }


    /**
     * Builds the Team View Node
     *
     * @return Team View VBox Node Object
     */
    private VBox buildTeam(Team teamEntity){
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
    private VBox buildUnit(Team teamEntity){
        List<Developer> developers = new TeamModel(teamEntity).getDevelopers();
        Developer leadDev = new DeveloperModel(teamEntity.getLeader()).getContainer();

        VBox unit = new VBox();
        unit.getStyleClass().add(STYLE_TEAM_UNIT);
        unit.getChildren().add(new Text(LEADER));

        HBox leader = new HBox();
        leader.getStyleClass().addAll(STYLE_TEAM_UNIT_MEMBER, STYLE_TEAM_UNIT_LEADER);
        leader.getChildren().add(CommonNodes.Image(IMG_USER, 22, 22));
        leader.getChildren().add(new Text(leadDev.getName()));
        unit.getChildren().add(leader);

        unit.getChildren().add(new Text(MEMBERS));

        for (Developer dev : developers) {
            if (dev.equals(leadDev)) {
                continue;
            }
            unit.getChildren().add(this.getDeveloperBox(dev));
        }

        return unit;
    }

    /**
     * Gets a single developer HBox
     *
     * @param dev developer entity object
     * @return developer HBox Node object
     */
    private HBox getDeveloperBox(Developer dev){
        HBox member = new HBox();
        member.getStyleClass().add(STYLE_TEAM_UNIT_MEMBER);
        member.getChildren().add(CommonNodes.Image(IMG_USER, 22, 22));
        member.getChildren().add(new Text(dev.getName()));
        return member;
    }

    /**
     * Builds the New Developer form
     *
     * @param app   main application
     * @param teams List of all teams
     * @return new form node object
     */
    private VBox buildNewDevForm(UiApp app, List<Team> teams){
        Validator validator = new Validator();
        VBox box = new VBox();
        box.getStyleClass().add("developer-form");

        Text headline = new Text("New Developer");
        headline.getStyleClass().add(STYLE_H2);
        box.getChildren().add(headline);

        HBox wrapper = new HBox();
        wrapper.getStyleClass().add(STYLE_WRAPPER);
        box.getChildren().add(wrapper);

        VBox form = new VBox();
        form.getStyleClass().add(STYLE_FORM);
        wrapper.getChildren().add(form);

        // Name
        VBox nameBox = new VBox();
        nameBox.getStyleClass().add(STYLE_FORM_ITEM);
        nameBox.getChildren().add(new Text("Name"));
        TextField name = new TextField();
        nameBox.getChildren().add(name);
        form.getChildren().add(nameBox);

        // Email
        VBox emailBox = new VBox();
        emailBox.getStyleClass().add(STYLE_FORM_ITEM);
        emailBox.getChildren().add(new Text("Email"));
        TextField email = new TextField();
        emailBox.getChildren().add(email);
        form.getChildren().add(emailBox);

        // Passwort
        VBox passwordBox = new VBox();
        passwordBox.getStyleClass().add(STYLE_FORM_ITEM);
        passwordBox.getChildren().add(new Text("Password"));
        TextField password = new TextField();
        passwordBox.getChildren().add(password);
        form.getChildren().add(passwordBox);

        // Submit button
        VBox submitWrapper = new VBox();
        Button submit = new Button("save developer");
        submit.getStyleClass().add("button");
        submitWrapper.getChildren().addAll(submit, validator.getMessageField());
        form.getChildren().add(submitWrapper);


        // Team Select
        VBox teamSelect = new VBox();
        teamSelect.getStyleClass().add("check-group");
        teamSelect.getChildren().add(new Text("Teams"));
        wrapper.getChildren().add(teamSelect);

        ArrayList<DataCheckbox<Team>> checkboxes = new ArrayList<>();
        for (Team team : teams) {
            DataCheckbox<Team> checkbox = new DataCheckbox<>(team, team.getName());
            checkboxes.add(checkbox);
            teamSelect.getChildren().add(checkbox);
        }

        // Submit event
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (validator.execute()) {
                Developer dev = new Developer();
                dev.setName(name.getText());
                dev.setEmail(email.getText());
                dev.setPwhash(password.getText());
                new DeveloperModel(dev).save();
                Developer savedDev = new DeveloperModel(dev.getEmail()).getContainer();
                checkboxes.forEach(teamDataCheckbox -> {
                    new TeamModel(teamDataCheckbox.getData()).addDeveloper(savedDev);
                });
                app.route("page-team", new HashMap<>(), true);
            }
            validator.reset();
        });

        // Validations
        validator.addRule(new Validator.Rule(name, Validator::TextNotEmpty, "Name can't be empty."));
        validator.addRule(new Validator.Rule(email, Validator::TextNotEmpty, "Email can't be empty."));
        validator.addRule(new Validator.Rule(password, Validator::TextNotEmpty, "Password can't be empty."));
        box.getChildren().add(validator.getMessageField());

        return box;
    }

    private VBox buildNewTeamForm(UiApp app, List<Developer> developers){
        Validator validator = new Validator();
        VBox box = new VBox();
        box.getStyleClass().add(STYLE_FORM);

        Text headline = new Text("New Team");
        headline.getStyleClass().add(STYLE_H2);
        box.getChildren().add(headline);

        // Team Name
        VBox nameBox = new VBox();
        nameBox.getStyleClass().add(STYLE_FORM_ITEM);
        nameBox.getChildren().add(new Text("Name"));
        TextField name = new TextField();
        nameBox.getChildren().add(name);
        box.getChildren().add(nameBox);

        // Team Leader
        VBox leaderBox = new VBox();
        leaderBox.getStyleClass().add(STYLE_FORM_ITEM);
        leaderBox.getChildren().add(new Text("Leader"));
        ChoiceBox<CommonNodes.EntityWrapper<Developer>> leader = CommonNodes.ChoiceBox(developers, dev -> dev.getName() + " <" + dev.getEmail() + ">");
        leaderBox.getChildren().add(leader);
        box.getChildren().add(leaderBox);

        // Submit button
        VBox submitWrapper = new VBox();
        Button submit = new Button("save team");
        submit.getStyleClass().add("button");
        submitWrapper.getChildren().addAll(submit, validator.getMessageField());
        box.getChildren().add(submitWrapper);

//         Submit event
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (validator.execute()) {
                Developer leadDev = leader.getValue().getItem();
                Team team = new Team();
                team.setName(name.getText());
                team.setLeader(leadDev.getId());
                team.setCreated(new Timestamp(System.currentTimeMillis()));
                TeamModel model = new TeamModel(team);
                model.save();
                model.addDeveloper(leadDev);
                app.route("page-team", new HashMap<>(), true);
            }
            validator.reset();
        });

        // Validations
        validator.addRule(new Validator.Rule(name, Validator::TextNotEmpty, "Name can't be empty"));

        return box;
    }

    private VBox buildDevTeamForms(UiApp app, List<Developer> developers, List<Team> teams){
        VBox form = new VBox();
        form.getStyleClass().add(STYLE_FORM);

        // Headline
        Text headline = new Text("Add/Remove developer to/from a team");
        headline.getStyleClass().add(STYLE_H2);
        form.getChildren().add(headline);

        // Form Inputs
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);
        box.getStyleClass().add(STYLE_FORM_ITEM);
        form.getChildren().add(box);
        ChoiceBox<CommonNodes.EntityWrapper<Developer>> developer = CommonNodes.ChoiceBox(developers, dev -> dev.getName() + " <" + dev.getEmail() + ">");
        ChoiceBox<CommonNodes.EntityWrapper<Team>> team = CommonNodes.ChoiceBox(teams, Team::getName);
        CheckBox remove = new CheckBox("remove");
        box.getChildren().addAll(developer, team, remove);

        // Submit
        Button submit = new Button("execute");
        submit.getStyleClass().addAll("button", "button--small");
        form.getChildren().add(submit);

        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Developer d = developer.getValue().getItem();
            Team t = team.getValue().getItem();
            boolean r = remove.isSelected();

            TeamModel tModel = new TeamModel(t);
            if (r && tModel.getDevelopers().contains(d)) {
                tModel.removeDeveloper(d);
            } else if (!tModel.getDevelopers().contains(d)) {
                tModel.addDeveloper(d);
            }
            app.route("page-team", new HashMap<>(), true);

        });

        return form;
    }
}
