package org.tpl.integration.dao.fantasy;

import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyCup;
import org.tpl.business.model.fantasy.FantasyCupGroup;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;

import java.util.List;

import static junit.framework.Assert.*;

public class JdbcFantasyCupGroupDaoTest {

    JdbcFantasyCupGroupDao jdbcFantasyCupGroupDao;

    @Before
    public void setup(){
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcFantasyCupGroupDao = new JdbcFantasyCupGroupDao();
        jdbcFantasyCupGroupDao.setDataSource(dataSource);
    }
    @Test
    public void testSaveOrUpdateCupGroup() throws Exception {
        FantasyCupGroup fantasyCupGroup = createFantasyCupGroup();
        jdbcFantasyCupGroupDao.saveOrUpdateCupGroup(fantasyCupGroup);
        assertFalse("cup group not new", fantasyCupGroup.isNew());
    }

    @Test
    public void testDeleteFantasyCupGroup() throws Exception {

    }

    @Test
    public void testGetFantasyCupGroupById() throws Exception {
        FantasyCupGroup fantasyCupGroup = jdbcFantasyCupGroupDao.getFantasyCupGroupById(1);
        assertEquals("id = 1", 1, fantasyCupGroup.getId());
        assertEquals("name = Mini cup group A", "Mini cup group A", fantasyCupGroup.getName());
        assertTrue("homeandawaymatches",fantasyCupGroup.isHomeAndAwayMatches());
        assertEquals("numberofteams = 2", 2, fantasyCupGroup.getNumberOfTeams());
        assertEquals("cup.id = 1", 1, fantasyCupGroup.getFantasyCup().getId());
    }

    @Test
    public void testDeleteCupGroup() throws Exception {
        jdbcFantasyCupGroupDao.deleteFantasyCupGroup(1);
        FantasyCupGroup fantasyCupGroup = jdbcFantasyCupGroupDao.getFantasyCupGroupById(1);
        assertNull("fantasyCupGroup is deleted", fantasyCupGroup);
    }

    @Test
    public void testGetAllFantasyCupGroups() throws Exception {
        List<FantasyCupGroup> fantasyCupGroupList = jdbcFantasyCupGroupDao.getAllFantasyCupGroups();
        assertEquals("league list size = 1 ",1, fantasyCupGroupList.size());
    }

    @Test
    public void testGetAllFantasyCupGroupsByCupId() throws Exception {
        List<FantasyCupGroup> fantasyCupGroupList = jdbcFantasyCupGroupDao.getAllFantasyCupGroups(1);
        assertEquals("cup list size = 1",1, fantasyCupGroupList.size());
    }

    @Test
    public void testGetFantasyCupGroupByFantasyRoundId() throws Exception {
        List<FantasyCupGroup> fantasyCupGroups = jdbcFantasyCupGroupDao.getAllFantasyCupGroupsForFantasyRound(1);
        assertEquals(1, fantasyCupGroups.size());
    }

    @Test
    public void testGetPreviousRoundId() throws Exception {
        int previousRoundId = jdbcFantasyCupGroupDao.getPreviousRoundId(1, 2);
        assertEquals(1,previousRoundId);
    }

    private FantasyCupGroup createFantasyCupGroup(){
        FantasyCupGroup fantasyCupGroup = new FantasyCupGroup();
        fantasyCupGroup.setName("CupGroup");
        fantasyCupGroup.setHomeAndAwayMatches(false);
        fantasyCupGroup.setNumberOfTeams(2);
        fantasyCupGroup.setFantasyCup(new FantasyCup());
        return fantasyCupGroup;
    }
}
