package de.htwsaar.owlkeeper.storage.model;

import de.htwsaar.owlkeeper.storage.entity.Team;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class TeamModelTest {

    private final String P_NAME_1 = "testTeam";
    private final long P_LEADER_1 = 2;

    @Test
    void testConstructor() {
        TeamModel pm = new TeamModel(P_NAME_1, P_LEADER_1);
        Team p = pm.getContainer();
        assertEquals(p.getLeader(), P_LEADER_1);
        assertEquals(p.getName(), P_NAME_1);
    }

    @Test
    void testSaveLoadRemove() {
        TeamModel pm = new TeamModel(P_NAME_1, P_LEADER_1);
        pm.save();
        long id = pm.getContainer().getId();

        Team p = pm.getContainer();

        // check against the old values, because .save() will reload the Team from the DB
        assertEquals(p.getLeader(), P_LEADER_1);
        assertEquals(p.getName(), P_NAME_1);

        // check against another loaded Team instance with same id
        TeamModel pmloaded = new TeamModel(id);
        Team ploaded = pmloaded.getContainer();
        assertEquals(p.getCreated(), ploaded.getCreated());
        assertEquals(p.getLeader(), ploaded.getLeader());
        assertEquals(p.getName(), p.getName());
        pmloaded.removeFromDB();
    }
}
