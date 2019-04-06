package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.helper.Permissions;
import de.htwsaar.owlkeeper.helper.exceptions.InsufficientPermissionsException;
import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.dao.ProjectDao;
import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import de.htwsaar.owlkeeper.storage.entity.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.extension.ExtensionCallback;

import java.util.List;
import java.util.function.Function;

import static de.htwsaar.owlkeeper.service.PermissionHandler.checkPermission;


public class ProjectModel extends AbstractModel<Project, ProjectDao> {

    private static Logger logger = LogManager.getLogger(ProjectModel.class);
    private static Function<Long, ExtensionCallback<Project, ProjectDao, RuntimeException>> loadCallbackFactory1 = id -> (dao -> dao.getProject(id));
    private static Function<Long, ExtensionCallback<Integer, ProjectDao, InsufficientPermissionsException>> deleteCallbackFactory =
            id -> (dao -> {
                checkPermission(Permissions.DELETE_PROJECT.get());
                return dao.deleteProject(id);
            });
    private static Function<Project, ExtensionCallback<Integer, ProjectDao, InsufficientPermissionsException>> saveCallbackFactory1 =
            p -> (dao -> {
                // To be allowed to make changes to the details: Be ProjectOwner and be assigned to this project.
                checkPermission(Permissions.CREATE_PROJECT.get());
                if (p.getId() != 0) {
                    checkPermission(user -> dao.getProjectsOfUser(user.getId()).contains(p));
                    return dao.updateProject(p);
                } else {
                    return dao.insertProject(p);
                }
            });


    /**
     * Constructor for new Projects and ProjectModel. Generates Project and saves it into the container
     * For parameters check Project class
     */
    public ProjectModel(String name, String description, String type) {

        super(logger, ProjectDao.class, loadCallbackFactory1, deleteCallbackFactory, saveCallbackFactory1);
        super.setContainer(new Project());
        this.getContainer().setName(name);
        this.getContainer().setDescription(description);
        this.getContainer().setType(type);
    }

    /**
     * Queries a Project out of the db
     *
     * @param id Id in the db
     */
    public ProjectModel(long id) {
        super(logger, ProjectDao.class, loadCallbackFactory1, deleteCallbackFactory, saveCallbackFactory1);
        getFromDB(id);
    }


    /**
     * Constructor for already existing Projects
     *
     * @param project Project
     */
    public ProjectModel(Project project) {
        super(project, logger, ProjectDao.class, loadCallbackFactory1, deleteCallbackFactory, saveCallbackFactory1);
    }

    /**
     * Retrieves List with all Project Stages for a Project
     *
     * @return ps List with all Stages
     * @throws InsufficientPermissionsException when the user is not assgined to the project he is trying to see the stages of.
     */
    public List<ProjectStage> getStages() throws InsufficientPermissionsException {
        checkPermission(user -> isAssignedToProject(user.getId())); // Only get stages for projects that a user is assigned to
        long id = getContainer().getId();
        List<ProjectStage> ps = DBConnection.getJdbi().withExtension(ProjectDao.class, (dao -> dao.getStagesForProject(id)));
        return ps;
    }

    /**
     * Retrieves all Projects
     *
     * @return projectlist List with all Projects
     */
    public static List<Project> getProjects() {
        List<Project> projectlist = DBConnection.getJdbi().withExtension(ProjectDao.class, (ProjectDao::getProjects));
        return projectlist;
    }

    /**
     * Retrieves all Teams working on the Project
     * @return List of Teams
     */
    public List<Team> getTeams(){
        long id = getContainer().getId();
        return DBConnection.getJdbi().withExtension(ProjectDao.class, (dao -> dao.getTeams(id)));
    }

    /**
     * Checks, whether a user is assigned to a project.
     * @param id of the user
     * @return true if the user is assigned to a project.
     */
    public boolean isAssignedToProject(final long id) {
        return DBConnection.getJdbi().withExtension(ProjectDao.class, dao -> dao.getProjectsOfUser(id)).contains(this.getContainer());
    }
}
