package org.tpl.integration.dao.fantasy;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyCompetition;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.business.model.fantasy.FantasyTeamCompetition;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.List;

public class JDBCFantasyCompetitionDaoTest extends TestCase {

    JDBCFantasyCompetitionDao jdbcFantasyCompetitionDao;
    JDBCFantasyTeamDao jdbcFantasyTeamDao;

    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcFantasyCompetitionDao = new JDBCFantasyCompetitionDao();
        jdbcFantasyCompetitionDao.setDataSource(dataSource);
        jdbcFantasyTeamDao = new JDBCFantasyTeamDao();
        jdbcFantasyTeamDao.setDataSource(dataSource);
    }

    @After
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        jdbcFantasyCompetitionDao = null;
        jdbcFantasyTeamDao = null;
    }

    @Test
    public void testSimpleSaveFantasyCompetition(){
        FantasyCompetition fantasyCompetition = createFantasyCompetition();
        jdbcFantasyCompetitionDao.saveOrUpdateFantasyCompetition(fantasyCompetition);
        assertTrue("fantasyCompetition.id > 0", fantasyCompetition.getFantasyCompetitionId() > 0 );
    }

    @Test
    public void testSaveFantasyCompetition(){
        FantasyCompetition fantasyCompetition = createFantasyCompetition();
        jdbcFantasyCompetitionDao.saveOrUpdateFantasyCompetition(fantasyCompetition);
        assertTrue("fantasyCompetition.id > 0", fantasyCompetition.getFantasyCompetitionId() > 0 );
        FantasyCompetition fantasyCompetition2 = jdbcFantasyCompetitionDao.getFantasyCompetitionById(fantasyCompetition.getFantasyCompetitionId());
        assertEquals("fantasyCompetition.id = fantasyCompetition2.id",fantasyCompetition.getFantasyCompetitionId(),fantasyCompetition2.getFantasyCompetitionId());
        assertEquals("fantasyCompetition.Name = fantasyCompetition2.Name",fantasyCompetition.getName(),fantasyCompetition2.getName());
        assertEquals("fantasyCompetition.isDefaultCompetition = fantasyCompetition2.isDefaultCompetition",fantasyCompetition.isDefaultCompetition(),fantasyCompetition2.isDefaultCompetition());
        assertEquals("fantasyCompetition.numberofteams = fantasyCompetition2.Season.id",fantasyCompetition.getNumberOfTeams(),fantasyCompetition2.getNumberOfTeams());
        assertEquals("fantasyCompetition.fantasyseason.id = fantasyCompetition2.fantasyseason.id",fantasyCompetition.getFantasySeason().getFantasySeasonId() ,fantasyCompetition2.getFantasySeason().getFantasySeasonId());

    }

    @Test
    public void testGetFantasyCompetitionById(){
        FantasyCompetition fantasyCompetition = jdbcFantasyCompetitionDao.getFantasyCompetitionById(1);
        assertTrue("fantasyCompetition.getFantasySeasonId() == 1", fantasyCompetition.getFantasyCompetitionId() == 1);
        assertTrue("fantasyCompetition.getName() == Venneliga", fantasyCompetition.getName().equals("Standard liga"));
        assertTrue("fantasyCompetition.isDefaultCompetition", fantasyCompetition.isDefaultCompetition());
        assertTrue("fantasyCompetition.getNumberOfTeams() == 9", fantasyCompetition.getNumberOfTeams() == 9);
        assertTrue("fantasyCompetition.getFantasySeason().getFantasySeasonId() == 1", fantasyCompetition.getFantasySeason().getFantasySeasonId() == 1);
    }

    @Test
    public void testDefaultCompetition(){
        FantasyCompetition fantasyCompetition = jdbcFantasyCompetitionDao.getFantasyCompetitionById(1);
        assertTrue("fantasyCompetition.isDefaultCompetition", fantasyCompetition.isDefaultCompetition());

        FantasyCompetition fantasyCompetition2 = createFantasyCompetition();
        jdbcFantasyCompetitionDao.saveOrUpdateFantasyCompetition(fantasyCompetition2);
        assertTrue("fantasyCompetition2.isDefaultCompetition", fantasyCompetition2.isDefaultCompetition());

        fantasyCompetition = jdbcFantasyCompetitionDao.getFantasyCompetitionById(1);
        assertFalse("fantasyCompetition.isDefaultCompetition", fantasyCompetition.isDefaultCompetition());

    }

    @Test
    public void testAddTeamToCompetetion(){
        FantasyTeam fantasyTeam = jdbcFantasyTeamDao.getFantasyTeamById(2);
        FantasyCompetition fantasyCompetition = jdbcFantasyCompetitionDao.getFantasyCompetitionById(1);
        boolean value = jdbcFantasyCompetitionDao.addTeamToCompetetion(fantasyCompetition.getFantasyCompetitionId(),fantasyTeam.getTeamId());
        assertTrue("insert team in competition", value);
        value = jdbcFantasyCompetitionDao.addTeamToCompetetion(fantasyCompetition.getFantasyCompetitionId(),fantasyTeam.getTeamId());
        assertFalse("insert team in competition", value);
    }

    @Test
    public void testGetFantasyTeamCompetitionByIds(){
        FantasyTeamCompetition fantasyTeamCompetition = jdbcFantasyCompetitionDao.getFantasyTeamCompetitionByIds(2,1,1);
        assertTrue("fantasyTeamCompetition.getSumPoints() == 71", fantasyTeamCompetition.getSumPoints() == 71);
    }

    @Test
    public void testGetTeamsForCompetition(){
        List<FantasyTeamCompetition> list1 = jdbcFantasyCompetitionDao.getTeamsForCompetition(1);
        List<FantasyTeamCompetition> list2 = jdbcFantasyCompetitionDao.getTeamsForCompetition(2);
        assertTrue("list1.size() == 1", list1.size() == 1);
        assertTrue("list2.size() == 2", list2.size() == 2);
    }

    @Test
    public void testGetCompetitonsForTeam(){
        List<FantasyTeamCompetition> list1 = jdbcFantasyCompetitionDao.getCompetitonsForTeam(1,1);
        List<FantasyTeamCompetition> list2 = jdbcFantasyCompetitionDao.getCompetitonsForTeam(2,1);
        assertTrue("list1.size() == 2", list1.size() == 2);
        assertTrue("list2.size() == 1", list2.size() == 1);
    }

    @Test
    public void testGetAllFantasyCompetitions(){
        List<FantasyCompetition> fantasyCompetitionList = jdbcFantasyCompetitionDao.getAllFantasyCompetition();
        assertEquals(2, fantasyCompetitionList.size());
    }

    @Test
    public void testRemoveTeamFromCompetition() throws Exception {
        List<FantasyTeamCompetition> list = jdbcFantasyCompetitionDao.getCompetitonsForTeam(2,1);
        assertTrue("list.size() == 1", list.size() == 1);
        jdbcFantasyCompetitionDao.removeTeamFromAllCompetitions(2);
        list = jdbcFantasyCompetitionDao.getCompetitonsForTeam(2,1);
        assertTrue("list.size() == 0", list.size() == 0);

    }

    @Test
    public void testGetFantasyCompetitionBySeasonAndTeam() throws Exception {
        List<FantasyCompetition> fantasyCompetitionForFantasyTeamInSeason = jdbcFantasyCompetitionDao.getFantasyCompetitionForFantasyTeamInSeason(1, 1);
        assertEquals(2,fantasyCompetitionForFantasyTeamInSeason.size());

    }

    private FantasyCompetition createFantasyCompetition(){
        FantasyCompetition fantasyCompetition = new FantasyCompetition();
        fantasyCompetition.setName("Standard liga");
        fantasyCompetition.setNumberOfTeams(3);
        fantasyCompetition.setFantasySeason(new FantasySeason());
        fantasyCompetition.getFantasySeason().setFantasySeasonId(1);
        fantasyCompetition.setDefaultCompetition(true);
        return fantasyCompetition;
    }
}