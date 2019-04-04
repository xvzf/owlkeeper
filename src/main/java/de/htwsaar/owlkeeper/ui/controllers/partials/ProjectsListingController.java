package de.htwsaar.owlkeeper.ui.controllers.partials;

import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.model.ProjectModel;
import de.htwsaar.owlkeeper.storage.model.ProjectStageModel;
import de.htwsaar.owlkeeper.ui.UiApp;
import de.htwsaar.owlkeeper.ui.controllers.Controller;
import de.htwsaar.owlkeeper.ui.helper.CommonNodes;
import de.htwsaar.owlkeeper.ui.state.TaskListState;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.List;

public class ProjectsListingController extends Controller{
    @FXML
    private VBox listing;

    public void setContent(UiApp app, HashMap<Long, Project> projects){
        this.listing.getChildren().clear();
        for (Project project : projects.values()) {
            this.listing.getChildren().add(this.getProject(app, project));
        }
        this.listing.getChildren().add(CommonNodes.Hr(600, true));
        this.listing.getChildren().add(this.newProjectForm(app));
    }

    private VBox newProjectForm(UiApp app){
        VBox wrapper = new VBox();

        Text headline = new Text("New project");
        headline.getStyleClass().add("h2");
        wrapper.getChildren().add(headline);

        VBox form = new VBox();
        form.getStyleClass().add("project-form");

        // Project name
        VBox nameBox = new VBox();
        nameBox.getStyleClass().add("project-form__item");
        nameBox.getChildren().add(new Text("Project Name"));
        TextField name = new TextField();
        nameBox.getChildren().add(name);
        form.getChildren().add(nameBox);

        // Project description
        VBox descBox = new VBox();
        descBox.getStyleClass().add("project-form__item");
        descBox.getChildren().add(new Text("Project description"));
        TextArea desc = new TextArea();
        desc.setWrapText(true);
        descBox.getChildren().add(desc);
        form.getChildren().add(descBox);

        // Project initial stage name
        VBox stageBox = new VBox();
        stageBox.getStyleClass().add("project-form__item");
        stageBox.getChildren().add(new Text("First stage name"));
        TextField stage = new TextField();
        stageBox.getChildren().add(stage);
        form.getChildren().add(stageBox);

        // Project type
        VBox typeBox = new VBox();
        typeBox.getStyleClass().add("project-form__item");
        typeBox.getChildren().add(new Text("Type"));
        ChoiceBox<String> type = new ChoiceBox<>(FXCollections.observableArrayList("spiral"));
        type.getSelectionModel().select(0);
        typeBox.getChildren().add(type);
        form.getChildren().add(typeBox);

        // Submit button
        Button submit = new Button("create project");
        submit.getStyleClass().addAll("button");
        form.getChildren().add(submit);
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            ProjectModel newProject = new ProjectModel(name.getText(), desc.getText(), type.getValue());
            newProject.save();
            long id = newProject.getContainer().getId();
            System.out.println(id);
            ProjectStageModel newStage = new ProjectStageModel(stage.getText(), id, 0);
            newStage.save();
            app.route("projects", new HashMap<>(), true);
        });


        wrapper.getChildren().add(form);
        return wrapper;
    }

    private VBox getProject(UiApp app, Project project){
        ProjectModel model = new ProjectModel(project);
        List<ProjectStage> stages = model.getStages();

        VBox box = new VBox();
        box.getStyleClass().add("project");

        // Project header
        VBox header = new VBox();
        header.getStyleClass().add("project__header");
        box.getChildren().add(header);

        Text headline = new Text(project.getName());
        headline.getStyleClass().add("h2");
        header.getChildren().add(headline);

        Text description = new Text(project.getDescription());
        description.setWrappingWidth(500);
        headline.getStyleClass().add("p");
        header.getChildren().add(description);

        // Project Stages Wrapper
        HBox stageWrapper = new HBox();
        stageWrapper.getStyleClass().add("project__stage-wrapper");

        // Project Stages
        VBox stagesBox = new VBox();
        stagesBox.getStyleClass().add("project__stage-listing");
        for (ProjectStage stage : stages) {
            VBox stageBox = new VBox();
            stageBox.getChildren().add(new Text(stage.getName()));
            stageBox.getStyleClass().add("project__stage");
            stagesBox.getChildren().add(stageBox);
            stageBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                app.route("page-iteration", TaskListState.getQueryMap(project.getId(), stage.getId(), null, false));
            });
        }
        stageWrapper.getChildren().add(stagesBox);

        // New Stage Form
        VBox newStage = new VBox();
        newStage.getStyleClass().add("project__stage-form");
        newStage.getChildren().add(new Text("New Stage"));
        TextField input = new TextField();
        input.setPromptText("Stage title");
        newStage.getChildren().add(input);

        Button submit = new Button("create stage");
        submit.getStyleClass().addAll("button", "button--small");
        newStage.getChildren().add(submit);
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            ProjectStageModel newStageModel = new ProjectStageModel(input.getText(), project.getId(), model.getStages().size() + 1);
            newStageModel.save();
            app.route("projects", new HashMap<>(), true);
        });
        stageWrapper.getChildren().add(newStage);

        box.getChildren().add(stageWrapper);

        return box;
    }
}
