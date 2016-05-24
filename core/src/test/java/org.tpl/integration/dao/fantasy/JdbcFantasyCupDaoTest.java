package org.tpl.integration.dao.fantasy;

import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyCup;
import org.tpl.business.model.fantasy.FantasyLeague;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;

public class JdbcFantasyCupDaoTest {

    JdbcFantasyCupDao jdbcFantasyCupDao;

    @Before
    public void setup(){
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcFantasyCupDao = new JdbcFantasyCupDao();
        jdbcFantasyCupDao.setDataSource(dataSource);
    }
    @Test
    public void testSaveOrUpdateCup() throws Exception {
        FantasyCup fantasyCup = createFantasyCup();
        jdbcFantasyCupDao.saveOrUpdateCup(fantasyCup);
        assertFalse("cup not new", fantasyCup.isNew());
    }


    @Test
    public void testGetFantasyCupById() throws Exception {
        FantasyCup fantasyCup = jdbcFantasyCupDao.getFantasyCupById(1);
        assertEquals("id = 1", 1, fantasyCup.getId());
        assertEquals("name = CGO", "CGO", fantasyCup.getName());
        assertEquals("numberofteams = 20", 20, fantasyCup.getNumberOfTeams());
        assertEquals("NumberOfQualifiedTeamsFromGroups = 4", 4, fantasyCup.getNumberOfQualifiedTeamsFromGroups());
    }

    @Test
    public void testGetAllFantasyCups() throws Exception {
        List<FantasyCup> fantasyCupList = jdbcFantasyCupDao.getAllFantasyCups();
        assertEquals("cup list size = 2",2, fantasyCupList.size());

    }

    @Test
    public void testGetAllFantasyCupsBySeasonId() throws Exception {
        List<FantasyCup> fantasyCupList = jdbcFantasyCupDao.getAllFantasyCups(1);
        assertEquals("cup list size = 2",2, fantasyCupList.size());

    }

    @Test
    public void testDeleteFantasyCup() throws Exception {
        jdbcFantasyCupDao.deleteCup(1);
        FantasyCup fantasyCup = jdbcFantasyCupDao.getFantasyCupById(1);
        assertNull("fantasyCup is deleted", fantasyCup);
    }

    @Test
    public void testGetAllFantasyCupsForTeam() throws Exception {
        List<FantasyCup> fantasyCupList = jdbcFantasyCupDao.getFantasyCupByTeamId(1);
        assertEquals("cup list size = 1",1, fantasyCupList.size());
    }

    @Test
    public void testGetFantasyCupByTeamAndSeason() throws Exception {
        List<FantasyCup> cupList = jdbcFantasyCupDao.getFantasyCupByTeamIdAndSeasonId(1, 1);
        assertEquals(cupList.size(),1);
    }

    private FantasyCup createFantasyCup(){
        FantasyCup fantasyCup = new FantasyCup();
        fantasyCup.setName("Cup");
        fantasyCup.setNumberOfTeams(2);
        fantasyCup.setFantasySeason(new FantasySeason());
        return fantasyCup;
    }
}
