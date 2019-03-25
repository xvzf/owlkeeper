package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.entity.HasID;
import javafx.scene.chart.PieChart;
import org.apache.logging.log4j.Logger;
import org.jdbi.v3.core.extension.ExtensionCallback;

import java.util.function.Function;

/**
 * AbstractModel class provides basic Model functionalities for Model classes
 * Model classes generate, save, remove and load Object from/to/for/whatever the database
 * Example with an TaskModel:
 *
 * Task task = new Task(param1, param2....);
 * TaskModel tm = new TaskModel(task);
 * tm.save();
 * List<Developer> devs = tm.getAssignedDevs();
 *
 * @param <R> The type of the container. Must implement HasID
 * @param <E> The DAO responsible for retrieving the container from db
 */
public abstract class AbstractModel<R extends HasID, E> {
    private Logger logger;
    private Class<E> DAOClass;  // Save E in DAOClass because java generics are awful
    private Function<Long, ExtensionCallback<R, E, RuntimeException>> loadCallbackFactory;
    private Function<Long, ExtensionCallback<Integer, E, RuntimeException>> removeCallbackFactory;
    private Function<R, ExtensionCallback<Integer, E, RuntimeException>> saveCallbackFactory;
    private R container;

    /**
     * Constructor
     * @param logger A logger for errors and infos
     * @param DAOClass MUST BE THE CLASS OF <E>
     * @param loadCallbackFactory A factory generating the callback for getFromDB.
     *                            Its parameter is the id.
     *                            Returns a function with parameter dao, that loads the container from db.
     *                            Example: id -> (dao -> dao.getFromId(id))
     * @param removeCallbackFactory A factory generating the callback for removeFromDB
     *                              Its parameter is the id
     *                              Returns a function with parameter dao, that removes the container from the db
     *                              Example: id -> (dao -> dao.remove(id))
     * @param saveCallbackFactory A factory generating the callback for save.
     *                            Its parameter is the container that will be saved.
     *                            Returns a function with parameter dao, that saves the container to the db
     *                            Important: This function must check on its own, if the container must be
     *                            inserted or updated into the db
     *                            Example: c -> (dao -> (c.isSaved() ? dao.update(c) : dao.insert(c)))
     */
    public AbstractModel(Logger logger,
                         Class<E> DAOClass,
                         Function<Long, ExtensionCallback<R, E, RuntimeException>> loadCallbackFactory,
                         Function<Long, ExtensionCallback<Integer, E, RuntimeException>> removeCallbackFactory,
                         Function<R, ExtensionCallback<Integer, E, RuntimeException>> saveCallbackFactory) {
        this.logger = logger;
        this.DAOClass = DAOClass;
        this.loadCallbackFactory = loadCallbackFactory;
        this.removeCallbackFactory = removeCallbackFactory;
        this.saveCallbackFactory = saveCallbackFactory;
    }

    /**
     * Constructor used for generating a model with existing container
     * @param container the container

     */
    public AbstractModel(R container,
                         Logger logger,
                         Class<E> DAOClass,
                         Function<Long, ExtensionCallback<R, E, RuntimeException>> loadCallbackFactory,
                         Function<Long, ExtensionCallback<Integer, E, RuntimeException>> removeCallbackFactory,
                         Function<R, ExtensionCallback<Integer, E, RuntimeException>> saveCallbackFactory){
        this(logger, DAOClass, loadCallbackFactory, removeCallbackFactory, saveCallbackFactory);
        setContainer(container);
    }

    /**
     * Refreshes local data
     *
     * @param id id of the container in the database
     */
    public void getFromDB(long id) {
        ExtensionCallback<R, E, RuntimeException> loadCallback = loadCallbackFactory.apply(id);
        container = DBConnection.getJdbi().withExtension(DAOClass, loadCallback);
        logger.info("Loaded " + toString() + " from index " + id);
    }

    /**
     * Get container
     *
     * @return the container
     */
    public R getContainer() {
        return container;
    }

    /**
     * Set Container
     * @param container the new container
     */
    public void setContainer(R container) {
        this.container = container;
    }

    /**
     * Saves changes to the DB
     *
     * @return
     */
    public void save() {
        ExtensionCallback<Integer, E, RuntimeException> saveCallback = saveCallbackFactory.apply(container);
        int newId = DBConnection.getJdbi().withExtension(DAOClass, saveCallback);

        // Refresh every time
        getFromDB(newId);
        logger.info("Saved " + toString() + " to index " + newId);
    }

    public int removeFromDB(){
        ExtensionCallback<Integer, E, RuntimeException> removeCallback = removeCallbackFactory.apply(getContainer().getId());
        int removedId = DBConnection.getJdbi().withExtension(DAOClass, removeCallback);
        logger.info("Removed " + toString() + " from index " + removedId);
        return removedId;
    }

    /**
     * toString
     *
     * @return String
     */
    @Override
    public String toString() {
        return getContainer().toString();
    }
}
