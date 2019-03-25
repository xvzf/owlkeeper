package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.dao.DeveloperDao;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.extension.ExtensionCallback;

import java.util.function.Function;

public class DeveloperModel extends AbstractModel<Developer, DeveloperDao> {

    private static Logger logger = LogManager.getLogger(DeveloperModel.class);
    private static Function<Long, ExtensionCallback<Developer, DeveloperDao, RuntimeException>> loadCallbackFactory1 = id -> (dao -> dao.getDeveloper(id));
    private static Function<Long, ExtensionCallback<Integer, DeveloperDao, RuntimeException>> deleteCallbackFactory = id -> (dao -> dao.deleteDeveloper(id));
    private static Function<Developer, ExtensionCallback<Integer, DeveloperDao, RuntimeException>> saveCallbackFactory1 =
            dev -> (dao -> (dev.getId() != 0 ? dao.updateDeveloper(dev) : dao.insertDeveloper(dev)));

    /**
     * Developer Model, creates a new developer
     *
     * @param name   Name
     * @param email  Email
     */
    public DeveloperModel(String name, String email) {
        super(logger, DeveloperDao.class, loadCallbackFactory1, deleteCallbackFactory, saveCallbackFactory1);
        setContainer(new Developer());
        Developer d = getContainer();
        d.setName(name);
        d.setEmail(email);
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

    public String getGroup() {
        return DBConnection.getJdbi().withExtension(DeveloperDao.class, dao -> dao.getGroup(getContainer().getId()));
    }

}
