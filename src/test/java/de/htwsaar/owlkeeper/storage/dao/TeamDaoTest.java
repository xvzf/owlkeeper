package de.htwsaar.owlkeeper.storage.dao;

import de.htwsaar.owlkeeper.storage.DBConnection;
import de.htwsaar.owlkeeper.storage.entity.Developer;
import de.htwsaar.owlkeeper.storage.entity.Team;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Needs to run against test database !!!
 */
class TeamDaoTest {

    @Test
    void testGetTeam() {

        Team team = DBConnection.getJdbi().withExtension(TeamDao.class, dao -> {
            return dao.getTeam(1);
        });

        assertNotNull(team);
        assertNotNull(team.getCreated());
        assertEquals("Team 1", team.getName());
        assertEquals(1, team.getLeader());
    }

    @Test
    void testGetTeams() {
        List<Team> teams = DBConnection.getJdbi().withExtension(TeamDao.class, TeamDao::getTeams);
        assertEquals(2, teams.size());
    }


    @Test
    void testGetDevelopersPerTeam() {
        List<Developer> developers = DBConnection.getJdbi().withExtension(TeamDao.class, dao -> {
            return dao.getDevelopersPerTeam(1);
        });

        List<Long> test = developers.stream().map(d -> d.getId()).collect(Collectors.toList());

        assertTrue(test.contains(1L));
        assertTrue(test.contains(2L));
        assertTrue(test.contains(3L));

        assertEquals(3, test.size());
    }

    @Test
    void testGetTeamsForDeveloperMultipleTeams() {
        List<Team> teams = DBConnection.getJdbi().withExtension(TeamDao.class, dao -> {
            return dao.getTeamForDeveloper(3);
        });

        assertEquals(2, teams.size());
    }

    @Test
    void testGetTeamsForDeveloperOneTeam() {
        List<Team> teams = DBConnection.getJdbi().withExtension(TeamDao.class, dao -> {
            return dao.getTeamForDeveloper(1);
        });

        assertEquals(1, teams.size());
    }

}