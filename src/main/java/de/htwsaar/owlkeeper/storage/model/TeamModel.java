package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.storage.dao.TeamDao;
import de.htwsaar.owlkeeper.storage.entity.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.extension.ExtensionCallback;

import java.util.function.Function;

public class TeamModel extends AbstractModel<Team, TeamDao> {

    private static Logger logger = LogManager.getLogger(TeamModel.class);
    private static Function<Long, ExtensionCallback<Team, TeamDao, RuntimeException>> loadCallbackFactory1 = id -> (dao -> dao.getTeam(id));
    private static Function<Long, ExtensionCallback<Integer, TeamDao, RuntimeException>> removeCallbackFactory = id -> (dao -> dao.removeTeam(id));
    private static Function<Team, ExtensionCallback<Integer, TeamDao, RuntimeException>> saveCallbackFactory1 =
            p -> (dao -> (p.getId() != 0 ? dao.updateTeam(p) : dao.insertTeam(p)));


    public TeamModel(String name, long leader) {

        super(logger, TeamDao.class, loadCallbackFactory1, removeCallbackFactory, saveCallbackFactory1);
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
        super(logger, TeamDao.class, loadCallbackFactory1, removeCallbackFactory, saveCallbackFactory1);
        getFromDB(id);
    }


    /**
     * Constructor for already existing Teams
     *
     * @param Team Team
     */
    public TeamModel(Team Team) {
        super(Team, logger, TeamDao.class, loadCallbackFactory1, removeCallbackFactory, saveCallbackFactory1);
    }
}