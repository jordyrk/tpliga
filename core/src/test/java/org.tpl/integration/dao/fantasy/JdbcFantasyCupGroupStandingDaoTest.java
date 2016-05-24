package org.tpl.integration.dao.fantasy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyCupGroup;
import org.tpl.business.model.fantasy.FantasyCupGroupStanding;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.List;

import static junit.framework.Assert.assertEquals;

public class JdbcFantasyCupGroupStandingDaoTest {
    
    JdbcFantasyCupGroupStandingDao jdbcFantasyCupGroupStandingDao;
    @Before
    public void setUp() throws Exception {
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcFantasyCupGroupStandingDao = new JdbcFantasyCupGroupStandingDao();
        jdbcFantasyCupGroupStandingDao.setDataSource(dataSource);
    }

    @After
    public void tearDown() throws Exception {
        jdbcFantasyCupGroupStandingDao = null;
    }

    @Test
    public void testSaveOrUpdateFantasyCupGroupStanding() throws Exception {
        jdbcFantasyCupGroupStandingDao.saveOrUpdateFantasyCupGroupStanding(createObject());
        FantasyCupGroupStanding fcs = jdbcFantasyCupGroupStandingDao.getFantasyCupGroupStandingByIds(1, 1, 1);
        jdbcFantasyCupGroupStandingDao.saveOrUpdateFantasyCupGroupStanding(fcs);
    }

    @Test
    public void testGetFantasyCupGroupStandingByIds() throws Exception {

        FantasyCupGroupStanding fcs = jdbcFantasyCupGroupStandingDao.getFantasyCupGroupStandingByIds(1, 1, 1);

        assertEquals("goalsscored == 2", 2, fcs.getGoalsScored());
        assertEquals("goalsagainst == 1", 1, fcs.getGoalsAgainst());
        assertEquals("matcheswon == 1", 1, fcs.getMatchesWon());
        assertEquals("matchesdraw == 0", 0, fcs.getMatchesDraw());
        assertEquals("matcheslost == 0", 0, fcs.getMatchesLost());
        assertEquals("position == 1", 1, fcs.getPosition());
        assertEquals("points == 3", 3, fcs.getPoints());
        assertEquals("lastroundposition == 1", 1, fcs.getLastRoundPosition());
    }

    @Test
    public void testGetFantasyCupGroupStandingByRoundAndCompetition() throws Exception {
        List<FantasyCupGroupStanding> list = jdbcFantasyCupGroupStandingDao.getFantasyCupGroupStandingByRoundAndCupGroup(1, 1);
        assertEquals("list size is 2",2, list.size());
        list = jdbcFantasyCupGroupStandingDao.getFantasyCupGroupStandingByRoundAndCupGroup(1, 2);
        assertEquals("list size is 2",2, list.size());
        list = jdbcFantasyCupGroupStandingDao.getFantasyCupGroupStandingByRoundAndCupGroup(1, 3);
        assertEquals("list size is 0",0, list.size());

    }

    @Test
    public void testGetFantasyCupGroupStandingByTeamAndCompetition() throws Exception {
        List<FantasyCupGroupStanding> list = jdbcFantasyCupGroupStandingDao.getFantasyCupGroupStandingByTeamAndCupGroup(1, 1);
        assertEquals("list size is 2",2, list.size());
        list = jdbcFantasyCupGroupStandingDao.getFantasyCupGroupStandingByTeamAndCupGroup(1, 2);
        assertEquals("list size is 2",2, list.size());

    }

    @Test
    public void testGetHighestRoundForGroupId(){
        int highestRoundId = jdbcFantasyCupGroupStandingDao.getHighestRoundForGroupId(1);
        assertEquals("highest cuproundid == 2",2,highestRoundId);
    }

    private FantasyCupGroupStanding createObject(){
        FantasyCupGroupStanding fantasyCupGroupStanding = new FantasyCupGroupStanding();
        fantasyCupGroupStanding.setFantasyTeam(new FantasyTeam());
        fantasyCupGroupStanding.getFantasyTeam().setTeamId(1);
        fantasyCupGroupStanding.setFantasyCupGroup(new FantasyCupGroup());
        fantasyCupGroupStanding.getFantasyCupGroup().setId(1);
        fantasyCupGroupStanding.setFantasyRound(new FantasyRound());
        fantasyCupGroupStanding.getFantasyRound().setFantasyRoundId(1);
        
        fantasyCupGroupStanding.setPosition(2);
        fantasyCupGroupStanding.setLastRoundPosition(2);
        fantasyCupGroupStanding.setPoints(0);
        fantasyCupGroupStanding.setGoalsScored(3);
        fantasyCupGroupStanding.setGoalsAgainst(3);
        fantasyCupGroupStanding.setMatchesWon(2);
        fantasyCupGroupStanding.setMatchesLost(2);
        fantasyCupGroupStanding.setMatchesDraw(2);

        
        return fantasyCupGroupStanding;
    }
}
