package org.tpl.integration.dao;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tpl.business.model.LeagueRound;
import org.tpl.business.model.Match;
import org.tpl.business.model.Team;
import org.tpl.integration.dao.util.DateUtil;
import org.tpl.integration.dao.util.HSQLDBDatabaseCreator;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
/*
Created by jordyr, 17.okt.2010

*/

public class JDBCMatchDaoTest extends TestCase {

    JDBCMatchDao jdbcMatchDao = new JDBCMatchDao();
    DateUtil dateUtil = new DateUtil();

    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DataSource dataSource = new HSQLDBDatabaseCreator("tpl", getClass().getClassLoader().getResourceAsStream("koren_hsqldb.sql")).createDatabase();
        jdbcMatchDao = new JDBCMatchDao();
        jdbcMatchDao.setDataSource(dataSource);

    }
    @After
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        jdbcMatchDao = null;

    }

    @Test
    public void testSimpleSaveMatch(){
        Match match = new Match();
        match.setExternalId(999);
        match.setMatchDate(new Date());
        match.setHomeTeam(new Team());
        match.getHomeTeam().setTeamId(1);
        match.setAwayTeam(new Team());
        match.getAwayTeam().setTeamId(2);
        match.setMatchUrl("http://www.test.no");
        match.setLeagueRound(new LeagueRound());
        jdbcMatchDao.saveOrUpdateMatch(match);
        assertTrue("match.id > 0", match.getMatchId() >0 );
    }

    @Test
    public void testGetMatchById(){
        Match match= jdbcMatchDao.getMatchById(1);
        assertTrue("match.id = 1", match.getMatchId() == 1);
        assertTrue("match.externalId = 200", match.getExternalId() == 200);
        assertTrue("match.day = 15", dateUtil.getDay(match.getMatchDate()) == 15);
        assertTrue("match.month = 15", dateUtil.getMonth(match.getMatchDate()) == 7);

        assertTrue("match.HomeGoals = 2", match.getHomeGoals() == 2);
        assertTrue("match.AwayGoals = 1", match.getAwayGoals() == 1);

        assertTrue("match.Shots = 10", match.getHomeShots() == 10);
        assertTrue("match.Shots = 5", match.getAwayShots() == 5);

        assertTrue("match.homeshotsontarget = 2", match.getHomeShotsOnTarget() == 7);
        assertTrue("match.awayshotsontarget = 2", match.getAwayShotsOnTarget() == 3);

        assertTrue("match.homefouls = 8", match.getHomeFouls() == 8);
        assertTrue("match.awayfouls = 18", match.getAwayFouls() == 18);

        assertTrue("match.homeoffside = 4", match.getHomeOffside() == 4);
        assertTrue("match.awayoffside = 6", match.getAwayOffside() == 6);

        assertTrue("match.homepossesion = 60", match.getHomePossession() == 60);
        assertTrue("match.awaypossesion = 40", match.getAwayPossession() == 40);

        assertTrue("match.homeyellow = 1", match.getHomeYellow() == 1);
        assertTrue("match.awayyellow = 3", match.getAwayYellow() == 3);

        assertTrue("match.homered = 0", match.getHomeRed() == 0);
        assertTrue("match.awayred = 0", match.getAwayRed() == 0);

        assertTrue("match.homesaves = 2", match.getHomeSaves() == 2);
        assertTrue("match.awaysaves = 6", match.getAwaySaves() == 6);

        assertTrue("match.hometeamid = 1", match.getHomeTeam().getTeamId()== 1);
        assertTrue("match.awayteamid = 2", match.getAwayTeam().getTeamId() == 2);
        assertTrue("match.getLeagueRound.getLeagueRoundId = 1", match.getLeagueRound().getLeagueRoundId() == 1);
        assertTrue("match.matchUrl = http://test.no", match.getMatchUrl().equalsIgnoreCase("http://test.no"));
        assertTrue("match.soccerNetId = 111", match.getSoccerNetId().equalsIgnoreCase("111"));
    }

    @Test
    public void testSaveMatch(){
        Match match = new Match();
        match.setExternalId(999);
        match.setMatchDate(new Date());
        match.setHomeTeam(new Team());
        match.getHomeTeam().setTeamId(11);
        match.setAwayTeam(new Team());
        match.getAwayTeam().setTeamId(12);

        match.setHomeGoals(4);
        match.setAwayGoals(5);
        match.setHomeShots(23);
        match.setAwayShots(18);
        match.setHomeShotsOnTarget(17);
        match.setAwayShotsOnTarget(12);
        match.setHomeFouls(66);
        match.setAwayFouls(55);
        match.setHomeOffside(33);
        match.setAwayOffside(36);
        match.setHomePossession(66);
        match.setAwayPossession(34);
        match.setHomeYellow(10);
        match.setAwayYellow(16);
        match.setHomeRed(5);
        match.setAwayRed(2);
        match.setHomeSaves(88);
        match.setAwaySaves(99);
        match.setLeagueRound(new LeagueRound());
        match.setMatchUrl("http://testesen.no");
        match.setSoccerNetId("345");


        jdbcMatchDao.saveOrUpdateMatch(match);
        assertTrue("match.id > 0", match.getMatchId() >0 );
        Match match2 = jdbcMatchDao.getMatchById(match.getMatchId());
        assertEquals("match.id = match2.id", match.getMatchId(),match2.getMatchId());
        assertEquals("match.externalId = match2.externalId", match.getExternalId(), match2.getExternalId());
        assertEquals("match.day = match2.day", dateUtil.getDay(match.getMatchDate()), dateUtil.getDay(match2.getMatchDate()));
        assertEquals("match.month = match2.month", dateUtil.getMonth(match.getMatchDate()), dateUtil.getMonth(match2.getMatchDate()));


        assertEquals("match.HomeGoals = match2.HomeGoals", match.getHomeGoals(), match2.getHomeGoals());
        assertEquals("match.AwayGoals = match2.AwayGoals", match.getAwayGoals(), match2.getAwayGoals());

        assertEquals("match.HomeShots = match2.HomeShots", match.getHomeShots(), match2.getHomeShots());
        assertEquals("match.AwayShots = match2.AwayShots", match.getAwayShots(), match2.getAwayShots());

        assertEquals("match.homeshotsontarget = match2.homeshotsontarget", match.getHomeShotsOnTarget(),match2.getHomeShotsOnTarget());
        assertEquals("match.awayshotsontarget = match2.awayshotsontarget", match.getAwayShotsOnTarget(), match2.getAwayShotsOnTarget());

        assertEquals("match.homefouls = match2.homefouls", match.getHomeFouls(), match2.getHomeFouls());
        assertEquals("match.awayfouls = match2.awayfouls", match.getAwayFouls(), match2.getAwayFouls());

        assertEquals("match.homeoffside = match2.homeoffside", match.getHomeOffside(), match2.getHomeOffside());
        assertEquals("match.awayoffside = match2.awayoffside", match.getAwayOffside(), match2.getAwayOffside());

        assertEquals("match.homepossesion = match2.homepossesion", match.getHomePossession(), match2.getHomePossession());
        assertEquals("match.awaypossesion = match2.awaypossesion", match.getAwayPossession(), match2.getAwayPossession());

        assertEquals("match.homeyellow = match2.homeyellow", match.getHomeYellow(), match2.getHomeYellow());
        assertEquals("match.awayyellow = match2.awayyellow", match.getAwayYellow(), match2.getAwayYellow());

        assertEquals("match.homered = match2.homered", match.getHomeRed(), match2.getHomeRed());
        assertEquals("match.awayred = match2.awayred", match.getAwayRed(), match2.getAwayRed());

        assertEquals("match.homesaves = match2.homesaves", match.getHomeSaves(), match2.getHomeSaves());
        assertEquals("match.awaysaves = match2.awaysaves", match.getAwaySaves(), match.getAwaySaves());

        assertEquals("match.hometeamid = match2.hometeamid", match.getHomeTeam().getTeamId(), match2.getHomeTeam().getTeamId());
        assertEquals("match.awayteamid = match2.awayteamid", match.getAwayTeam().getTeamId(), match2.getAwayTeam().getTeamId());
        assertEquals("match.leagueround.id = match2.leagueround.id", match.getLeagueRound().getLeagueRoundId(), match2.getLeagueRound().getLeagueRoundId());
        assertEquals("match.matchUrl = match2.matchUrl", match.getMatchUrl(), match2.getMatchUrl());
        assertEquals("match.soccerNetId = match2.soccerNetId", match.getSoccerNetId(),match2.getSoccerNetId());

    }

    @Test
    public void testGetMatchesWithRounds() throws Exception {
        List<Match> matchesWithRounds = jdbcMatchDao.getMatchesWithRounds("1,2", true);
        assertEquals(2,matchesWithRounds.size());
        matchesWithRounds = jdbcMatchDao.getMatchesWithRounds("1,2", false);
        assertEquals(1,matchesWithRounds.size());


    }
}
