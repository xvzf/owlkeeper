package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.dao.DeveloperDao;
import de.htwsaar.owlkeeper.storage.entity.Developer;

public class DeveloperModel {
    private Developer d;

    /**
     * Developer Model, creates a new developer
     *
     * @param name
     * @param role
     * @param email
     * @param pwhash
     * @param chief
     */
    public DeveloperModel(String name, String role, String email, String pwhash, boolean chief) {
        d = new Developer();
        d.setName(name);
        d.setRole(role);
        d.setEmail(email);
        d.setPwhash(pwhash);
        d.setChief(chief);
    }

    /**
     * Queries a developer out of the db
     *
     * @param id
     */
    public DeveloperModel(long id) {
        getFromDB(id);
    }

    /**
     * Data container
     *
     * @return
     */
    public Developer getContainer() {
        return d;
    }

    /**
     * Refreshes local data
     *
     * @param id
     */
    private void getFromDB(long id) {
        d = DBConnection.getJdbi().withExtension(DeveloperDao.class, dao -> {
            return dao.getDeveloper(id);
        });
    }

    /**
     * Saves changes to the DB
     *
     * @return
     */
    public boolean save() {
        long newId = DBConnection.getJdbi().withExtension(DeveloperDao.class, dao -> {
            if(d.getId() != 0) {
                return dao.updateDeveloper(d);
            } else {
                return dao.insertDeveloper(d);
            }
        });

        // Refresh every time
        getFromDB(newId);

        return true;
    }

}
