package org.tpl.integration.dao.fantasy;

import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyLeague;
import org.tpl.business.model.fantasy.FantasySeason;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;
import javax.sql.DataSource;

import java.util.List;

import static junit.framework.Assert.*;


public class JdbcFantasyLeagueDaoTest {

    JdbcFantasyLeagueDao jdbcFantasyLeagueDao;

    @Before
    public void setup(){
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcFantasyLeagueDao = new JdbcFantasyLeagueDao();
        jdbcFantasyLeagueDao.setDataSource(dataSource);
    }
    @Test
    public void testSaveOrUpdateLeague() throws Exception {
        FantasyLeague fantasyLeague = createFantasyLeague();
        jdbcFantasyLeagueDao.saveOrUpdateLeague(fantasyLeague);
        assertFalse("league not new", fantasyLeague.isNew());
    }

    @Test
    public void testDeleteFantasyLeague() throws Exception {
        FantasyLeague fantasyLeague = jdbcFantasyLeagueDao.getFantasyLeagueById(1);
        assertNotNull("fantasyLeage is not deleted", fantasyLeague);
        jdbcFantasyLeagueDao.deleteFantasyLeague(1);
        fantasyLeague = jdbcFantasyLeagueDao.getFantasyLeagueById(1);
        assertNull("fantasyLeage is deleted", fantasyLeague);

    }

    @Test
    public void testGetFantasyLeagueById() throws Exception {
        FantasyLeague fantasyLeague = jdbcFantasyLeagueDao.getFantasyLeagueById(1);
        assertEquals("id = 1", 1, fantasyLeague.getId());
        assertEquals("name = 1 division", "1 division", fantasyLeague.getName());
        assertFalse("homeandawaymatches",fantasyLeague.isHomeAndAwayMatches());
        assertEquals("numberofteams = 10", 10, fantasyLeague.getNumberOfTeams());
        assertEquals(1, fantasyLeague.getNumberOfTopTeams());
        assertEquals(2, fantasyLeague.getNumberOfSecondTopTeams());
        assertEquals(2, fantasyLeague.getNumberOfBottomTeams());
        assertEquals(0, fantasyLeague.getNumberOfSecondBotttomTeams());
        assertEquals("style", fantasyLeague.getStyletheme());
    }

    @Test
    public void testGetAllFantasyLeagues() throws Exception {
        List<FantasyLeague> fantasyLeagueList = jdbcFantasyLeagueDao.getAllFantasyLeagues();
        assertEquals("league list size = 3",3, fantasyLeagueList.size());

    }

    @Test
    public void testGetAllFantasyLeaguesBySeasonId() throws Exception {
        List<FantasyLeague> fantasyLeagueList = jdbcFantasyLeagueDao.getAllFantasyLeagues(1);
        assertEquals("league list size = 3",3, fantasyLeagueList.size());

    }

    @Test
    public void testGetFantasyLeaguesForFantasyTeamInSeason() throws Exception {
        List<FantasyLeague> list = jdbcFantasyLeagueDao.getFantasyLeaguesForFantasyTeam(1,1);
        assertEquals("size is 1",1,list.size());

        list = jdbcFantasyLeagueDao.getFantasyLeaguesForFantasyTeam(1,100);
        assertEquals("size is 0",0,list.size());


    }

    @Test
    public void testGetFantasyLeaguesForFantasyTeam() throws Exception {
        List<FantasyLeague> list = jdbcFantasyLeagueDao.getFantasyLeaguesForFantasyTeam(1);
        assertEquals("size is 1",1,list.size());
    }

    @Test
    public void testGetAllFantasyLeaguesForFantasyRound() throws Exception {
        List<FantasyLeague> fantasyLeagues = jdbcFantasyLeagueDao.getAllFantasyLeaguesForFantasyRound(1);
        assertEquals(1, fantasyLeagues.size());
    }

    private FantasyLeague createFantasyLeague(){
        FantasyLeague fantasyLeague = new FantasyLeague();
        fantasyLeague.setName("League");
        fantasyLeague.setHomeAndAwayMatches(false);
        fantasyLeague.setNumberOfTeams(2);
        fantasyLeague.setStyletheme("mystyle");
        fantasyLeague.setNumberOfTopTeams(1);
        fantasyLeague.setFantasySeason(new FantasySeason());


        return fantasyLeague;

    }
}
