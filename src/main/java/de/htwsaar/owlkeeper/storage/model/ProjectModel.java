package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.storage.dao.ProjectDao;
import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.entity.ProjectStage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.extension.ExtensionCallback;
import java.util.List;

import java.util.function.Function;

public class ProjectModel extends AbstractModel<Project, ProjectDao> {

    private static Logger logger = LogManager.getLogger(ProjectModel.class);
    private static Function<Long, ExtensionCallback<Project, ProjectDao, RuntimeException>> loadCallbackFactory1 = id -> (dao -> dao.getProject(id));
    private static Function<Long, ExtensionCallback<Integer, ProjectDao, RuntimeException>> deleteCallbackFactory = id -> (dao -> dao.deleteProject(id));
    private static Function<Project, ExtensionCallback<Integer, ProjectDao, RuntimeException>> saveCallbackFactory1 =
            p -> (dao -> (p.getId() != 0 ? dao.updateProject(p) : dao.insertProject(p)));


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
     */
    public List<ProjectStage> getStages() {
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
        List<Project> projectlist = DBConnection.getJdbi().withExtension(ProjectDao.class, (dao -> dao.getProjects()));
        return projectlist;
    }
}
