package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.dao.DeveloperDao;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Task;
import de.htwsaar.owlkeeper.storage.entity.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.extension.ExtensionCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.lang.String;
import java.security.*;

public class DeveloperModel extends AbstractModel<Developer, DeveloperDao> {

    private static Logger logger = LogManager.getLogger(DeveloperModel.class);
    private static Function<Long, ExtensionCallback<Developer, DeveloperDao, RuntimeException>> loadCallbackFactory1 = id -> (dao -> dao.getDeveloper(id));
    private static Function<Long, ExtensionCallback<Integer, DeveloperDao, RuntimeException>> deleteCallbackFactory = id -> (dao -> dao.deleteDeveloper(id));
    private static Function<Developer, ExtensionCallback<Integer, DeveloperDao, RuntimeException>> saveCallbackFactory1 =
            dev -> (dao -> (dev.getId() != 0 ? dao.updateDeveloper(dev) : dao.insertDeveloper(dev)));

    /**
     * Developer Model, creates a new developer
     *
     * @param name  Name
     * @param role  Role
     * @param email Email
     * @param chief chief
     */
    public DeveloperModel(String name, String role, String email, boolean chief) {
        super(logger, DeveloperDao.class, loadCallbackFactory1, deleteCallbackFactory, saveCallbackFactory1);
        setContainer(new Developer());
        Developer d = getContainer();
        d.setName(name);
        d.setRole(role);
        d.setEmail(email);
        d.setChief(chief);
    }

    /**
     * Queries a developer out of the db
     *
     * @param id Id in the db
     */
    public DeveloperModel(long id) {
        super(logger, DeveloperDao.class, loadCallbackFactory1, deleteCallbackFactory, saveCallbackFactory1);
        getFromDB(id);
    }

    /**
     * Queries a developer out of the db
     *
     * @param email Developer email
     */
    public DeveloperModel(final String email) {
        super(logger, DeveloperDao.class, loadCallbackFactory1, deleteCallbackFactory, saveCallbackFactory1);

        setContainer(DBConnection.getJdbi().withExtension(DeveloperDao.class, dao -> {
            return dao.getDeveloper(email);
        }));
    }

    /**
     * Constructor for already existing Developers
     *
     * @param dev Developer
     */
    public DeveloperModel(Developer dev) {
        super(dev, logger, DeveloperDao.class, loadCallbackFactory1, deleteCallbackFactory, saveCallbackFactory1);
    }


    /**
     * Retrieves all Developers
     *
     * @return developerList List with all Projects
     */
    public static List<Developer> getDevelopers() {
        List<Developer> developerList = DBConnection.getJdbi().withExtension(DeveloperDao.class, (dao -> dao.getDevelopers()));
        return developerList;
    }

    /**
     * Retrieves all teams of a developer
     *
     * @return all teams
     */
    public List<Team> getTeams() {
        return DBConnection.getJdbi().withExtension(DeveloperDao.class, dao -> (dao.getTeams(getContainer())));
    }

    /**
     * Retrieves all tasks of a developer
     *
     * @return all tasks
     */
    public List<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Team team : getTeams()) {
            tasks.addAll(new TeamModel(team).getTasks());
        }
        return tasks;
    }

    private String getHash(String pw) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (java.security.NoSuchAlgorithmException e) {
            return "Nicht gefunden!";   //TODO Something better than that
        }
        byte[] hash;
        try {
            hash = digest.digest(pw.getBytes("UTF_8"));
        }catch(java.io.UnsupportedEncodingException e){
            return "FEHLER";            //TODO something better
        }
        return Arrays.toString(hash);

    }
}
