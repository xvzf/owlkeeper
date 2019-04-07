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
import de.htwsaar.owlkeeper.ui.helper.DataCheckbox;
import de.htwsaar.owlkeeper.ui.helper.Validator;
import de.htwsaar.owlkeeper.ui.state.BaseState;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TeamController extends Controller {
    private static final String IMG_USER = "/images/user.png";

    private static final String NAME = "Name:";
    private static final String EMAIL = "Email:";
    private static final String PASSWORD = "Password:";
    private static final String LEADER = "Leader:";
    private static final String MEMBERS = "Members:";
    private static final String TEAMS = "Teams:";
    private static final String DEVELOPERS = "Teamless developers";
    private static final String DEVELOPER_NEW = "New developer";
    private static final String TEAM_NEW = "New team";
    private static final String SAVE_DEV = "Create developer";
    private static final String SAVE_TEAM = "Create team";
    private static final String REMOVE = "Remove";
    private static final String ADD = "Add";
    private static final String DEV_TEAM_FORM = "Change team members";

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
    private static final String STYLE_TEAM_FORM_WRAPPER = "project__stage-wrapper";
    private static final String STYLE_DEV_FORM = "developer-form";
    private static final String STYLE_BUTTON = "button";
    private static final String STYLE_BUTTON_SMALL = "button--small";
    private static final String STYLE_CHECK_GROUP = "check-group";

    private static final String VALIDATION_NAME = "Name can't be empty.";
    private static final String VALIDATION_EMAIL = "Email can't be empty.";
    private static final String VALIDATION_EMAIL_SYNTAX = "Email doesn't seem to be in a valid format.";
    private static final String VALIDATION_EMAIL_EXISTS = "Email already exists.";
    private static final String VALIDATION_PASSWORD = "Password can't be empty.";
    private static final String VALIDATION_DEV_TEAM = "This developer is already a member of this team and therefore can't be added again.";
    private static final String VALIDATION_DEV_TEAM_NOT = "This developer is not a member of this team and therefore can't be removed from it.";
    private static final String VALIDATION_DEV_LEADER = "This developer is the team's leader and can't be removed.";

    @FXML
    public VBox team;

    public void setContent(List<Team> teams, List<Developer> developers) {
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
        forms.getStyleClass().add(STYLE_TEAM_FORM_WRAPPER);
        forms.getChildren().add(this.buildDevTeamForms(this.getApp(), developers, teams));
        forms.getChildren().add(CommonNodes.Hr(600, true));
        forms.getChildren().add(this.buildNewDevForm(this.getApp(), teams));
        forms.getChildren().add(CommonNodes.Hr(600, true));
        forms.getChildren().add(this.buildNewTeamForm(this.getApp(), developers));
        this.team.getChildren().add(forms);
    }

    /**
     * Builds a list of all developers
     *
     * @param developers list of developers
     * @return Dev View VBox Node Object
     */
    private VBox buildUserList(List<Developer> developers) {
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
    private HBox getDeveloperBox(Developer dev) {
        HBox member = new HBox();
        member.getStyleClass().add(STYLE_TEAM_UNIT_MEMBER);
        member.getChildren().add(CommonNodes.Image(IMG_USER, 22, 22));
        member.getChildren().add(new Text(dev.getName()));
        return member;
    }

    /**
     * Builds the New Developer form
     *
     * @param app main application
     * @param teams List of all teams
     * @return new form node object
     */
    private VBox buildNewDevForm(UiApp app, List<Team> teams) {
        Validator validator = new Validator();
        VBox box = new VBox();
        box.getStyleClass().add(STYLE_DEV_FORM);

        Text headline = new Text(DEVELOPER_NEW);
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
        nameBox.getChildren().add(new Text(NAME));
        TextField name = new TextField();
        nameBox.getChildren().add(name);
        form.getChildren().add(nameBox);

        // Email
        VBox emailBox = new VBox();
        emailBox.getStyleClass().add(STYLE_FORM_ITEM);
        emailBox.getChildren().add(new Text(EMAIL));
        TextField email = new TextField();
        emailBox.getChildren().add(email);
        form.getChildren().add(emailBox);

        // Passwort
        VBox passwordBox = new VBox();
        passwordBox.getStyleClass().add(STYLE_FORM_ITEM);
        passwordBox.getChildren().add(new Text(PASSWORD));
        TextField password = new TextField();
        passwordBox.getChildren().add(password);
        form.getChildren().add(passwordBox);

        // Submit button
        VBox submitWrapper = new VBox();
        Button submit = new Button(SAVE_DEV);
        submit.getStyleClass().addAll(STYLE_BUTTON, STYLE_BUTTON_SMALL);
        submitWrapper.getChildren().addAll(submit, validator.getMessageField());
        form.getChildren().add(submitWrapper);

        // Team Select
        VBox teamSelect = new VBox();
        teamSelect.getStyleClass().add(STYLE_CHECK_GROUP);
        teamSelect.getChildren().add(new Text(TEAMS));
        wrapper.getChildren().add(teamSelect);

        ArrayList<DataCheckbox<Team>> checkboxes = new ArrayList<>();
        for (Team team : teams) {
            DataCheckbox<Team> checkbox = new DataCheckbox<>(team, team.getName());
            checkboxes.add(checkbox);
            teamSelect.getChildren().add(checkbox);
        }

        // Submit event
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                if (validator.execute()) {
                    BaseState.QUERY_COUNT++;
                    Developer dev = new Developer();
                    dev.setName(name.getText());
                    dev.setEmail(email.getText());
                    dev.setPwhash(DeveloperModel.getHash(password.getText()));
                    DeveloperModel devModel = new DeveloperModel(dev);
                    devModel.save();
                    devModel.addToGroup("admin"); // TODO: 07.04.2019 change this after access-control is fully implemented
                    Developer savedDev = new DeveloperModel(dev.getEmail()).getContainer();
                    checkboxes.forEach(teamDataCheckbox -> {
                        if (teamDataCheckbox.isSelected()){
                            new TeamModel(teamDataCheckbox.getData()).addDeveloper(savedDev);
                        }
                    });
                    app.route("page-team", new HashMap<>(), true);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            validator.reset();
        });

        // Validations
        validator.addRule(new Validator.Rule(name, Validator::TextNotEmpty, VALIDATION_NAME));
        validator.addRule(new Validator.Rule(email, Validator::TextNotEmpty, VALIDATION_EMAIL));
        validator.addRule(new Validator.Rule(email, Validator::checkEmailSyntax, VALIDATION_EMAIL_SYNTAX));
        validator.addRule(new Validator.Rule(email, Validator::checkEmailExists, VALIDATION_EMAIL_EXISTS));
        validator.addRule(new Validator.Rule(password, Validator::TextNotEmpty, VALIDATION_PASSWORD));
        box.getChildren().add(validator.getMessageField());

        return box;
    }

    private VBox buildNewTeamForm(UiApp app, List<Developer> developers) {
        Validator validator = new Validator();
        VBox box = new VBox();
        box.getStyleClass().add(STYLE_FORM);

        Text headline = new Text(TEAM_NEW);
        headline.getStyleClass().add(STYLE_H2);
        box.getChildren().add(headline);

        // Team Name
        VBox nameBox = new VBox();
        nameBox.getStyleClass().add(STYLE_FORM_ITEM);
        nameBox.getChildren().add(new Text(NAME));
        TextField name = new TextField();
        nameBox.getChildren().add(name);
        box.getChildren().add(nameBox);

        // Team Leader
        VBox leaderBox = new VBox();
        leaderBox.getStyleClass().add(STYLE_FORM_ITEM);
        leaderBox.getChildren().add(new Text(LEADER));
        ChoiceBox<CommonNodes.EntityWrapper<Developer>> leader = CommonNodes.ChoiceBox(developers,
                dev -> dev.getName() + " <" + dev.getEmail() + ">");
        leaderBox.getChildren().add(leader);
        box.getChildren().add(leaderBox);

        // Submit button
        VBox submitWrapper = new VBox();
        Button submit = new Button(SAVE_TEAM);
        submit.getStyleClass().addAll(STYLE_BUTTON, STYLE_BUTTON_SMALL);
        submitWrapper.getChildren().addAll(submit, validator.getMessageField());
        box.getChildren().add(submitWrapper);

        // Submit event
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (validator.execute()) {
                BaseState.QUERY_COUNT++;
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
        validator.addRule(new Validator.Rule(name, Validator::TextNotEmpty, VALIDATION_NAME));

        return box;
    }

    private VBox buildDevTeamForms(UiApp app, List<Developer> developers, List<Team> teams) {
        Validator validator = new Validator();

        VBox form = new VBox();
        form.getStyleClass().add(STYLE_FORM);

        // Headline
        Text headline = new Text(DEV_TEAM_FORM);
        headline.getStyleClass().add(STYLE_H2);
        form.getChildren().add(headline);

        // Form Inputs
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER_LEFT);
        box.getStyleClass().add(STYLE_FORM_ITEM);
        form.getChildren().add(box);
        ChoiceBox<CommonNodes.EntityWrapper<Developer>> developer = CommonNodes.ChoiceBox(developers,
                dev -> dev.getName() + " <" + dev.getEmail() + ">");
        ChoiceBox<CommonNodes.EntityWrapper<Team>> team = CommonNodes.ChoiceBox(teams, Team::getName);
        box.getChildren().addAll(developer, team);

        // Submit
        HBox submit = new HBox();
        submit.getStyleClass().add(STYLE_FORM_ITEM);
        Button add = new Button(ADD);
        Button remove = new Button(REMOVE);
        add.getStyleClass().addAll(STYLE_BUTTON, STYLE_BUTTON_SMALL);
        remove.getStyleClass().addAll(STYLE_BUTTON, STYLE_BUTTON_SMALL);
        submit.getChildren().addAll(add, remove);
        form.getChildren().add(submit);

        add.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Developer d = developer.getValue().getItem();
            Team t = team.getValue().getItem();
            changeTeamMember(t, d, false, validator, app);
        });
        remove.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Developer d = developer.getValue().getItem();
            Team t = team.getValue().getItem();
            changeTeamMember(t, d, true, validator, app);
        });
        form.getChildren().add(validator.getMessageField());

        return form;
    }

    private void changeTeamMember(Team t, Developer d, boolean r, Validator validator, UiApp app) {
        // Validation
        TeamModel tModel = new TeamModel(t);
        if (r) {
            if (t.getLeader() == d.getId()) {
                validator.getMessages().add(VALIDATION_DEV_LEADER);
            } else if (!tModel.getDevelopers().contains(d)) {
                validator.getMessages().add(VALIDATION_DEV_TEAM_NOT);
            }
        } else {
            if (tModel.getDevelopers().contains(d)) {
                validator.getMessages().add(VALIDATION_DEV_TEAM);
            }
        }

        // Execute
        if (validator.execute() && validator.getMessages().size() == 0) {
            if (r) {
                tModel.removeDeveloper(d);
            } else {
                tModel.addDeveloper(d);
            }
            app.route("page-team", new HashMap<>(), true);
        }
        validator.reset();
    }
}
