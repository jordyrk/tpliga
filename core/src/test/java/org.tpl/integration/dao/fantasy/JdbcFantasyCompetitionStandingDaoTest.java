package org.tpl.integration.dao.fantasy;
/*
Created by jordyr, 03.02.11

*/

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.fantasy.FantasyCompetition;
import org.tpl.business.model.fantasy.FantasyCompetitionStanding;
import org.tpl.business.model.fantasy.FantasyRound;
import org.tpl.business.model.fantasy.FantasyTeam;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;

import java.util.List;

import static junit.framework.Assert.assertEquals;

public class JdbcFantasyCompetitionStandingDaoTest {
    JdbcFantasyCompetitionStandingDao jdbcFantasyCompetitionStandingDao;
    @Before
    public void setUp() throws Exception {
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcFantasyCompetitionStandingDao = new JdbcFantasyCompetitionStandingDao();
        jdbcFantasyCompetitionStandingDao.setDataSource(dataSource);
    }

    @After
    public void tearDown() throws Exception {
        jdbcFantasyCompetitionStandingDao = null;
    }

    @Test
    public void testSaveOrUpdateFantasyCompetitionStanding() throws Exception {
        jdbcFantasyCompetitionStandingDao.saveOrUpdateFantasyCompetitionStanding(createObject());
        FantasyCompetitionStanding fcs = jdbcFantasyCompetitionStandingDao.getFantasyCompetitionStandingByIds(1, 1, 1);
        jdbcFantasyCompetitionStandingDao.saveOrUpdateFantasyCompetitionStanding(fcs);
    }

    @Test
    public void testGetFantasyCompetitionStandingByIds() throws Exception {

        FantasyCompetitionStanding fcs = jdbcFantasyCompetitionStandingDao.getFantasyCompetitionStandingByIds(1, 1, 1);
        assertEquals("Points == 10", 10, fcs.getPoints());
    }

    @Test
    public void testGetFantasyCompetitionStandingByRoundAndCompetition() throws Exception {
        List<FantasyCompetitionStanding> list = jdbcFantasyCompetitionStandingDao.getFantasyCompetitionStandingByRoundAndCompetition(1,1);
        assertEquals("list size is 2",2, list.size());
        list = jdbcFantasyCompetitionStandingDao.getFantasyCompetitionStandingByRoundAndCompetition(1,2);
        assertEquals("list size is 2",2, list.size());
        list = jdbcFantasyCompetitionStandingDao.getFantasyCompetitionStandingByRoundAndCompetition(1,3);
        assertEquals("list size is 2",2, list.size());
    }

    @Test
    public void testGetFantasyCompetitionStandingByTeamAndCompetition() throws Exception {
        List<FantasyCompetitionStanding> list = jdbcFantasyCompetitionStandingDao.getFantasyCompetitionStandingByTeamAndCompetition(1,1);
        assertEquals("list size is 3",3, list.size());
        list = jdbcFantasyCompetitionStandingDao.getFantasyCompetitionStandingByTeamAndCompetition(1,2);
        assertEquals("list size is 3",3, list.size());

    }

    @Test
    public void testGetAccumulatedFantasyCompetitionStandingsByCompetition() throws Exception {
        List<FantasyCompetitionStanding> list = jdbcFantasyCompetitionStandingDao.getAccumulatedFantasyCompetitionStandingsByCompetition(1,new int[]{1,2,3});
        assertEquals("list size is 2",2, list.size());

    }

    @Test
    public void testGetLatestUpdatedRoundId() throws Exception {
        int roundId = jdbcFantasyCompetitionStandingDao.getLastUpdatedRoundIdForCompetition(1);
        assertEquals("roundid is 3", 3, roundId);
    }

    @Test
    public void testDeleteFantasyCompetitionStandingsForTeam() throws Exception {
        List<FantasyCompetitionStanding> list = jdbcFantasyCompetitionStandingDao.getFantasyCompetitionStandingByTeamAndCompetition(1,1);
        assertEquals("list size is 3",3, list.size());
        jdbcFantasyCompetitionStandingDao.deleteFantasyCompetitionStandings(1);
        list = jdbcFantasyCompetitionStandingDao.getFantasyCompetitionStandingByTeamAndCompetition(1,1);
        assertEquals("list size is 0",0, list.size());
    }

    private FantasyCompetitionStanding createObject(){
        FantasyCompetitionStanding fantasyCompetitionStanding = new FantasyCompetitionStanding();
        fantasyCompetitionStanding.setFantasyTeam(new FantasyTeam());
        fantasyCompetitionStanding.getFantasyTeam().setTeamId(1);
        fantasyCompetitionStanding.setFantasyCompetition(new FantasyCompetition());
        fantasyCompetitionStanding.getFantasyCompetition().setFantasyCompetitionId(1);
        fantasyCompetitionStanding.setFantasyRound(new FantasyRound());
        fantasyCompetitionStanding.getFantasyRound().setFantasyRoundId(4);
        fantasyCompetitionStanding.setAccumulatedPoints(40);
        fantasyCompetitionStanding.setPosition(2);
        fantasyCompetitionStanding.setLastRoundPosition(2);
        fantasyCompetitionStanding.setPoints(0);
        fantasyCompetitionStanding.setAveragepoints(10);
        fantasyCompetitionStanding.setMinimumpoints(0);
        fantasyCompetitionStanding.setMaximumpoints(2);
        return fantasyCompetitionStanding;
    }
}
