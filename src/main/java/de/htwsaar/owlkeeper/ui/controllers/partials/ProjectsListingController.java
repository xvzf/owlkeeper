package de.htwsaar.owlkeeper.ui.controllers.partials;

import java.util.HashMap;
import java.util.List;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.model.ProjectModel;
import de.htwsaar.owlkeeper.storage.model.ProjectStageModel;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.Controller;
import de.htwsaar.owlkeeper.ui.helper.CommonNodes;
import de.htwsaar.owlkeeper.ui.helper.Validator;
import de.htwsaar.owlkeeper.ui.state.BaseState;
import de.htwsaar.owlkeeper.ui.state.TaskListState;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ProjectsListingController extends Controller {

    private static final String CREATE_PROJECT = "Create project";
    private static final String CREATE_STAGE = "Create stage";
    private static final String FIRST_STAGE_NAME = "First stage name:";
    private static final String NEW_PROJECT = "New project";
    private static final String NEW_STAGE = "New stage:";
    private static final String PROJECT_NAME = "Project name:";
    private static final String PROJECT_DESC = "Project description:";
    private static final String STAGE_TITLE = "Stage title";
    private static final String MSG_INITIAL_STAGE_NAME_EMPTY = "The initial project stage needs to have a name.";
    private static final String MSG_NEW_STAGE_NAME_EMPTY = "The new stage must have a name.";
    private static final String MSG_PROJECT_NAME_EMPTY = "Project name can't be empty.";
    private static final String MSG_PROJECT_DESC_EMPTY = "Project description can't be empty.";
    private static final String STYLE_BUTTON = "button";
    private static final String STYLE_BUTTON_SMALL = "button--small";
    private static final String STYLE_H2 = "h2";
    private static final String STYLE_P = "p";
    private static final String STYLE_PROJECT = "project";
    private static final String STYLE_PROJECT_HEADER = "project__header";
    private static final String STYLE_PROJECT_FORM = "project-form";
    private static final String STYLE_PROJECT_FORM_ITEM = "project-form__item";
    private static final String STYLE_PROJECT_STAGE = "project__stage";
    private static final String STYLE_PROJECT_STAGE_FORM = "project__stage-form";
    private static final String STYLE_PROJECT_STAGE_LISTING = "project__stage-listing";
    private static final String STYLE_PROJECT_STAGE_WRAPPER = "project__stage-wrapper";

    @FXML
    private VBox listing;

    public void setContent(UiApp app, HashMap<Long, Project> projects) {
        this.listing.getChildren().clear();
        for (Project project : projects.values()) {
            this.listing.getChildren().add(this.getProject(app, project));
        }
        this.listing.getChildren().add(CommonNodes.Hr(600, true));
        this.listing.getChildren().add(this.newProjectForm(app));
    }

    /**
     * Builds the new project form
     * 
     * @param app Main UiApp object
     * @return VBox javafx node object
     */
    private VBox newProjectForm(UiApp app) {
        Validator validator = new Validator();

        VBox wrapper = new VBox();

        Text headline = new Text(NEW_PROJECT);
        headline.getStyleClass().add(STYLE_H2);
        wrapper.getChildren().add(headline);

        VBox form = new VBox();
        form.getStyleClass().add(STYLE_PROJECT_FORM);

        // Project name
        VBox nameBox = new VBox();
        nameBox.getStyleClass().add(STYLE_PROJECT_FORM_ITEM);
        nameBox.getChildren().add(new Text(PROJECT_NAME));
        TextField name = new TextField();
        nameBox.getChildren().add(name);
        form.getChildren().add(nameBox);

        // Project description
        VBox descBox = new VBox();
        descBox.getStyleClass().add(STYLE_PROJECT_FORM_ITEM);
        descBox.getChildren().add(new Text(PROJECT_DESC));
        TextArea desc = new TextArea();
        desc.setWrapText(true);
        descBox.getChildren().add(desc);
        form.getChildren().add(descBox);

        // Project initial stage name
        VBox stageBox = new VBox();
        stageBox.getStyleClass().add(STYLE_PROJECT_FORM_ITEM);
        stageBox.getChildren().add(new Text(FIRST_STAGE_NAME));
        TextField stage = new TextField();
        stageBox.getChildren().add(stage);
        form.getChildren().add(stageBox);

        // Submit button
        Button submit = new Button(CREATE_PROJECT);
        submit.getStyleClass().addAll(STYLE_BUTTON);
        form.getChildren().add(submit);
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (validator.execute()) {
                ProjectModel newProject = new ProjectModel(name.getText(), desc.getText(), "none");
                newProject.save();
                long id = newProject.getContainer().getId();
                System.out.println(id);
                ProjectStageModel newStage = new ProjectStageModel(stage.getText(), id, 0);
                newStage.save();
                BaseState.QUERY_COUNT++;
                app.route("projects", new HashMap<>(), true);
            }
            validator.reset();
        });
        wrapper.getChildren().add(form);

        // Validations
        validator.addRule(new Validator.Rule(name, Validator::TextNotEmpty, MSG_PROJECT_NAME_EMPTY));
        validator.addRule(new Validator.Rule(desc, Validator::TextNotEmpty, MSG_PROJECT_DESC_EMPTY));
        validator.addRule(new Validator.Rule(stage, Validator::TextNotEmpty, MSG_INITIAL_STAGE_NAME_EMPTY));
        wrapper.getChildren().add(validator.getMessageField());

        return wrapper;
    }

    /**
     * Builds a single project view
     * 
     * @param app Main UiApp
     * @param project project entity object
     * @return javafx node object
     */
    private VBox getProject(UiApp app, Project project) {
        ProjectModel model = new ProjectModel(project);
        List<ProjectStage> stages = model.getStages();

        VBox box = new VBox();
        box.getStyleClass().add(STYLE_PROJECT);

        // Project header
        VBox header = new VBox();
        header.getStyleClass().add(STYLE_PROJECT_HEADER);
        box.getChildren().add(header);

        Text headline = new Text(project.getName());
        headline.getStyleClass().add(STYLE_H2);
        header.getChildren().add(headline);

        Text description = new Text(project.getDescription());
        description.setWrappingWidth(500);
        headline.getStyleClass().add(STYLE_P);
        header.getChildren().add(description);

        // Project Stages Wrapper
        HBox stageWrapper = new HBox();
        stageWrapper.getStyleClass().add(STYLE_PROJECT_STAGE_WRAPPER);

        // Project Stages
        VBox stagesBox = new VBox();
        stagesBox.getStyleClass().add(STYLE_PROJECT_STAGE_LISTING);
        for (ProjectStage stage : stages) {
            VBox stageBox = new VBox();
            stageBox.getChildren().add(new Text(stage.getName()));
            stageBox.getStyleClass().add(STYLE_PROJECT_STAGE);
            stagesBox.getChildren().add(stageBox);
            stageBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                app.route("page-iteration", TaskListState.getQueryMap(project.getId(), stage.getId(), null, false));
            });
        }
        stageWrapper.getChildren().add(stagesBox);

        // New Stage Form
        stageWrapper.getChildren().add(this.getNewStageForm(app, project, model));

        box.getChildren().add(stageWrapper);
        return box;
    }

    /**
     * Build the new stage form
     * 
     * @param app UiApp object
     * @param project project entity object
     * @param model projectmodel object used for querying the project-stages
     * @return javafx vbox node object
     */
    private VBox getNewStageForm(UiApp app, Project project, ProjectModel model) {
        Validator validator = new Validator();

        VBox newStage = new VBox();
        newStage.getStyleClass().add(STYLE_PROJECT_STAGE_FORM);
        newStage.getChildren().add(new Text(NEW_STAGE));
        TextField input = new TextField();
        input.setPromptText(STAGE_TITLE);
        newStage.getChildren().add(input);

        Button submit = new Button(CREATE_STAGE);
        submit.getStyleClass().addAll(STYLE_BUTTON, STYLE_BUTTON_SMALL);
        newStage.getChildren().add(submit);
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (validator.execute()) {
                ProjectStageModel newStageModel = new ProjectStageModel(input.getText(), project.getId(),
                        model.getStages().size() + 1);
                newStageModel.save();
                app.route("projects", new HashMap<>(), true);
            }
            validator.reset();
        });

        // Validations
        validator.addRule(new Validator.Rule(input, Validator::TextNotEmpty, MSG_NEW_STAGE_NAME_EMPTY));
        newStage.getChildren().add(validator.getMessageField());

        return newStage;
    }
}
