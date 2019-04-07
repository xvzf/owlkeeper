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
import de.htwsaar.owlkeeper.storage.dao.TeamDao;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Project;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.entity.Team;

public class TeamModel extends AbstractModel<Team, TeamDao> {

    private static Logger logger = LogManager.getLogger(TeamModel.class);
    private static Function<Long, ExtensionCallback<Team, TeamDao, RuntimeException>> loadCallbackFactory1 = id -> (dao -> dao
            .getTeam(id));
    private static Function<Long, ExtensionCallback<Integer, TeamDao, InsufficientPermissionsException>> deleteCallbackFactory = id -> (dao -> {
        checkPermission(Permissions.DISSOLVE_TEAM.get());
        return dao.deleteTeam(id);
    });
    private static Function<Team, ExtensionCallback<Integer, TeamDao, InsufficientPermissionsException>> saveCallbackFactory1 = p -> (dao -> {
        checkPermission(Permissions.CREATE_TEAM.get());
        return (p.getId() != 0 ? dao.updateTeam(p) : dao.insertTeam(p));
    });


    /**
     * Constructor for new Team and TeamModel. Generates the Team and saves it into the container
     * For parameters check Team class
     */
    public TeamModel(String name, long leader) {

        super(logger, TeamDao.class, loadCallbackFactory1, deleteCallbackFactory, saveCallbackFactory1);
        super.setContainer(new Team());
        this.getContainer().setName(name);
        this.getContainer().setLeader(leader);
    }

    /**
     * Queries a Team out of the db
     *
     * @param id Id in the db
     */
    public TeamModel(long id) {
        super(logger, TeamDao.class, loadCallbackFactory1, deleteCallbackFactory, saveCallbackFactory1);
        getFromDB(id);
    }


    /**
     * Constructor for already existing Teams
     *
     * @param team team
     */
    public TeamModel(Team team) {
        super(team, logger, TeamDao.class, loadCallbackFactory1, deleteCallbackFactory, saveCallbackFactory1);
    }


    /**
     * Retrieves all Teams
     *
     * @return teamList List with all Projects
     */
    public static List<Team> getTeams() {
        List<Team> teamList = DBConnection.getJdbi().withExtension(TeamDao.class, (dao -> dao.getTeams()));
        return teamList;
    }

    /**
     * Retrieves all tasks of the team
     *
     * @return all tasks
     */
    public List<Task> getTasks() {
        // Only users assigned to the team should be allowed to see the tasks.
        checkPermission(user -> DBConnection.getJdbi().withExtension(TeamDao.class,
                teamDao -> teamDao.getTeamForDeveloper(user.getId()).contains(this.getContainer())));
        long id = getContainer().getId();
        return DBConnection.getJdbi().withExtension(TeamDao.class, (dao -> dao.getTasks(id)));
    }

    /**
     * Retrieves all projects the team is involved in
     *
     * @return all projects
     */
    public List<Project> getProjects(long teamId) {
        return DBConnection.getJdbi().withExtension(TeamDao.class, dao -> dao.getProjectForTeam(teamId));
    }

    /**
     * Adds Developer to Team
     *
     * @param dev Developer
     */
    public void addDeveloper(Developer dev) {
        checkPermission(user -> this.getContainer().getLeader() == user
                .getId()); // Team Leader is allowed to add users to the team
        long developerId = dev.getId();
        long teamId = this.getContainer().getId();

        DBConnection.getJdbi().withExtension(TeamDao.class, (dao -> dao.addDeveloper(developerId, teamId)));
    }

    /**
     * Deletes Developer from Team
     *
     * @param dev Developer
     */
    public void removeDeveloper(Developer dev) {
        checkPermission(user -> dev.equals(user) || this.getContainer().getLeader() == user
                .getId()); // Team leader and the user itself can leave the team
        long developerId = dev.getId();
        long teamId = this.getContainer().getId();

        DBConnection.getJdbi().withExtension(TeamDao.class, (dao -> dao.removeDeveloper(developerId, teamId)));

    }

    /**
     * Retrieves all developers of a team
     *
     * @return all devs
     */
    public List<Developer> getDevelopers() {
        long id = getContainer().getId();
        return DBConnection.getJdbi().withExtension(TeamDao.class, (dao -> dao.getDevelopersPerTeam(id)));
    }
}
