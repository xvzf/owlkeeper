package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.dao.ProjectStageDao;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.entity.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.extension.ExtensionCallback;

import java.util.List;
import java.util.function.Function;

public class ProjectStageModel extends AbstractModel<ProjectStage, ProjectStageDao> {

    private static Logger logger = LogManager.getLogger(ProjectStageModel.class);
    private static Function<Long, ExtensionCallback<ProjectStage, ProjectStageDao, RuntimeException>> loadCallbackFactory1 = id -> (dao -> dao.getProjectStage(id));
    private static Function<Long, ExtensionCallback<Integer, ProjectStageDao, RuntimeException>> removeCallbackFactory = id -> (dao -> dao.removeProjectStage(id));
    private static Function<ProjectStage, ExtensionCallback<Integer, ProjectStageDao, RuntimeException>> saveCallbackFactory1 =
            p -> (dao -> (p.getId() != 0 ? dao.updateProjectStage(p) : dao.insertProjectStage(p)));


    /**
     * Constructor for new ProjectStages and ProjectStageModel. Generates ProjectModel and saves it into the container
     * For parameters check ProjectStage class
     */
    public ProjectStageModel(String name, long project, long index) {

        super(logger, ProjectStageDao.class, loadCallbackFactory1, removeCallbackFactory, saveCallbackFactory1);
        super.setContainer(new ProjectStage());
        this.getContainer().setName(name);
        this.getContainer().setProject(project);
        this.getContainer().setIndex(index);
    }

    /**
     * Queries a ProjectStage out of the db
     *
     * @param id Id in the db
     */
    public ProjectStageModel(long id) {
        super(logger, ProjectStageDao.class, loadCallbackFactory1, removeCallbackFactory, saveCallbackFactory1);
        getFromDB(id);
    }


    /**
     * Constructor for already existing ProjectStages
     *
     * @param projectstage ProjectStage
     */
    public ProjectStageModel(ProjectStage projectstage) {
        super(projectstage, logger, ProjectStageDao.class, loadCallbackFactory1, removeCallbackFactory, saveCallbackFactory1);
    }

    /**
     * Retrieves all Tasks of the ProjectStage
     *
     * @return all Tasks
     */
    public List<Task> getTasks() {
        long id = getContainer().getId();
        return DBConnection.getJdbi().withExtension(ProjectStageDao.class, (dao -> dao.getTasks(id)));
    }

    


}
