package de.htwsaar.owlkeeper.storage.model;

import static de.htwsaar.owlkeeper.service.PermissionHandler.checkPermission;

import java.util.List;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.extension.ExtensionCallback;

import de.htwsaar.owlkeeper.helper.Permissions;
import de.htwsaar.owlkeeper.helper.exceptions.InsufficientPermissionsException;
import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.dao.ProjectDao;
import de.htwsaar.owlkeeper.storage.dao.ProjectStageDao;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.entity.Team;

public class ProjectStageModel extends AbstractModel<ProjectStage, ProjectStageDao> {

    private static Logger logger = LogManager.getLogger(ProjectStageModel.class);
    private static Function<Long, ExtensionCallback<ProjectStage, ProjectStageDao, RuntimeException>> loadCallbackFactory1 = id -> (dao -> dao.getProjectStage(id));
    private static Function<Long, ExtensionCallback<Integer, ProjectStageDao, InsufficientPermissionsException>> removeCallbackFactory =
        id -> (dao -> {
            checkPermission(Permissions.DELETE_PROJECT_STAGE.get());
            checkPermission(user -> // Check that the user is assigned to the project.
                    DBConnection.getJdbi().withExtension(ProjectDao.class,
                            projectDao -> projectDao.getProjectsOfUser(user.getId())).contains(dao.getProject(id)));
            return dao.removeProjectStage(id);
        });
    private static Function<ProjectStage, ExtensionCallback<Integer, ProjectStageDao, InsufficientPermissionsException>> saveCallbackFactory1 =
            p -> (dao -> {
                checkPermission(Permissions.CREATE_PROJECT_STAGE.get());
                checkPermission(user -> // Check that the user is assigned to the project.
                        DBConnection.getJdbi().withExtension(ProjectDao.class,
                                projectDao -> projectDao.getProjectsOfUser(user.getId()))
                                .contains(new ProjectModel(p.getProject()).getContainer()));
                return (p.getId() != 0 ? dao.updateProjectStage(p) : dao.insertProjectStage(p));
            });


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

    /**
     * Retrieves all Teams involved in Tasks of the ProjectStage
     *
     * @return all Teams
     */
    public List<Team> getTeams() {
        return DBConnection.getJdbi().withExtension(ProjectStageDao.class, dao -> dao.getTeams(getContainer().getId()));
    }

}
