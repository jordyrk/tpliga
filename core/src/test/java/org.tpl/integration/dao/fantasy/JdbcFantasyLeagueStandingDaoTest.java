package org.tpl.integration.dao.fantasy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.fantasy.*;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class JdbcFantasyLeagueStandingDaoTest {
    JdbcFantasyLeagueStandingDao jdbcFantasyLeagueStandingDao;
    @Before
    public void setUp() throws Exception {
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcFantasyLeagueStandingDao = new JdbcFantasyLeagueStandingDao();
        jdbcFantasyLeagueStandingDao.setDataSource(dataSource);
    }

    @After
    public void tearDown() throws Exception {
        jdbcFantasyLeagueStandingDao = null;
    }

    @Test
    public void testSaveOrUpdateFantasyLeagueStanding() throws Exception {
        jdbcFantasyLeagueStandingDao.saveOrUpdateFantasyLeagueStanding(createObject());
        FantasyLeagueStanding fcs = jdbcFantasyLeagueStandingDao.getFantasyLeagueStandingByIds(1, 1, 1);
        jdbcFantasyLeagueStandingDao.saveOrUpdateFantasyLeagueStanding(fcs);
    }

    @Test
    public void testGetFantasyLeagueStandingByIds() throws Exception {

        FantasyLeagueStanding fls = jdbcFantasyLeagueStandingDao.getFantasyLeagueStandingByIds(1, 3, 1);
        assertEquals("Points == 3", 3, fls.getPoints());
        assertEquals("goalsscored == 2", 2, fls.getGoalsScored());
        assertEquals("goalsagainst == 1", 1, fls.getGoalsAgainst());
        assertEquals("matcheswon == 1", 1, fls.getMatchesWon());
        assertEquals("matchesdraw == 0", 0, fls.getMatchesDraw());
        assertEquals("matcheslost == 0", 0, fls.getMatchesLost());
        assertEquals("position == 1", 1, fls.getPosition());
        assertEquals("points == 3", 3, fls.getPoints());
        assertEquals("lastroundposition == 1", 1, fls.getLastRoundPosition());
    }

    @Test
    public void testGetFantasyLeagueStandingByRoundAndCompetition() throws Exception {
        List<FantasyLeagueStanding> list = jdbcFantasyLeagueStandingDao.getFantasyLeagueStandingByRoundAndLeague(3, 1);
        assertEquals("list size is 2",2, list.size());
        list = jdbcFantasyLeagueStandingDao.getFantasyLeagueStandingByRoundAndLeague(3, 2);
        assertEquals("list size is 2",2, list.size());
        list = jdbcFantasyLeagueStandingDao.getFantasyLeagueStandingByRoundAndLeague(3, 3);
        assertEquals("list size is 0",0, list.size());

    }

    @Test
    public void testGetFantasyLeagueStandingByTeamAndCompetition() throws Exception {
        List<FantasyLeagueStanding> list = jdbcFantasyLeagueStandingDao.getFantasyLeagueStandingByTeamAndLeague(3, 1);
        assertEquals("list size is 2",2, list.size());
        list = jdbcFantasyLeagueStandingDao.getFantasyLeagueStandingByTeamAndLeague(3, 2);
        assertEquals("list size is 2",2, list.size());

    }

    private FantasyLeagueStanding createObject(){
        FantasyLeagueStanding fantasyLeagueStanding = new FantasyLeagueStanding();
        fantasyLeagueStanding.setFantasyTeam(new FantasyTeam());
        fantasyLeagueStanding.getFantasyTeam().setTeamId(1);
        fantasyLeagueStanding.setFantasyLeague(new FantasyLeague());
        fantasyLeagueStanding.getFantasyLeague().setId(1);
        fantasyLeagueStanding.setFantasyRound(new FantasyRound());
        fantasyLeagueStanding.getFantasyRound().setFantasyRoundId(1);
        
        fantasyLeagueStanding.setPosition(2);
        fantasyLeagueStanding.setLastRoundPosition(2);
        fantasyLeagueStanding.setPoints(0);
        fantasyLeagueStanding.setGoalsScored(3);
        fantasyLeagueStanding.setGoalsAgainst(3);
        fantasyLeagueStanding.setMatchesWon(2);
        fantasyLeagueStanding.setMatchesLost(2);
        fantasyLeagueStanding.setMatchesDraw(2);

        
        return fantasyLeagueStanding;
    }
}
